package com.mjitech.model;

public class Points extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6344857905215450814L;
	
	private int userid;
	private int type;
	private int total;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	

}
