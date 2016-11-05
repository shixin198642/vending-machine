package com.mjtech.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.constant.CommonConstants;
import com.mjitech.constant.UserinfoConstants;
import com.mjitech.exception.UserExistedException;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.model.Userinfo;
import com.mjitech.utils.PasswordUtils;

public class TestUserinfoLib {
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}

	@Test
	public void testAddUser() throws UserExistedException {
		UserinfoLib ul = (UserinfoLib) ctx.getBean("userinfoLib");
		Userinfo user = this.createUser();

		user = ul.addUser(user);

		Assert.assertTrue(user!=null && user.getId() > 0);
	}

	private Userinfo createUser() {
		PasswordUtils pu = (PasswordUtils) ctx.getBean("passwordUtils");
		Userinfo user = new Userinfo();
		user.setUsername("streetgentleman@126.com");
		user.setPassword(pu.hashPassword("123456"));
		user.setDisplayName("叶耀键");
		user.setEmail("streetgentleman@126.com");
		user.setGender(CommonConstants.GENDER_MALE);
		user.setMobile("15201636681");
		user.setUserType(UserinfoConstants.USER_TYPE_USER);
		user.setImage("");
		return user;
	}

}
