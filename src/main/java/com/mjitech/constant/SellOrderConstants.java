package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

public class SellOrderConstants {
	
	public final static int IS_PARENT_YES = 1;
	public final static int IS_PARENT_NO = 2;
	
	/**
	 * 默认取货码长度
	 */
	public final static int TAKE_GOODS_NUMBER_LENGTH = 10;
	
	public final static int SELLORDER_STATUS_NEW = 1;
	public final static int SELLORDER_STATUS_PAYED = 2;
	/**
	 * 未取货
	 */
	public final static int SELLORDER_STATUS_UNTAKEN = 3;
	/**
	 * 已取货
	 */
	public final static int SELLORDER_STATUS_TAKEN = 4;
	public final static int SELLORDER_STATUS_CANCEL = 90;
	public final static int SELLORDER_STATUS_CANCELED = 91;

	public final static int SELLORDER_PAYSTATUS_UNPAY = 1;
	public final static int SELLORDER_PAYSTATUS_PAYED = 2;

	private final static Map<Integer, String> statusNames = new HashMap<Integer, String>();
	static {
		statusNames.put(SELLORDER_STATUS_NEW, "已下单");
		statusNames.put(SELLORDER_STATUS_PAYED, "已付款");
		statusNames.put(SELLORDER_STATUS_UNTAKEN, "付款未取货");
		statusNames.put(SELLORDER_STATUS_TAKEN, "已取货");
		statusNames.put(SELLORDER_STATUS_CANCEL, "待取消");
		statusNames.put(SELLORDER_STATUS_CANCELED, "已取消");
	}
	
	public static Map<Integer, String> getStatusNames(){
		return statusNames;
	}

	private final static Map<Integer, String> paystatusNames = new HashMap<Integer, String>();
	static {
		paystatusNames.put(SELLORDER_PAYSTATUS_UNPAY, "未付款");
		paystatusNames.put(SELLORDER_PAYSTATUS_PAYED, "已付款");
	}

	public static String getStatusName(int status) {
		if (statusNames.containsKey(status)) {
			return statusNames.get(status);
		}
		return "";
	}

	public static String getPayStatusName(int status) {
		if (paystatusNames.containsKey(status)) {
			return paystatusNames.get(status);
		}
		return "";
	}

}
