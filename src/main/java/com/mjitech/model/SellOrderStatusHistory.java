package com.mjitech.model;

import java.util.Date;

public class SellOrderStatusHistory extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4817185351511318402L;

	private int sellOrderId;
	private int userid;
	private int status;
	private Date statusDatetime;

	public int getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(int sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getStatusDatetime() {
		return statusDatetime;
	}

	public void setStatusDatetime(Date statusDatetime) {
		this.statusDatetime = statusDatetime;
	}

}
