package com.mjitech.dao;

import java.util.Date;
import java.util.List;

import com.mjitech.model.SellOrder;

public interface SellOrderDao extends BaseDao<SellOrder> {
	
	public List<SellOrder> getByCondition(SellOrder condition);
	
	public List<SellOrder> getAllUnpayOrders(Date beforeDate);

}
