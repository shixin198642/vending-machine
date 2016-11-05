package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.SkuBrand;

public interface SkuBrandDao extends BaseDao<SkuBrand> {

	public List<SkuBrand> getAll();

	public List<SkuBrand> getByCondition(SkuBrand condition);
}
