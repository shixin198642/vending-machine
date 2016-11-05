package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.SkuSpecDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SkuSpecLib;
import com.mjitech.model.SkuSpec;

@Component("skuSpecLib")
public class SkuSpecLibImpl implements SkuSpecLib {
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private SkuSpecDao skuSpecDao;

	private String getSkuSpecIdCacheKey(int id) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_KEY_SKU_SPEC_ID).append(id);
		return sb.toString();
	}

	private String getSkuSpecSkuIdCacehKey(int skuId) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_KEY_SKU_SPEC_SKUID).append(skuId);
		return sb.toString();
	}

	private void removeCache(SkuSpec skuSpec) {
		if (skuSpec.getId() > 0) {
			this.redisLib
					.removeCache(this.getSkuSpecIdCacheKey(skuSpec.getId()));
		}
		if (skuSpec.getSkuid() > 0) {
			this.redisLib.removeCache(this.getSkuSpecSkuIdCacehKey(skuSpec
					.getSkuid()));
		}
	}

	@Override
	public SkuSpec add(SkuSpec skuSpec) {
		if (skuSpec.getCreateDatetime() == null) {
			skuSpec.setCreateDatetime(new Date());
		}
		if (skuSpec.getUpdateDatetime() == null) {
			skuSpec.setUpdateDatetime(skuSpec.getCreateDatetime());
		}
		int i = skuSpecDao.add(skuSpec);

		if (skuSpec != null && skuSpec.getId() > 0) {
			this.removeCache(skuSpec);
			this.redisLib.addCache(this.getSkuSpecIdCacheKey(skuSpec.getId()),
					skuSpec);
			return skuSpec;
		} else {
			return null;
		}
	}

	@Override
	public SkuSpec getById(int id) {

		String key = this.getSkuSpecIdCacheKey(id);
		SkuSpec skuSpec = (SkuSpec) this.redisLib.getCache(key);

		if (skuSpec == null) {
			skuSpec = this.skuSpecDao.getById(id);
			if (skuSpec != null) {
				this.redisLib.addCache(key, skuSpec);
			}
		}
		return skuSpec;
	}

	@Override
	public int update(SkuSpec skuSpec) {
		return 0;
	}

	@Override
	public int delete(int id) {

		SkuSpec skuSpec = this.getById(id);
		int i = this.skuSpecDao.delete(id);
		if (i > 0) {
			this.removeCache(skuSpec);
			return i;
		}
		return 0;
	}

	@Override
	public List<SkuSpec> getByCondition(SkuSpec skuSpec) {

		List<SkuSpec> list = this.skuSpecDao.getByCondition(skuSpec);
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<SkuSpec> getSkuSpecs(int skuId) {
		String key = this.getSkuSpecSkuIdCacehKey(skuId);
		List<SkuSpec> specs = (List<SkuSpec>) this.redisLib.getCache(key);
		if (specs != null) {
			return specs;
		}
		SkuSpec condition = new SkuSpec();
		condition.setSkuid(skuId);
		specs = this.skuSpecDao.getByCondition(condition);
		this.redisLib.addCache(key, (ArrayList<SkuSpec>) specs);

		return specs;
	}

}
