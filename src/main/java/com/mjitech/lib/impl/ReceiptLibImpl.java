package com.mjitech.lib.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mjitech.lib.ReceiptLib;
import com.mjitech.model.Receipt;

@Component("receiptLib")
public class ReceiptLibImpl implements ReceiptLib{

	private static Logger logger = LoggerFactory.getLogger(ReceiptLibImpl.class);

	@Override
	public Receipt getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Receipt add(Receipt t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Receipt t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}


}
