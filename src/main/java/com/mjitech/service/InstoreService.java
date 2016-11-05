package com.mjitech.service;

import java.util.List;

import com.mjitech.model.Instore;

public interface InstoreService {
	public List<Instore> listInstore(int warehouse);

	public void addManualInstore(int userid, int warehouseId, int skuId,
			int quantity, double price, String description);

	public void addCancelInstore(int userid, int sellOrderId);
}
