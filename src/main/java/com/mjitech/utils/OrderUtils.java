package com.mjitech.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.model.Order;
import com.mjitech.model.OrderSku;

@Component
@Scope("singleton")
public class OrderUtils {

	public JSONObject getOrderJSON(Order order) {
		JSONObject json = new JSONObject(order);
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}
	
	
	public JSONObject getOrderSkuJSON(OrderSku orderSku) {
		JSONObject json = new JSONObject(orderSku);
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}
	
	
	public JSONArray getOrderSkuListJSON(List<OrderSku> list) {
		JSONArray array = new JSONArray();
		if(list.size()>0){
			for(OrderSku s:list){
				JSONObject json = new JSONObject(s);
				CommonUtils.removeCommonUselessProperties(json);
				array.put(json);
			}
		}
		return array;
	}
	
}
