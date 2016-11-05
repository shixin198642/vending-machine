package com.mjitech.model;

public class Instore extends BaseModel {

	private static final long serialVersionUID = 5112196584696771429L;

	private int type;
	private int warehouseId;
	private int userid;
	private int skuId;
	private int quantity;
	private int receiptId;
	private int fromWarehouseId;
	private String description;
	private double price;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public int getFromWarehouseId() {
		return fromWarehouseId;
	}

	public void setFromWarehouseId(int fromWarehouseId) {
		this.fromWarehouseId = fromWarehouseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}
