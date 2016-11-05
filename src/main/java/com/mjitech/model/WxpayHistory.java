package com.mjitech.model;

public class WxpayHistory extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3904855931382494099L;

	private int sellOrderId;
	private String sellOrderNumber;
	private String wxpayReturn;

	public int getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(int sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public String getSellOrderNumber() {
		return sellOrderNumber;
	}

	public void setSellOrderNumber(String sellOrderNumber) {
		this.sellOrderNumber = sellOrderNumber;
	}

	public String getWxpayReturn() {
		return wxpayReturn;
	}

	public void setWxpayReturn(String wxpayReturn) {
		this.wxpayReturn = wxpayReturn;
	}

}
