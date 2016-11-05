package com.mjitech.dao;

import java.util.List;
import com.mjitech.model.Supplier;

public interface SupplierDao extends BaseDao<Supplier> {
	public Supplier getBySname(String sname);
	public List<Supplier> getByCondition(Supplier supplier);
	public List<Supplier> getWithMinScByCondition(Supplier supplier);
	
}
