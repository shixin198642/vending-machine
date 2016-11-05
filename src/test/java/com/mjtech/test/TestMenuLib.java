package com.mjtech.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.lib.MenuLib;
import com.mjitech.model.Menu;

public class TestMenuLib {
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Test
	public void testAddMenu() {
		MenuLib menuLib = (MenuLib) ctx.getBean("menuLib");
		Menu menu = menuLib.getById(7);
		menu.setSortNumber(7);
		menu.setUrl("sku_list.action");
		menuLib.update(menu);
//		Menu menu = new Menu();
//		menu.setParentId(1);
//		menu.setName("商品列表");
//		menu.setUrl("sku_list.action");
//		menu.setSortNumber(1);
//		menuLib.add(menu);
		Assert.assertTrue(menuLib.getById(7).getSortNumber() == 7);
	}
}
