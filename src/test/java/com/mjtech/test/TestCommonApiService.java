package com.mjtech.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.constant.JSONConstants;
import com.mjitech.service.apiservice.CommonApiService;

public class TestCommonApiService {

	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	@Test
	public void testAddOrder(){
		CommonApiService cai = (CommonApiService)ctx.getBean("commonApiService");
		Map<Integer, Integer> cart = new HashMap<Integer, Integer>();
		cart.put(590, 2);
		JSONObject ret = cai.addOrder(cart, 7, 1, 0,0);
		System.out.println(ret);
		Assert.assertTrue(ret.getBoolean(JSONConstants.RETURN_KEY_IS_SUCCESS));
	}

}
