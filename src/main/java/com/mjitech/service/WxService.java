package com.mjitech.service;

import java.util.Map;

public interface WxService {
	public String handleMessage(Map<String, Object> inputMsg);
}
