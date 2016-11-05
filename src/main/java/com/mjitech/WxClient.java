package com.mjitech;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mjitech.constant.WXConstants;

public class WxClient {

	private static String appid = "wx4da5ecd6305e620a";
	private static String secret = "15a2f5a94b4c862cd9e8eff865d11eb9";
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	private static String getAccessToken() {
		String ret = null;
		HttpGet get = new HttpGet(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&"
						+ "appid=" + appid + "&secret=" + secret);
		CloseableHttpResponse httpResponse = null;
		// 发送get请求
		try {
			httpResponse = httpClient.execute(get);
			String httpret = EntityUtils.toString(httpResponse.getEntity());
			JSONObject json = new JSONObject(httpret);

			if (json.has("access_token")) {
				ret = json.getString("access_token");
			}
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
		return ret;
	}

	public static JSONObject getRequest(String url) {
		JSONObject ret = null;

		CloseableHttpResponse httpResponse = null;
		HttpGet httpGet = new HttpGet(url);

		try {
			httpResponse = httpClient.execute(httpGet);
			String postret = EntityUtils.toString(httpResponse.getEntity(),
					"UTF-8");
			ret = new JSONObject(postret);
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

		return ret;
	}

	public static JSONObject postJSON(String url, JSONObject content) {
		JSONObject ret = null;
		String encoderJson = content.toString();
		StringEntity se = null;
		// encoderJson = URLEncoder.encode(content.toString(), "UTF-8");
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

		return ret;
	}

	private static void createSellerMenus() throws UnsupportedEncodingException {
		String token = getAccessToken();
		System.out.println(token);
		// String url =
		// "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
		String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token="
				+ token;
		String clickurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ appid
				+ "&redirect_uri="
				+ URLEncoder.encode(
						"http://www.mjitech.com/web/wxauthorize.action",
						"UTF-8")
				+ "&response_type=code&scope=snsapi_base&state=checkin#wechat_redirect";
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject button = new JSONObject();
		button.put("type", "view");
		button.put("name", "结帐");
		button.put("url", clickurl);
		array.put(button);
		String clickurl1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ appid
				+ "&redirect_uri="
				+ URLEncoder.encode(
						"http://www.mjitech.com/web/wxauthorize.action",
						"UTF-8")
				+ "&response_type=code&scope=snsapi_base&state=orderlist#wechat_redirect";
		JSONObject button1 = new JSONObject();
		button1.put("type", "view");
		button1.put("name", "订单列表");
		button1.put("url", clickurl1);
		array.put(button1);
		json.put("button", array);
		JSONObject matchrulejson = new JSONObject();
		matchrulejson.put("tag_id", "101");
		json.put("matchrule", matchrulejson);
		System.out.println(json.toString());
		JSONObject ret = postJSON(url, json);
		System.out.println(ret);
	}

	private static void createDefaultMenus()
			throws UnsupportedEncodingException {
		String token = getAccessToken();
		System.out.println(token);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ token;
		String clickurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ appid
				+ "&redirect_uri="
				+ URLEncoder.encode(
						"http://www.mjitech.com/web/wxauthorize.action",
						"UTF-8")
				+ "&response_type=code&scope=snsapi_base&state=common#wechat_redirect";
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject button = new JSONObject();
		button.put("type", "view");
		button.put("name", "关于我们");
		button.put("url", clickurl);
		array.put(button);

		json.put("button", array);
		System.out.println(json.toString());
		JSONObject ret = postJSON(url, json);
		System.out.println(ret);
	}

	private static void deleteDefaultMenus() {
		String token = getAccessToken();
		System.out.println(token);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
				+ token;

		JSONObject ret = getRequest(url);
		System.out.println(ret);
	}

	private static void createSellerTag() throws UnsupportedEncodingException {
		String token = getAccessToken();
		System.out.println(token);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token="
				+ token;

		JSONObject json = new JSONObject();
		JSONObject tagjson = new JSONObject();
		tagjson.put("name", "商家");
		json.put("tag", tagjson);
		JSONObject ret = postJSON(url, json);
		System.out.println(ret);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// StringBuilder getUrl = new StringBuilder(
		// "https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
		// .append(WXConstants.APPID).append("&secret=")
		// .append(WXConstants.SECRET).append("&code=").append("0013Hanf15Uksm0IHTnf1qaanf13HanS")
		// .append("&grant_type=authorization_code");
		// System.out.println(getRequest(getUrl.toString()));
//		deleteDefaultMenus();
//		createDefaultMenus();
//		createSellerMenus();
//		getCreatedTags();
//		getUserTags();
		queryMenus();

	}
	
	private static void getUserTags(){
		String token = getAccessToken();
		String openid = "o41Mgv9cEidvcjuTtwporX4izMVM";
		JSONObject json = new JSONObject();
		json.put("openid", openid);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token="+token;
		JSONObject ret = postJSON(url, json);
		System.out.println(ret);
	}
	
	private static void getCreatedTags(){
		String token = getAccessToken();
		System.out.println(token);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token="
				+ token;

		JSONObject json = new JSONObject();
		JSONObject tagjson = new JSONObject();
		tagjson.put("name", "商家");
		json.put("tag", tagjson);
		JSONObject ret = getRequest(url);
		System.out.println(ret);
	}

	private static void queryMenus()
			throws UnsupportedEncodingException {
		String token = getAccessToken();
		System.out.println(token);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="
				+ token;
		
		JSONObject ret = getRequest(url);
		System.out.println(ret);
	}

}
