package com.mjitech.model;

public class Supplier extends BaseModel{

	private static final long serialVersionUID = -7138611157654753924L;
	
	private String sname;
	private String address;
	private String remarks;
	private String account;
	private String bank;
	private String shipaddress;
	private int mid;//主要联系人ID
	private String payname; //乙方户名
	
	//主要联系人信息--查询用
	private String mcname;
	private String mtel;
	private String mfax;
	private String memail;
	
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getShipaddress() {
		return shipaddress;
	}
	public void setShipaddress(String shipaddress) {
		this.shipaddress = shipaddress;
	}
	public String getPayname() {
		return payname;
	}
	public void setPayname(String payname) {
		this.payname = payname;
	}
	public String getMcname() {
		return mcname;
	}
	public void setMcname(String mcname) {
		this.mcname = mcname;
	}
	public String getMtel() {
		return mtel;
	}
	public void setMtel(String mtel) {
		this.mtel = mtel;
	}
	public String getMfax() {
		return mfax;
	}
	public void setMfax(String mfax) {
		this.mfax = mfax;
	}
	public String getMemail() {
		return memail;
	}
	public void setMemail(String memail) {
		this.memail = memail;
	}

	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
}
