package com.mjitech.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjitech.constant.ErrorCodeConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.lib.OrderLib;
import com.mjitech.lib.OrderSkuLib;
import com.mjitech.model.Order;
import com.mjitech.model.OrderSku;
import com.mjitech.service.OrderService;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.OrderUtils;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	
	private static Logger logger = LoggerFactory
			.getLogger(SupplierServiceImpl.class);
	
	
	@Autowired
	private OrderLib orderLib;
	@Autowired
	private OrderSkuLib orderSkuLib;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private ErrorCodeConstants errorCodeConstants;
	@Autowired
	private OrderUtils orderUtils;
	

	@Override
	@Transactional
	public JSONObject addOrder(Order order) {
		Order s = orderLib.add(order);

		JSONObject ret = new JSONObject();
		if(s!=null && s.getId()>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_ADDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject findOrderDeail(int id) {
		Order s = orderLib.getById(id);
		JSONObject ret = new JSONObject();
		if(s!=null){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			ret.put(JSONConstants.RETURN_KEY_RESULT, orderUtils.getOrderJSON(s));
		}else{
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_FINDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject deleteOrder(int id) {
		OrderSku sku = new OrderSku();
		sku.setOrderid(id);
		List<OrderSku> list = this.orderSkuLib.getByCondition(sku);
		if (list != null && list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				OrderSku orderSku = list.get(i);
				orderSkuLib.delete(orderSku.getId());
			}
		}
		int i = orderLib.delete(id);
		JSONObject ret = new JSONObject();
		if(i>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_DELETEFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject updateOrder(Order order) {
		int i = orderLib.update(order);
		JSONObject ret = new JSONObject();
		if (i > 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		} else {
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_UPDATEFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject addOrderSku(OrderSku orderSku) {

		JSONObject ret = new JSONObject();

		// 入库
		OrderSku s = this.orderSkuLib.add(orderSku);
		if (s != null && s.getId() > 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			JSONObject sjson = orderUtils.getOrderSkuJSON(s);
			ret.put("ordersku", sjson);
		} else {
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_ADDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject findOrderSkuDeail(int id) {
		OrderSku s = orderSkuLib.getById(id);
		JSONObject ret = new JSONObject();
		if(s!=null){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			ret.put(JSONConstants.RETURN_KEY_RESULT, orderUtils.getOrderSkuJSON(s));
		}else{
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_FINDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject deleteOrderSku(int id) {
		int i = orderSkuLib.delete(id);
		JSONObject ret = new JSONObject();
		if(i>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret,errorCodeConstants,
					ErrorCodeConstants.RET_CODE_DELETEFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject updateOrderSku(OrderSku orderSku) {
		int i = orderSkuLib.update(orderSku);
		JSONObject ret = new JSONObject();
		if(i>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_UPDATEFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject findOrderSkuListByOrderId(int id) {

		OrderSku s = new OrderSku();
		s.setOrderid(id);
		List<OrderSku> list = orderSkuLib.getByCondition(s);
		JSONObject ret = new JSONObject();
		//查询永远返回成功，只是JSON可能为{}
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		ret.put(JSONConstants.RETURN_KEY_RESULT, orderUtils.getOrderSkuListJSON(list));
		return ret;
	}

	@Override
	public List<Order> listOrder(Order order) {
		List<Order> list = orderLib.getOrderByCondition(order);
		return list;
	}

	@Override
	public List<OrderSku> findOrderSkuListOByOrderId(int id) {
		OrderSku s = new OrderSku();
		s.setOrderid(id);
		List<OrderSku> list = orderSkuLib.getByCondition(s);
		return list;
	}

}
