package com.mjitech.model;

public class SkuSpec extends BaseModel{

	private static final long serialVersionUID = 4315676132156465498L;
	
	private int amount;
	private String unit;
	private int type;
	private int skuid;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSkuid() {
		return skuid;
	}
	public void setSkuid(int skuid) {
		this.skuid = skuid;
	}
}
