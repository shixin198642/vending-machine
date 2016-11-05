package com.mjitech.utils;

import java.util.List;

import org.elasticsearch.common.inject.Singleton;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.SellOrderConstants;
import com.mjitech.model.SellOrder;

@Component("sellOrderUtils")
@Singleton
public class SellOrderUtils {
	@Autowired
	private SellOrderSkuUtils sellOrderSkuUtils;
	@Autowired
	private WarehouseUtils warehouseUtils;

	public JSONObject getSellOrderJSON(SellOrder order) {
		JSONObject json = new JSONObject(order);
		json.put("statusName",
				SellOrderConstants.getStatusName(order.getStatus()));
		json.put("payStatusName",
				SellOrderConstants.getPayStatusName(order.getPayStatus()));
		json.put("totalPrice", (Math.round(order.getTotalPrice() * 100)));
		if (order.getSkus() != null) {
			json.put("skus",
					sellOrderSkuUtils.getSellOrderSkuJSONArray(order.getSkus()));
		}
		if (order.getStore() != null) {
			json.put("store",
					this.warehouseUtils.getWarehouseJSON(order.getStore()));
		}
		json.put("sellTime", CommonUtils.formatDateToFront(order.getSellTime()));
		if (order.getPayTime() != null) {
			json.put("payTime",
					CommonUtils.formatDateToFront(order.getPayTime()));
		} else {
			json.put("payTime", "");
		}
		if (order.getCancelTime() != null) {
			json.put("cancelTime",
					CommonUtils.formatDateToFront(order.getCancelTime()));
		} else {
			json.put("cancelTime", "");
		}
		if (order.getChildOrders() != null) {
			json.put("childOrders",
					this.getSellOrderJSONArray(order.getChildOrders()));

		}
		CommonUtils.removeCommonUselessProperties(json);
		return json;
	}

	public JSONArray getSellOrderJSONArray(List<SellOrder> orders) {
		JSONArray array = new JSONArray();
		if (orders != null) {
			for (SellOrder order : orders) {
				array.put(this.getSellOrderJSON(order));
			}
		}
		return array;
	}
}
