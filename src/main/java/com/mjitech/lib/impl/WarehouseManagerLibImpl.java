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
import com.mjitech.dao.WarehouseManagerDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseManagerLib;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseManager;

@Component("warehouseManagerLib")
public class WarehouseManagerLibImpl implements WarehouseManagerLib {
	private static Logger logger = LoggerFactory
			.getLogger(WarehouseManagerLibImpl.class);

	@Autowired
	private RedisLib redisLib;
	@Autowired
	private WarehouseManagerDao warehouseManagerDao;
	@Autowired
	private UserinfoLib userinfoLib;

	private void removeCache(WarehouseManager warehouseManager) {
		if (warehouseManager == null) {
			return;
		}
		if (warehouseManager.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(warehouseManager.getId()));
		}
		if (warehouseManager.getManagerId() > 0) {
			this.redisLib.removeCache(this.getManagerKey(warehouseManager
					.getManagerId()));
		}

	}

	private String getIdKey(int id) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_WAREHOUSEMANAGER_ID).append(id)
				.toString();
	}

	private String getManagerKey(int managerId) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_WAREHOUSEMANAGER_MANAGER).append(
				managerId).toString();
	}

	private String getWarehouseKey(int warehouseId) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_WAREHOUSEMANAGER_WAREHOUSE).append(
				warehouseId).toString();
	}

	@Override
	public WarehouseManager getById(int id) {
		String key = this.getIdKey(id);
		WarehouseManager value = (WarehouseManager) this.redisLib.getCache(key);
		if (value == null) {
			value = this.warehouseManagerDao.getById(id);
			if (value != null) {
				this.redisLib.addCache(key, value);
			}
		}
		return value;
	}

	@Override
	public WarehouseManager add(WarehouseManager t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		if (t.getManagerId() > 0) {
			if (t.getType() == 0) {
				t.setType(WarehouseConstants.WAREHOUSE_MANAGER_TYPE_MANAGER);
			}
			WarehouseManager old = this.getByManager(t.getManagerId());
			if (old != null) {
				this.delete(old.getId());
			}
		} else {
			return null;
		}
		this.warehouseManagerDao.add(t);
		if (t.getId() > 0) {
			this.removeCache(t);
			return t;
		}
		return null;
	}

	@Override
	public int update(WarehouseManager t) {
		if (t.getId() > 0) {
			if (t.getUpdateDatetime() == null) {
				t.setUpdateDatetime(new Date());
			}
			WarehouseManager old = this.getById(t.getId());
			if (old != null) {
				int ret = this.warehouseManagerDao.update(t);
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
		WarehouseManager old = this.getById(id);
		if (old != null) {
			int ret = this.warehouseManagerDao.delete(id);
			if (ret > 0) {
				this.removeCache(old);
				return ret;
			}
		}
		return 0;
	}
	@Override
	public boolean isWarehouseManager(int warehouseId, int managerId) {
		WarehouseManager wm = this.getByManager(managerId);
		return wm != null && wm.getWarehouseId() == warehouseId;
	}

	@Override
	public WarehouseManager getByManager(int managerId) {
		String key = this.getManagerKey(managerId);
		WarehouseManager value = (WarehouseManager) this.redisLib.getCache(key);
		if (value == null) {
			WarehouseManager condition = new WarehouseManager();
			condition.setManagerId(managerId);
			List<WarehouseManager> dbs = this.warehouseManagerDao
					.getByCondition(condition);
			if (dbs != null && dbs.size() > 0) {
				if (dbs.size() > 1) {
					if (logger.isWarnEnabled()) {
						logger.warn("there are more than 1 warehouse associated to user:"
								+ managerId);
					}
					for (int i = 1; i < dbs.size(); i++) {
						if (logger.isWarnEnabled()) {
							logger.warn("delete redundant warehouse manager"
									+ dbs.get(i).getId() + " for user:"
									+ managerId);
						}
						this.delete(dbs.get(i).getId());
					}
				}
				value = dbs.get(0);
				this.redisLib.addCache(key, value);
			}

		}
		return value;
	}

	@Override
	public List<WarehouseManager> getByWarehouse(int warehouseId) {
		String key = this.getWarehouseKey(warehouseId);
		List<WarehouseManager> list = (List<WarehouseManager>) this.redisLib
				.getCache(key);
		if (list == null) {
			WarehouseManager condition = new WarehouseManager();
			condition.setWarehouseId(warehouseId);
			list = this.warehouseManagerDao.getByCondition(condition);
			if(list!=null){
				for(WarehouseManager manager: list){
					Userinfo user = userinfoLib.getById(manager.getId());
					manager.setManager(user);
				}
			}
			if (list != null) {
				this.redisLib.addCache(key, (ArrayList<WarehouseManager>) list);
			}
		}
		return list;
	}

}
