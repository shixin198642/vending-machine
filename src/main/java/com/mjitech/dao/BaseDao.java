package com.mjitech.dao;

import com.mjitech.model.BaseModel;

public interface BaseDao<T extends BaseModel> {
	public T getById(int id);

	public int add(T t);

	public int update(T t);
	
	public int delete(int id);
}
