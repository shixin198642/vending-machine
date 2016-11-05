package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Order;

public interface OrderLib extends BaseModelLib<Order>{
	//列表页面查询订单，未分页
	public List<Order> getOrderByCondition(Order order);
}
