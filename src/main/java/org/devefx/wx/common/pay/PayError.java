package org.devefx.wx.common.pay;

import java.util.HashMap;
import java.util.Map;

import org.devefx.wx.web.Constant;

/**
 * 支付错误
 * @author： youqian.yue
 * @date： 2016-3-14 下午2:48:55
 */
public class PayError {
	
	private String cause;
	private String solution;
	
	private PayError(String cause, String solution) {
		this.cause = cause;
		this.solution = solution;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	private static final Map<String, PayError> errorMap = new HashMap<String, PayError>();
	static {
		errorMap.put(Constant.NOAUTH, new PayError("商户未开通此接口权限", "请商户前往申请此接口权限"));
		errorMap.put(Constant.NOTENOUGH, new PayError("用户帐号余额不足", "用户帐号余额不足，请用户充值或更换支付卡后再支付"));
		errorMap.put(Constant.ORDERPAID, new PayError("商户订单已支付，无需重复操作", "商户订单已支付，无需更多操作"));
		errorMap.put(Constant.ORDERCLOSED, new PayError("当前订单已关闭，无法支付", "当前订单已关闭，请重新下单"));
		errorMap.put(Constant.SYSTEMERROR, new PayError("系统超时", "系统异常，请用相同参数重新调用"));
		errorMap.put(Constant.APPID_NOT_EXIST, new PayError("参数中缺少APPID", "请检查APPID是否正确"));
		errorMap.put(Constant.MCHID_NOT_EXIST, new PayError("参数中缺少MCHID", "请检查MCHID是否正确"));
		errorMap.put(Constant.APPID_MCHID_NOT_MATCH, new PayError("appid和mch_id不匹配", "请确认appid和mch_id是否匹配"));
		errorMap.put(Constant.LACK_PARAMS, new PayError("缺少必要的请求参数", "请检查参数是否齐全"));
		errorMap.put(Constant.OUT_TRADE_NO_USED, new PayError("同一笔交易不能多次提交", "请核实商户订单号是否重复提交"));
		errorMap.put(Constant.SIGNERROR, new PayError("参数签名结果不正确", "请检查签名参数和方法是否都符合签名算法要求"));
		errorMap.put(Constant.XML_FORMAT_ERROR, new PayError("XML格式错误", "请检查XML参数格式是否正确"));
		errorMap.put(Constant.REQUIRE_POST_METHOD, new PayError("未使用post传递参数", "请检查请求参数是否通过post方法提交"));
		errorMap.put(Constant.POST_DATA_EMPTY, new PayError("post数据不能为空", "请检查post数据是否为空"));
		errorMap.put(Constant.NOT_UTF8, new PayError("未使用指定编码格式", "请使用NOT_UTF8编码格式"));
	}
	public static PayError getInstance(String errorCode) {
		return errorMap.get(errorCode);
	}
}
