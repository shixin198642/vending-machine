package com.mjitech.lib.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.SupplierContactDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SupplierContactLib;
import com.mjitech.model.SupplierContact;

@Component("supplierContactLib")
public class SupplierContactLibImpl implements SupplierContactLib {
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private SupplierContactDao supplierContactDao;
	
	
	private String getSupplierContactIdCacheKey(int id) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_SUPPLIERCONTACT_ID)
				.append(id);
		return sb.toString();
	}
	
	private void removeCache(SupplierContact supplierContact) {
		
		if (supplierContact.getId() > 0) {
			this.redisLib.removeCache(this.getSupplierContactIdCacheKey(supplierContact.getId()));
		}
	}
	
	@Override
	public SupplierContact add(SupplierContact supplierContact) {
		if (supplierContact.getCreateDatetime() == null) {
			supplierContact.setCreateDatetime(new Date());
		}
		if (supplierContact.getUpdateDatetime() == null) {
			supplierContact.setUpdateDatetime(supplierContact.getCreateDatetime());
		}
		supplierContactDao.add(supplierContact);
		if (supplierContact!=null && supplierContact.getId() > 0) {
			this.removeCache(supplierContact);
			this.redisLib.addCache(
					this.getSupplierContactIdCacheKey(supplierContact.getId()), supplierContact);
			return supplierContact;
		} else {
			return null;
		}
	}

	@Override
	public SupplierContact getById(int id) {
		String key = this.getSupplierContactIdCacheKey(id);
		SupplierContact supplierContact = (SupplierContact) this.redisLib.getCache(key);
		
		if (supplierContact == null) {
			supplierContact = this.supplierContactDao.getById(id);
			if (supplierContact != null) {
				this.redisLib.addCache(key, supplierContact);
			}
		}
		return supplierContact;
	}



	@Override
	public int update(SupplierContact supplierContact) {

		if (supplierContact.getUpdateDatetime() == null) {
			supplierContact.setUpdateDatetime(new Date());
		}
		int i = this.supplierContactDao.update(supplierContact);
		if(i>0){
			this.removeCache(supplierContact);
			return i;
		}	
		return 0;
	}



	@Override
	public int delete(int id) {
		SupplierContact supplierContact = this.getById(id);
		int i = this.supplierContactDao.delete(id);
		if(i>0){
			this.removeCache(supplierContact);
			return i;
		}
		return 0;
	}

	@Override
	public List<SupplierContact> getByCondition(SupplierContact supplierContact) {

		List<SupplierContact> list = this.supplierContactDao.getByCondition(supplierContact);
		
		if(list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public int removeAllSupplierMainContactTag(SupplierContact supplierContact) {
		if (supplierContact.getUpdateDatetime() == null) {
			supplierContact.setUpdateDatetime(new Date());
		}
		int i = this.supplierContactDao.removeAllSupplierMainContactTag(supplierContact);
		if(i>0){
			//TODO 清空缓存
			List<SupplierContact> list = getByCondition(supplierContact);
			if(list!=null && list.size()>0){
				System.out.println(list.size());
				for(SupplierContact sc:list){
					this.removeCache(sc);
				}
			}
			return i;
		}
		return 0;
	}
	
	
}
