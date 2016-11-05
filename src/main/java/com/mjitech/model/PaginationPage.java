package com.mjitech.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PaginationPage {

	private int page;
	private String url;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
}
