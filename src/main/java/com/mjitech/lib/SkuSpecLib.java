package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.SkuSpec;

public interface SkuSpecLib extends BaseModelLib<SkuSpec>{
	public List<SkuSpec> getByCondition(SkuSpec skuSpec);
	
	public List<SkuSpec> getSkuSpecs(int skuId);
}
