package com.mjitech.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.constant.OperationLogConstants;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.OperationLogLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.OperationLog;
import com.mjitech.model.Sku;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.service.OperationLogService;

@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {
	private static Logger logger = LoggerFactory
			.getLogger(OperationLogServiceImpl.class);
	@Autowired
	private OperationLogLib operationLogLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private InventoryLib inventoryLib;
	@Autowired
	private SkuLib skuLib;
	@Autowired
	private WarehouseLib warehouseLib;

	private String buildModifyInventoryQuantityDescription(int userid,
			int inventoryId, int oldQuantity, int newQuantity) {
		StringBuilder sb = new StringBuilder("user:");
		Userinfo user = this.userinfoLib.getById(userid);
		if (user != null) {
			sb.append(user.getUsername());
		}
		sb.append(" change the quantity of ");
		Inventory inv = inventoryLib.getById(inventoryId);
		if (inv != null) {
			Sku sku = skuLib.getById(inv.getSkuId());
			if (sku != null) {
				sb.append(sku.getName()).append("(").append(sku.getSkuNumber())
						.append(")");
			}
			sb.append(" in ");
			Warehouse warehouse = this.warehouseLib.getById(inv
					.getWarehouse_id());
			if (warehouse != null) {
				sb.append(warehouse.getName());
			}
		}
		sb.append(" from ").append(oldQuantity).append(" to ")
				.append(newQuantity);
		return sb.toString();
	}

	@Override
	public void modifyInventoryQuantityLog(int userid, String ip,
			int inventoryId, int oldQuantity, int newQuantity) {
		OperationLog log = new OperationLog();
		log.setUserid(userid);
		log.setTargetId(inventoryId);
		log.setType(OperationLogConstants.TYPE_INVENTORY);
		log.setAction(OperationLogConstants.INVENTORY_ACTION_ADD);
		if (newQuantity < oldQuantity) {
			log.setAction(OperationLogConstants.INVENTORY_ACTION_DEDUCT);
		}
		log.setIp(ip);
		log.setOldValue(Integer.toString(oldQuantity));
		log.setNewValue(Integer.toString(newQuantity));
		log.setDescription(this.buildModifyInventoryQuantityDescription(userid,
				inventoryId, oldQuantity, newQuantity));
		this.operationLogLib.add(log);
		if (log.getId() <= 0) {
			if (logger.isWarnEnabled()) {
				logger.warn("adding modify inventory quantity log failed:"
						+ log);
			}
		}
	}

	private String buildModifyInventoryPriceDescription(int userid,
			int inventoryId, double oldPrice, double newPrice) {
		StringBuilder sb = new StringBuilder("user:");
		Userinfo user = this.userinfoLib.getById(userid);
		if (user != null) {
			sb.append(user.getUsername());
		}
		sb.append(" change the price of ");
		Inventory inv = inventoryLib.getById(inventoryId);
		if (inv != null) {
			Sku sku = skuLib.getById(inv.getSkuId());
			if (sku != null) {
				sb.append(sku.getName()).append("(").append(sku.getSkuNumber())
						.append(")");
			}
			sb.append(" in ");
			Warehouse warehouse = this.warehouseLib.getById(inv
					.getWarehouse_id());
			if (warehouse != null) {
				sb.append(warehouse.getName());
			}
		}
		sb.append(" from ").append(oldPrice).append(" to ").append(newPrice);
		return sb.toString();
	}

	@Override
	public void modifyInventoryPriceLog(int userid, String ip, int inventoryId,
			double oldPrice, double newPrice) {
		OperationLog log = new OperationLog();
		log.setUserid(userid);
		log.setTargetId(inventoryId);
		log.setType(OperationLogConstants.TYPE_INVENTORY);
		log.setAction(OperationLogConstants.INVENTORY_ACTION_MODIFY_PRICE);
		log.setIp(ip);
		log.setOldValue(Double.toString(oldPrice));
		log.setNewValue(Double.toString(newPrice));
		log.setDescription(this.buildModifyInventoryPriceDescription(userid,
				inventoryId, oldPrice, newPrice));
		this.operationLogLib.add(log);
		if (log.getId() <= 0) {
			if (logger.isWarnEnabled()) {
				logger.warn("adding modify inventory price log failed:" + log);
			}
		}
	}

	private String buildNewInventoryDescription(int userid, Inventory inventory) {
		StringBuilder sb = new StringBuilder("user:");
		Userinfo user = this.userinfoLib.getById(userid);
		if (user != null) {
			sb.append(user.getUsername());
		}
		sb.append(" add new inventory of ");

		Sku sku = skuLib.getById(inventory.getSkuId());
		if (sku != null) {
			sb.append(sku.getName()).append("(").append(sku.getSkuNumber())
					.append(")");
		}
		sb.append(" to ");
		Warehouse warehouse = this.warehouseLib.getById(inventory
				.getWarehouse_id());
		if (warehouse != null) {
			sb.append(warehouse.getName());
		}

		sb.append(" quantity is ").append(inventory.getQuantity())
				.append(" sellprice is ").append(inventory.getSellprice());
		return sb.toString();
	}

	@Override
	public void addNewInventoryLog(int userid, String ip, Inventory inventory) {
		OperationLog log = new OperationLog();
		log.setUserid(userid);
		log.setTargetId(inventory.getId());
		log.setType(OperationLogConstants.TYPE_INVENTORY);
		log.setAction(OperationLogConstants.INVENTORY_ACTION_ADD_NEW_INVENTORY);
		log.setIp(ip);
		log.setOldValue("");
		log.setNewValue(Integer.toString(inventory.getId()));
		log.setDescription(this.buildNewInventoryDescription(userid, inventory));
		this.operationLogLib.add(log);
		if (log.getId() <= 0) {
			if (logger.isWarnEnabled()) {
				logger.warn("adding add new inventory log failed:" + log);
			}
		}
	}

}
