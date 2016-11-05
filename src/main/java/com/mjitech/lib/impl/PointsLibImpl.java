package com.mjitech.lib.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.PointsDao;
import com.mjitech.lib.PointsLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.Points;

@Component("pointsLib")
public class PointsLibImpl implements PointsLib {
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private PointsDao pointsDao;
	private static Logger logger = LoggerFactory.getLogger(PointsLibImpl.class);

	private String getIDKey(int id) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_POINTS_ID).append(id);
		return key.toString();
	}

	private String getUseridKey(int userid) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_POINTS_USERID)
				.append(userid);
		return key.toString();
	}

	private void removeCache(Points points) {
		if (points.getId() > 0) {
			this.redisLib.removeCache(this.getIDKey(points.getId()));
		}
		if (points.getUserid() > 0) {
			this.redisLib.removeCache(this.getUseridKey(points.getUserid()));
		}
	}

	@Override
	public Points getById(int id) {
		String key = this.getIDKey(id);
		Points value = (Points) this.redisLib.getCache(key);
		if (value == null) {
			value = this.pointsDao.getById(id);
			if (value != null) {
				this.redisLib.addCache(key, value);
			}
		}
		return value;
	}

	@Override
	public Points add(Points t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		this.pointsDao.add(t);
		if (t.getId() > 0) {
			this.removeCache(t);
			return t;
		}
		return null;
	}

	@Override
	public int update(Points t) {

		if (t.getId() > 0) {
			if (t.getUpdateDatetime() == null) {
				t.setUpdateDatetime(new Date());
			}
			Points old = this.getById(t.getId());
			if (old != null) {
				this.removeCache(old);
				int ret = this.pointsDao.update(t);
				return ret;
			}

		}

		return 0;
	}

	@Override
	public int delete(int id) {
		Points old = this.getById(id);
		if (old != null) {
			this.removeCache(old);
			return this.pointsDao.delete(id);
		}
		return 0;
	}

	@Override
	public Points getByUserid(int userid) {
		String key = this.getUseridKey(userid);
		Points value = (Points) this.redisLib.getCache(key);
		if (value == null) {
			value = this.pointsDao.getByUserid(userid);
			if (value != null) {
				this.redisLib.addCache(key, value);
			}
		}
		return value;
	}

}
