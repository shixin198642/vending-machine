package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.SkuAttributeDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SkuAttributeLib;
import com.mjitech.model.SkuAttribute;

@Component("skuAttributeLib")
public class SkuAttributeLibImpl implements SkuAttributeLib {
	private static Logger logger = LoggerFactory
			.getLogger(SkuAttributeLibImpl.class);
	@Autowired
	private SkuAttributeDao skuAttributeDao;
	@Autowired
	private RedisLib redisLib;

	private void removeCache(SkuAttribute attribute) {
		if (attribute.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(attribute.getId()));
		}
		if (attribute.getSkuId() > 0) {
			this.redisLib.removeCache(this.getSkuIdKey(attribute.getSkuId()));
		}
	}

	private String getIdKey(int id) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUATTRIBUTE_ID)
				.append(id).toString();
	}

	private String getSkuIdKey(int skuId) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUATTRIBUTE_SKUID)
				.append(skuId).toString();
	}

	@Override
	public SkuAttribute getById(int id) {
		String key = this.getIdKey(id);
		SkuAttribute a = (SkuAttribute) this.redisLib.getCache(key);
		if (a == null) {
			a = this.skuAttributeDao.getById(id);
			if (a != null) {
				this.redisLib.addCache(key, a);
			}
		}
		return a;
	}

	@Override
	public SkuAttribute add(SkuAttribute a) {
		if (a.getCreateDatetime() == null) {
			a.setCreateDatetime(new Date());
		}
		if (a.getUpdateDatetime() == null) {
			a.setUpdateDatetime(a.getCreateDatetime());
		}
		this.skuAttributeDao.add(a);
		if (a.getId() > 0) {
			this.removeCache(a);
			return a;
		} else {
			logger.error("error in adding SkuAttribute:" + a);
		}
		return null;
	}

	@Override
	public int update(SkuAttribute sa) {
		if (sa.getId() > 0) {
			if (sa.getUpdateDatetime() == null) {
				sa.setUpdateDatetime(new Date());
			}
			int ret = this.skuAttributeDao.update(sa);
			if (ret > 0) {
				this.removeCache(sa);
			}
			return ret;
		}
		return 0;
	}

	@Override
	public int delete(int id) {
		if (id > 0) {
			SkuAttribute old = this.getById(id);
			if (old != null) {
				int ret = this.skuAttributeDao.delete(id);
				this.removeCache(old);
				return ret;
			}

		}
		return 0;
	}


	
	@Override
	public List<SkuAttribute> getBySku(int skuId) {
		String key = this.getSkuIdKey(skuId);
		List<SkuAttribute> sas = (List<SkuAttribute>) this.redisLib
				.getCache(key);
		if (sas != null) {
			return sas;
		}
		sas = this.skuAttributeDao.getBySkuId(skuId);
		this.redisLib.addCache(key, (ArrayList<SkuAttribute>) sas);
		return sas;
	}

}
