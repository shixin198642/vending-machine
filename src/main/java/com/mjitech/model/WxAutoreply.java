package com.mjitech.model;

public class WxAutoreply extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5196018349499590918L;

	private int type;
	private String content;
	private int enabled;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getEnabled() {
		return enabled;
	}

}
