package com.mjitech.utils;

import java.util.List;

import org.elasticsearch.common.inject.Singleton;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.model.SellOrderSku;

@Component("sellOrderSkuUtils")
@Singleton
public class SellOrderSkuUtils {
	@Autowired
	private SkuUtils skuUtils;

	public JSONObject getSellOrderSkuJSON(SellOrderSku sku) {
		JSONObject json = new JSONObject(sku);

		if (sku.getSku() != null) {
			json.put("sku", skuUtils.getSkuJSON(sku.getSku()));
		}
		json.put("sellPrice",(int)(sku.getSellPrice()*100));
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}

	public JSONArray getSellOrderSkuJSONArray(List<SellOrderSku> skus) {
		JSONArray array = new JSONArray();
		for (SellOrderSku sku : skus) {
			array.put(this.getSellOrderSkuJSON(sku));
		}
		return array;
	}
}
