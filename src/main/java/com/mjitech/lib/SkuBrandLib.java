package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.SkuBrand;

public interface SkuBrandLib extends BaseModelLib<SkuBrand> {
	public List<SkuBrand> getAll();
	public List<SkuBrand> getByName(String name);
}
