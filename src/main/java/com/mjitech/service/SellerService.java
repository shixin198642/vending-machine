package com.mjitech.service;

import org.json.JSONObject;

public interface SellerService {

	JSONObject sell(int skuId, int skuSpecId, int quantity);

}
