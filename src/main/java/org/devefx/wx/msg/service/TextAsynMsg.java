package org.devefx.wx.msg.service;

import java.io.IOException;
import java.io.Writer;

/**
 * 文本消息
 * @author： youqian.yue
 * @date： 2016-3-10 上午9:59:35
 */
public class TextAsynMsg extends AsynMsg {
	
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String getMsgType() {
		return "text";
	}
	@Override
	protected void outputBody(Writer writer) throws IOException {
		line(writer, "content", content, null);
	}
}
