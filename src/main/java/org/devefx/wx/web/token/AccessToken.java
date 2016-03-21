package org.devefx.wx.web.token;

import java.util.Calendar;
import java.util.Date;

public class AccessToken {
	
	private static final int PREVENT = 600;
	
	private String accessToken;
	private Date expires;
	
	public AccessToken(String accessToken, int expires) {
		this.accessToken = accessToken;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, expires - PREVENT);
		this.expires = calendar.getTime();
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public boolean isExpires() {
		return expires.before(new Date());
	}
}
