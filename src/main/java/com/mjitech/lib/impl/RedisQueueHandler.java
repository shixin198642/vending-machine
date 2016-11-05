package com.mjitech.lib.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mjitech.constant.RedisConstants;
import com.mjitech.dao.SmsHistoryDao;
import com.mjitech.lib.SmsLib;
import com.mjitech.logdbdao.UserlogDao;
import com.mjitech.logdbmodel.Userlog;
import com.mjitech.model.SmsHistory;
import com.mjitech.utils.RedisUtils;

@Component
public class RedisQueueHandler implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory
			.getLogger(RedisQueueHandler.class);
	@Autowired
	private SmsHistoryDao smsHistoryDao;
	@Autowired
	private SmsLib smsLib;
	@Autowired
	private UserlogDao userlogDao;
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	private Map<String, QueueListenerThread> threads = new HashMap<String, QueueListenerThread>();

	private void handleMessage(String channel, Object message) {
		if (RedisConstants.REDIS_QUEUE_KEY_SMS_HISTORY
				.equalsIgnoreCase(channel)) {
			if (logger.isDebugEnabled()) {
				logger.debug("got adding sms history message");
			}
			SmsHistory history = (SmsHistory) message;
			this.smsHistoryDao.add(history);
		} else if (RedisConstants.REDIS_QUEUE_KEY_SEND_SMS
				.equalsIgnoreCase(channel)) {
			if (logger.isDebugEnabled()) {
				logger.debug("got sending sms message");
			}
			SmsHistory history = (SmsHistory) message;
			this.smsLib
					.sendSms(history.getMobile(), history.getContent(), true);

		} else if (RedisConstants.REDIS_QUEUE_KEY_USERLOG
				.equalsIgnoreCase(channel)) {
			if (logger.isDebugEnabled()) {
				logger.debug("got adding userlog message");
			}
			Userlog log = (Userlog) message;
			userlogDao.add(log);
		}
	}

	@Override
	public void destroy() throws Exception {
		Iterator<QueueListenerThread> iter = this.threads.values().iterator();
		while(iter.hasNext()){
			QueueListenerThread t = iter.next();
			t.close();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}

	private void init() {
		for (String queueName : RedisConstants.REDIS_QUEUE_KEYS) {
			QueueListenerThread queueListenerThread = new QueueListenerThread(
					queueName);
			queueListenerThread.start();
			threads.put(queueName, queueListenerThread);
		}
	}

	class QueueListenerThread extends Thread {
		private String channel;
		private RedisConnection conn = RedisQueueHandler.this.redisTemplate
				.getConnectionFactory().getConnection();
		private boolean isClose = false;

		public QueueListenerThread(String channel) {
			this.channel = channel;
		}

		public void close() {
			isClose = true;
			this.interrupt();
		}

		@Override
		public void run() {
			while (!isClose) {
				List<byte[]> raws = conn.bRPop(30,
						redisUtils.serialize(channel));
				if (CollectionUtils.isEmpty(raws)) {
					continue;
				}
				handleMessage(channel, redisUtils.unserialize(raws.get(1)));
			}
			RedisConnectionUtils.releaseConnection(this.conn,
					redisTemplate.getConnectionFactory());
		}

	}
}
