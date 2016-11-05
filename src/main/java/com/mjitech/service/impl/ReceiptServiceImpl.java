package com.mjitech.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjitech.dao.ReceiptDao;
import com.mjitech.model.Receipt;
import com.mjitech.model.ReceiptItem;
import com.mjitech.service.ReceiptService;

@Service("receiptService")
public class ReceiptServiceImpl implements ReceiptService{
	
	@Autowired
	private ReceiptDao receiptDao;
	
	@Override
	public List<Receipt> listReceiptByWarehouse(int warehouseId, String status) {
		return receiptDao.listReceiptByWarehouse(warehouseId, status);
	}

	@Override
	public List<Receipt> listReceiptItemByWarehouse(int warehouseId, String status) {
		return receiptDao.listReceiptItemByWarehouse(warehouseId, status);
	}
	
	@Override
	@Transactional
	public JSONObject addReceipt(Receipt receipt) {
		
		return null;
	}

	@Override
	public JSONObject getReceipt(int id) {
		return null;
	}

	@Override
	@Transactional	
	public JSONObject deleteReceipt(int id) {
		return null;
	}

	@Override
	@Transactional	
	public JSONObject updateReceipt(Receipt receipt) {
		return null;
	}

	@Override
	public JSONObject addReceiptItem(ReceiptItem receiptItem) {
		return null;
	}

	@Override
	public JSONObject getReceiptItem(int id) {
		return null;
	}

	@Override
	public JSONObject listReceipts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject checking(int id, String result, String comments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject receiving(int id, String result, String comments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject inspecting(int id, int item_id, int pass, int issue, int lost) {
		// TODO Auto-generated method stub
		return null;
	}




}
