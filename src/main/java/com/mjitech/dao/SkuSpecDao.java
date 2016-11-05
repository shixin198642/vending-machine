package com.mjitech.dao;

import java.util.List;
import com.mjitech.model.SkuSpec;

public interface SkuSpecDao extends BaseDao<SkuSpec>{
	
	public List<SkuSpec> getByCondition(SkuSpec condition);
}
