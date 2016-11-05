package com.mjitech.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.lib.DictAreaLib;
import com.mjitech.lib.SkuBrandLib;
import com.mjitech.lib.SkuTypeLib;
import com.mjitech.model.Sku;
import com.mjitech.model.SkuSpec;

@Component
@Scope("singleton")
public class SkuUtils {
	@Autowired
	private DictAreaLib dictAreaLib;
	@Autowired
	private SkuTypeLib skuTypeLib;
	@Autowired
	private SkuBrandLib skuBrandLib;

	public JSONObject getSkuJSON(Sku sku) {
		if (StringUtils.isNotEmpty(sku.getImagePath())) {
			sku.setImagePath("/static" + sku.getImagePath());
		}
		if (StringUtils.isEmpty(sku.getCountryName())) {
			sku.setCountryName(dictAreaLib.getById(sku.getCountry()).getName());
		}
		if (StringUtils.isEmpty(sku.getCategoryName())) {
			sku.setCategoryName(skuTypeLib.getById(sku.getCategory()).getName());
		}
		if (StringUtils.isEmpty(sku.getBrandName())) {
			sku.setBrandName(skuBrandLib.getById(sku.getBrand()).getName());
		}
		JSONObject json = new JSONObject(sku);
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}
	
	public JSONArray getSkuListJSON(List<Sku> skus){
		JSONArray array = new JSONArray();
		if(skus!=null && skus.size()>0){
			for(Sku sku : skus){
				array.put(this.getSkuJSON(sku));
			}
		}
		return array;
	}

	public JSONObject getSkuSpecJSON(SkuSpec skuSpec) {
		JSONObject json = new JSONObject(skuSpec);
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}

	public JSONArray getSkuSpecListJSON(List<SkuSpec> list) {
		JSONArray array = new JSONArray();
		if (list.size() > 0) {
			for (SkuSpec s : list) {
				JSONObject json = new JSONObject(s);
				CommonUtils.removeCommonUselessProperties(json);
				array.put(json);
			}
		}
		return array;
	}
}
