package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.Userinfo;

public interface UserinfoDao extends BaseDao<Userinfo> {
	public Userinfo getByUsername(String username);

	public List<Userinfo> getByCondition(Userinfo condition);
}
