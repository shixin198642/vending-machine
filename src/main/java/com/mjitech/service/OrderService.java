package com.mjitech.service;

import java.util.List;

import org.json.JSONObject;

import com.mjitech.model.Order;
import com.mjitech.model.OrderSku;

public interface OrderService {
	//Order相关操作
	
	public JSONObject addOrder(Order order);
	public JSONObject findOrderDeail(int id);
	public JSONObject deleteOrder(int id);
	public JSONObject updateOrder(Order order);
	/**
	 * 查询订单列表集合
	 * @param order
	 * @return
	 */
	public List<Order> listOrder(Order order);
	
	//OrderSku相关操作
	
	public JSONObject addOrderSku(OrderSku orderSku);
	public JSONObject findOrderSkuDeail(int id);
	public JSONObject deleteOrderSku(int id);
	public JSONObject updateOrderSku(OrderSku orderSku);
	
	//读取一个Order的所有OrderSku
	public JSONObject findOrderSkuListByOrderId(int id);
	public List<OrderSku> findOrderSkuListOByOrderId(int id);

}
