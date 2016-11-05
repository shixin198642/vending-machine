package com.mjitech.service;

import com.mjitech.model.Inventory;

public interface OperationLogService {

	public void modifyInventoryQuantityLog(int userid, String ip, int inventoryId,
			int oldQuantity, int newQuantity);

	public void modifyInventoryPriceLog(int userid, String ip, int inventoryId,
			double oldPrice, double newPrice);

	void addNewInventoryLog(int userid, String ip, Inventory inventory);

}
