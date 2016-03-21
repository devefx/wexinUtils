package org.devefx.wx.common.pay.order.support;

import java.io.InputStream;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.devefx.wx.common.pay.util.SignPart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 微信数据
 * @author： youqian.yue
 * @date： 2016-3-18 下午5:47:36
 */
public abstract class MessageData {
	
	protected static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	protected boolean validate;
	/**
	 * 数据是否有效
	 * 
	 * @param key 商户密匙
	 * @return boolean
	 */
	public boolean isValidate() {
		return validate;
	}
	/**
	 * 获取Class
	 * @return Class<?>
	 */
	protected abstract Class<?> getRealClass();
	/**
	 * 获取签名
	 * 
	 * @return String
	 */
	protected abstract String getSign();

	/**
	 * 解析数据
	 * 
	 * @param inputStream
	 * @throws Exception
	 */
	public final void parser(InputStream inputStream, String key) throws Exception {
		SignPart signPart = new SignPart(key);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String name = node.getNodeName();
				String value = node.getTextContent();
				try {
					Field field = getRealClass().getDeclaredField(name);
					field.setAccessible(true);
					field.set(this, value);
				} catch (NoSuchFieldException e) {
				}
				if (!"sign".equals(name)) {
					signPart.addPart(name, value);
				}
			}
		}
		if (key != null) {
			this.validate = signPart.build(SignPart.MD5).equals(getSign());
		}
	}
}
