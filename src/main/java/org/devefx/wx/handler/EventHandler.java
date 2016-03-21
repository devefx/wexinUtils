package org.devefx.wx.handler;

import org.devefx.wx.msg.Msg;
import org.devefx.wx.msg.ReceiveMsg;
import org.devefx.wx.web.token.AccessToken;

public interface EventHandler {
	
	Msg handle(AccessToken token, ReceiveMsg msg);
}
