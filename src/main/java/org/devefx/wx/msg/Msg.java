package org.devefx.wx.msg;

import java.io.IOException;
import java.io.Writer;

/**
 * 消息
 * @author： youqian.yue
 * @date： 2016-3-10 上午10:19:24
 */
public interface Msg {
	/**
	 * 格式化输出消息
	 * @param writer
	 * @throws IOException
	 */
	public void output(Writer writer) throws IOException;
}
