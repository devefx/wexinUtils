package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;

import org.devefx.wx.msg.Msg;

/**
 * 异步消息
 * @author： youqian.yue
 * @date： 2016-3-10 上午10:27:59
 */
public abstract class AsynMsg implements Msg {
	
	protected static final String QUOT				= "\"";
	protected static final String COLON				= ":";
	protected static final String COMMA				= ",";
	protected static final String QUOT_COLON_QUOT	= "\":\"";
	protected static final String QUOT_COMMA_QUOT	= "\",\"";
	protected static final String BRACE_L			= "{";
	protected static final String BRACE_R			= "}";
	
	private String toUser;			// 普通用户openid
	private String customService;	// 客服账号（以某个客服帐号的身份来发消息）
	
	/**
	 * 普通用户openid
	 * @return String
	 */
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	/**
	 * 客服账号（以某个客服帐号的身份来发消息）
	 * @return String
	 */
	public String getCustomService() {
		return customService;
	}
	public void setCustomService(String customService) {
		this.customService = customService;
	}
	/**
	 * 消息类型
	 * @return String
	 */
	public abstract String getMsgType();
	/**
	 * 输出主体
	 * @param writer 写出器
	 */
	protected abstract void outputBody(Writer writer) throws IOException;
	
	protected static void line(Writer writer, String name, String value, String tail) throws IOException {
		writer.append(QUOT);
		writer.append(name);
		writer.append(QUOT_COLON_QUOT);
		writer.append(value);
		writer.append(QUOT);
		if (tail != null) {
			writer.append(tail);
		}
	}
	
	@Override
	public void output(Writer writer) throws IOException {
		writer.append(BRACE_L);
		line(writer, "touser", toUser, COMMA);
		line(writer, "msgtype", getMsgType(), COMMA);
		writer.append(QUOT);
		writer.append(getMsgType());
		writer.append(QUOT);
		writer.append(COLON);
		writer.append(BRACE_L);
		outputBody(writer);
		writer.append(BRACE_R);
		if (customService != null) {
			writer.append(COMMA);
			writer.append(COLON);
			writer.append("customservice");
			writer.append(QUOT);
			writer.append(COLON);
			writer.append(BRACE_L);
			writer.append(QUOT);
			writer.append("kf_account");
			writer.append(QUOT_COLON_QUOT);
			writer.append(customService);
			writer.append(QUOT);
			writer.append(BRACE_R);
		}
		writer.append(BRACE_R);
	}
}
