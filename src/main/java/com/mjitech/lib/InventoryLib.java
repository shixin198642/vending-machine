package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Inventory;

public interface InventoryLib extends BaseModelLib<Inventory> {
	
	public Inventory getBySkuWarehouse(int skuId, int warehouseId);
	public List<Inventory> getByWarehouse(int warehouseId);
	
}
