package com.mjitech.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.model.SkuType;

@Component
@Scope("singleton")
public class SkuTypeUtils {

	public JSONObject getSkuTypeJSON(SkuType type) {
		JSONObject json = new JSONObject(type);
		CommonUtils.removeCommonUselessProperties(json);

		return json;
	}

	public JSONArray getTypesJSONArray(List<SkuType> types) {
		JSONArray array = new JSONArray();
		for (SkuType type : types) {
			array.put(this.getSkuTypeJSON(type));
		}
		return array;
	}

}
