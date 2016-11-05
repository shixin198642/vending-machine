package com.mjitech.model;

public class ReceiptItem extends BaseModel{

	private static final long serialVersionUID = 5183590028567100981L;
	
	private int receipt_id;
	private Sku sku;
	private int quantity;
	private int skuSpecId;
	
	
	public int getReceipt_id() {
		return receipt_id;
	}
	public void setReceipt_id(int receipt_id) {
		this.receipt_id = receipt_id;
	}
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSkuSpecId() {
		return skuSpecId;
	}
	public void setSkuSpecId(int skuSpecId) {
		this.skuSpecId = skuSpecId;
	}

}
