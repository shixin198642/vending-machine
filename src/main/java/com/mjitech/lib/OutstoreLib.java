package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Outstore;

public interface OutstoreLib extends BaseModelLib<Outstore>{
	
	public List<Outstore> getByWarehouse(int warehouseId);

	List<Outstore> getBySellorder(int sellOrderId);
	
}
