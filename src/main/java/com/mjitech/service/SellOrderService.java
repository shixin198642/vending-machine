package com.mjitech.service;

import com.mjitech.model.SellOrder;

public interface SellOrderService {
	
	public final static int CANCEL_RETURNCODE_SUCC = 1;
	public final static int CANCEL_RETURNCODE_ORDERNOTFOUND = -1;
	public final static int CANCEL_RETURNCODE_NOAUTH = -2;
	public final static int CANCEL_RETURNCODE_WRONGSTATUS = -3;
	
	public SellOrder addOrder(SellOrder order, int userId);

	public int cancelOrder(int orderId, int userId);

	void batchCancelOrders();

}
