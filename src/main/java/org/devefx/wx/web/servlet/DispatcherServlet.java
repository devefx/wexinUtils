package org.devefx.wx.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devefx.wx.common.util.ClassUtils;
import org.devefx.wx.common.util.CommonUtils;
import org.devefx.wx.common.util.WeixinUtils;
import org.devefx.wx.common.util.aes.MsgCrypt;
import org.devefx.wx.common.xml.Nodelet;
import org.devefx.wx.common.xml.NodeletParser;
import org.devefx.wx.config.Config;
import org.devefx.wx.config.EncryptType;
import org.devefx.wx.handler.EventHandler;
import org.devefx.wx.handler.annotation.HandlerModule;
import org.devefx.wx.msg.EncryptMsg;
import org.devefx.wx.msg.Msg;
import org.devefx.wx.msg.ReceiveMsg;
import org.devefx.wx.msg.service.AsynMsg;
import org.devefx.wx.web.Constant;
import org.devefx.wx.web.token.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	
	protected final NodeletParser parser = new NodeletParser();
	protected ThreadLocal<ReceiveMsg> localMsg = new ThreadLocal<ReceiveMsg>();
	protected MsgCrypt msgCrypt;
	
	protected Config config;
	protected Map<String, Class<? extends EventHandler>> handlerMap = new HashMap<String, Class<? extends EventHandler>>();
	protected List<String> weChatIP;
	
	public DispatcherServlet() {
		addGeneralMsgNodelets();
		addEventPushNodelets();
		addEncryptNodelets();
	} 
	
	public void init(ServletConfig servletConfig) throws ServletException {
		if (logger.isInfoEnabled()) {
			logger.info("start initialize DispatcherServlet...");
		}
		String configClass = servletConfig.getInitParameter("configClass");
		if (configClass == null)
			throw new RuntimeException("Please set configClass parameter of DispatcherServlet in web.xml");
		Object temp = null;
		try {
			temp = Class.forName(configClass).newInstance();
			if (logger.isInfoEnabled()) {
				logger.info("create configClass successful.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Can not create instance of class: " + configClass, e);
		}
		if (temp instanceof Config) {
			config = (Config) temp;
			config.init();
		} else
			throw new RuntimeException("Can not create instance of class: " + configClass + ". Please check the config in web.xml");

		String basePackage = servletConfig.getInitParameter("basePackage");
		if (basePackage == null)
			throw new RuntimeException("Please set basePackage parameter of DispatcherServlet in web.xml");
		if (logger.isInfoEnabled()) {
			logger.info("start load EventHandler...");
		}
		Set<Class<?>> classes = ClassUtils.getClasses(basePackage);
		for (Class<?> clazz : classes) {
			if (EventHandler.class.isAssignableFrom(clazz) &&
					clazz.isAnnotationPresent(HandlerModule.class)) {
				HandlerModule module = clazz.getAnnotation(HandlerModule.class);
				String handlerName = module.msgType();
				if (handlerName.equals(Constant.MSG_EVENT)) {
					handlerName += "#" + module.event();
				}
				handlerMap.put(handlerName, (Class<? extends EventHandler>) clazz);
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("load EventHandler finish.");
		}
		// 获取微信服务器IP地址
		AccessToken token = config.getAccessToken();
		if (token == null) {
			logger.error("get accessToken failed.");
			throw new RuntimeException("get accessToken failed.");
		}
		weChatIP = WeixinUtils.getCallbackIP(token.getAccessToken());
		if (logger.isInfoEnabled()) {
			logger.info("weChatIP:" + weChatIP);
		}
		this.msgCrypt = new MsgCrypt(config.getEncodingAESKey(), config.getAppID());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			// 开始服务对接
			if ("GET".equals(request.getMethod()) && hasParam(request, "signature")) {
				if (logger.isInfoEnabled()) {
					logger.info("start WeChat server validate.");
				}
				if (checkSignature(request)) {
					if (logger.isInfoEnabled()) {
						logger.info("server validate successful.");
					}
					String echostr = request.getParameter("echostr");
					out.print(echostr);
				} else {
					logger.error("server validator failed.");
				}
				return;
			}
			// 处理微信通知
			if (isWeChatRequest(request)) {
				// 验证token
				if (hasParam(request, "signature") && !checkSignature(request)) {
					if (logger.isInfoEnabled()) {
						logger.info("Token验证失败");
					}
					out.print("Token验证失败");
					return;
				}
				try {
					parser.parse(request.getInputStream());
					// 判断消息是否加密
					if ("aes".equals(request.getParameter("encrypt_type"))) {
						String msgSignature = request.getParameter("msg_signature");
						String timestamp = request.getParameter("timestamp");
						String nonce = request.getParameter("nonce");
						// 验证消息体签名的正确性
						String sortStr = CommonUtils.createLinkString(config.getToken(), timestamp, nonce, getMsg().getEncrypt());
						String sha1Str = CommonUtils.sha1(sortStr);
						if (!msgSignature.equals(sha1Str)) {
							if (logger.isInfoEnabled()) {
								logger.info("消息签名验证不通过");
							}
							out.print("消息签名验证不通过");
							return;
						}
						String msgText = msgCrypt.decrypt(getMsg().getEncrypt());
						if (logger.isDebugEnabled()) {
							logger.debug("解密后：" + msgText);
						}
						parser.parse(new StringReader(msgText));
					}
					String handlerName = getMsg().getMsgType();
					if (handlerName.equals(Constant.MSG_EVENT)) {
						handlerName += "#" + getMsg().getEvent();
					}
					Class<? extends EventHandler> handlerClass = handlerMap.get(handlerName);
					if (handlerClass != null) {
						EventHandler handler = config.getHandler(handlerClass);
						AccessToken accessToken = config.getAccessToken();
						Msg replyMsg = handler.handle(accessToken, getMsg());
						if (replyMsg != null) {
							Msg _replyMsg = replyMsg;
							// 如果是安全模式
							if (config.getEncryptType() == EncryptType.SECURITY
									&& replyMsg != null) {
								StringWriter writer = new StringWriter();
								replyMsg.output(writer);
								
								EncryptMsg encryptMsg = new EncryptMsg();
								encryptMsg.setEncrypt(msgCrypt.encrypt(writer.toString()));
								encryptMsg.setTimestamp(String.valueOf(new Date().getTime()));
								encryptMsg.setNonce(CommonUtils.getRandomStr(8));
								String sortStr = CommonUtils.createLinkString(config.getToken(), encryptMsg.getTimestamp(), 
										encryptMsg.getEncrypt(), encryptMsg.getNonce());
								encryptMsg.setMsgSignature(CommonUtils.sha1(sortStr));
								replyMsg = encryptMsg;
							}
							if (_replyMsg instanceof AsynMsg) {
								out.print(WeixinUtils.sendMessage(accessToken.getAccessToken(), replyMsg));
							} else {
								replyMsg.output(out);
							}
						} else {
							out.print("success");
						}
					}
				} catch (Exception e) {
					logger.error("出现异常，异常原因：" + e.getMessage());
				}
			}
		} finally {
			out.flush();
		}
	}
	
	/**
	 * 判断请求中是否存在某个参数
	 * 
	 * @param request 请求对象
	 * 
	 * @param name 需要判断的参数名称
	 * 
	 * @return boolean 判断结果
	 */
	protected boolean hasParam(HttpServletRequest request, String name) {
		return request.getParameter(name) != null;
 	}
	
	/**
	 * 验证服务器地址的有效性
	 * 
	 * @param request 请求对象
	 * 
	 * @return boolean 验证结果
	 */
	protected boolean checkSignature(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		
		String sortStr = CommonUtils.createLinkString(config.getToken(), timestamp, nonce);
		String sha1Str = CommonUtils.sha1(sortStr);

		if (signature.equals(sha1Str)) {
			return true;
		}
		return false;
	}
	/**
	 * 判断请求是否为来自微信服务器
	 * 
	 * @param request 请求对象
	 * @return boolean 判断结果
	 */
	protected boolean isWeChatRequest(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return weChatIP.contains(ip);
	}
	/**
	 * get local msg
	 * @return Msg
	 */
	ReceiveMsg getMsg() {
		return localMsg.get();
	}
	/**
	 * 普通消息解析器
	 */
	protected void addGeneralMsgNodelets() {
		parser.addNodelet("/xml", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				localMsg.set(new ReceiveMsg());
			}
		});
		parser.addNodelet("/xml/ToUserName", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setToUserName(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/FromUserName", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setFromUserName(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/CreateTime", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setCreateTime(new Date(Long.parseLong(node.getTextContent())));
			}
		});
		parser.addNodelet("/xml/MsgType", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setMsgType(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Content", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setContent(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/MsgId", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setMsgId(Long.parseLong(node.getTextContent()));
			}
		});
		parser.addNodelet("xml/PicUrl", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setPicUrl(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/MediaId", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setMediaId(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Format", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setFormat(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Recognition", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setRecognition(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/ThumbMediaId", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setThumbMediaId(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Location_X", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setLocation_X(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Location_Y", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setLocation_Y(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Scale", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setScale(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Label", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setLabel(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Title", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setTitle(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Description", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setDescription(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Url", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setUrl(node.getTextContent());
			}
		});
	}
	/**
	 * 事件推送解析器
	 */
	protected void addEventPushNodelets() {
		parser.addNodelet("/xml/Event", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setEvent(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/EventKey", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setEventKey(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Ticket", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setTicket(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Latitude", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setLatitude(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Longitude", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setLongitude(node.getTextContent());
			}
		});
		parser.addNodelet("/xml/Precision", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setPrecision(node.getTextContent());
			}
		});
	}
	
	protected void addEncryptNodelets() {
		parser.addNodelet("/xml/Encrypt", new Nodelet() {
			@Override
			public void process(Node node) throws Exception {
				getMsg().setEncrypt(node.getTextContent());
			}
		});
	}
}
