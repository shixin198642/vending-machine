package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.SellOrderSku;

public interface SellOrderSkuDao extends BaseDao<SellOrderSku> {
	
	public List<SellOrderSku> getByCondition(SellOrderSku condition);

}
