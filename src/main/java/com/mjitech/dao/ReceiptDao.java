package com.mjitech.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mjitech.model.Receipt;

public interface ReceiptDao extends BaseDao<Receipt> {

	List<Receipt> listReceiptByWarehouse(@Param("warehouse_id") int warehouse_id, 
			@Param("status") String status);

	List<Receipt> listReceiptItemByWarehouse(@Param("warehouse_id") int warehouse_id, 
			@Param("status") String status);
}
