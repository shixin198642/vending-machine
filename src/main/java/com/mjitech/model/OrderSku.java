package com.mjitech.model;

public class OrderSku extends BaseModel {

	private static final long serialVersionUID = 3179851374357984L;

	private int skuid;
	private String skunumber;
	private String skuname;
	private int skuspecid;
	private String specname;
	private String unit;
	private int orderamt;
	private float specprice;
	private float amount;
	private String remarks;
	private int orderid;

	public int getSkuid() {
		return skuid;
	}

	public void setSkuid(int skuid) {
		this.skuid = skuid;
	}

	public String getSkunumber() {
		return skunumber;
	}

	public void setSkunumber(String skunumber) {
		this.skunumber = skunumber;
	}

	public String getSkuname() {
		return skuname;
	}

	public void setSkuname(String skuname) {
		this.skuname = skuname;
	}

	public int getSkuspecid() {
		return skuspecid;
	}

	public void setSkuspecid(int skuspecid) {
		this.skuspecid = skuspecid;
	}

	public String getSpecname() {
		return specname;
	}

	public void setSpecname(String specname) {
		this.specname = specname;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getOrderamt() {
		return orderamt;
	}

	public void setOrderamt(int orderamt) {
		this.orderamt = orderamt;
	}

	public float getSpecprice() {
		return specprice;
	}

	public void setSpecprice(float specprice) {
		this.specprice = specprice;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

}
