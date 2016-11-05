package com.mjitech.constant;

import com.mjitech.model.Menu;

public class MenuConstants {
	/**
	 * 商品列表
	 */
	public final static int SKU_LIST_MENU_ID = 7;
	/**
	 * 添加商品
	 */
	public final static int ADD_SKU_MENU_ID = 8;
	
	public final static int WAREHOUSE_INVENTORY_MENU_ID = 9;
	public final static int RECEIPT_MENU_ID=10;
	public static final int STOCKIN_REQUEST_MENU_ID = 11;
	public static final int STOCKIN_MENU_ID = 12;
	public static final int WAREHOUSE_REQUEST_MENU_ID = 13;
	public static final int PICKING_MENU_ID = 14;
	
	public static final int MACHINE_DETAIL_MENU_ID = 15;
	public static final int MACHINE_REQUEST_MENU_ID = 16;
	public static final int MACHINE_INVENTORY_MENU_ID = 17;
	public static final int SELL_HISTORY_MENU_ID = 18;
	public static final int DELIVERY_MENU_ID = 19;

	
	public final static String MENU_PARAM_NAME = "menu_id";


	public final static String buildMenuUrl(String context, Menu menu) {
		StringBuilder sb = new StringBuilder(context)
				.append(WebPageConstants.COMMON_PREFIX).append(menu.getUrl())
				.append("?").append(MenuConstants.MENU_PARAM_NAME).append("=")
				.append(menu.getId());
		return sb.toString();
	}

}
