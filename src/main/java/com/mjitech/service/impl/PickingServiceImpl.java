package com.mjitech.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.dao.PickingDao;
import com.mjitech.dao.RequestDao;
import com.mjitech.model.Picking;
import com.mjitech.model.Request;
import com.mjitech.service.PickingService;

@Service("pickingService")
public class PickingServiceImpl implements PickingService{

	@Autowired
	private RequestDao requestDao;
	@Autowired
	private PickingDao pickingDao;
	
	@Override
	// TODO add transaction here
	public void generatePicking(Integer[] requestIds) {		
		Picking picking = new Picking();
		picking.setEmployee_id(0); //fix me
		picking.setCreateDatetime(new Date());
		picking.setCreator(0); //fix me
		picking.setUpdator(0); //fix me
		picking.setUpdateDatetime(new Date());
		picking.setStatus("untreated"); //init
		
		if(requestIds !=null && requestIds.length>0){
			int id = requestIds[0];
			Request req = requestDao.getById(id);	
			picking.setMachine_id(req.getMachine_id());
			picking.setWarehouse_id(req.getWarehouse_id());
		}
		
		pickingDao.createPicking(picking);
		
		for(int requestId : requestIds){
			requestDao.setPickingId(requestId, picking.getId());
		}		
	}

	@Override
	public List<Picking> listPickingByWarehouse(int warehouse_id) {	
		return pickingDao.listPickingByWarehouse(warehouse_id);
	}

	@Override
	public void createPicking(int warehouse_id, int userId, int[] skuIds, int[] quantities) {
		//TODO. which machine, warehouse ??
	}

}
