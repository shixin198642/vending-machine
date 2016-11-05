package com.mjitech.lib.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.dao.RequestDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.RequestLib;
import com.mjitech.model.Request;

@Component("requestLib")
public class RequestLibImpl implements RequestLib {
	@Autowired
	private RequestDao requestDao;
	@Autowired
	private RedisLib redisLib;
	
	
	
	@Override
	public Request getById(int id) {

		return null;
	}

	@Override
	public Request add(Request t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Request t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Request> getByWarehouse(int warehouseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
