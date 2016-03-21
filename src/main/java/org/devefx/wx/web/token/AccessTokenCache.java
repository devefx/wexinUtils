package org.devefx.wx.web.token;

import org.devefx.wx.config.Config;

public interface AccessTokenCache {
	
	AccessToken getAccessToken(Config config);
}
