package com.mjitech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.mjitech.model.Sku;

public interface SkuDao extends BaseDao<Sku> {
	
	public Sku getByBarcode(String barcode);
	
	public List<Sku> getByCondition(Sku condition);
	
	public int getTotalByCondition(Sku condition);
	
	public int getCountByParentType(int parentType);
	
	public List<Sku> getSkus(Sku condition);
	
	@Select("select count(id) from mt_sku where status!=1")
	public int getTotal(Sku condition);

}
