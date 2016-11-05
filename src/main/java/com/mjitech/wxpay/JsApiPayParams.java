package com.mjitech.wxpay;

import org.json.JSONObject;

public class JsApiPayParams {
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String signType;
	private String paySign;
	private String packageProperty;
	
	public JSONObject toJSON(){
		JSONObject json = new JSONObject(this);
		json.put("package", this.packageProperty);
		json.remove("packageProperty");
		return json;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public String getPackageProperty() {
		return packageProperty;
	}
	public void setPackageProperty(String packageProperty) {
		this.packageProperty = packageProperty;
	}
	


}
