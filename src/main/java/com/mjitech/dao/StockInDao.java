package com.mjitech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mjitech.model.StockIn;

@Repository("stockInDao")
public interface StockInDao {
	
	public List<StockIn> getByWarehouse(@Param("warehouse_id") int warehouse_id);
	
}
