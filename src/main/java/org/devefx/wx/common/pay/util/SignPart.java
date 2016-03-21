package org.devefx.wx.common.pay.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 微信支付参数签名
 * @author： youqian.yue
 * @date： 2016-3-21 上午10:33:59
 */
public class SignPart {
	
	public static final String MD5 = "md5";
	public static final String SHA1 = "sha1";
	
	private String key;	// 商户密匙
	
	private Set<String> set = new TreeSet<String>(new Comparator<String>() {
		@Override
		public int compare(String s1, String s2) {
			return s1.compareTo(s2);
		}
	});
	
	public SignPart() {
	}
	public SignPart(String key) {
		this(key, null);
	}
	public SignPart(String key, Map<String, ?> parts) {
		setKey(key);
		addParts(parts);
	}
	/**
	 * 获取商户密匙
	 * @return String
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 设置商户密匙
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 添加一个参数
	 * @param name
	 * @param value
	 */
	public void addPart(String name, String value) {
		if (value != null && !value.isEmpty()) {
			set.add(name + "=" + value);
		}
	}
	/**
	 * 添加参数
	 * @param parts
	 */
	public void addParts(Map<String, ?> parts) {
		if (parts != null) {
			for (Map.Entry<String, ?> entry : parts.entrySet()) {
				Object object = entry.getValue();
				if (object != null) {
					addPart(entry.getKey(), String.valueOf(object));
				}
			}
		}
	}
	/**
	 * 签名运算
	 * @param algorithm 算法：sha1、md5
	 * @return String
	 */
	public String build(String algorithm) {
		if (!set.isEmpty()) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance(algorithm);
				digest.update(toString().getBytes("utf-8"));
				String result = new BigInteger(1, digest.digest()).toString(16);
				for (int i = result.length(); i < 32; i++) {
					result = "0" + result;
				}
				return result.toUpperCase();
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (String value : set) {
			buf.append(value);
			buf.append("&");
		}
		buf.append("key=");
		buf.append(key);
		return buf.toString();
	}
}
