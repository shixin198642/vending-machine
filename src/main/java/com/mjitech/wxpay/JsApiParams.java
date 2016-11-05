package com.mjitech.wxpay;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class JsApiParams{

	/**
	 * 
	 */
	
	private String noncestr;
	private String jsapi_ticket;
	private long timestamp;
	private String url;
	private String sign;

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
