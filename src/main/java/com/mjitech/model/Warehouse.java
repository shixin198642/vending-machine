package com.mjitech.model;

import java.util.List;

public class Warehouse extends BaseModel{

	private static final long serialVersionUID = 7201739638122102324L;

	private String name;
	private String type;
	private int status;
	private int warehouse_parent;
	private int province;
	private int city;
	private int region;
	private String address;
	private int manager;
	private String manager_name;
	private String manager_mobile;
	private String deliver_name;
	private String deliver_mobile;
	private float latitude;
	private float longitude;
	private String remarks;
	
	private List<WarehouseManager> managers;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	private Warehouse warehouseParent;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getProvince() {
		return province;
	}
	public void setProvince(int province) {
		this.province = province;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	public String getManager_mobile() {
		return manager_mobile;
	}
	public void setManager_mobile(String manager_mobile) {
		this.manager_mobile = manager_mobile;
	}
	public String getDeliver_name() {
		return deliver_name;
	}
	public void setDeliver_name(String deliver_name) {
		this.deliver_name = deliver_name;
	}
	public String getDeliver_mobile() {
		return deliver_mobile;
	}
	public void setDeliver_mobile(String deliver_mobile) {
		this.deliver_mobile = deliver_mobile;
	}	
	public int getWarehouse_parent() {
		return warehouse_parent;
	}
	public void setWarehouse_parent(int warehouse_parent) {
		this.warehouse_parent = warehouse_parent;
	}
	public Warehouse getWarehouseParent() {
		return warehouseParent;
	}
	public void setWarehouseParent(Warehouse warehouseParent) {
		this.warehouseParent = warehouseParent;
	}
	public void setManager(int manager) {
		this.manager = manager;
	}
	public int getManager() {
		return manager;
	}
	public void setManagers(List<WarehouseManager> managers) {
		this.managers = managers;
	}
	public List<WarehouseManager> getManagers() {
		return managers;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}

}
