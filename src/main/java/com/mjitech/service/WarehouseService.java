package com.mjitech.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.model.StockIn;
import com.mjitech.model.StockOut;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseEnum;

public interface WarehouseService {
	
	public List<Warehouse> listAll(WarehouseEnum we);
	
	public JSONObject add(Warehouse store);

	public JSONObject get(int id);

	public JSONObject delete(int id);

	public JSONObject update(Warehouse warehouse);

	public JSONObject addStockin(StockIn stockin);

	public JSONObject enterStockin(int id);

	public JSONObject addStockout(StockOut stockout);

	public JSONObject exitStockout(int id);

	public JSONObject listStockin();

	public JSONObject listStockout();

	void prepareMachineStoreInventoryList(int userId, int warehouseId,
			ModelAndView mv);

	void prepareWarehouseInventoryList(int userId, int warehouseId,
			ModelAndView mv);

	
}
