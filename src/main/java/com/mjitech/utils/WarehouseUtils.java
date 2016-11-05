package com.mjitech.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.model.Warehouse;

@Component("warehouseUtils")
@Scope("singleton")
public class WarehouseUtils {

	public JSONObject getWarehouseJSON(Warehouse store) {
		JSONObject json = new JSONObject(store);
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}
	
	public JSONArray getWarehouseListJSON(List<Warehouse> list) {
		JSONArray array = new JSONArray();
		if(list.size()>0){
			for(Warehouse s:list){
				JSONObject json = new JSONObject(s);
				CommonUtils.removeCommonUselessProperties(json);
				array.put(json);
			}
		}
		return array;
	}
	
	
	
}
