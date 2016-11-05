package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.DictAreaConstants;
import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.DictAreaDao;
import com.mjitech.lib.DictAreaLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.DictArea;

@Component("dictAreaLib")
public class DictAreaLibImpl implements DictAreaLib {
	private static Logger logger = LoggerFactory
			.getLogger(DictAreaLibImpl.class);
	@Autowired
	private DictAreaDao dictAreaDao;
	@Autowired
	private RedisLib redisLib;

	private String getIDKey(int id) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_DICTAREA_ID)
				.append(id);
		return key.toString();
	}

	private String getListByParentKey(int parentId) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_DICTAREA_LISTBYPARENT)
				.append(parentId);
		return key.toString();
	}

	private String getNameKey(String name) {
		StringBuilder key = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_DICTAREA_NAME)
				.append(name);
		return key.toString();
	}

	private String getCountryListKey() {
		return RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_DICTAREA_ALLCOUNTRY;
	}

	private void removeCache(DictArea area) {
		this.redisLib.removeCache(this.getIDKey(area.getId()));
		if (area.getType() == DictAreaConstants.AREA_TYPE_COUNTRY) {
			this.redisLib.removeCache(this.getCountryListKey());
		} else {
			this.redisLib.removeCache(this.getListByParentKey(area
					.getParentId()));
		}
		this.redisLib.removeCache(this.getNameKey(area.getName()));
	}

	@Override
	public DictArea add(DictArea area) {
		if (area.getCreateDatetime() == null) {
			area.setCreateDatetime(new Date());
		}
		if (area.getUpdateDatetime() == null) {
			area.setUpdateDatetime(area.getCreateDatetime());
		}
		this.dictAreaDao.add(area);
		if (area.getId() > 0) {
			this.removeCache(area);
			return area;
		} else {
			logger.error("failed in saving " + area);
			return null;
		}

	}

	@Override
	public List<DictArea> getAllCountries() {
		String allCountryCacheKey = this.getCountryListKey();
		List<DictArea> addresses = (List<DictArea>) this.redisLib
				.getCache(allCountryCacheKey);
		if (addresses == null) {
			ArrayList<DictArea> dbaddresses = (ArrayList<DictArea>) this.dictAreaDao
					.getByType(DictAreaConstants.AREA_TYPE_COUNTRY);

			this.redisLib.addCache(allCountryCacheKey, dbaddresses);
			addresses = dbaddresses;
		}

		return addresses;
	}

	@Override
	public List<DictArea> getAllProvinces(int countryId) {
		if (countryId == 0) {
			return new ArrayList<DictArea>();
		}
		String provinceListCacheKey = this.getListByParentKey(countryId);
		List<DictArea> addresses = (List<DictArea>) this.redisLib
				.getCache(provinceListCacheKey);
		if (addresses == null) {
			ArrayList<DictArea> dbaddresses = (ArrayList<DictArea>) this.dictAreaDao
					.getByParent(countryId);

			this.redisLib.addCache(provinceListCacheKey, dbaddresses);
			addresses = dbaddresses;
		}
		return addresses;
	}

	@Override
	public List<DictArea> getAllCities(int provinceId) {
		String cityListCacheKey = this.getListByParentKey(provinceId);
		List<DictArea> addresses = (List<DictArea>) this.redisLib
				.getCache(cityListCacheKey);
		if (addresses == null) {
			ArrayList<DictArea> dbaddresses = (ArrayList<DictArea>) this.dictAreaDao
					.getByParent(provinceId);

			this.redisLib.addCache(cityListCacheKey, dbaddresses);
			addresses = dbaddresses;
		}
		return addresses;
	}

	@Override
	public int delete(int id) {
		DictArea address = this.getById(id);
		if (address != null) {
			this.removeCache(address);
			return this.dictAreaDao.delete(id);
		} else {
			return 0;
		}

	}

	@Override
	public DictArea getById(int id) {
		String cacheKey = this.getIDKey(id);
		DictArea address = (DictArea) this.redisLib.getCache(cacheKey);
		if (address == null) {
			address = this.dictAreaDao.getById(id);
			if (address != null) {
				this.redisLib.addCache(cacheKey, address);
			}
		}
		return address;
	}

	@Override
	public List<DictArea> getAllDistricts(int cityId) {
		String districtListCacheKey = this.getListByParentKey(cityId);
		List<DictArea> addresses = (List<DictArea>) this.redisLib
				.getCache(districtListCacheKey);
		if (addresses == null) {
			ArrayList<DictArea> dbaddresses = (ArrayList<DictArea>) this.dictAreaDao
					.getByParent(cityId);

			this.redisLib.addCache(districtListCacheKey, dbaddresses);
			addresses = dbaddresses;
		}
		return addresses;
	}

	@Override
	public List<DictArea> getChildren(int parentId) {
		String listCacheKey = this.getListByParentKey(parentId);
		List<DictArea> addresses = (List<DictArea>) this.redisLib
				.getCache(listCacheKey);
		if (addresses == null) {
			ArrayList<DictArea> dbaddresses = (ArrayList<DictArea>) this.dictAreaDao
					.getByParent(parentId);

			this.redisLib.addCache(listCacheKey, dbaddresses);
			addresses = dbaddresses;
		}
		return addresses;
	}

	@Override
	public List<DictArea> getByName(String name) {
		String key = this.getNameKey(name);
		List<DictArea> addresses = (List<DictArea>) this.redisLib.getCache(key);
		if (addresses == null) {
			DictArea condition = new DictArea();
			condition.setName(name);
			ArrayList<DictArea> dbaddresses = (ArrayList<DictArea>) this.dictAreaDao
					.getByCondition(condition);
			addresses = dbaddresses;
			this.redisLib.addCache(key, dbaddresses);

		}
		return addresses;
	}

	@Override
	public int update(DictArea area) {
		if (area.getId() > 0) {
			if (area.getUpdateDatetime() == null) {
				area.setUpdateDatetime(new Date());
			}
			this.dictAreaDao.update(area);
			this.removeCache(area);
		}

		return 0;
	}

}
