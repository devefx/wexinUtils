package org.devefx.wx.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
			return bytesToHex(digest.digest());
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
			return bytesToHex(digest.digest());
		} catch (Exception e) {
		}
		return text;
	}
	/**
	 * 字节数组转16进制
	 * @param bytes
	 * @return String
	 */
	public static String bytesToHex(byte[] bytes) {
		final String HEX = "0123456789abcdef";
		StringBuilder buff = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			// 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			buff.append(HEX.charAt((b >> 4) & 0x0f));
			// 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
			buff.append(HEX.charAt(b & 0x0f));
		}
		return buff.toString();
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
	/**
	 * 将流写到文件
	 * @param is 要写出的流
	 * @return 文件
	 * @throws IOException
	 */
	public static File outFile(InputStream is, String filename) throws IOException {
		String dir = System.getProperty("java.io.tmpdir");
		int len;
		byte[] buf = new byte[1024];
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(dir + filename);
			while ((len = is.read(buf)) > 0) {
				fileOutputStream.write(buf, 0, len);
			}
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		return new File(dir + filename);
	}
}
