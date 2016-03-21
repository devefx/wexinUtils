package org.devefx.wx.web.servlet.pay;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.devefx.wx.common.pay.order.support.OrderInfo;
import org.devefx.wx.config.Config;
import org.devefx.wx.web.servlet.DispatcherServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayNotifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	
	protected Config config;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		if (logger.isInfoEnabled()) {
			logger.info("start initialize PayNotifyServletServlet...");
		}
		String configClass = servletConfig.getInitParameter("configClass");
		if (configClass == null)
			throw new RuntimeException("Please set configClass parameter of PayNotifyServletServlet in web.xml");
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
		if (config.getNotifyHandler() == null) {
			throw new RuntimeException("this config NotifyHandler is null");
		}
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		// 处理订单，并响应请求
		StringBuffer outBuf = new StringBuffer("<xml>");
		try {
			// 解析订单数据
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.parser(request.getInputStream(), config.getKey());
			// 判断参数是否验证通过
			if (orderInfo.isValidate()) {
				config.getNotifyHandler().handle(orderInfo);
				outBuf.append("<return_code><![CDATA[SUCCESS]]></return_code>");
				return;
			}
			outBuf.append("<return_code><![CDATA[FAIL]]></return_code>");
		} catch (Exception e) {
			logger.error("处理微信支付结果通知时发生错误，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			outBuf.append("</xml>");
			response.getWriter().print(outBuf);
		}
	}
}
