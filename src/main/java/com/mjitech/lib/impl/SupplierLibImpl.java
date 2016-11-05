package com.mjitech.lib.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.SupplierDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SupplierLib;
import com.mjitech.model.Supplier;

@Component("supplierLib")
public class SupplierLibImpl implements SupplierLib{
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private SupplierDao supplierDao;
	
	
	private String getSupplierIdCacheKey(int id) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SUPPLIER_ID)
				.append(id);
		return sb.toString();
	}
	
	private String getSupplierSnameCacheKey(String sname) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SUPPLIER_SNAME)
				.append(sname);
		return sb.toString();
	}
	

	private void removeCache(Supplier supplier) {
		if (supplier.getId() > 0) {
			this.redisLib.removeCache(this.getSupplierIdCacheKey(supplier
					.getId()));
		}
		if (StringUtils.isNotEmpty(supplier.getSname())) {
			this.redisLib.removeCache(this.getSupplierSnameCacheKey(supplier
					.getSname()));
		}
	}
	
	
	/** 
	 * getById
	 */
	@Override
	public Supplier getById(int id) {
		String key = this.getSupplierIdCacheKey(id);
		Supplier supplier = (Supplier) this.redisLib.getCache(key);
		
		if (supplier == null) {
			supplier = this.supplierDao.getById(id);
			if (supplier != null) {
				this.redisLib.addCache(key, supplier);
			}
		}
		return supplier;
	}
	
	
	/* 新增供应商
	 * @see com.mjitech.lib.BaseModelLib#add(com.mjitech.model.BaseModel)
	 */
	@Override
	public Supplier add(Supplier supplier) {
		if (supplier.getCreateDatetime() == null) {
			supplier.setCreateDatetime(new Date());
		}
		if (supplier.getUpdateDatetime() == null) {
			supplier.setUpdateDatetime(supplier.getCreateDatetime());
		}
		this.supplierDao.add(supplier);
		if (supplier!=null && supplier.getId() > 0) {
			this.removeCache(supplier);
			this.redisLib.addCache(
					this.getSupplierIdCacheKey(supplier.getId()), supplier);
			this.redisLib.addCache(
					this.getSupplierSnameCacheKey(supplier.getSname()),supplier);
			return supplier;
		} else {
			return null;
		}
	}
	
	/* 更新供应商
	 * @see com.mjitech.lib.BaseModelLib#update(com.mjitech.model.BaseModel)
	 */
	@Override
	public int update(Supplier supplier) {
		
		if (supplier.getUpdateDatetime() == null) {
			supplier.setUpdateDatetime(new Date());
		}
		int i = this.supplierDao.update(supplier);
		if(i>0){
			this.removeCache(supplier);
			return i;
		}	
		return 0;
	}
	

	/* 删除供应商
	 * @see com.mjitech.lib.BaseModelLib#delete(int)
	 */
	@Override
	public int delete(int id) {

		Supplier supplier = this.getById(id);
		int i = this.supplierDao.delete(id);
		
		if(i>0){
			this.removeCache(supplier);
			return i;
		}
		return 0;
	}
	
	
	@Override
	public Supplier getSupplierBySname(String sname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> getByCondition(Supplier supplier) {
		// TODO Auto-generated method stub
		return this.supplierDao.getByCondition(supplier);
	}

	@Override
	public List<Supplier> getWithMinScByCondition(Supplier supplier) {
		// TODO Auto-generated method stub
		return this.supplierDao.getWithMinScByCondition(supplier);
	}
	
}
