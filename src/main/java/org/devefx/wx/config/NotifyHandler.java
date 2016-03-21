package org.devefx.wx.config;

import org.devefx.wx.common.pay.order.support.OrderInfo;

public interface NotifyHandler {
	
	void handle(OrderInfo orderInfo);
}
