package com.mjitech.model;

import java.util.List;

public class SkuType extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5970750163533204314L;

	private int parentId;
	private String name;
	private String code;
	private int sortNumber;
	
	private List<SkuType> subTypes;
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	public List<SkuType> getSubTypes() {
		return subTypes;
	}

	public void setSubTypes(List<SkuType> subTypes) {
		this.subTypes = subTypes;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	

}
