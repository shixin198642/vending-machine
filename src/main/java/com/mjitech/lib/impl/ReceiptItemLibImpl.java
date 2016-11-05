package com.mjitech.lib.impl;

import org.springframework.stereotype.Component;

import com.mjitech.lib.ReceiptItemLib;
import com.mjitech.model.ReceiptItem;

@Component("receiptItemLib")
public class ReceiptItemLibImpl implements ReceiptItemLib{

	@Override
	public ReceiptItem getById(int id) {
		return null;
	}

	@Override
	public ReceiptItem add(ReceiptItem t) {
		return null;
	}

	@Override
	public int update(ReceiptItem t) {
		return 0;
	}

	@Override
	public int delete(int id) {
		return 0;
	}


}
