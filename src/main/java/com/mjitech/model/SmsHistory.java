package com.mjitech.model;

public class SmsHistory extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3580013848784444707L;

	private String mobile;
	private String content;
	private String retCode;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

}
