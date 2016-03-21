package org.devefx.wx.common.pay.util;

import java.util.HashMap;
import java.util.Map;

/**
 * XmlPart
 * @author： youqian.yue
 * @date： 2016-3-21 上午10:47:02
 */
public class XmlPart {
	
	private Map<String, String> parts = new HashMap<String, String>();
	
	public void addPart(String name, String value) {
		if (value != null && !value.isEmpty()) {
			parts.put(name, value);
		}
	}
	
	public Map<String, String> getParts() {
		return parts;
	}
	
	public void output(StringBuffer buf) {
		buf.append("<xml>");
		for (Map.Entry<String, String> entry : parts.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			buf.append("<" + key + ">");
			buf.append(val);
			buf.append("</" + key + ">");
		}
		buf.append("</xml>");
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		output(buf);
		return buf.toString();
	}
}
