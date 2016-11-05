package com.mjitech.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.dao.InventoryDao;
import com.mjitech.dao.RequestDao;
import com.mjitech.dao.WarehouseDao;
import com.mjitech.lib.RequestLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.Request;
import com.mjitech.model.Sku;
import com.mjitech.model.Warehouse;
import com.mjitech.service.RequestService;

@Service("requestService")
public class RequestServiceImpl implements RequestService {

	@Autowired
	private RequestDao requestDao;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private WarehouseDao warehouseDao;
	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private RequestLib requestLib;
	@Autowired
	private SkuLib skuLib;

	@Override
	public List<Request> getByWarehouse(int warehouse_id, boolean history) {
		List<Request> requestList = new ArrayList<Request>();
		List<Inventory> inventoryList = inventoryDao.getByWarehouse(warehouse_id);
		List<Warehouse> machineList = warehouseDao.listChildren(warehouse_id);
		
		for(Warehouse m : machineList){
			List<Request> l = null;
			if(!history){
				l = requestDao.getNewByMachine(m.getId());
			}else{
				l = requestDao.getHistoryByMachine(m.getId());
			}
			for(Request r: l){
				r.setMachineName(m.getName());
			}
			requestList.addAll(l);
		}
		
		for (Request r : requestList) {
			int sku_id = r.getSku_id();
			Sku sku = skuLib.getById(sku_id);
			r.setSku(sku);
			Inventory inventory = getInventoryBySku(inventoryList, sku_id);
			if (inventory != null) {
				r.setQuantityInventory(inventory.getQuantity());
			}
		}
		return requestList;
	}
	
	@Override
	public void prepareWarehouseRequestList(int userId, int warehouseId,
			ModelAndView mv) {
		List<Warehouse> warehouseList = warehouseLib.listAllWarehouse();
		mv.addObject("warehouseList", warehouseList);
		Warehouse selected = null;
		for (Warehouse w : warehouseList) {
			if (w.getId() == warehouseId || selected == null) {
				selected = w;
			}
		}
		if (selected != null) {
			List<Request> requestList = requestLib.getByWarehouse(selected
					.getId());
			mv.addObject("requestList", requestList);
		}
		mv.addObject("selected",selected);

	}

	@Override
	public List<Request> getByMachine(int machine_id) {
		List<Request> requestList = requestDao.getByMachine(machine_id);
		List<Inventory> inventoryList = inventoryDao.getByWarehouse(machine_id);
		Warehouse machine = this.warehouseLib.getById(machine_id);
		if (machine != null) {
			Warehouse warehouse = warehouseDao.getWarehouseById(machine
					.getWarehouse_parent());
			if (warehouse != null) {
				for (Request r : requestList) {
					r.setWarehouseName(warehouse.getName());

					int sku_id = r.getSku().getId();
					Inventory inventory = getInventoryBySku(inventoryList,
							sku_id);
					if (inventory != null) {
						r.setQuantityInventory(inventory.getQuantity());
						r.setWarehouseName(warehouse.getName());
					}
				}
			}

		}
		return requestList;
	}

	private Inventory getInventoryBySku(List<Inventory> inventoryList, int skuId) {
		Inventory inventory = null;
		for (Inventory i : inventoryList) {
			int id = i.getSku().getId();
			if (skuId == id) {
				inventory = i;
				break;
			}
		}
		return inventory;
	}

	@Override
	public void createRequest(Request req) {
		requestDao.createRequest(req);
	}

	@Override
	public Request getById(int id) {
		return requestDao.getById(id);
	}

}
