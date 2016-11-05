package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.SkuTypeDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SkuTypeLib;
import com.mjitech.model.SkuType;

@Component("skuTypeLib")
public class SkuTypeLibImpl implements SkuTypeLib {
	@Autowired
	private SkuTypeDao skuTypeDao;
	@Autowired
	private RedisLib redisLib;

	private String getSkuTypeIdCacheKey(int id) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUTYPE_ID).append(id);
		return sb.toString();
	}

	private String getSkuTypeParentCacheKey(int parentId) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUTYPE_PARENTID)
				.append(parentId);
		return sb.toString();
	}

	private String getSkuTypeNameCacheKey(String name) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUTYPE_NAME)
				.append(name);
		return sb.toString();
	}

	private void removeCache(SkuType skuType) {
		if (skuType.getId() > 0) {
			this.redisLib
					.removeCache(this.getSkuTypeIdCacheKey(skuType.getId()));
		}
		if (skuType.getParentId() > 0) {
			this.redisLib.removeCache(this.getSkuTypeParentCacheKey(skuType
					.getParentId()));
		}
		if (StringUtils.isNotEmpty(skuType.getName())) {
			this.redisLib.removeCache(this.getSkuTypeNameCacheKey(skuType
					.getName()));
		}
	}

	@Override
	public SkuType getById(int id) {
		String key = this.getSkuTypeIdCacheKey(id);
		SkuType cache = (SkuType) this.redisLib.getCache(key);
		if (cache != null) {
			return cache;
		}
		cache = this.skuTypeDao.getById(id);
		this.redisLib.addCache(key, cache);
		return cache;
	}

	@Override
	public SkuType add(SkuType st) {
		if (st.getCreateDatetime() == null) {
			st.setCreateDatetime(new Date());
		}
		if (st.getUpdateDatetime() == null) {
			st.setUpdateDatetime(st.getCreateDatetime());
		}
		this.skuTypeDao.add(st);
		if (st.getId() > 0) {
			this.removeCache(st);
			return st;
		}
		return null;
	}

	@Override
	public int update(SkuType st) {
		if (st.getId() > 0) {
			if (st.getUpdateDatetime() == null) {
				st.setUpdateDatetime(new Date());
			}
			SkuType old = this.getById(st.getId());
			int ret = this.skuTypeDao.update(st);
			if(ret > 0){
				this.removeCache(old);
				return ret;
			}
			
		}
		return 0;
	}

	@Override
	public int delete(int id) {
		SkuType old = this.getById(id);
		if (old != null) {
			int ret = this.skuTypeDao.delete(id);
			this.removeCache(old);
			return ret;
		}
		return 0;
	}

	@Override
	public List<SkuType> getByParentId(int parentId) {
		String key = this.getSkuTypeParentCacheKey(parentId);
		List<SkuType> sts = (List<SkuType>) this.redisLib.getCache(key);
		if (sts != null) {
			return sts;
		}
		sts = this.skuTypeDao.getByParentId(parentId);
		this.redisLib.addCache(key, (ArrayList) sts);
		return sts;
	}
	
	@Override
	public List<SkuType> getByName(String name) {
		String key = this.getSkuTypeNameCacheKey(name);
		List<SkuType> sts = (List<SkuType>) this.redisLib.getCache(key);
		if (sts != null) {
			return sts;
		}
		SkuType condition = new SkuType();
		condition.setName(name);
		sts = this.skuTypeDao.getByCondition(condition);
		this.redisLib.addCache(key, (ArrayList) sts);
		return sts;
	}

}
