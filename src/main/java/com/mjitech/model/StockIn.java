package com.mjitech.model;

public class StockIn extends BaseModel{

	private static final long serialVersionUID = -4055814098155589481L;
	
	private Userinfo userinfo;
	private String type;
	private int receipt_id;
	
	
	public Userinfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getReceipt_id() {
		return receipt_id;
	}
	public void setReceipt_id(int receipt_id) {
		this.receipt_id = receipt_id;
	}
	
	

}
