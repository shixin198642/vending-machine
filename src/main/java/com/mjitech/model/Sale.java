package com.mjitech.model;

import java.util.List;

public class Sale extends BaseModel{
	
	private static final long serialVersionUID = 2234540108136561461L;
	
	private int machine_id;
	private String username;
	private float total_amount;
	private float discounted_amount;
	private float actual_amount;
	private int quantity;
	private String status;
	private List<SaleItem> saleItemList;
	
	public int getMachine_id() {
		return machine_id;
	}
	public void setMachine_id(int machine_id) {
		this.machine_id = machine_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public float getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(float total_amount) {
		this.total_amount = total_amount;
	}
	public float getDiscounted_amount() {
		return discounted_amount;
	}
	public void setDiscounted_amount(float discounted_amount) {
		this.discounted_amount = discounted_amount;
	}
	public float getActual_amount() {
		return actual_amount;
	}
	public void setActual_amount(float actual_amount) {
		this.actual_amount = actual_amount;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<SaleItem> getSaleItemList() {
		return saleItemList;
	}
	public void setSaleItemList(List<SaleItem> saleItemList) {
		this.saleItemList = saleItemList;
	}
	
	
}
