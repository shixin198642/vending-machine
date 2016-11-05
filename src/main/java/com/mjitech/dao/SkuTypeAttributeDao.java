package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.SkuSpec;
import com.mjitech.model.SkuTypeAttribute;

public interface SkuTypeAttributeDao extends BaseDao<SkuTypeAttribute>{
	
	public List<SkuTypeAttribute> getByTypeId(int typeId);
}
