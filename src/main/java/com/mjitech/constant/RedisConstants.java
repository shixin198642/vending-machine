package com.mjitech.constant;

import java.util.ArrayList;
import java.util.List;

public class RedisConstants {
	/**
	 * redis队列key: 添加短信历史
	 */
	public final static String REDIS_QUEUE_KEY_SMS_HISTORY = "mjitech_sms_history";
	/**
	 * redis队列key: 发短信
	 */
	public final static String REDIS_QUEUE_KEY_SEND_SMS = "mjitech_send_sms";
	/**
	 * redis队列key: 添加用户日志
	 */
	public final static String REDIS_QUEUE_KEY_USERLOG = "mjitech_userlog";
	
	public final static List<String> REDIS_QUEUE_KEYS = new ArrayList<String>();
	static {
		REDIS_QUEUE_KEYS.add(REDIS_QUEUE_KEY_SMS_HISTORY);
		REDIS_QUEUE_KEYS.add(REDIS_QUEUE_KEY_SEND_SMS);
		REDIS_QUEUE_KEYS.add(REDIS_QUEUE_KEY_USERLOG);
	}
}
