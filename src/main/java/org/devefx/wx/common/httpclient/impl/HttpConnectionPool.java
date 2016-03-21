package org.devefx.wx.common.httpclient.impl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

public class HttpConnectionPool {
	
	private static final int MAX_TOTAL = 500;		// 最大连接数
	private static final int MAX_PER_ROUTE = 50;	// 最大并行链接数
	
	private PoolingHttpClientConnectionManager poolConnManager;
	
	private RedirectStrategy redirectStrategy;
	
	public HttpConnectionPool() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			           .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			
			poolConnManager.setMaxTotal(MAX_TOTAL);
			
			poolConnManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
			
			redirectStrategy = new DefaultRedirectStrategy() {
				@Override
				public boolean isRedirected(HttpRequest request,
						HttpResponse response, HttpContext context)
						throws ProtocolException {
					boolean isRedirect = super.isRedirected(request, response, context);
					if (!isRedirect) {
						int statusCode = response.getStatusLine().getStatusCode();
						if (statusCode == 302 || statusCode == 301) {
							return true;
						}
					}
					return isRedirect;
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CloseableHttpClient getConnection() {
		return HttpClients.custom().setConnectionManager(poolConnManager).
				setRedirectStrategy(redirectStrategy).build();
	}
}
