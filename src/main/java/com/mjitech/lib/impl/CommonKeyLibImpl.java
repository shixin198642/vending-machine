package com.mjitech.lib.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.CommonKeyDao;
import com.mjitech.lib.CommonKeyLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.CommonKey;


@Component("commonKeyLib")
public class CommonKeyLibImpl implements CommonKeyLib{

	@Autowired
	private RedisLib redisLib;
	@Autowired
	private CommonKeyDao commonKeyDao;
	
	private Lock lock = new ReentrantLock();
	
	private String getCommonKeyNameCacheKey(String keyName) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_COMMONKEY_NAME)
				.append(keyName);
		return sb.toString();
	}
	
	
	private void removeCache(String keyName) {
		if (StringUtils.isNotEmpty(keyName)) {
			this.redisLib.removeCache(this.getCommonKeyNameCacheKey(keyName));
		}
	}


	@Override
	public int getCommonKey(CommonKey commonKey) {
		
		this.lock.lock();
		if(this.redisLib.getCache(getCommonKeyNameCacheKey(commonKey.getKeyName()))!=null){
			commonKey = (CommonKey)this.redisLib.getCache(
											getCommonKeyNameCacheKey(commonKey.getKeyName()));
			
			int key = commonKey.getCurrentKey();
			key = key + commonKey.getStep();
			commonKey.setCurrentKey(key);
			
			this.redisLib.removeCache(getCommonKeyNameCacheKey(commonKey.getKeyName()));
			this.redisLib.addCache(getCommonKeyNameCacheKey(commonKey.getKeyName()), commonKey);
		
			this.lock.unlock();
			return key;
		
		}else{
			
			int key = this.commonKeyDao.getInitialKey(commonKey);
			
			commonKey.setInitialKey(key);
			key = key + commonKey.getStep();
			commonKey.setCurrentKey(key);
			commonKey.setKeyName(commonKey.getKeyName());
			
			this.redisLib.addCache(getCommonKeyNameCacheKey(commonKey.getKeyName()), commonKey);
			this.lock.unlock();
			return key;

		}
	}

}
