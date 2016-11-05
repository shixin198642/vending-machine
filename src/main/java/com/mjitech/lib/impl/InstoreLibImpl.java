package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.InstoreDao;
import com.mjitech.lib.InstoreLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.Instore;

@Component("instoreLib")
public class InstoreLibImpl implements InstoreLib {

	@Autowired
	private InstoreDao instoreDao;
	@Autowired
	private RedisLib redisLib;

	private String getListInStoreIdKey(int id) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_INSTORE_LIST_ID)
				.append(id).toString();
	}

	private String getIDKey(int id) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_KEY_INSTORE_ID).append(id);
		return key.toString();
	}

	private void removeCache(Instore instore) {
		if (instore.getId() > 0) {
			this.redisLib.removeCache(this.getIDKey(instore.getId()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Instore> listInStore(int warehouseId) {
		String key = this.getListInStoreIdKey(warehouseId);
		List<Instore> list = (List<Instore>) this.redisLib.getCache(key);
		if (list == null) {
			list = instoreDao.listInStore(warehouseId);
			if (list != null) {
				this.redisLib.addCache(key, (ArrayList<Instore>) list);
			}
		}
		return list;
	}

	@Override
	public Instore getById(int id) {
		String key = this.getIDKey(id);
		Instore value = (Instore) this.redisLib.getCache(key);
		if (value == null) {
			value = this.instoreDao.getById(id);
			if (value != null) {
				this.redisLib.addCache(key, value);
			}
		}
		return value;
	}

	@Override
	public Instore add(Instore t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		this.instoreDao.add(t);
		if (t.getId() > 0) {
			this.removeCache(t);
			return t;
		}
		return null;
	}

	@Override
	public int update(Instore t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
