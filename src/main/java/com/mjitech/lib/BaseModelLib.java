package com.mjitech.lib;

import com.mjitech.model.BaseModel;

public interface BaseModelLib<T extends BaseModel> {
	
	public T getById(int id);
	
	public T add(T t);

	public int update(T t);

	public int delete(int id);

}
