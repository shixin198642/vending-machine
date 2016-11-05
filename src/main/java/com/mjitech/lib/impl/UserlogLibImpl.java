package com.mjitech.lib.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisConstants;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.UserlogLib;
import com.mjitech.logdbmodel.Userlog;
import com.mjitech.utils.RedisUtils;

@Component("userlogLib")
public class UserlogLibImpl implements UserlogLib {
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public void addUserlog(Userlog userlog) {
		byte[] message = redisUtils.serialize(userlog);
		redisLib.addQueue(RedisConstants.REDIS_QUEUE_KEY_USERLOG, message);
	}

}
