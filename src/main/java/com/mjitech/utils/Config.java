package com.mjitech.utils;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("config")
@Singleton
public class Config {

	@Value("${base_url}")
	private String baseUrl;
	@Value("${base_static_url}")
	private String baseStaticUrl;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseStaticUrl() {
		return baseStaticUrl;
	}

	public void setBaseStaticUrl(String baseStaticUrl) {
		this.baseStaticUrl = baseStaticUrl;
	}

}
