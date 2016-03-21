package org.devefx.wx.web.token.impl;

import org.devefx.wx.common.util.WeixinUtils;
import org.devefx.wx.config.Config;
import org.devefx.wx.web.token.AccessToken;
import org.devefx.wx.web.token.AccessTokenCache;

public class AccessTokenCacheImpl implements AccessTokenCache {

	private AccessToken accessToken;
	@Override
	public AccessToken getAccessToken(Config config) {
		if (accessToken == null || accessToken.isExpires()) {
			accessToken = WeixinUtils.getAccessToken("client_credential", config.getAppID(), config.getAppSecret());
		}
		return accessToken;
	}
}
