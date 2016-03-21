package org.devefx.wx.common.util;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;
import org.devefx.wx.common.httpclient.HttpURI;
import org.devefx.wx.common.httpclient.impl.HttpClientImpl;
import org.devefx.wx.msg.Msg;
import org.devefx.wx.web.token.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public abstract class WeixinUtils {
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(WeixinUtils.class);
	/**
	 * 获取access token
	 * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。
	 * access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新，重复获
	 * 取将导致上次获取的access_token失效。
	 * 
	 * @param grantType 获取access_token填写client_credential
	 * @param appid 第三方用户唯一凭证
	 * @param secret 第三方用户唯一凭证密钥，即appsecret
	 * 
	 * @return AccessToken 微信全局票据
	 */
	public static AccessToken getAccessToken(String grantType, String appid, String secret) {
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/cgi-bin/token");
			uri.addPart("grant_type", grantType);
			uri.addPart("appid", appid);
			uri.addPart("secret", secret);
			JSONObject json = clientImpl.getJson(uri);
			return new AccessToken(json.getString("access_token"), json.getIntValue("expires_in"));
		} catch (Exception e) {
			logger.error("获取access token失败，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取微信服务器IP地址
	 * 
	 * @param accessToken 公众号的access_token
	 * @return List<String> 微信服务器IP地址列表
	 */
	public static List<String> getCallbackIP(String accessToken) {
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/cgi-bin/getcallbackip");
			uri.addPart("access_token", accessToken);
			JSONObject json = clientImpl.getJson(uri);
			return json.getObject("ip_list", List.class);
		} catch (Exception e) {
			logger.error("获取微信服务器IP地址失败，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 添加客服帐号
	 * 
	 * @param accessToken 公众号的access_token
	 * @param kfAccount 完整客服账号，格式为：账号前缀@公众号微信号
	 * @param nickname 客服昵称
	 * @param password 客服账号登录密码
	 * @return JSONObject
	 */
	public static JSONObject addCustomService(String accessToken, String kfAccount, String nickname, String password) {
		JSONObject data = new JSONObject();
		data.put("kf_account", kfAccount);
		data.put("nickname", nickname);
		data.put("password", password);
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/customservice/kfaccount/add");
			uri.addPart("access_token", accessToken);
			clientImpl.getJson(uri, new StringEntity(data.toJSONString(), "utf-8"));
		} catch (Exception e) {
			logger.error("添加客服帐号，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 修改客服帐号
	 * 
	 * @param accessToken 公众号的access_token
	 * @param kfAccount 完整客服账号，格式为：账号前缀@公众号微信号
	 * @param nickname 客服昵称
	 * @param password 客服账号登录密码
	 * @return JSONObject
	 */
	public static JSONObject updateCustomService(String accessToken, String kfAccount, String nickname, String password) {
		JSONObject data = new JSONObject();
		data.put("kf_account", kfAccount);
		data.put("nickname", nickname);
		data.put("password", password);
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/customservice/kfaccount/update");
			uri.addPart("access_token", accessToken);
			clientImpl.getJson(uri, new StringEntity(data.toJSONString(), "utf-8"));
		} catch (Exception e) {
			logger.error("修改客服帐号失败，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除客服帐号
	 * 
	 * @param accessToken 公众号的access_token
	 * @param kfAccount 完整客服账号，格式为：账号前缀@公众号微信号
	 * @param nickname 客服昵称
	 * @param password 客服账号登录密码
	 * @return JSONObject
	 */
	public static JSONObject deleteCustomService(String accessToken, String kfAccount, String nickname, String password) {
		JSONObject data = new JSONObject();
		data.put("kf_account", kfAccount);
		data.put("nickname", nickname);
		data.put("password", password);
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/customservice/kfaccount/del");
			uri.addPart("access_token", accessToken);
			clientImpl.getJson(uri, new StringEntity(data.toJSONString(), "utf-8"));
		} catch (Exception e) {
			logger.error("删除客服帐号失败，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 设置客服帐号的头像
	 * 
	 * @param accessToken 公众号的access_token
	 * @param kfAccount 完整客服账号，格式为：账号前缀@公众号微信号
	 * @param avatarImage 客服头像
	 * @return JSONObject
	 */
	public static JSONObject setCustomServiceAvatar(String accessToken, String kfAccount, File avatarImage) {
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg");
			uri.addPart("access_token", accessToken);
			uri.addPart("kf_account", kfAccount);
			return clientImpl.getJson(uri, new FormBodyPart("", new FileBody(avatarImage)));
		} catch (Exception e) {
			logger.error("设置客服帐号的头像失败，异常原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取所有客服账号
	 * 
	 * @param accessToken 公众号的access_token
	 * @return JSONObject
	 */
	public static JSONObject getCustomServiceList(String accessToken) {
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/cgi-bin/customservice/getkflist");
			uri.addPart("access_token", accessToken);
			return clientImpl.getJson(uri);
		} catch (Exception e) {
			logger.error("获取所有客服账号失败，异常原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 客服接口-发消息
	 * 
	 * @param accessToken 公众号的access_token
	 * @param msg 欲发送的消息
	 * @return String
	 */
	public static String sendMessage(final String accessToken, final Msg msg) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClientImpl clientImpl = new HttpClientImpl();
				StringWriter writer = new StringWriter();
				try {
					msg.output(writer);
					
					HttpURI uri = new HttpURI("https://api.weixin.qq.com/cgi-bin/message/custom/send");
					uri.addPart("access_token", accessToken);
					JSONObject result = clientImpl.getJson(uri, new StringEntity(writer.toString(), "utf-8"));
					if (result.getIntValue("errcode") != 0) {
						logger.error("客户接口发送信息失败，错误原因：" + result.toJSONString());
					}
				} catch (Exception e) {
					logger.error("客户接口发送信息失败，错误原因：" + e.getMessage(), e);
					e.printStackTrace();
				}
			}
		}).start();
		return "success";
	}
	/**
	 * 上传图文消息内的图片获取URL
	 * 
	 * <p>
	 * 请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。<br>
	 * 图片仅支持jpg/png格式，大小必须在1MB以下。
	 * </p>
	 * 
	 * @param accessToken 公众号的access_token
	 * @param imgfile 图片文件
	 * @return String 上传图片的URL地址
	 */
	public static String uploadimg(String accessToken, File imgfile) {
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/cgi-bin/media/uploadimg");
			uri.addPart("access_token", accessToken);
			HttpResponse response = clientImpl.excute(uri, new FormBodyPart("media", new FileBody(imgfile)));
			String result = EntityUtils.toString(response.getEntity());
			JSONObject json = JSONObject.parseObject(result);
			return json.getString("url");
		} catch (Exception e) {
			logger.error("上传图文消息内的图片失败，错误原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	
/*	private static final String accessToken = "Z7TY8-ODnVtRmXNDKLhNxialAiffVG4DUB-mA0DB1WbrID7v3Lz7oca_0lpSMbi4pyLgg_n5u6lZ4ZcUk8gxZRtjwSaNnnW_MAOpBsm8brUyljEHnpuATVUaQYcOPxv0HZGaAHALDK";
	public static void main(String[] args) throws Exception {
		File file = new File("d:/01.jpg");
		
		FileInputStream inputStream = new FileInputStream(file);
		
		ContentBody body = new InputStreamBody(inputStream, file.getName());
		
		body = new FileBody(file);
		
		HttpClientImpl clientImpl = new HttpClientImpl();
		try {
			HttpURI uri = new HttpURI("https://api.weixin.qq.com/cgi-bin/media/uploadimg");
			uri.addPart("access_token", accessToken);
			HttpResponse response = clientImpl.excute(uri, new FormBodyPart("media", body));
			String result = EntityUtils.toString(response.getEntity());
			JSONObject json = JSONObject.parseObject(result);
			
			System.out.println(json);
		} catch (Exception e) {
			logger.error("上传图文消息内的图片失败，异常原因：" + e.getMessage(), e);
			e.printStackTrace();
		}
	}*/
}
