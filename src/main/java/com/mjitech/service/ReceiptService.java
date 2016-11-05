package com.mjitech.service;

import java.util.List;

import org.json.JSONObject;

import com.mjitech.model.Receipt;
import com.mjitech.model.ReceiptItem;

public interface ReceiptService {
	
	public JSONObject addReceipt(Receipt receipt);
	public JSONObject getReceipt(int id);
	public JSONObject deleteReceipt(int id);
	public JSONObject updateReceipt(Receipt receipt);
	public JSONObject listReceipts();
	public List<Receipt> listReceiptByWarehouse(int warehouse_id, String status);
	public List<Receipt> listReceiptItemByWarehouse(int id, String status);
	
	public JSONObject addReceiptItem(ReceiptItem receiptItem);
	public JSONObject getReceiptItem(int id);
	
	public JSONObject checking(int id, String result, String comments);
	public JSONObject receiving(int id, String result, String comments);	
	public JSONObject inspecting(int id, int item_id, int pass, int issue, int lost);
	
}
