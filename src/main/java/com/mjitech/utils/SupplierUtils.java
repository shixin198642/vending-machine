package com.mjitech.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.model.Supplier;
import com.mjitech.model.SupplierContact;

@Component
@Scope("singleton")
public class SupplierUtils {

	public JSONObject getSupplierJSON(Supplier supplier) {
		JSONObject json = new JSONObject(supplier);
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}
	
	
	public JSONObject getSupplierContactJSON(SupplierContact supplierContact) {
		JSONObject json = new JSONObject(supplierContact);
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}
	
	
	public JSONArray getSupplierContactListJSON(List<SupplierContact> list) {
		JSONArray array = new JSONArray();
		if(list.size()>0){
			for(SupplierContact s:list){
				JSONObject json = new JSONObject(s);
				CommonUtils.removeCommonUselessProperties(json);
				array.put(json);
			}
		}
		return array;
	}
	
	
	
}
