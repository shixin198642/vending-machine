package com.mjtech.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.model.Order;
import com.mjitech.model.Supplier;
import com.mjitech.service.OrderService;
import com.mjitech.service.SupplierService;

public class TestOrderService {
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	

	@Test
	public void TestAddOrder(){
		
		OrderService os = (OrderService) ctx.getBean("orderService");
		Order od = new Order();
		od.setOrderdate(new Date());
		od.setSuppliercontactid(1);
		od.setSupplierid(1);
		od.setStorehousemanagerid(1);
		od.setStorehouseid(1);
		od.setPaydate(new Date());
		od.setPayname("test com.");
		od.setPaybank("test Bank");
		od.setPayaccount("1010101010");
		od.setPayamt(1000);
		od.setPaymode(1);
		od.setInvoicetype("办公用品");
		od.setReceivedate(new Date());
		od.setContract("test");
		od.setDistamt(100);
		od.setOrderstate(1);
		
//		od.setCreator(1);
//		od.setUpdator(1);
		
		os.addOrder(od);
		
		
		Assert.assertTrue(od.getId() > 0);
	}
}
