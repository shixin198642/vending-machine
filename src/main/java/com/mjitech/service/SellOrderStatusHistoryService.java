package com.mjitech.service;

public interface SellOrderStatusHistoryService {

	public void addHistory(int sellOrderId, int userid, int status);

}
