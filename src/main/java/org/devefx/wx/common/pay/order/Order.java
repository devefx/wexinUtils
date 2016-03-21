package org.devefx.wx.common.pay.order;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.devefx.wx.common.pay.util.SignPart;
import org.devefx.wx.common.pay.util.XmlPart;
import org.devefx.wx.common.util.CommonUtils;

/**
 * 微信订单
 * @author： youqian.yue
 * @date： 2016-3-14 下午2:08:34
 */
public class Order {
	
	private String appId;					// 公众账号ID
	private String mchId;					// 商户号
	private String key;						// 商户密匙
	private String deviceInfo = "WEB";		// 设备号
	
	private String body;					// 商品描述
	private String detail;					// 商品详情
	private String attach;					// 附加数据
	private String outTradeNo;				// 商户订单号
	private String feeType = "CNY";			// 货币类型
	private String totalFee;				// 总金额
	private String spbillCreateIp;			// 终端IP
	private Date timeStart;					// 交易起始时间
	private Date timeExpire;				// 交易结束时间
	private String goodsTag;				// 商品标记
	private String notifyUrl;				// 通知地址
	private String tradeType;				// 交易类型
	private String productId;				// 商品ID
	private String limitPay;				// 指定支付方式
	private String openId;					// 用户标识
	
	public String getAppId() {
		return appId;
	}
	/**
	 * 公众账号ID
	 * 
	 * @param appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchId() {
		return mchId;
	}
	/**
	 * 商户号
	 * 
	 * @param mchId
	 */
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getKey() {
		return key;
	}
	/**
	 * 商户密匙
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	/**
	 * 设置设备号
	 * @param deviceInfo
	 */
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	/**
	 * 商品或支付单简要描述
	 * 
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	/**
	 * 商品名称明细列表
	 * 
	 * @param detail
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAttach() {
		return attach;
	}
	/**
	 * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * 
	 * @param attach
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	/**
	 * 商户系统内部的订单号,32个字符内
	 * 
	 * @param outTradeNo
	 */
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getFeeType() {
		return feeType;
	}
	/**
	 * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 * 
	 * @param feeType
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getTotalFee() {
		return totalFee;
	}
	/**
	 * 订单总金额，单位为分。
	 * 
	 * @param totalFee
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	/**
	 * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	 * 
	 * @param spbillCreateIp
	 */
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	public Date getTimeStart() {
		return timeStart;
	}
	/**
	 * 订单生成时间
	 * 
	 * @param timeStart
	 */
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	public Date getTimeExpire() {
		return timeExpire;
	}
	/**
	 * 订单失效时间
	 * 
	 * @param timeExpire
	 */
	public void setTimeExpire(Date timeExpire) {
		this.timeExpire = timeExpire;
	}
	public String getGoodsTag() {
		return goodsTag;
	}
	/**
	 * 商品标记，代金券或立减优惠功能的参数
	 * 
	 * @param goodsTag
	 */
	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	/**
	 * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	 * 
	 * @param notifyUrl
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getTradeType() {
		return tradeType;
	}
	/**
	 * 取值如下：JSAPI，NATIVE，APP
	 * @param tradeType
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getProductId() {
		return productId;
	}
	/**
	 * trade_type=NATIVE，此参数必传。<br>
	 * 此id为二维码中包含的商品ID，商户自行定义。
	 * 
	 * @param productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getLimitPay() {
		return limitPay;
	}
	/**
	 * no_credit--指定不能使用信用卡支付
	 * 
	 * @param limitPay
	 */
	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}
	public String getOpenId() {
		return openId;
	}
	/**
	 * trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
	 * 
	 * @param openId
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public void output(Writer writer) throws IOException {
		// 时间格式化
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// xml参数
		XmlPart xmlPart = new XmlPart();
		xmlPart.addPart("appid", appId);
		xmlPart.addPart("mch_id", mchId);
		xmlPart.addPart("device_info", deviceInfo);
		xmlPart.addPart("nonce_str", CommonUtils.getRandomStr(32));
		xmlPart.addPart("body", body);
		xmlPart.addPart("detail", detail);
		xmlPart.addPart("attach", attach);
		xmlPart.addPart("out_trade_no", outTradeNo);
		xmlPart.addPart("fee_type", feeType);
		xmlPart.addPart("total_fee", totalFee);
		xmlPart.addPart("spbill_create_ip", spbillCreateIp);
		xmlPart.addPart("time_start", format.format(timeStart));
		xmlPart.addPart("time_expire", format.format(timeExpire));
		xmlPart.addPart("goods_tag", goodsTag);
		xmlPart.addPart("notify_url", notifyUrl);
		xmlPart.addPart("trade_type", tradeType);
		xmlPart.addPart("product_id", productId);
		xmlPart.addPart("limit_pay", limitPay);
		xmlPart.addPart("openid", openId);
		xmlPart.addPart("sign", new SignPart(key, xmlPart.getParts()).build(SignPart.MD5));
		// 写出xml
		writer.write(xmlPart.toString());
	}
}
