package org.devefx.wx.common.httpclient.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;
import org.devefx.wx.common.httpclient.HttpClient;
import org.devefx.wx.common.httpclient.HttpURI;

import com.alibaba.fastjson.JSONObject;

/**
 * HttpClientImpl
 * @author： youqian.yue
 * @date： 2016-3-11 下午4:25:19
 */
public class HttpClientImpl implements HttpClient {
	
	private static final HttpConnectionPool pool = new HttpConnectionPool();
	
	private HttpContext context = null;
	
	public HttpContext getContext() {
		return context;
	}
	
	public void setContext(HttpContext context) {
		this.context = context;
	}
	/**
	 * 使用get方式请求资源
	 * 
	 * @param uri 资源路径
	 * @return HttpResponse 响应结果
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse excute(HttpURI uri) throws ClientProtocolException, IOException {
		
		HttpUriRequest request = new HttpGet(uri.getURI());
		
		CloseableHttpClient client = pool.getConnection();
		
		if (context == null) {
			context = HttpCoreContext.create();
		}
		return client.execute(request, context);
	}
	/**
	 * 使用post方式请求资源
	 * 
	 * @param uri 资源路径
	 * @param parts 参数
	 * @return HttpResponse 响应结果
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse excute(HttpURI uri, FormBodyPart ...parts) throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(uri.getURI());
		if (parts.length != 0) {
			MultipartEntity multipartEntity = new MultipartEntity();
			for (FormBodyPart part : parts) {
				multipartEntity.addPart(part);
			}
			request.setEntity(multipartEntity);
		}
		CloseableHttpClient client = pool.getConnection();
		if (context == null) {
			context = HttpCoreContext.create();
		}
		return client.execute(request, context);
	}
	/**
	 * 使用post方式请求资源
	 * 
	 * @param uri 资源路径
	 * @param entity 请求实体
	 * @return HttpResponse 响应结果
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse excute(HttpURI uri, HttpEntity entity) throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(uri.getURI());
		if (entity != null) {
			request.setEntity(entity);
		}
		CloseableHttpClient client = pool.getConnection();
		if (context == null) {
			context = HttpCoreContext.create();
		}
		return client.execute(request, context);
	}
	
	/**
	 * 使用get方式请求json数据
	 * 
	 * @param uri 资源路径
	 * @return JSONObject json结果
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JSONObject getJson(HttpURI uri) throws ClientProtocolException, IOException {
		HttpResponse response = excute(uri);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new RuntimeException("request failed, response code:" + statusCode);
		}
		String result = EntityUtils.toString(response.getEntity());
		return JSONObject.parseObject(result);
	}
	/**
	 * 使用post方式请求json数据
	 * 
	 * @param uri 资源路径
	 * @param parts 参数
	 * @return JSONObject json结果
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JSONObject getJson(HttpURI uri, FormBodyPart ...parts) throws ClientProtocolException, IOException {
		HttpResponse response = excute(uri, parts);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new RuntimeException("request failed, response code:" + statusCode);
		}
		String result = EntityUtils.toString(response.getEntity());
		return JSONObject.parseObject(result);
	}
	
	/**
	 * 使用post方式请求json数据
	 * 
	 * @param uri 资源路径
	 * @param parts 参数
	 * @return JSONObject json结果
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public JSONObject getJson(HttpURI uri, HttpEntity entity) throws ClientProtocolException, IOException {
		HttpResponse response = excute(uri, entity);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new RuntimeException("request failed, response code:" + statusCode);
		}
		String result = EntityUtils.toString(response.getEntity());
		return JSONObject.parseObject(result);
	}
}
