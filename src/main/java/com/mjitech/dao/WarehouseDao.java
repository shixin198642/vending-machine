package com.mjitech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mjitech.model.Warehouse;

@Repository("warehouseDao")
public interface WarehouseDao extends BaseDao<Warehouse>{

	List<Warehouse> listWarehouse();

	List<Warehouse> listMachineStore();
	
	Warehouse getWarehouseById(@Param("id") int id);
	
	List<Warehouse> getByCondition(Warehouse condition);	
	
	List<Warehouse> listChildren(@Param("warehouse_parent") int warehouse_parent);
}
