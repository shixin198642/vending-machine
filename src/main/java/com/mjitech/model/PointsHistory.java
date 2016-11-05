package com.mjitech.model;

import java.util.Date;

public class PointsHistory extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2476441659057373667L;

	private int userid;
	private int type;
	private int count;
	private Date pointsDatetime;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setPointsDatetime(Date pointsDatetime) {
		this.pointsDatetime = pointsDatetime;
	}

	public Date getPointsDatetime() {
		return pointsDatetime;
	}

}
