package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;

/**
 * 视频消息
 * @author： youqian.yue
 * @date： 2016-3-9 下午4:39:11
 */
public class VideoAsynMsg extends AsynMsg {

	private String mediaId;
	private String thumbMediaId;
	private String title;
	private String description;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String getMsgType() {
		return "video";
	}
	@Override
	protected void outputBody(Writer writer) throws IOException {
		line(writer, "media_id", mediaId, COMMA);
		line(writer, "thumb_media_id", thumbMediaId, COMMA);
		line(writer, "title", title, COMMA);
		line(writer, "description", description, null);
	}
}
