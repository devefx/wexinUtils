package org.devefx.wx.common.pay;

/**
 * 微信支付失败异常
 * @author： youqian.yue
 * @date： 2016-3-14 下午2:43:42
 */
public class PayException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private PayError error;
	
	public PayException(String errorCode) {
		error = PayError.getInstance(errorCode);
    }
	public PayError getError() {
		return error;
	}
	@Override
	public String getMessage() {
		return error.getCause();
	}
}
