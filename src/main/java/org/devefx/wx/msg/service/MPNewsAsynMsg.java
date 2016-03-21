package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;

/**
 * 图文消息（点击跳转到图文消息页面） 
 * @author： youqian.yue
 * @date： 2016-3-9 下午4:38:37
 */
public class MPNewsAsynMsg extends AsynMsg {
	
	private String mediaId;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@Override
	public String getMsgType() {
		return "mpnews";
	}
	@Override
	protected void outputBody(Writer writer) throws IOException {
		line(writer, "media_id", mediaId, null);
	}
}
