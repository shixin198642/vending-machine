package com.mjitech.model;

public class Request extends BaseModel{
	
	private static final long serialVersionUID = -1048998634367637521L;
	
	private int id;
	private int machine_id;
	private int warehouse_id;
	private int sorting_id;
	private int sku_id;
	private int sku_spec_id;
	private int quantity;
	private int quantityInventory;
	private String machineName;
	private String warehouseName;
	private Sku sku;

	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getMachine_id() {
		return machine_id;
	}
	public void setMachine_id(int machine_id) {
		this.machine_id = machine_id;
	}
	public int getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public int getSorting_id() {
		return sorting_id;
	}
	public void setSorting_id(int sorting_id) {
		this.sorting_id = sorting_id;
	}
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public int getSku_spec_id() {
		return sku_spec_id;
	}
	public void setSku_spec_id(int sku_spec_id) {
		this.sku_spec_id = sku_spec_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getQuantityInventory() {
		return quantityInventory;
	}
	public void setQuantityInventory(int quantityInventory) {
		this.quantityInventory = quantityInventory;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	
	
}
