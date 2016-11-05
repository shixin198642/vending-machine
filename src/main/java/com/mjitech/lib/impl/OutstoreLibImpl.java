package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.OutstoreDao;
import com.mjitech.lib.OutstoreLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.Outstore;

@Component("outstoreLib")
public class OutstoreLibImpl implements OutstoreLib {

	private static Logger logger = LoggerFactory
			.getLogger(OutstoreLibImpl.class);

	@Autowired
	private OutstoreDao outstoreDao;
	@Autowired
	private RedisLib redisLib;

	private String getIDKey(int id) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_OUTSTORE_ID)
				.append(id);
		return key.toString();
	}

	private String getWarehouseKey(int warehouseId) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_OUTSTORE_WAREHOUSEID)
				.append(warehouseId);
		return key.toString();
	}
	
	private String getSellOrderKey(int sellOrderId) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_OUTSTORE_SELLORDERID)
				.append(sellOrderId);
		return key.toString();
	}

	private void removeCache(Outstore outstore) {
		if (outstore.getId() > 0) {
			this.redisLib.removeCache(this.getIDKey(outstore.getId()));
		}
		if(outstore.getWarehouseId()>0){
			this.redisLib.removeCache(this.getWarehouseKey(outstore.getWarehouseId()));
		}
		if(outstore.getSellOrderId()>0){
			this.redisLib.removeCache(this.getSellOrderKey(outstore.getSellOrderId()));
		}
	}

	@Override
	public Outstore getById(int id) {
		String key = this.getIDKey(id);
		Outstore value = (Outstore) this.redisLib.getCache(key);
		if (value == null) {
			value = this.outstoreDao.getById(id);
			if (value != null) {
				this.redisLib.addCache(key, value);
			}
		}
		return value;
	}

	@Override
	public Outstore add(Outstore t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		this.outstoreDao.add(t);
		if (t.getId() > 0) {
			this.removeCache(t);
			return t;
		}
		return null;
	}

	@Override
	public int update(Outstore t) {
		if (t.getId() > 0) {
			Outstore old = this.getById(t.getId());
			if (old != null) {
				if (t.getUpdateDatetime() == null) {
					t.setUpdateDatetime(new Date());
				}
				int ret = this.outstoreDao.update(t);
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
		if (id > 0) {
			Outstore old = this.getById(id);
			if (old != null) {
				int ret = this.outstoreDao.delete(id);
				if (ret > 0) {
					this.removeCache(old);
					return ret;
				}
			}
		}
		return 0;
	}

	@Override
	public List<Outstore> getByWarehouse(int warehouseId) {
		if (warehouseId <= 0) {
			return null;
		}
		Outstore condition = new Outstore();
		condition.setWarehouseId(warehouseId);
		String key = this.getWarehouseKey(warehouseId);
		List<Outstore> value = (List<Outstore>) this.redisLib.getCache(key);
		if (value == null) {
			value = this.outstoreDao.getByCondition(condition);
			if (value != null) {
				this.redisLib.addCache(key, (ArrayList<Outstore>)value);
			}
		}
		return value;
	}
	
	@Override
	public List<Outstore> getBySellorder(int sellOrderId){
		if (sellOrderId <= 0) {
			return null;
		}
		Outstore condition = new Outstore();
		condition.setSellOrderId(sellOrderId);
		String key = this.getSellOrderKey(sellOrderId);
		List<Outstore> value = (List<Outstore>) this.redisLib.getCache(key);
		if (value == null) {
			value = this.outstoreDao.getByCondition(condition);
			if (value != null) {
				this.redisLib.addCache(key, (ArrayList<Outstore>)value);
			}
		}
		return value;
	}
}
