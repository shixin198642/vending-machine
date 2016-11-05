package com.mjitech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mjitech.model.Picking;

@Repository("pickingDao")
public interface PickingDao {

	public List<Picking> listPicking();
	
	public List<Picking> listPickingByWarehouse(@Param("warehouse_id") int warehouse_id);

	public void createPicking(Picking picking);
}
