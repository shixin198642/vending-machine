package com.mjitech.service;

import java.util.List;

import org.json.JSONObject;

import com.mjitech.model.Supplier;
import com.mjitech.model.SupplierContact;

public interface SupplierService {

	//供应商相关操作
	
	public JSONObject addSupplier(Supplier supplier);
	public JSONObject findSupplierDeail(int id);
	public JSONObject deleteSupplier(int id);
	public JSONObject updateSupplier(Supplier supplier);
	
	//直接写JSP非JSON的交互 6-12
	public List<Supplier> listSuppliers(Supplier supplier);
	
	
	//供应商联系人相关操作
	
	public JSONObject addSupplierContact(SupplierContact supplierContact);
	public JSONObject findSupplierContactDeail(int id);
	public JSONObject deleteSupplierContact(int id);
	public JSONObject updateSupplierContact(SupplierContact supplierContact);
	
	//读取一个供应商的所有联系人
	public JSONObject findContactListBySupplierId(int id);
	public List<SupplierContact> findContactListOBySupplierId(int id);
	
	//供应商销售产品相关操作
	//TODO
	
}
