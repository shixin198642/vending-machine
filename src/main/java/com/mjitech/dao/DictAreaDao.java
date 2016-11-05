package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.DictArea;

public interface DictAreaDao extends BaseDao<DictArea> {

	public List<DictArea> getByParent(int parentId);

	public List<DictArea> getByType(int type);

	public List<DictArea> getByCondition(DictArea condition);

}
