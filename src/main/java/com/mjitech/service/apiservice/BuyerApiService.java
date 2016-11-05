package com.mjitech.service.apiservice;

import java.util.List;

import org.json.JSONObject;

public interface BuyerApiService {

	public JSONObject getMainpageData(int userid, int warehouseId);

	JSONObject getStoreList(int userid);

	JSONObject addSkuToCart(int userid, int storeId, String skuId, int count);

	JSONObject getStoreCarts(int userid);

	JSONObject removeSkuFromCart(int userid, int storeId, int skuId, int count);

	JSONObject clearStoreCart(int userid, int storeId);

	JSONObject submitStoreCart(int userid, int storeId);
	
	JSONObject submitStoreCarts(int userid, List<Integer> storeIds);

	JSONObject getMainpageDataByGeo(int userid, long longitude, long latitude);

	JSONObject getJSAPIParams(int userid, String url);

	JSONObject requestJSPayParams(int userid, String orderNumber);
	
	JSONObject getBuyerInfo(int userid);

	JSONObject getOrderList(int userid, int storeId);

	JSONObject getOrderDetail(int userid, String orderNumber);

	JSONObject getSkuDetail(int userid, String skuNumber, int storeId);

	JSONObject getBrandDetail(int userid, int brandId);

}
