package com.mjtech.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.model.Points;
import com.mjitech.service.PointsService;

public class TestPointsService {
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}

	@Test
	public void TestAddOrder() {

		PointsService ps = (PointsService) ctx.getBean("pointsService");
		Points points = ps.addUserPoints(1, 5);

		Assert.assertTrue(points.getId() > 0);
	}
}
