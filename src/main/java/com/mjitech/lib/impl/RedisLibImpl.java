package com.mjitech.lib.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.mjitech.lib.RedisLib;
import com.mjitech.utils.RedisUtils;

@Component("redisLib")
public class RedisLibImpl implements RedisLib {

	private static Logger logger = LoggerFactory.getLogger(RedisLibImpl.class);

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	@Autowired
	private RedisUtils redisUtils;

	@Value("${redis.default.expireseconds}")
	private long cacheExpireSeconds;

	public void addQueue(final String channel, final byte[] message) {
		if (logger.isTraceEnabled()) {
			logger.trace("enter adding redis queue, channel:" + channel);
		}

		this.redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.lPush(redisUtils.serialize(channel), message);
			}
		});
		if (logger.isTraceEnabled()) {
			logger.trace("exit adding redis queue");
		}
	}
	
	@Override
	public void addCache(final String key, final Serializable value, final int expireSeconds){
		if (logger.isTraceEnabled()) {
			logger.trace("enter adding redis cache with expire seconds, key:" + key);
		}
		this.redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(
						redisTemplate.getStringSerializer().serialize(key),
						expireSeconds, redisUtils.serialize(value));
				return null;
			}

		});
		if (logger.isTraceEnabled()) {
			logger.trace("exit adding redis cache with expire seconds");
		}
	}
	
	@Override
	public void addCache(final String key, final Serializable value) {
		if (logger.isTraceEnabled()) {
			logger.trace("enter adding redis cache, key:" + key);
		}
		this.redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(
						redisTemplate.getStringSerializer().serialize(key),
						cacheExpireSeconds, redisUtils.serialize(value));
				return null;
			}

		});
		if (logger.isTraceEnabled()) {
			logger.trace("exit adding redis cache");
		}
	}

	@Override
	public Object getCache(final String key) {
		if (logger.isTraceEnabled()) {
			logger.trace("enter getting redis cache, key:" + key);
		}
		Object ret = this.redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] bytekey = redisTemplate.getStringSerializer().serialize(
						key);
				if (connection.exists(bytekey)) {
					byte[] value = connection.get(bytekey);
					Object obj = redisUtils.unserialize(value);
					return obj;
				}
				return null;
			}

		});
		if (logger.isTraceEnabled()) {
			logger.trace("exit getting redis cache");
		}
		return ret;
	}

	@Override
	public void removeCache(final String key) {
		if (logger.isTraceEnabled()) {
			logger.trace("enter removing redis cache, key:" + key);
		}
		this.redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.del(redisTemplate.getStringSerializer().serialize(
						key));
				return null;
			}

		});
		if (logger.isTraceEnabled()) {
			logger.trace("exit removing redis cache");
		}

	}

	class QueueListenerThread extends Thread {
		@Override
		public void run() {
			
		}
	}
}
