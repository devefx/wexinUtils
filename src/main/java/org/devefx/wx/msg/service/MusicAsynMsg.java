package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;

/**
 * 音乐消息
 * @author： youqian.yue
 * @date： 2016-3-9 下午4:38:11
 */
public class MusicAsynMsg extends AsynMsg {

	private String title;
	private String description;
	private String musicUrl;
	private String hqMusicUrl;
	private String thumbMediaId;
	
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
	public String getMusicUrl() {
		return musicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	public String getHqMusicUrl() {
		return hqMusicUrl;
	}
	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	@Override
	public String getMsgType() {
		return "music";
	}
	@Override
	protected void outputBody(Writer writer) throws IOException {
		line(writer, "title", title, COMMA);
		line(writer, "description", description, COMMA);
		line(writer, "musicurl", musicUrl, COMMA);
		line(writer, "hqmusicurl", hqMusicUrl, COMMA);
		line(writer, "thumb_media_id", thumbMediaId, null);
	}
}
