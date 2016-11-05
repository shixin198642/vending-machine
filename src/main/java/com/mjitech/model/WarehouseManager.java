package com.mjitech.model;

public class WarehouseManager extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -119537143127192878L;

	private int warehouseId;
	private int managerId;

	private int type;

	private Userinfo manager;
	private Warehouse warehouse;

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public Userinfo getManager() {
		return manager;
	}

	public void setManager(Userinfo manager) {
		this.manager = manager;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

}
