package com.mjitech.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("httpUtils")
@Scope("singleton")
public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	public JSONObject postJSON(String url, JSONObject content) {
		JSONObject ret = null;
		String encoderJson = content.toString();
		StringEntity se = null;
		System.out.println(encoderJson);
		se = new StringEntity(encoderJson, "UTF-8");
		CloseableHttpResponse httpResponse = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		httpPost.setEntity(se);
		try {
			httpResponse = httpClient.execute(httpPost);
			String postret = EntityUtils.toString(httpResponse.getEntity(),
					"UTF-8");
			ret = new JSONObject(postret);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error("", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("", e);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("", e);
				}
			}
		}

		return ret;
	}

	public String sendGet(String url) {
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse httpResponse = null;

		try {
			httpResponse = httpClient.execute(get);
			String httpret = EntityUtils.toString(httpResponse.getEntity(),
					"UTF-8");
			return httpret;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String sendXMLPost(String url, String postDataXML) {
		String result = null;

		HttpPost httpPost = new HttpPost(url);

		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);

		try {
			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

		} catch (ConnectionPoolTimeoutException e) {
			if (logger.isErrorEnabled()) {
				logger.error(
						"http get throw ConnectionPoolTimeoutException(wait time out)",
						e);
			}

		} catch (ConnectTimeoutException e) {
			if (logger.isErrorEnabled()) {
				logger.error("http get throw ConnectTimeoutException", e);
			}

		} catch (SocketTimeoutException e) {
			if (logger.isErrorEnabled()) {
				logger.error("", e);
			}

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("", e);
			}

		} finally {
			httpPost.abort();
		}

		return result;
	}

}
