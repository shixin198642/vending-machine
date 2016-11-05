package com.mjitech.lib.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.dao.OrderDao;
import com.mjitech.lib.OrderLib;
import com.mjitech.model.Order;

@Component("orderLib")
public class OrderLibImpl implements OrderLib{
	
	@Autowired
	private OrderDao orderDao;

	//订单暂不使用缓存
	
	@Override
	public Order getById(int id) {
		return this.orderDao.getById(id);

	}

	@Override
	public Order add(Order order) {
		if (order.getCreateDatetime() == null) {
			order.setCreateDatetime(new Date());
		}
		if (order.getUpdateDatetime() == null) {
			order.setUpdateDatetime(order.getCreateDatetime());
		}
		this.orderDao.add(order);
		if (order!=null && order.getId() > 0) {
			return order;
		} else {
			return null;
		}
	}

	@Override
	public int update(Order order) {
		if (order.getUpdateDatetime() == null) {
			order.setUpdateDatetime(new Date());
		}
		int i = this.orderDao.update(order);
		if(i>0){
			return i;
		}	
		return 0;
	}

	@Override
	public int delete(int id) {
		int i = this.orderDao.delete(id);
		if(i>0){
			return i;
		}
		return 0;
	}

	@Override
	public List<Order> getOrderByCondition(Order order) {
		return this.orderDao.getOrderByCondition(order);
	}

}
