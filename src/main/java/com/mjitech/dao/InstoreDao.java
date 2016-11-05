package com.mjitech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mjitech.model.Instore;

@Repository("inStoreDao")
public interface InstoreDao extends BaseDao<Instore>{
	
	List<Instore> listInStore(@Param("warehouseId") int warehouseId);
	
}
