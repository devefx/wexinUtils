package org.devefx.wx.msg;

import java.io.IOException;
import java.io.Writer;

/**
 * 密文消息
 * @author： youqian.yue
 * @date： 2016-3-10 上午10:35:03
 */
public class EncryptMsg implements Msg {

	private String encrypt;			// 密文，仅在兼容或安全模式下会出现
	private String msgSignature;	// 消息签名
	private String timestamp;		// 时间戳
	private String nonce;			// 随机字符串
	/**
	 * 密文，仅在兼容或安全模式下会出现
	 * @return String
	 */
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	/**
	 * 消息签名
	 * @return String
	 */
	public String getMsgSignature() {
		return msgSignature;
	}
	public void setMsgSignature(String msgSignature) {
		this.msgSignature = msgSignature;
	}
	/**
	 * 时间戳
	 * @return String
	 */
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * 随机字符串
	 * @return String
	 */
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	@Override
	public void output(Writer writer) throws IOException {
		writer.append("<xml>");
		
		line(writer, "Encrypt", encrypt);
		line(writer, "MsgSignature", msgSignature);
		line(writer, "TimeStamp", timestamp);
		line(writer, "Nonce", nonce);
		
		writer.append("</xml>");
	}
	private void line(Writer writer, String name, Object value) throws IOException {
		if (value != null) {
			writer.append("<" + name + "><![CDATA[");
			writer.append(String.valueOf(value));
			writer.append("]]></" + name + ">");
		}
	}
	
}
