package org.devefx.wx.handler;


public interface EventHandlerFactory {
	
	EventHandler getHandler(Class<? extends EventHandler> handlerClass) throws Exception;
}
