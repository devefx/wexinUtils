package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;

/**
 * 卡券
 * @author： youqian.yue
 * @date： 2016-3-9 下午4:38:47
 */
public class CardAsynMsg extends AsynMsg {

	private String cardId;
	private String code;
	private String openid;
	private String timestamp;
	private String signature;
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String getMsgType() {
		return "wxcard";
	}
	@Override
	protected void outputBody(Writer writer) throws IOException {
		
	}
}
