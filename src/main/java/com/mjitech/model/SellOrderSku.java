package com.mjitech.model;

public class SellOrderSku extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5046927347861272423L;

	private int sellOrderId;
	private int skuId;
	private int count;
	private double sellPrice;
	private int inventoryId;
	private Sku sku;

	public int getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(int sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public Sku getSku() {
		return sku;
	}

}
