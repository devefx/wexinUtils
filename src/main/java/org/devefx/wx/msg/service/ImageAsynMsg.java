package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;

/**
 * 图片消息
 * @author： youqian.yue
 * @date： 2016-3-9 下午4:36:38
 */
public class ImageAsynMsg extends AsynMsg {
	
	private String mediaId;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@Override
	public String getMsgType() {
		return "image";
	}
	@Override
	protected void outputBody(Writer writer) throws IOException {
		line(writer, "media_id", mediaId, null);
	}
}
