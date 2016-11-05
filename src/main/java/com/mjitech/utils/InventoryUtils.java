package com.mjitech.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.lib.SkuLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.SkuAttribute;
import com.mjitech.service.SkuService;

@Component
@Scope("singleton")
public class InventoryUtils {
	@Autowired
	private SkuLib skuLib;
	@Autowired
	private SkuUtils skuUtils;
	@Autowired
	private SkuService skuService;
	

	public JSONObject getProductJSON(Inventory inv) {
		if(inv.getSku()==null){
			inv.setSku(this.skuLib.getById(inv.getSkuId()));
		}
		JSONObject json = skuUtils.getSkuJSON(inv.getSku());
		
		json.put("sellprice", (int) (inv.getSellprice() * 100));
		json.put("quantity", inv.getQuantity());
		List<SkuAttribute> atts = this.skuService.getSkuAttributes(inv.getSkuId(),
				inv.getSku().getCategory());
		JSONArray array = new JSONArray();
		for (SkuAttribute att : atts) {
			JSONObject attjson = new JSONObject();
			attjson.put("name", att.getSkuTypeAttribute().getName());
			attjson.put("value", att.getValue());
			attjson.put("unit", att.getSkuTypeAttribute().getUnit());
			array.put(attjson);
		}
		json.put("attributes", array);
		return json;
	}
}
