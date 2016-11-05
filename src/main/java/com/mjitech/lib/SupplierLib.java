package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Supplier;

public interface SupplierLib extends BaseModelLib<Supplier>{
	
	public Supplier getSupplierBySname(String sname);
	
	public List<Supplier> getByCondition(Supplier supplier);
	
	//列表页面查询供应商，未分页
	public List<Supplier> getWithMinScByCondition(Supplier supplier);
}
