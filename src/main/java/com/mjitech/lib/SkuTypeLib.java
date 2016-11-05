package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.SkuType;

public interface SkuTypeLib extends BaseModelLib<SkuType> {
	List<SkuType> getByParentId(int parentId);

	List<SkuType> getByName(String name);
}
