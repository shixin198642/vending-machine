package com.mjitech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mjitech.model.Inventory;

public interface InventoryDao extends BaseDao<Inventory> {

	public List<Inventory> getByWarehouse(@Param("warehouse_id") int warehouse_id) ;
	
	public List<Inventory> getByCondition(Inventory condition);
	
}
