package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.SkuAttribute;

public interface SkuAttributeDao extends BaseDao<SkuAttribute> {

	public List<SkuAttribute> getBySkuId(int skuId);
}
