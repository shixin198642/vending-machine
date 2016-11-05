package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.WarehouseManager;

public interface WarehouseManagerDao extends BaseDao<WarehouseManager> {
	
	public List<WarehouseManager> getByCondition(WarehouseManager condition);
	
}
