package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.SellOrder;

public interface SellOrderLib  extends BaseModelLib<SellOrder>{
	
	public SellOrder getByOrderNumber(String orderNumber);
	
	public List<SellOrder> getBySeller(int sellerId);
	
	public List<SellOrder> getToCancelList();

	List<SellOrder> getByWarehouse(int warehouseId);
	
	List<SellOrder> getByCondition(SellOrder condition);

	SellOrder getByTakeGoodsNumber(String number);

	List<SellOrder> getByBuyer(int buyerId);

	List<SellOrder> getByParent(int parentId);
}
