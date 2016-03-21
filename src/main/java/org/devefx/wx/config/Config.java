package org.devefx.wx.config;

import java.util.HashMap;
import java.util.Map;

import org.devefx.wx.handler.EventHandler;
import org.devefx.wx.handler.EventHandlerFactory;
import org.devefx.wx.web.token.AccessToken;

public abstract class Config implements EventHandlerFactory {
	
	private String appID;				// 应用ID
	private String appSecret;			// 应用密匙
	
	private String mchId;				// 商户号
	private String deviceInfo = "WEB";	// 终端设备号
	private String key;					// 商户密匙
	
	private String url;					// 服务器URL
	private String token;				// 用户Token
	private String encodingAESKey;		// 消息加密密匙
	private EncryptType encryptType;	// 消息加解密方式
	
	private NotifyHandler notifyHandler;// 支付结果通知处理器
	
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEncodingAESKey() {
		return encodingAESKey;
	}
	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}
	public EncryptType getEncryptType() {
		return encryptType;
	}
	public void setEncryptType(EncryptType encryptType) {
		this.encryptType = encryptType;
	}
	public NotifyHandler getNotifyHandler() {
		return notifyHandler;
	}
	public void setNotifyHandler(NotifyHandler notifyHandler) {
		this.notifyHandler = notifyHandler;
	}
	/**
	 * 初始化
	 */
	public abstract void init();
	/**
	 * 获取AccessToken
	 * 
	 * @return AccessToken
	 */
	public abstract AccessToken getAccessToken();
	
	private static final Map<Class<? extends EventHandler>, EventHandler> handlerMap
		= new HashMap<Class<? extends EventHandler>, EventHandler>();
	
	@Override
	public EventHandler getHandler(Class<? extends EventHandler> handlerClass) throws Exception {
		if (handlerClass != null) {
			EventHandler handler = handlerMap.get(handlerClass);
			if (handler == null) {
				handler = handlerClass.newInstance();
				handlerMap.put(handlerClass, handler);
			}
			return handler;
		}
		return null;
	}
}
