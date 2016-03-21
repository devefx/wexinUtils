package org.devefx.wx.handler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerModule {
	/**
	 * 消息类型
	 */
	String msgType();
	
	/**
	 * 事件类型，当msgType为event时有效
	 */
	String event() default "";
}