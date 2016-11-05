package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.SupplierContact;

public interface SupplierContactDao extends BaseDao<SupplierContact> {
	
	public List<SupplierContact> getByCondition(SupplierContact supplierContact);
	
	//将指定供应商关联的所有联系人都置成 非主要联系人
	public int removeAllSupplierMainContactTag(SupplierContact supplierContact);
}
