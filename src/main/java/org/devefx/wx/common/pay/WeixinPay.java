package org.devefx.wx.common.pay;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.devefx.wx.common.httpclient.HttpURI;
import org.devefx.wx.common.httpclient.impl.HttpClientImpl;
import org.devefx.wx.common.pay.order.Order;
import org.devefx.wx.common.pay.order.support.OrderInfo;
import org.devefx.wx.common.pay.util.SignPart;
import org.devefx.wx.common.pay.util.XmlPart;
import org.devefx.wx.common.util.CommonUtils;
import org.devefx.wx.web.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信支付
 * @author： youqian.yue
 * @date： 2016-3-14 下午4:45:50
 */
public abstract class WeixinPay {
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(WeixinPay.class);
	/**
	 * 统一下单
	 * 
	 * @return {return_code: 返回状态码[, return_msg: 返回信息]}
	 * @throws PayException 
	 */
	public static OrderInfo unifiedOrder(Order order) throws PayException {
		HttpClientImpl clientImpl = new HttpClientImpl();
		InputStream is = null;
		try {
			StringWriter writer = new StringWriter();
			order.output(writer);
			HttpURI uri = new HttpURI("https://api.mch.weixin.qq.com/pay/unifiedorder");
			HttpResponse response = clientImpl.excute(uri, new StringEntity(writer.toString(), "utf-8"));
			
			is = response.getEntity().getContent();
			
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.parser(is, order.getKey());
			if (Constant.SUCCESS.equals(orderInfo.getReturnCode())) {
				if (!Constant.SUCCESS.equals(orderInfo.getResultCode())) {
					throw new PayException(orderInfo.getErrCode());
				}
			}
			return orderInfo;
		} catch (PayException e) {
			throw e;
		} catch (Exception e) {
			logger.error("调用统一下单接口失败，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * 获取网页授权access_token
	 * 
	 * @param appid 公众号的唯一标识
	 * @param secret 公众号的appsecret
	 * @param code 授权code
	 * @return JSONObject
	 */
	public static JSONObject oauthAccessToken(String appid, String secret, String code) {
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/sns/oauth2/access_token");
			uri.addPart("appid", appid);
			uri.addPart("secret", secret);
			uri.addPart("code", code);
			uri.addPart("grant_type", "authorization_code");
			return clientImpl.getJson(uri);
		} catch (Exception e) {
			logger.error("获取网页授权access_token失败，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查询订单
	 * 
	 * @param appid 公众账号ID
	 * @param mchId 商户号
	 * @param transactionId 微信订单号
	 * @param outTradeNo 商户订单号
	 * @param key 商户密匙
	 * @return JSONObject
	 */
	public static OrderInfo orderQuery(String appid, String mchId, String transactionId,
			String outTradeNo, String key) {
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			XmlPart xmlPart = new XmlPart();
			xmlPart.addPart("appid", appid);
			xmlPart.addPart("mch_id", mchId);
			xmlPart.addPart("transaction_id", transactionId);
			xmlPart.addPart("out_trade_no", outTradeNo);
			xmlPart.addPart("nonce_str", CommonUtils.getRandomStr(32));
			xmlPart.addPart("sign", new SignPart(key, xmlPart.getParts()).build(SignPart.MD5));
			HttpURI uri = new HttpURI("https://api.mch.weixin.qq.com/pay/orderquery");
			HttpResponse response = clientImpl.excute(uri, new StringEntity(xmlPart.toString()));
			if (response.getStatusLine().getStatusCode() == 200) {
				OrderInfo orderRecord = new OrderInfo();
				orderRecord.parser(response.getEntity().getContent(), key);
				return orderRecord;
			}
		} catch (Exception e) {
			logger.error("查询订单失败，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
}
