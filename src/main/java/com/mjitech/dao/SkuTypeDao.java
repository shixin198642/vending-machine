package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.SkuType;

public interface SkuTypeDao extends BaseDao<SkuType> {

	public List<SkuType> getByParentId(int parentId);
	
	public List<SkuType> getByCondition(SkuType condition);

}
