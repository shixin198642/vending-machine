package com.mjitech.lib.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.dao.OrderSkuDao;
import com.mjitech.lib.OrderSkuLib;
import com.mjitech.model.OrderSku;

@Component("orderSkuLib")
public class OrderSkuLibImpl  implements OrderSkuLib {
	@Autowired
	private OrderSkuDao orderSkuDao;

	@Override
	public OrderSku getById(int id) {
		return this.orderSkuDao.getById(id);
	}

	@Override
	public OrderSku add(OrderSku orderSku) {
		if (orderSku.getCreateDatetime() == null) {
			orderSku.setCreateDatetime(new Date());
		}
		if (orderSku.getUpdateDatetime() == null) {
			orderSku.setUpdateDatetime(orderSku.getCreateDatetime());
		}
		int i = orderSkuDao.add(orderSku);
		
		if (orderSku!=null && orderSku.getId() > 0) {
			return orderSku;
		} else {
			return null;
		}
	}

	@Override
	public int update(OrderSku orderSku) {
		if (orderSku.getUpdateDatetime() == null) {
			orderSku.setUpdateDatetime(new Date());
		}
		int i = this.orderSkuDao.update(orderSku);
		if(i>0){
			return i;
		}	
		return 0;
	}

	@Override
	public int delete(int id) {
		int i = this.orderSkuDao.delete(id);
		if(i>0){
			return i;
		}
		return 0;
	}

	@Override
	public List<OrderSku> getByCondition(OrderSku orderSku) {
		List<OrderSku> list = this.orderSkuDao.getByCondition(orderSku);
		
		if(list.size()>0){
			return list;
		}
		return null;
	}
}
