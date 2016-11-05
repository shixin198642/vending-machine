package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.ReceiptItem;

public interface ReceiptItemDao extends BaseDao<ReceiptItem> {
	
	public List<ReceiptItem> getByReceiptId(int receiptId);

}
