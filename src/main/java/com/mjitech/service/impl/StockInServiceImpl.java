package com.mjitech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.dao.StockInDao;
import com.mjitech.model.StockIn;
import com.mjitech.service.StockInService;

@Service("stockInService")
public class StockInServiceImpl implements StockInService{

	@Autowired
	private StockInDao stockInDao;
	
	@Override
	public List<StockIn> getByWarehouse(int warehouse_id) {
		return stockInDao.getByWarehouse(warehouse_id);
	}

}
