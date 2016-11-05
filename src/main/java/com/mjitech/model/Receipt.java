package com.mjitech.model;

import java.util.List;

public class Receipt extends BaseModel{

	private static final long serialVersionUID = 7459914821439913414L;
	private int warehouse_id;
	private int employee_id;
	private int order_id;
	private String supplier_name; //
	private int tracking_id;
	private String remark;
	private String status;
	private List<ReceiptItem> receiptItems;
	
	public int getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public int getTracking_id() {
		return tracking_id;
	}
	public void setTracking_id(int tracking_id) {
		this.tracking_id = tracking_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ReceiptItem> getReceiptItems() {
		return receiptItems;
	}
	public void setReceiptItems(List<ReceiptItem> receiptItems) {
		this.receiptItems = receiptItems;
	}
	
}
