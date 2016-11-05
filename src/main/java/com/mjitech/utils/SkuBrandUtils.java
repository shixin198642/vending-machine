package com.mjitech.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.model.SkuBrand;

@Component
@Scope("singleton")
public class SkuBrandUtils {

	public JSONObject getSkuBrandJSON(SkuBrand skuBrand) {
		if (StringUtils.isNotEmpty(skuBrand.getImagePath())) {
			skuBrand.setImagePath("/static" + skuBrand.getImagePath());
		}
		
		JSONObject json = new JSONObject(skuBrand);
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}
	
	public JSONArray getSkuBrandListJSON(List<SkuBrand> brands){
		JSONArray array = new JSONArray();
		if(brands!=null && brands.size()>0){
			for(SkuBrand brand : brands){
				array.put(this.getSkuBrandJSON(brand));
			}
		}
		return array;
	}
}
