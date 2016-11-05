package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.InventoryDao;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.Inventory;

@Component("inventoryLib")
public class InventoryLibImpl implements InventoryLib {
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private RedisLib redisLib;

	private void removeCache(Inventory inv) {
		if (inv.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(inv.getId()));
		}
		if (inv.getSkuId() > 0 && inv.getWarehouse_id() > 0) {
			this.redisLib.removeCache(this.getSkuWarehouseKey(inv.getSkuId(),
					inv.getWarehouse_id()));
		}
		if (inv.getWarehouse_id() > 0) {
			this.redisLib.removeCache(this.getWarehouseKey(inv
					.getWarehouse_id()));
		}
	}

	private String getIdKey(int id) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_INVENTORY_ID)
				.append(id).toString();
	}

	private String getSkuWarehouseKey(int skuId, int warehouseId) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_INVENTORY_SKU_WAREHOUSE)
				.append(skuId).append(":").append(warehouseId).toString();
	}

	private String getWarehouseKey(int warehouseId) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_INVENTORY_WAREHOUSE).append(
				warehouseId).toString();
	}

	@Override
	public Inventory getBySkuWarehouse(int skuId, int warehouseId) {
		String key = this.getSkuWarehouseKey(skuId, warehouseId);

		Inventory inv = (Inventory) this.redisLib.getCache(key);
		if (inv == null) {
			Inventory condition = new Inventory();
			condition.setSkuId(skuId);
			condition.setWarehouse_id(warehouseId);
			List<Inventory> dbs = this.inventoryDao.getByCondition(condition);
			if (dbs != null && dbs.size() > 0) {
				inv = dbs.get(0);
				this.redisLib.addCache(key, inv);
			}
		}
		return inv;
	}

	@Override
	public Inventory getById(int id) {
		String key = this.getIdKey(id);
		Inventory inv = (Inventory) this.redisLib.getCache(key);
		if (inv == null) {
			inv = this.inventoryDao.getById(id);
			if (inv != null) {
				this.redisLib.addCache(key, inv);
			}
		}
		return inv;
	}

	@Override
	public Inventory add(Inventory t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		if (t.getSkuId() > 0 && t.getWarehouse_id() > 0) {
			Inventory old = this.getBySkuWarehouse(t.getSkuId(),
					t.getWarehouse_id());
			if (old != null) {
				return null;
			}
			this.inventoryDao.add(t);
			if (t.getId() > 0) {
				this.removeCache(t);
				return t;
			}
		}

		return null;
	}

	@Override
	public int update(Inventory t) {
		if (t.getId() > 0) {
			if (t.getUpdateDatetime() == null) {
				t.setUpdateDatetime(new Date());
			}
			Inventory old = this.getById(t.getId());
			if (old != null) {
				int ret = this.inventoryDao.update(t);
				if (ret > 0) {
					this.removeCache(old);
				}
				return ret;
			}

		}
		return 0;
	}

	@Override
	public int delete(int id) {
		if (id > 0) {
			Inventory old = this.getById(id);
			if (old != null) {
				int ret = this.inventoryDao.delete(id);
				if (ret > 0) {
					this.removeCache(old);
					return ret;
				}
			}
		}
		return 0;
	}

	@Override
	public List<Inventory> getByWarehouse(int warehouseId) {
		String key = this.getWarehouseKey(warehouseId);

		List<Inventory> value = (List<Inventory>) this.redisLib.getCache(key);
		if (value == null) {
			Inventory condition = new Inventory();
			condition.setWarehouse_id(warehouseId);
			List<Inventory> dbs = this.inventoryDao.getByCondition(condition);
			if (dbs != null && dbs.size() > 0) {
				value = dbs;
				this.redisLib.addCache(key, (ArrayList<Inventory>) value);
			}
		}
		return value;
	}

}
