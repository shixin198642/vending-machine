package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.Order;

public interface OrderDao extends BaseDao<Order>{
	public List<Order> getOrderByCondition(Order order);
}
