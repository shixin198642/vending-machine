package com.mjitech.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.WarehouseErrorCodes;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.StockIn;
import com.mjitech.model.StockOut;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseEnum;
import com.mjitech.service.WarehouseService;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.WarehouseUtils;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private WarehouseErrorCodes warehouseErrorCodes;
	@Autowired
	private WarehouseUtils warehouseUtils;
	@Autowired
	private InventoryLib inventoryLib;
	@Autowired
	private SkuLib skuLib;

	@Override
	public List<Warehouse> listAll(WarehouseEnum we) {
		List<Warehouse> list = null;
		if (we == WarehouseEnum.WAREHOUSE) {
			list = warehouseLib.listAllWarehouse();
		} else if (we == WarehouseEnum.MACHINE) {
			list = warehouseLib.listAllMachineStore();
		}
		return list;
	}

	@Override
	public void prepareWarehouseInventoryList(int userId, int warehouseId,
			ModelAndView mv) {
		List<Warehouse> ws = this.warehouseLib.listAllWarehouse();
		mv.addObject("warehouseList", ws);
		Warehouse selected = null;
		for (Warehouse w : ws) {
			if (w.getId() == warehouseId || selected == null) {
				selected = w;
			}
		}
		List<Inventory> invs = this.inventoryLib.getByWarehouse(selected
				.getId());
		if (invs != null) {
			for (Inventory inv : invs) {
				inv.setSku(skuLib.getById(inv.getSkuId()));
			}
		}

		mv.addObject("inventoryList", invs);
		mv.addObject("warehouseId", warehouseId);
		mv.addObject("selected", selected);

	}

	@Override
	public void prepareMachineStoreInventoryList(int userId, int warehouseId,
			ModelAndView mv) {
		List<Warehouse> ws = this.warehouseLib.listAllMachineStore();
		mv.addObject("warehouseList", ws);
		Warehouse selected = null;
		for (Warehouse w : ws) {
			if (w.getId() == warehouseId || selected == null) {
				selected = w;
			}
		}
		List<Inventory> invs = this.inventoryLib.getByWarehouse(selected
				.getId());
		if (invs != null) {
			for (Inventory inv : invs) {
				inv.setSku(skuLib.getById(inv.getSkuId()));
			}
		}

		mv.addObject("inventoryList", invs);
		mv.addObject("warehouseId", warehouseId);
		mv.addObject("selected", selected);

	}

	public JSONObject add(Warehouse store) {
		if (store.getCreateDatetime() == null) {
			store.setCreateDatetime(new Date());
		}
		if (store.getUpdateDatetime() == null) {
			store.setUpdateDatetime(store.getCreateDatetime());
		}
		Warehouse st = null;
		try {
			// we will catch the exception when error happens
			st = warehouseLib.add(store);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject ret = new JSONObject();
		if (st != null && st.getId() > 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		} else {
			jsonUtils.setRetErrorCode(ret, warehouseErrorCodes,
					WarehouseErrorCodes.RET_CODE_WAREHOUSE_ADDFAILTURE, null);
		}
		return ret;
	}

	@Override
	public JSONObject get(int id) {
		Warehouse st = warehouseLib.getById(id);
		JSONObject ret = new JSONObject();
		if (st != null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			ret.put(JSONConstants.RETURN_KEY_RESULT,
					warehouseUtils.getWarehouseJSON(st));
		} else {
			jsonUtils.setRetErrorCode(ret, warehouseErrorCodes,
					WarehouseErrorCodes.RET_CODE_WAREHOUSE_GETFAILTURE, null);
		}
		return ret;
	}

	@Transactional
	public JSONObject assign(Warehouse store, int manager_id) {
		return null;
	}

	@Override
	public JSONObject delete(int id) {
		int i = warehouseLib.delete(id);
		JSONObject ret = new JSONObject();
		if (i > 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		} else {
			jsonUtils
					.setRetErrorCode(
							ret,
							warehouseErrorCodes,
							WarehouseErrorCodes.RET_CODE_WAREHOUSE_DELETEFAILTURE,
							null);
		}
		return ret;
	}

	@Override
	public JSONObject update(Warehouse warehouse) {
		int i = warehouseLib.update(warehouse);
		JSONObject ret = new JSONObject();
		if (i > 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		} else {
			jsonUtils
					.setRetErrorCode(
							ret,
							warehouseErrorCodes,
							WarehouseErrorCodes.RET_CODE_WAREHOUSE_UPDATEFAILTURE,
							null);
		}
		return ret;
	}

	@Override
	public JSONObject addStockin(StockIn stockin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject enterStockin(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject addStockout(StockOut stockout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject exitStockout(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject listStockin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject listStockout() {
		// TODO Auto-generated method stub
		return null;
	}

}
