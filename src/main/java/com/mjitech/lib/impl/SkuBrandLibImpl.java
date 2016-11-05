package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.SkuBrandDao;
import com.mjitech.lib.FileModelLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SkuBrandLib;
import com.mjitech.model.FileModel;
import com.mjitech.model.SkuBrand;

@Component("skuBrandLib")
public class SkuBrandLibImpl implements SkuBrandLib {
	@Autowired
	private SkuBrandDao skuBrandDao;
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private FileModelLib fileModelLib;

	private String getSkuBrandIdCacheKey(int id) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUBRAND_ID)
				.append(id);
		return sb.toString();
	}

	private String getSkuBrandAllCacheKey() {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUBRAND_ALL);
		return sb.toString();
	}

	private String getSkuBrandNameCacheKey(String name) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SKUBRAND_NAME)
				.append(name);
		return sb.toString();
	}

	private void removeCache(SkuBrand skuBrand) {
		this.redisLib.removeCache(this.getSkuBrandAllCacheKey());
		if (skuBrand.getId() > 0) {
			this.redisLib.removeCache(this.getSkuBrandIdCacheKey(skuBrand
					.getId()));
		}
		if (StringUtils.isNotEmpty(skuBrand.getName())) {
			this.redisLib.removeCache(this.getSkuBrandNameCacheKey(skuBrand
					.getName()));
		}
	}

	@Override
	public SkuBrand getById(int id) {
		String key = this.getSkuBrandIdCacheKey(id);
		SkuBrand cache = (SkuBrand) this.redisLib.getCache(key);
		if (cache == null) {
			cache = this.skuBrandDao.getById(id);
			if (cache != null) {
				this.redisLib.addCache(key, cache);
			}
		}
		if (cache != null) {
			cache.setImagePath("");
			List<FileModel> images = this.fileModelLib.getSkuBrandImages(id);
			if (images != null && images.size() > 0) {
				cache.setImagePath(images.get(0).getFilePath());
			}
		}
		return cache;
	}

	@Override
	public SkuBrand add(SkuBrand st) {
		if (st.getCreateDatetime() == null) {
			st.setCreateDatetime(new Date());
		}
		if (st.getUpdateDatetime() == null) {
			st.setUpdateDatetime(st.getCreateDatetime());
		}
		this.skuBrandDao.add(st);
		if (st.getId() > 0) {
			this.removeCache(st);
			return st;
		}
		return null;
	}

	@Override
	public int update(SkuBrand st) {
		if (st.getId() > 0) {
			if (st.getUpdateDatetime() == null) {
				st.setUpdateDatetime(new Date());
			}
			this.skuBrandDao.update(st);
			this.removeCache(st);
		}
		return 0;
	}

	@Override
	public int delete(int id) {
		SkuBrand old = this.getById(id);
		if (old != null) {
			int ret = this.skuBrandDao.delete(id);
			this.removeCache(old);
			return ret;
		}
		return 0;
	}

	@Override
	public List<SkuBrand> getAll() {
		String key = this.getSkuBrandAllCacheKey();
		List<SkuBrand> sbs = (List<SkuBrand>) this.redisLib.getCache(key);
		if (sbs != null) {
			return sbs;
		}
		sbs = this.skuBrandDao.getAll();
		this.redisLib.addCache(key, (ArrayList) sbs);
		return sbs;
	}

	@Override
	public List<SkuBrand> getByName(String name) {
		String key = this.getSkuBrandNameCacheKey(name);
		List<SkuBrand> sbs = (List<SkuBrand>) this.redisLib.getCache(key);
		if (sbs != null) {
			return sbs;
		}
		SkuBrand condition = new SkuBrand();
		condition.setName(name);
		sbs = this.skuBrandDao.getByCondition(condition);
		this.redisLib.addCache(key, (ArrayList) sbs);
		return sbs;
	}

}
