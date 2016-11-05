package com.mjtech.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.lib.SkuLib;
import com.mjitech.model.Sku;

public class TestSkuLib {
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}

	@Test
	public void testAddSku() {
		SkuLib skuLib = (SkuLib) ctx.getBean("skuLib");
		Sku sku = new Sku();
		sku.setBarcode("101010101010");
		sku.setBrand(1);
		sku.setCountry(1);
		sku.setCategory(1);
		sku.setMsrp(10.2f);
		sku.setTags("饮料,碳酸饮料");
		sku.setStatus(1);
		sku.setRemarks("hello");
		sku.setName("可口可乐500ML装");
		sku.setUnit("瓶");
		sku = skuLib.add(sku);
		Assert.assertTrue(sku.getId() > 0);
	}

}
