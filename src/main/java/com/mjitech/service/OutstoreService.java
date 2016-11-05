package com.mjitech.service;

import java.util.List;

import com.mjitech.model.Outstore;

public interface OutstoreService {
	public List<Outstore> listOutStore(int warehouse);

	public void addManualOutstore(int userid, int warehouseId, int skuId,
			int quantity, String description);

	public void addSellOrderOutstore(int sellOrderId);
}
