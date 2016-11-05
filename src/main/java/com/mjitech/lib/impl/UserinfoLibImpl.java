package com.mjitech.lib.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.UserinfoDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.model.Userinfo;

@Component("userinfoLib")
public class UserinfoLibImpl implements UserinfoLib {
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private UserinfoDao userinfoDao;

	private String getUserinfoIdCacheKey(int id) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_USERINFO_ID)
				.append(id);
		return sb.toString();
	}

	private String getUserinfoUsernameCacheKey(String username) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_USERINFO_USERNAME)
				.append(username);
		return sb.toString();
	}

	private String getUserinfoOpenIdCacheKey(String openId) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_USERINFO_OPENID)
				.append(openId);
		return sb.toString();
	}

	private void removeCache(Userinfo userinfo) {
		if (userinfo.getId() > 0) {
			this.redisLib.removeCache(this.getUserinfoIdCacheKey(userinfo
					.getId()));
		}
		if (StringUtils.isNotEmpty(userinfo.getUsername())) {
			this.redisLib.removeCache(this.getUserinfoUsernameCacheKey(userinfo
					.getUsername()));
		}
	}

	private boolean isOpenIdExisted(String openId, int currentId) {
		boolean is = false;
		Userinfo user = this.getByOpenId(openId);
		if (user != null && user.getId() > 0) {
			is = (currentId == user.getId());
		}
		return is;
	}

	@Override
	public Userinfo addUser(Userinfo userinfo){
		if (userinfo.getCreateDatetime() == null) {
			userinfo.setCreateDatetime(new Date());
		}
		if (userinfo.getUpdateDatetime() == null) {
			userinfo.setUpdateDatetime(userinfo.getCreateDatetime());
		}
		if (StringUtils.isNotEmpty(userinfo.getOpenId())) {
			if (this.isOpenIdExisted(userinfo.getOpenId(), 0)) {
				return null;
			}
		}
		userinfoDao.add(userinfo);
		if (userinfo.getId() > 0) {
			this.removeCache(userinfo);
			this.redisLib.addCache(
					this.getUserinfoIdCacheKey(userinfo.getId()), userinfo);
			this.redisLib.addCache(
					this.getUserinfoUsernameCacheKey(userinfo.getUsername()),
					userinfo);
			return userinfo;
		} else {
			return null;
		}

	}

	@Override
	public Userinfo getByUsername(String username) {
		String key = this.getUserinfoUsernameCacheKey(username);
		Userinfo userinfo = (Userinfo) this.redisLib.getCache(key);
		if (userinfo == null) {
			userinfo = this.userinfoDao.getByUsername(username);
			if (userinfo != null) {
				this.redisLib.addCache(key, userinfo);
			}
		}
		return userinfo;
	}

	@Override
	public int updateById(Userinfo userinfo){
		if (userinfo.getId() > 0) {
			if (StringUtils.isNotEmpty(userinfo.getOpenId())) {
				Userinfo old = this.getByOpenId(userinfo.getOpenId());
				if (old != null) {
					Userinfo update = new Userinfo();
					update.setId(old.getId());
					update.setOpenId("");
					update.setUpdateDatetime(new Date());
					this.userinfoDao.update(update);
					this.removeCache(old);
				}
			}
			if (userinfo.getUpdateDatetime() == null) {
				userinfo.setUpdateDatetime(new Date());
			}
			int ret = this.userinfoDao.update(userinfo);
			if (ret > 0) {
				this.removeCache(userinfo);
				return ret;
			}
		}

		return 0;
	}

	@Override
	public Userinfo getById(int id) {
		String cacheKey = this.getUserinfoIdCacheKey(id);
		Userinfo info = (Userinfo) this.redisLib.getCache(cacheKey);
		if (info == null) {
			info = this.userinfoDao.getById(id);
			if (info != null) {
				this.redisLib.addCache(cacheKey, info);
			}
		}
		return info;
	}

	@Override
	public Userinfo getByOpenId(String openId) {
		String cacheKey = this.getUserinfoOpenIdCacheKey(openId);
		Userinfo info = (Userinfo) this.redisLib.getCache(cacheKey);
		if (info == null) {
			Userinfo condition = new Userinfo();
			condition.setOpenId(openId);
			List<Userinfo> dbs = this.userinfoDao.getByCondition(condition);
			if (dbs != null && dbs.size() > 0) {
				info = dbs.get(0);
			}
			if (info != null) {
				this.redisLib.addCache(cacheKey, info);
			}
		}
		return info;
	}

	@Override
	public List<Userinfo> getUserinfoList(Userinfo userinfo) {
		return this.userinfoDao.getByCondition(userinfo);
	}

}
