package com.mjitech.dao;

import java.util.List;
import com.mjitech.model.OrderSku;

public interface OrderSkuDao extends BaseDao<OrderSku>{
	
	public List<OrderSku> getByCondition(OrderSku condition);

}
