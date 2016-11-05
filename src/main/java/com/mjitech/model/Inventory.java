package com.mjitech.model;

import java.io.Serializable;

public class Inventory extends BaseModel implements Serializable {

	private static final long serialVersionUID = -3764396257357557839L;
	private int id;
	private int warehouse_id;
	private int quantity;
	private int max_stock;
	private int min_stock;
	private int safe_stock;
	private double sellprice;
	private String position;
	private String status;
	private Sku sku;
	private int skuId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
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

	public int getMax_stock() {
		return max_stock;
	}

	public void setMax_stock(int max_stock) {
		this.max_stock = max_stock;
	}

	public int getMin_stock() {
		return min_stock;
	}

	public void setMin_stock(int min_stock) {
		this.min_stock = min_stock;
	}

	public int getSafe_stock() {
		return safe_stock;
	}

	public void setSafe_stock(int safe_stock) {
		this.safe_stock = safe_stock;
	}

	public double getSellprice() {
		return sellprice;
	}

	public void setSellprice(double sellprice) {
		this.sellprice = sellprice;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public int getSkuId() {
		return skuId;
	}

}
