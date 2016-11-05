package com.mjitech.service;

import java.util.List;

import com.mjitech.model.StockIn;


public interface StockInService {

	List<StockIn> getByWarehouse(int id);

}
