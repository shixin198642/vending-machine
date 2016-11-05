package com.mjitech.service.apiservice;

import java.util.Map;

import org.json.JSONObject;

public interface SellerApiService {

	public JSONObject bindWXAccount(String openid, String username,
			String password);

	public JSONObject getSkuDetail(String skuNumber, int userId);

	public JSONObject addOrder(Map<Integer, Integer> skuCounts, int userId);

	public JSONObject orderList(int userId);

	public JSONObject orderDetail(String orderNumber, int userId);

	public JSONObject orderStatus(String orderNumber, int userId);

	public JSONObject requestPayUrl(String orderNumber, int userId);

}
