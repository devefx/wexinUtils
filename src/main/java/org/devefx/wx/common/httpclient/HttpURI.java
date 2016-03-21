package org.devefx.wx.common.httpclient;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * HttpURI
 * @author： youqian.yue
 * @date： 2016-3-11 下午3:15:10
 */
public class HttpURI {
	
	protected StringBuffer buf;
	protected boolean hasQuery;
	
	public HttpURI(String spec) throws URISyntaxException {
		URI uri = new URI(spec);
		buf = new StringBuffer(spec.length());
		buf.append(uri.getScheme());
		buf.append(":");
		buf.append(uri.getSchemeSpecificPart());
		if (uri.getQuery() != null) {
			hasQuery = true;
		}
	}
	/**
	 * 添加url参数
	 * 
	 * @param name 参数名
	 * @param value 参数值
	 */
	public void addPart(String name, String value) {
		try {
			buf.append(hasQuery ? "&" : "?");
			buf.append(URLEncoder.encode(name, "utf-8"));
			buf.append("=");
			buf.append(URLEncoder.encode(value, "utf-8"));
			hasQuery = true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取URI
	 * 
	 * @return URI
	 */
	public URI getURI() {
		try {
			return new URI(buf.toString());
		} catch (URISyntaxException e) {
		}
		return null;
	}
	
	@Override
	public String toString() {
		return buf.toString();
	}
}
