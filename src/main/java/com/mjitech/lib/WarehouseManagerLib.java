package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.WarehouseManager;

public interface WarehouseManagerLib extends BaseModelLib<WarehouseManager> {
	
	public WarehouseManager getByManager(int managerId);
	
	public List<WarehouseManager> getByWarehouse(int warehouseId);

	boolean isWarehouseManager(int warehouseId, int managerId);
	
	
	
}
