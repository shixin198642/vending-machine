package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import com.mjitech.model.Warehouse;

public class WarehouseConstants {

	public final static String WAREHOUSE_TYPE_MACHINE = "machine";
	public final static String WAREHOUSE_TYPE_STORE = "store";
	public final static String WAREHOUSE_TYPE_WAREHOUSE = "warehouse";

	public final static Map<String, String> typeNames = new HashMap<String, String>();
	static {
		typeNames.put(WAREHOUSE_TYPE_MACHINE, "机器");
		typeNames.put(WAREHOUSE_TYPE_STORE, "门店");
		typeNames.put(WAREHOUSE_TYPE_WAREHOUSE, "仓库");
	}

	public static boolean isStoreOrMachine(Warehouse warehouse) {
		if (warehouse != null) {
			if (WAREHOUSE_TYPE_MACHINE.equals(warehouse.getType())
					|| WAREHOUSE_TYPE_STORE.equals(warehouse.getType())) {
				return true;
			}
		}
		return false;
	}

	public static String getTypeName(String type) {
		if (typeNames.containsKey(type)) {
			return typeNames.get(type);
		}
		return "";
	}

	/**
	 * 店长
	 */
	public final static int WAREHOUSE_MANAGER_TYPE_MANAGER = 1;
	/**
	 * 机器负责人
	 */
	public final static int WAREHOUSE_MANAGER_TYPE_MACHINE_MANAGER = 2;
	/**
	 * 店员
	 */
	public final static int WAREHOUSE_MANAGER_TYPE_EMPLOYEE = 3;

	public final static Map<Integer, String> managerNames = new HashMap<Integer, String>();
	static {
		managerNames.put(WAREHOUSE_MANAGER_TYPE_MANAGER, "店长");
		managerNames.put(WAREHOUSE_MANAGER_TYPE_MACHINE_MANAGER, "机器负责人");
		managerNames.put(WAREHOUSE_MANAGER_TYPE_EMPLOYEE, "店员");
	}

	public static String getManagerTypeName(int type) {
		if (managerNames.containsKey(type)) {
			return managerNames.get(type);
		}
		return "";
	}

	/**
	 * 仓库状态：正常运营
	 */
	public final static int WAREHOUSE_STATUS_ONLINE = 1;
	/**
	 * 仓库状态：停用
	 */
	public final static int WAREHOUSE_STATUS_OFFLINE = 2;
	public final static Map<Integer, String> statusNames = new HashMap<Integer, String>();
	static {
		statusNames.put(WAREHOUSE_STATUS_ONLINE, "正常运营");
		statusNames.put(WAREHOUSE_STATUS_OFFLINE, "停用");
	}

	public static String getStatusName(int status) {
		if (statusNames.containsKey(status)) {
			return statusNames.get(status);
		}
		return "";
	}

}
