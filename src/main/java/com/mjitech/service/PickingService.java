package com.mjitech.service;

import java.util.List;

import com.mjitech.model.Picking;

public interface PickingService {

	void generatePicking(Integer[] requestIds);

	List<Picking> listPickingByWarehouse(int warehouse_id);
	
	void createPicking(int warehouse_id, int userId, int[] skuIds, int[] quantities);

}
