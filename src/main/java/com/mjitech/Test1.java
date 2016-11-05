package com.mjitech;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.constant.SkuTypeAttributeConstants;
import com.mjitech.lib.SkuTypeAttributeLib;
import com.mjitech.lib.SkuTypeLib;
import com.mjitech.model.SkuType;
import com.mjitech.model.SkuTypeAttribute;

public class Test1 {
	public static void main(String[] args) throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SkuTypeLib stl = (SkuTypeLib) ctx.getBean("skuTypeLib");
		SkuTypeAttributeLib stal = (SkuTypeAttributeLib)ctx.getBean("skuTypeAttributeLib");
		
		String[] types = new String[] { "辅食","奶粉","清洁","应季","杂货","纸尿裤" };
//		SkuTypeAttribute sta = new SkuTypeAttribute();
//		sta.setSkuTypeId(5);
//		sta.setName("年龄段");
//		sta.setUnit("");
//		sta.setOptions("0-3个月,4-6个月,6-12个月,1-2岁");
//		sta.setRemarks("");
//		sta.setType(SkuTypeAttributeConstants.ATTRIBUTE_TYPE_SELECT);
//		stal.add(sta);
//		System.out.println("ok:"+sta);
		int parentTypeId = 5;
		for(String type : types){
			SkuType st = new SkuType();
			st.setName(type);
			st.setParentId(parentTypeId);
			stl.add(st);
			System.out.println(st);
			List<SkuTypeAttribute> stas = stal.getByType(parentTypeId);
			for(SkuTypeAttribute a : stas){
				SkuTypeAttribute ta = new SkuTypeAttribute();
				ta.setName(a.getName());
				ta.setOptions(a.getOptions());
				ta.setRemarks(a.getRemarks());
				ta.setSkuTypeId(st.getId());
				ta.setType(a.getType());
				ta.setUnit(a.getUnit());
				stal.add(ta);
			}
		}
		System.out.println("ok");
		
	}
}
