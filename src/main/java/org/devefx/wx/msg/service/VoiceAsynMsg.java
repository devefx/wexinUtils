package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;

/**
 * 语音消息
 * @author： youqian.yue
 * @date： 2016-3-10 上午10:00:14
 */
public class VoiceAsynMsg extends AsynMsg {

	private String mediaId;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@Override
	public String getMsgType() {
		return "voice";
	}
	@Override
	protected void outputBody(Writer writer) throws IOException {
		line(writer, "media_id", mediaId, null);
	}
}
