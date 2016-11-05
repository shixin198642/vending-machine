package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.constant.SkuConstants;
import com.mjitech.dao.SkuDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.model.Sku;

@Component("skuLib")
public class SkuLibImpl implements SkuLib {
	private static Logger logger = LoggerFactory.getLogger(SkuLibImpl.class);
	@Autowired
	private SkuDao skuDao;

	@Autowired
	private RedisLib redisLib;

	private void removeCache(Sku sku) {
		if (sku.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(sku.getId()));
		}
		if (StringUtils.isNotEmpty(sku.getBarcode())) {
			this.redisLib.removeCache(this.getBarcodeKey(sku.getBarcode()));
		}
		if (StringUtils.isNotEmpty(sku.getSkuNumber())) {
			this.redisLib.removeCache(this.getSkuNumberKey(sku.getSkuNumber()));
		}
		if (sku.getBrand() > 0) {
			this.redisLib.removeCache(this.getBrandKey(sku.getBrand()));
		}
	}

	private String getIdKey(int id) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SKU_ID).append(id)
				.toString();
	}

	private String getBarcodeKey(String barcode) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SKU_BARCODE)
				.append(barcode).toString();
	}

	private String getSkuNumberKey(String skuNumber) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SKU_SKUNUMBER)
				.append(skuNumber).toString();
	}

	private String getBrandKey(int brand) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SKU_BRAND).append(
				brand).toString();
	}

	@Override
	public Sku getById(int id) {
		String key = this.getIdKey(id);
		Sku sku = (Sku) this.redisLib.getCache(key);
		if (sku == null) {
			sku = this.skuDao.getById(id);
			if (sku != null) {
				this.redisLib.addCache(key, sku);
			}
		}
		return sku;
	}

	@Override
	public Sku add(Sku sku) {
		if (sku.getCreateDatetime() == null) {
			sku.setCreateDatetime(new Date());
		}
		if (sku.getUpdateDatetime() == null) {
			sku.setUpdateDatetime(sku.getCreateDatetime());
		}
		if (sku.getImagePath() == null) {
			sku.setImagePath("");
		}
		this.skuDao.add(sku);
		if (sku.getId() > 0) {
			this.removeCache(sku);
			return sku;
		} else {
			logger.error("error in adding sku:" + sku);
		}
		return null;
	}

	@Override
	public int update(Sku sku) {
		if (sku.getId() > 0) {
			if (sku.getUpdateDatetime() == null) {
				sku.setUpdateDatetime(new Date());
			}
			Sku old = this.getById(sku.getId());
			if (old != null) {
				int ret = this.skuDao.update(sku);
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
			int ret = this.skuDao.delete(id);
			if (ret > 0) {
				Sku sku = new Sku();
				sku.setId(id);
				this.removeCache(sku);
				return ret;
			}
		}
		return 0;
	}

	@Override
	public Sku getSkuBySkuNumber(String skuNumber) {
		String key = this.getSkuNumberKey(skuNumber);
		Sku sku = (Sku) this.redisLib.getCache(key);
		if (sku == null) {
			Sku condition = new Sku();
			condition.setSkuNumber(skuNumber);
			List<Sku> skus = this.skuDao.getByCondition(condition);
			if (skus != null && skus.size() > 0) {
				sku = skus.get(0);
				this.redisLib.addCache(key, sku);
			}
		}
		return sku;
	}

	@Override
	public Sku getSkuByBarcode(String barcode) {
		String key = this.getBarcodeKey(barcode);
		Sku sku = (Sku) this.redisLib.getCache(key);
		if (sku == null) {
			sku = this.skuDao.getByBarcode(barcode);
			if (sku != null) {
				this.redisLib.addCache(key, sku);
			}
		}
		return sku;
	}

	@Override
	public Sku getDraftSku(int userid) {
		Sku condition = new Sku();
		condition.setCreator(userid);
		condition.setStatus(SkuConstants.STATUS_DRAFT);
		List<Sku> skus = skuDao.getByCondition(condition);
		if (skus != null && skus.size() > 0) {
			return skus.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Sku> getSkus(Sku condition) {
		return this.skuDao.getByCondition(condition);
	}

	@Override
	public int getSkusTotal(Sku condition) {
		return this.skuDao.getTotalByCondition(condition);
	}

	@Override
	public int getCountByParentType(int parentType) {
		return this.skuDao.getCountByParentType(parentType);
	}

	@Override
	public List<Sku> getByBrand(int brand) {
		String key = this.getBrandKey(brand);
		List<Sku> value = (List<Sku>) this.redisLib.getCache(key);
		if (value == null) {
			Sku condition = new Sku();
			condition.setPerpage(9999);
			condition.setBrand(brand);
			value = this.skuDao.getByCondition(condition);
			if(value!=null){
				this.redisLib.addCache(key, (ArrayList<Sku>)value);
			}
		}
		return value;
	}

}
