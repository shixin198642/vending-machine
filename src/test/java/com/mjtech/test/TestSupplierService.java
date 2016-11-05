package com.mjtech.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.lib.SupplierLib;
import com.mjitech.model.Supplier;
import com.mjitech.model.SupplierContact;
import com.mjitech.service.SupplierService;

public class TestSupplierService {
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}

	
	public void TestAddSupplierWithContact(){
		
		SupplierService sps = (SupplierService) ctx.getBean("supplierService");
		
		Supplier sp = new Supplier();
		sp.setSname("测试一对多公司");
		sp.setAddress("西二旗大街");
		sp.setAccount("1237912837129");
		sp.setBank("花旗银行");

//		SupplierContact sc1 = new SupplierContact();
//		SupplierContact sc2 = new SupplierContact();
//		SupplierContact sc3 = new SupplierContact();
//		
//		sc1.setCname("aaa");
//		sc2.setCname("bbb");
//		sc3.setCname("ccc");
//		
//		sc1.setTel("111");
//		sc2.setTel("222");
//		sc3.setTel("333");
//		
//		List<SupplierContact> list = new ArrayList<SupplierContact>();
//		list.add(sc1);
//		list.add(sc2);
//		list.add(sc3);
//		
//		sp.setContactlist(list);
		
		sps.addSupplier(sp);
		Assert.assertTrue(sp.getId() > 0);
	}
	
	@Test
	public void TestGetSupplierWithContact(){
		SupplierService sps = (SupplierService) ctx.getBean("supplierService");
		JSONObject jso = sps.findSupplierDeail(2);
		System.out.println(jso.toString());
		Assert.assertTrue(jso.toString().length()>2);
		
	}
}
