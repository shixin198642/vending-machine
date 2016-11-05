package com.mjitech.model;

import java.util.List;

public class Picking extends BaseModel{

	private static final long serialVersionUID = 6447596033101493546L;
	
	private int machine_id;
	private int warehouse_id;
	private int employee_id;
	private Userinfo userinfo;
	private List<Request> requestList;
	private String status;
	
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
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Userinfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}
	public List<Request> getRequestList() {
		return requestList;
	}
	public void setRequestList(List<Request> requestList) {
		this.requestList = requestList;
	}
	

}
