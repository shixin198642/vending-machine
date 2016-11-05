package com.mjitech.lib;

import java.io.Serializable;

public interface RedisLib {

	/**
	 * 添加消息到redis队列
	 * 
	 * @param channel
	 *            频道名称，用以区别不同的队列
	 * @param message
	 *            消息
	 */
	public void addQueue(String channel, byte[] message);

	public void addCache(String key, Serializable value);

	public Object getCache(String key);

	public void removeCache(String key);

	void addCache(String key, Serializable value, int expireSeconds);

}
