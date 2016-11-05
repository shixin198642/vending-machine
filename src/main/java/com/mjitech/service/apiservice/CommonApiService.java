package com.mjitech.service.apiservice;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface CommonApiService {

	public JSONObject addOrder(Map<Integer, Integer> skuCounts,
			int warehouseId, int sellerId, int buyerId, int parentId);

	String wxpayCallback(String callbackInput);

	JSONObject addParentOrder(List<Integer> orderids, int buyerId, int sellerId);

}
