package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Request;

public interface RequestLib extends BaseModelLib<Request> {
	
	public List<Request> getByWarehouse(int warehouseId);
	
}
