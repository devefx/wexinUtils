package org.devefx.wx.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * CommonUtils
 * @author： youqian.yue
 * @date： 2016-3-4 上午10:13:54
 */
public abstract class CommonUtils {
	/**
	 * 把所有参数进行字典排序，并拼接成字符串
	 * 
	 * @param args 需要排序并参与字符拼接的参数
	 * 
	 * @return String 拼接后字符串
	 */
	public static String createLinkString(String ...args) {
		List<String> values = new LinkedList<String>();
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && !args[i].isEmpty()) {
				values.add(args[i]);
			}
		}
		Collections.sort(values);
		
		String prestr = "";
		for (String value : values) {
			if (value != null) {
				prestr += value;
			}
		}
		return prestr;
	}
	/**
	 * 把参数进行sha1加密
	 * @param text 需要加密的参数
	 * @return String 加密后的字符串
	 */
	public static String sha1(String text) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("sha1");
			digest.update(text.getBytes());
			return new BigInteger(1, digest.digest()).toString(16);
		} catch (Exception e) {
		}
		return text;
	}
	/**
	 * 把参数进行sha1加密
	 * @param text 需要加密的参数
	 * @return String 加密后的字符串
	 */
	public static String md5(String text) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("md5");
			digest.update(text.getBytes());
			return new BigInteger(1, digest.digest()).toString(16);
		} catch (Exception e) {
		}
		return text;
	}
	/**
	 * 将字符串第一个字母转换成小写
	 * @param s 待转换的字符串
	 * @return String 转换后的字符串
	 */
	public static String firstToLower(String s) {
		if (s != null && !s.isEmpty()) {
			String newString = s.substring(0, 1).toLowerCase();
			if (s.length() > 1) {
				return newString + s.substring(1);
			}
			return newString;
		}
		return s;
	}
	
	/**
	 * 将字符串第一个字母转换成大写
	 * @param s 待转换的字符串
	 * @return String 转换后的字符串
	 */
	public static String firstToUpper(String s) {
		if (s != null && !s.isEmpty()) {
			String newString = s.substring(0, 1).toUpperCase();
			if (s.length() > 1) {
				return newString + s.substring(1);
			}
			return newString;
		}
		return s;
	}
	/**
	 * 随机生成指定位字符串
	 * @param length 长度
	 * @return String 随机字符串
	 */
	public static String getRandomStr(int length) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			buf.append(base.charAt(number));
		}
		return buf.toString();
	}
}
