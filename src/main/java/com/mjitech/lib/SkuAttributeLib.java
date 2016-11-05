package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.SkuAttribute;

public interface SkuAttributeLib extends BaseModelLib<SkuAttribute>{
	public List<SkuAttribute> getBySku(int skuId);
	
}
