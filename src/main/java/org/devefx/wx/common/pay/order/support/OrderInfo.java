package org.devefx.wx.common.pay.order.support;


/**
 * 订单信息
 * @author： youqian.yue
 * @date： 2016-3-18 下午4:52:17
 */
public class OrderInfo extends MessageData {
	
	private String return_code;								// 返回状态码
	private String return_msg;								// 返回信息
	
	private String appid;									// 公众账号ID
	private String mch_id;									// 商户号
	private String nonce_str;								// 随机字符串
	private String sign;									// 签名
	private String result_code;								// 业务结果 SUCCESS/FAIL
	private String err_code;								// 错误代码
	private String err_code_des;							// 错误代码描述
	// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
	private String device_info;								// 设备号
	private String openid;									// 用户标识
	private String is_subscribe;							// 是否关注公众账号
	private String trade_type;								// 交易类型
	private String trade_state;								// 交易状态
	private String bank_type;								// 付款银行
	private String total_fee;								// 总金额
	private String fee_type;								// 货币种类
	private String cash_fee;								// 现金支付金额
	private String cash_fee_type;							// 现金支付货币类型
	private String coupon_fee;								// 代金券或立减优惠金额
	private String coupon_count;							// 代金券或立减优惠使用数量
	private String[] coupon_batch_id_;						// 代金券或立减优惠批次ID
	private String[] coupon_id_;							// 代金券或立减优惠ID
	private String[] coupon_fee_;							// 单个代金券或立减优惠支付金额
	private String transaction_id;							// 微信支付订单号
	private String out_trade_no;							// 商户订单号
	private String attach;									// 附加数据
	private String time_end;								// 支付完成时间
	private String trade_state_desc;						// 交易状态描述
	// 以下字段在统一下单的时候有返回
	private String prepay_id;								// 预支付交易会话标识
	private String code_url;								// 二维码链接
	
	public String getReturnCode() {
		return return_code;
	}
	public String getReturnMsg() {
		return return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public String getMchId() {
		return mch_id;
	}
	public String getNonceStr() {
		return nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public String getResultCode() {
		return result_code;
	}
	public String getErrCode() {
		return err_code;
	}
	public String getErrCodeDes() {
		return err_code_des;
	}
	public String getDeviceInfo() {
		return device_info;
	}
	public String getOpenId() {
		return openid;
	}
	public String getIsSubscribe() {
		return is_subscribe;
	}
	public String getTradeType() {
		return trade_type;
	}
	public String getTradeState() {
		return trade_state;
	}
	public String getBankType() {
		return bank_type;
	}
	public String getTotalFee() {
		return total_fee;
	}
	public String getFeeType() {
		return fee_type;
	}
	public String getCashFee() {
		return cash_fee;
	}
	public String getCashFeeType() {
		return cash_fee_type;
	}
	public String getCouponFee() {
		return coupon_fee;
	}
	public String getCouponCount() {
		return coupon_count;
	}
	public String[] getCouponBatchIds() {
		return coupon_batch_id_;
	}
	public String[] getCouponIds() {
		return coupon_id_;
	}
	public String[] getCouponFees() {
		return coupon_fee_;
	}
	public String getTransactionId() {
		return transaction_id;
	}
	public String getOutTradeNo() {
		return out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public String getTimeEnd() {
		return time_end;
	}
	public String getTradeStateDesc() {
		return trade_state_desc;
	}
	public String getPrepayId() {
		return prepay_id;
	}
	public String getCodeUrl() {
		return code_url;
	}
	@Override
	protected Class<?> getRealClass() {
		return OrderInfo.class;
	}
}
