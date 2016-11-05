package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.PointsHistoryDao;
import com.mjitech.lib.PointsHistoryLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.PointsHistory;

@Component("pointsHistoryLib")
public class PointsHistoryLibImpl implements PointsHistoryLib {
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private PointsHistoryDao pointsHistoryDao;
	private static Logger logger = LoggerFactory
			.getLogger(PointsHistoryLibImpl.class);

	private String getIDKey(int id) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_POINTSHISTORY_ID)
				.append(id);
		return key.toString();
	}

	private String getUseridKey(int userid) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_POINTSHISTORY_USERID)
				.append(userid);
		return key.toString();
	}

	private void removeCache(PointsHistory history) {
		if (history.getId() > 0) {
			this.redisLib.removeCache(this.getIDKey(history.getId()));
		}
		if (history.getUserid() > 0) {
			this.redisLib.removeCache(this.getUseridKey(history.getUserid()));
		}
	}

	@Override
	public PointsHistory getById(int id) {
		String key = this.getIDKey(id);
		PointsHistory value = (PointsHistory) this.redisLib.getCache(key);
		if (value == null) {
			value = this.pointsHistoryDao.getById(id);
			if (value != null) {
				this.redisLib.addCache(key, value);
			}
		}
		return value;
	}

	@Override
	public PointsHistory add(PointsHistory t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		if (t.getPointsDatetime() == null) {
			t.setPointsDatetime(t.getCreateDatetime());
		}
		this.pointsHistoryDao.add(t);
		if (t.getId() > 0) {
			this.removeCache(t);
			return t;
		}
		return null;
	}

	@Override
	public int update(PointsHistory t) {
		if (t.getId() > 0) {
			PointsHistory old = this.getById(t.getId());
			if (old != null) {
				if (t.getUpdateDatetime() == null) {
					t.setUpdateDatetime(new Date());
				}
				this.removeCache(old);
				return this.pointsHistoryDao.update(t);
			}
		}
		return 0;
	}

	@Override
	public int delete(int id) {
		if (id > 0) {
			PointsHistory old = this.getById(id);
			if (old != null) {
				this.removeCache(old);
				return this.pointsHistoryDao.delete(id);
			}
		}
		return 0;
	}

	@Override
	public List<PointsHistory> getByUserid(int userid) {
		String key = this.getUseridKey(userid);
		List<PointsHistory> value = (List<PointsHistory>) this.redisLib
				.getCache(key);
		if (value == null) {
			value = this.pointsHistoryDao.getByUserid(userid);
			if (value != null) {
				this.redisLib.addCache(key, (ArrayList<PointsHistory>)value);
			}
		}
		return value;
	}

}
