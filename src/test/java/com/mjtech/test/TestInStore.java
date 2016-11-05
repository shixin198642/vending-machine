package com.mjtech.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.model.Instore;
import com.mjitech.service.InstoreService;

public class TestInStore {
	
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Test
	public void TestListInStore(){
		InstoreService dao = (InstoreService) ctx.getBean("inStoreService");
		List<Instore> list = dao.listInstore(1);
		
		System.out.println(list);
	}
}
