package com.mjitech.model;

public class SkuTypeAttribute extends BaseModel {

	private static final long serialVersionUID = 4315676132156465498L;

	private int skuTypeId;
	private String name;
	private String unit;
	private String remarks;
	private int type;
	private String options;
	public int getSkuTypeId() {
		return skuTypeId;
	}

	public void setSkuTypeId(int skuTypeId) {
		this.skuTypeId = skuTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getOptions() {
		return options;
	}

}
