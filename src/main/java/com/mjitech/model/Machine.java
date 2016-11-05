package com.mjitech.model;

import java.util.List;

public class Machine {

	private Warehouse warehouse;
	private List<Request> requestList;

	public List<Request> getRequestList() {
		return requestList;
	}	
	public void setRequestList(List<Request> requestList) {
		this.requestList = requestList;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	
}
