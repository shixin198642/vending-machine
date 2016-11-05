package com.mjtech.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.constant.UserlogConstants;
import com.mjitech.lib.UserlogLib;
import com.mjitech.logdbdao.UserlogDao;
import com.mjitech.logdbmodel.Userlog;

public class TestUserLogLib {
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}

	@Test
	public void testAddLog() {
		UserlogLib userLogLib = (UserlogLib) ctx.getBean("userlogLib");
		UserlogDao userlogDao = (UserlogDao) ctx.getBean("userlogDao");
		Userlog userlog = new Userlog();
		userlog.setDescription("dddd");
		userlog.setIp("1.1.1.1");
		userlog.setLogTime(new Date());
		userlog.setType(UserlogConstants.USERLOG_TYPE_LOGOUT);
		userlog.setSourceId(1);
		userLogLib.addUserlog(userlog);
		Userlog fromdb = userlogDao.getLatestLog();

		Assert.assertTrue(fromdb.getIp().equalsIgnoreCase(userlog.getIp())
				&& fromdb.getType() == userlog.getType());
	}
}
