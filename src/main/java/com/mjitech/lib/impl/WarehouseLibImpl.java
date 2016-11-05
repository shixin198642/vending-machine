package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.constant.WarehouseConstants;
import com.mjitech.dao.WarehouseDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.lib.WarehouseManagerLib;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseManager;

@Component("warehouseLib")
public class WarehouseLibImpl implements WarehouseLib {

	private static Logger logger = LoggerFactory
			.getLogger(WarehouseLibImpl.class);

	@Autowired
	private WarehouseDao warehouseDao;
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private WarehouseManagerLib warehouseManagerLib;
	@Autowired
	private UserinfoLib userinfoLib;

	private void removeCache(Warehouse warehouse) {
		if (warehouse == null) {
			return;
		}
		if (warehouse.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(warehouse.getId()));
		}
		this.redisLib.removeCache(this.getAllKey());
		this.redisLib.removeCache(this.getAllMachineStoreKey());

	}

	private String getIdKey(int id) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_WAREHOUSE_ID)
				.append(id).toString();
	}

	private String getAllKey() {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_WAREHOUSE_ALL)
				.toString();
	}

	private String getAllMachineStoreKey() {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_MACHINESTORE_ALL)
				.toString();
	}

	@Override
	public List<Warehouse> listAllMachineStore() {
		String key = this.getAllMachineStoreKey();
		List<Warehouse> list = (List<Warehouse>) this.redisLib.getCache(key);
		if (list == null) {
			list = warehouseDao.listMachineStore();
			if (list != null) {
				this.redisLib.addCache(key, (ArrayList<Warehouse>) list);
			}
		}
		return list;
	}

	@Override
	public Warehouse getById(int id) {
		String key = this.getIdKey(id);
		Warehouse warehouse = this.warehouseDao.getById(id);
		if (warehouse == null) {
			warehouse = warehouseDao.getById(id);
			if (warehouse != null) {
				this.redisLib.addCache(key, warehouse);
			}
		}

		return warehouse;
	}

	@Override
	public Warehouse add(Warehouse storehouse) {
		Warehouse sh = null;
		if (storehouse.getCreateDatetime() == null) {
			storehouse.setCreateDatetime(new Date());
		}
		if (storehouse.getUpdateDatetime() == null) {
			storehouse.setUpdateDatetime(storehouse.getCreateDatetime());
		}
		warehouseDao.add(storehouse);
		if (storehouse.getId() > 0) {
			sh = storehouse;
			this.removeCache(sh);
		} else {
			logger.error("error in adding sku:" + storehouse);
		}
		return sh;
	}

	@Override
	public int update(Warehouse st) {
		if (st.getUpdateDatetime() == null) {
			st.setUpdateDatetime(new Date());
		}
		if (st.getId() > 0) {
			Warehouse old = this.getById(st.getId());
			if (old != null) {
				int ret = warehouseDao.update(st);
				if (ret > 0) {

					this.removeCache(old);
					return ret;
				}
			}

		}
		return 0;
	}

	@Override
	public int delete(int id) {
		Warehouse old = this.getById(id);
		if (old != null) {
			int i = warehouseDao.delete(id);
			if (i > 0) {
				this.removeCache(old);
				return i;
			}
		}

		return 0;
	}

	@Override
	public Warehouse getByManager(int manager) {
		Warehouse value = null;
		WarehouseManager wm = this.warehouseManagerLib.getByManager(manager);
		if (wm != null) {
			value = this.getById(wm.getWarehouseId());

		}

		return value;
	}
	@Override
	public Warehouse getNearestStore(long longitude, long latitude){
		List<Warehouse> stores = this.listAllMachineStore();
		if(stores!=null && stores.size()>0){
			return stores.get(0);
		}
		return null;
	}
	
	@Override
	public Warehouse getMachineById(int id) {
		Warehouse w = this.getById(id);
		if (WarehouseConstants.WAREHOUSE_TYPE_MACHINE.equalsIgnoreCase(w
				.getType())) {
			return w;
		}
		return null;
	}

	@Override
	public Warehouse getStoreById(int id) {
		Warehouse w = this.getById(id);
		if (WarehouseConstants.WAREHOUSE_TYPE_STORE.equalsIgnoreCase(w
				.getType())) {
			return w;
		}
		return null;
	}

	@Override
	public void setWarehouseManagers(Warehouse warehouse) {
		if (warehouse != null) {
			List<WarehouseManager> wms = this.warehouseManagerLib
					.getByWarehouse(warehouse.getId());
			for (WarehouseManager wm : wms) {
				wm.setManager(this.userinfoLib.getById(wm.getManagerId()));
			}
			warehouse.setManagers(wms);
		}

	}

	@Override
	public List<Warehouse> listAllWarehouse() {
		String key = this.getAllKey();
		List<Warehouse> list = (List<Warehouse>) this.redisLib.getCache(key);
		if (list == null) {
			list = warehouseDao.listWarehouse();
			if (list != null) {
				this.redisLib.addCache(key, (ArrayList<Warehouse>) list);
			}
		}
		return list;
	}
}
