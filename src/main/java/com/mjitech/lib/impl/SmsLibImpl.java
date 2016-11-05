package com.mjitech.lib.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisConstants;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SmsLib;
import com.mjitech.model.SmsHistory;
import com.mjitech.utils.RedisUtils;

@Component("smsLib")
public class SmsLibImpl implements SmsLib {

	private static Logger logger = LoggerFactory.getLogger(SmsLibImpl.class);

	@Autowired
	private RedisLib redisLib;

	@Autowired
	private RedisUtils redisUtils;
	@Value("${sms.send.url}")
	private String smsUrl;
	@Value("${sms.userid}")
	private String smsUserid;
	@Value("${sms.password}")
	private String smsPassword;
	@Value("${sms.istest}")
	private boolean isSmsTest;
	@Value("${sms.issend}")
	private boolean isSend;

	private final static String testSmsContent = "员工您好，感谢您对此次测试的配合。";

	private static final CloseableHttpClient httpClient;
	public static final String CHARSET = "UTF-8";

	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000)
				.setSocketTimeout(15000).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config)
				.build();
	}

	@Override
	public String sendSms(String mobile, String content, boolean isSync) {
		if(!isSend){
			return "";
		}
		if (isSync) {
			return this.syncSendSms(mobile, content);
		} else {
			return this.asyncSendSms(mobile, content);
		}
	}

	private String syncSendSms(String mobile, String content) {
		String ret = this.realSendSms(mobile, content);
		this.addSmsHistory(mobile, content, ret);
		return ret;
	}

	private String asyncSendSms(String mobile, String content) {
		SmsHistory history = new SmsHistory();
		history.setMobile(mobile);
		history.setContent(content);
		history.setRetCode("");
		byte[] message = redisUtils.serialize(history);

		this.redisLib
				.addQueue(RedisConstants.REDIS_QUEUE_KEY_SEND_SMS, message);

		return "";
	}

	private String packageSmsUrl(String mobile, String content) {
		StringBuilder sb = new StringBuilder(this.smsUrl);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", this.smsUserid));
		params.add(new BasicNameValuePair("password", this.smsPassword));
		params.add(new BasicNameValuePair("pszMobis", mobile));
		int mobileCount = 1;
		if (mobile.indexOf(",") != -1) {
			String[] mobiles = mobile.split(",");
			mobileCount = mobiles.length;
		}
		params.add(new BasicNameValuePair("iMobiCount", Integer
				.toString(mobileCount)));
		params.add(new BasicNameValuePair("pszSubPort", "*"));
		params.add(new BasicNameValuePair("MsgId", Integer
				.toString(new Random().nextInt())));
		if (this.isSmsTest) {
			params.add(new BasicNameValuePair("pszMsg", testSmsContent));
		} else {
			params.add(new BasicNameValuePair("pszMsg", content));
		}
		try {
			sb.append("?").append(
					EntityUtils.toString(new UrlEncodedFormEntity(params,
							CHARSET)));
		} catch (ParseException e) {
			logger.error("error in packaging sms url", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("error in packaging sms url", e);
		} catch (IOException e) {
			logger.error("error in packaging sms url", e);
		}
		return sb.toString();
	}

	private String getReturnCodeFromXML(String xml) {
		String ret = "-999";
		if (xml.toLowerCase().indexOf("<?xml version") != -1) {
			xml = xml.substring(xml.indexOf(">")+1);
			ret = xml.substring(xml.indexOf(">") + 1, xml.indexOf("</"));
		}
		return ret;
	}

	// TODO: implement this method
	private String realSendSms(String mobile, String content) {
		String ret = "";
		String url = this.packageSmsUrl(mobile, content);
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != 200) {
				ret = "-999";
				httpGet.abort();
			} else {
				HttpEntity entity = response.getEntity();
				String resultXML = null;
				if (entity != null) {
					resultXML = EntityUtils.toString(entity, CHARSET);
					if (logger.isDebugEnabled()) {
						logger.debug("return of sending sms is:" + resultXML);
					}
				}
				EntityUtils.consume(entity);
				ret = this.getReturnCodeFromXML(resultXML);
			}
		} catch (ClientProtocolException e) {
			logger.error("error in sending sms by url", e);
		} catch (IOException e) {
			logger.error("error in sending sms by url", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("error in closing send sms response", e);
				}
			}
		}
		return ret;
	}

	private void addSmsHistory(String mobile, String content, String retCode) {
		if (logger.isTraceEnabled()) {
			logger.trace("enter add sms history");
		}
		SmsHistory history = new SmsHistory();
		history.setMobile(mobile);
		history.setContent(content);
		history.setRetCode(retCode);
		Date current = new Date();
		history.setCreateDatetime(current);
		history.setUpdateDatetime(current);

		byte[] message = redisUtils.serialize(history);
		this.redisLib.addQueue(RedisConstants.REDIS_QUEUE_KEY_SMS_HISTORY,
				message);

		if (logger.isTraceEnabled()) {
			logger.trace("exit add sms history");
		}

	}

}
