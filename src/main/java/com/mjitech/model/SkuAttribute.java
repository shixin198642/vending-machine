package com.mjitech.model;

public class SkuAttribute extends BaseModel {

	private static final long serialVersionUID = 4315676132156465498L;
	private int skuId;
	private int skuTypeId;
	private int skuTypeAttributeId;
	private String value;
	private String remarks;

	private SkuTypeAttribute skuTypeAttribute;

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public int getSkuTypeAttributeId() {
		return skuTypeAttributeId;
	}

	public void setSkuTypeAttributeId(int skuTypeAttributeId) {
		this.skuTypeAttributeId = skuTypeAttributeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setSkuTypeAttribute(SkuTypeAttribute skuTypeAttribute) {
		this.skuTypeAttribute = skuTypeAttribute;
	}

	public SkuTypeAttribute getSkuTypeAttribute() {
		return skuTypeAttribute;
	}

	public void setSkuTypeId(int skuTypeId) {
		this.skuTypeId = skuTypeId;
	}

	public int getSkuTypeId() {
		return skuTypeId;
	}

}
