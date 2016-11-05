package com.mjitech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mjitech.model.Sale;

@Repository("saleDao")
public interface SaleDao {
	
	List<Sale> listSaleByMachine(@Param("machine_id") int machine_id);
	
}
