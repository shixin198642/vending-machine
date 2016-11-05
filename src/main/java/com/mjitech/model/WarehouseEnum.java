package com.mjitech.model;

public enum WarehouseEnum {
	
	WAREHOUSE("warehouse"),
	MACHINE("machine"),
	STORE("store");
	
	private String type;
	private WarehouseEnum(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
