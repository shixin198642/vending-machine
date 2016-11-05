package com.mjitech.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.mjitech.model.Request;


public interface RequestService {

	List<Request> getByWarehouse(int warehouse_id, boolean history);

	List<Request> getByMachine(int machine_id);

	void createRequest(Request req);

	void prepareWarehouseRequestList(int userId, int warehouseId,
			ModelAndView mv);
	
	Request getById(int id);

}
