package com.mjitech.service;

import java.util.List;

import org.json.JSONObject;

import com.mjitech.model.Inventory;


public interface InventoryService {

	List<Inventory> getByWarehouse(int id);

	JSONObject modifyQuantity(int userid, String ip, int inventoryId, int quantity,
			String description);

	JSONObject modifySellprice(int userid, String ip, int inventoryId, double sellprice);

	JSONObject addNewInventory(int userid, String ip, Inventory inventory, String description);

}
