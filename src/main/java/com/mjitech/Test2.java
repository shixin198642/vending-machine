package com.mjitech;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.constant.SellOrderConstants;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;
import com.mjitech.model.Sku;
import com.mjitech.model.Warehouse;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String tmp = "add me to:";
		// System.out.println(tmp.substring("add me to:".length()));
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		// UserinfoLib ul = (UserinfoLib) ctx.getBean("userinfoLib");
		// System.out.println(ul.getByOpenId("o41Mgv5qXayH9P9C6IGYhN1Ujz3g"));
		// WarehouseManagerLib wml =
		// (WarehouseManagerLib)ctx.getBean("warehouseManagerLib");
		// WarehouseLib wl = (WarehouseLib)ctx.getBean("warehouseLib");
		// WarehouseManager wm = new WarehouseManager();
		// wm.setManagerId(5);
		// wm.setWarehouseId(7);
		// wml.add(wm);
		// System.out.println(wl.getByManager(3));
		// SellOrderLib sellOrderLib =
		// (SellOrderLib)ctx.getBean("sellOrderLib");
		// // System.out.println(sellOrderLib.getToCancelList());
		// SellOrderService sos =
		// (SellOrderService)ctx.getBean("sellOrderService");
		// System.out.println(sos.cancelOrder(39, 1));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SellOrderLib ol = (SellOrderLib) ctx.getBean("sellOrderLib");
		SkuLib sl = (SkuLib) ctx.getBean("skuLib");
		WarehouseLib wl = (WarehouseLib) ctx.getBean("warehouseLib");
		SellOrderSkuLib sosl = (SellOrderSkuLib) ctx.getBean("sellOrderSkuLib");
		InventoryLib il = (InventoryLib) ctx.getBean("inventoryLib");
		SellOrder condition = new SellOrder();
		condition.setPayStatus(SellOrderConstants.SELLORDER_PAYSTATUS_PAYED);
		List<SellOrder> orders = ol.getByCondition(condition);
		for (SellOrder order : orders) {
			Warehouse store = wl.getById(order.getWarehouseId());
			List<SellOrderSku> sos = sosl.getBySellOrder(order.getId());
			for (SellOrderSku ssku : sos) {
				Sku sku = sl.getById(ssku.getSkuId());
				Inventory i = il.getById(ssku.getInventoryId());
				System.out.println(sdf.format(order.getSellTime()) + ","
						+ store.getName() + "," + sku.getSkuNumber() + ","
						+ sku.getShortName() + "," + sku.getUnit() + ","
						+ ssku.getCount() + ","
						+ String.format("%.2f",(ssku.getSellPrice() / ssku.getCount())) + ","
						+ ssku.getSellPrice());
			}

		}
	}
}
