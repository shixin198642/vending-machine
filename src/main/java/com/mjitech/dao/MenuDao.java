package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.Menu;

public interface MenuDao extends BaseDao<Menu> {

	public List<Menu> getByParent(int parentId);
	
	public List<Menu> getAll();
}
