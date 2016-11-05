package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.SkuTypeAttribute;

public interface SkuTypeAttributeLib extends BaseModelLib<SkuTypeAttribute>{
	public List<SkuTypeAttribute> getByType(int typeId);
	
}
