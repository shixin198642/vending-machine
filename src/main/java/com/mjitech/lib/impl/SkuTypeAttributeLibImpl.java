package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.SkuTypeAttributeDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SkuTypeAttributeLib;
import com.mjitech.model.SkuTypeAttribute;

@Component("skuTypeAttributeLib")
public class SkuTypeAttributeLibImpl implements SkuTypeAttributeLib {
	private static Logger logger = LoggerFactory
			.getLogger(SkuTypeAttributeLibImpl.class);
	@Autowired
	private SkuTypeAttributeDao skuTypeAttributeDao;
	@Autowired
	private RedisLib redisLib;

	private void removeCache(SkuTypeAttribute typeAttribute) {
		if (typeAttribute.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(typeAttribute.getId()));
		}
		if (typeAttribute.getSkuTypeId() > 0) {
			this.redisLib.removeCache(this.getTypeKey(typeAttribute
					.getSkuTypeId()));
		}
	}

	private String getIdKey(int id) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUTYPEATTRIBUTE_ID)
				.append(id).toString();
	}

	private String getTypeKey(int typeId) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUTYPEATTRIBUTE_TYPEID)
				.append(typeId).toString();
	}

	@Override
	public SkuTypeAttribute getById(int id) {
		String key = this.getIdKey(id);
		SkuTypeAttribute ta = (SkuTypeAttribute) this.redisLib.getCache(key);
		if (ta == null) {
			ta = this.skuTypeAttributeDao.getById(id);
			if (ta != null) {
				this.redisLib.addCache(key, ta);
			}
		}
		return ta;
	}

	@Override
	public SkuTypeAttribute add(SkuTypeAttribute ta) {
		if (ta.getCreateDatetime() == null) {
			ta.setCreateDatetime(new Date());
		}
		if (ta.getUpdateDatetime() == null) {
			ta.setUpdateDatetime(ta.getCreateDatetime());
		}
		this.skuTypeAttributeDao.add(ta);
		if (ta.getId() > 0) {
			this.removeCache(ta);
			return ta;
		} else {
			logger.error("error in adding SkuTypeAttribute:" + ta);
		}
		return null;
	}

	@Override
	public int update(SkuTypeAttribute ta) {
		if (ta.getId() > 0) {
			if (ta.getUpdateDatetime() == null) {
				ta.setUpdateDatetime(new Date());
			}
			SkuTypeAttribute old = this.getById(ta.getId());
			if (old != null) {
				int ret = this.skuTypeAttributeDao.update(ta);
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
			SkuTypeAttribute old = this.getById(id);
			if (old != null) {
				int ret = this.skuTypeAttributeDao.delete(id);
				this.removeCache(old);
				return ret;
			}

		}
		return 0;
	}

	@Override
	public List<SkuTypeAttribute> getByType(int typeId) {
		String key = this.getTypeKey(typeId);
		List<SkuTypeAttribute> stas = (List<SkuTypeAttribute>) this.redisLib
				.getCache(key);
		if (stas != null) {
			return stas;
		}
		stas = this.skuTypeAttributeDao.getByTypeId(typeId);
		this.redisLib.addCache(key, (ArrayList<SkuTypeAttribute>) stas);
		return stas;
	}

}
