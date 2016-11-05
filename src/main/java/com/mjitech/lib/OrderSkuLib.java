package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.OrderSku;

public interface OrderSkuLib extends BaseModelLib<OrderSku>{
	public List<OrderSku> getByCondition(OrderSku orderSku);
}
