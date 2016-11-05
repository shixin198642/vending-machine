package com.mjitech.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.constant.InventoryErrorCodeConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.UserTypeLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.Sku;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.service.InstoreService;
import com.mjitech.service.InventoryService;
import com.mjitech.service.OperationLogService;
import com.mjitech.service.OutstoreService;
import com.mjitech.utils.JsonUtils;

@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryLib inventoryLib;
	@Autowired
	private InventoryErrorCodeConstants errorCodeConstants;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private OperationLogService operationLogService;
	@Autowired
	private OutstoreService outstoreService;
	@Autowired
	private InstoreService instoreService;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private UserTypeLib userTypeLib;
	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private SkuLib skuLib;

	@Override
	public List<Inventory> getByWarehouse(int id) {
		return inventoryLib.getByWarehouse(id);
	}

	@Override
	public JSONObject modifyQuantity(int userid, String ip, int inventoryId,
			int quantity, String description) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (quantity == 0) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_WRONG_PARAM, null);
			return json;
		}
		Userinfo user = this.userinfoLib.getById(userid);
		if (user == null || !this.userTypeLib.canAccessAdmin(user)) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_NOAUTH, null);
			return json;
		}
		Inventory inv = this.inventoryLib.getById(inventoryId);
		if (inv == null) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_DB_ERROR, null);
			return json;
		}
		if (quantity + inv.getQuantity() < 0) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_WRONG_PARAM, null);
			return json;
		}
		Inventory update = new Inventory();
		update.setId(inv.getId());
		int updateQuantity = inv.getQuantity() + quantity;
		if (updateQuantity == 0) {
			updateQuantity = -1;
		}
		update.setQuantity(updateQuantity);
		int ret = this.inventoryLib.update(update);
		if (ret > 0) {
			if (quantity > 0) {
				this.instoreService.addManualInstore(userid,
						inv.getWarehouse_id(), inv.getSkuId(), quantity, 0,
						description);
			} else {
				this.outstoreService.addManualOutstore(userid,
						inv.getWarehouse_id(), inv.getSkuId(),
						Math.abs(quantity), description);
			}
			this.operationLogService.modifyInventoryQuantityLog(userid, ip,
					inventoryId, inv.getQuantity(), inv.getQuantity()
							+ quantity);
		} else {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_DB_ERROR, null);
			return json;
		}
		return json;
	}

	@Override
	public JSONObject modifySellprice(int userid, String ip, int inventoryId,
			double sellprice) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (sellprice <= 0) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_WRONG_PARAM, null);
			return json;
		}
		Userinfo user = this.userinfoLib.getById(userid);
		if (user == null || !this.userTypeLib.canAccessAdmin(user)) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_NOAUTH, null);
			return json;
		}
		Inventory inv = this.inventoryLib.getById(inventoryId);
		if (inv == null) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_DB_ERROR, null);
			return json;
		}
		Inventory update = new Inventory();
		update.setId(inv.getId());
		update.setSellprice(sellprice);
		int ret = this.inventoryLib.update(update);
		if (ret > 0) {
			this.operationLogService.modifyInventoryPriceLog(userid, ip,
					inventoryId, inv.getSellprice(), update.getSellprice());
		} else {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_DB_ERROR, null);
			return json;
		}
		return json;
	}

	@Override
	public JSONObject addNewInventory(int userid, String ip,
			Inventory inventory, String description) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (inventory.getSellprice() <= 0) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_WRONG_PARAM, null);
			return json;
		}
		Userinfo user = this.userinfoLib.getById(userid);
		if (user == null || !this.userTypeLib.canAccessAdmin(user)) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_NOAUTH, null);
			return json;
		}
		Warehouse warehouse = this.warehouseLib.getById(inventory
				.getWarehouse_id());
		if (warehouse == null) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_WRONG_PARAM, null);
			return json;
		}
		Sku sku = this.skuLib.getById(inventory.getSkuId());
		if (sku == null) {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_WRONG_PARAM, null);
			return json;
		}
		Inventory old = this.inventoryLib.getBySkuWarehouse(
				inventory.getSkuId(), inventory.getWarehouse_id());
		if (old != null) {
			jsonUtils
					.setRetErrorCode(
							json,
							errorCodeConstants,
							InventoryErrorCodeConstants.RET_CODE_NEW_INVENTORY_OLD_EXISTED,
							new Object[] { sku.getName() + "("
									+ sku.getSkuNumber() + ")" });
			return json;
		}

		inventory = this.inventoryLib.add(inventory);
		if (inventory != null) {
			this.operationLogService.addNewInventoryLog(userid, ip, inventory);
			this.instoreService.addManualInstore(userid,
					inventory.getWarehouse_id(), inventory.getSkuId(),
					inventory.getQuantity(), inventory.getSellprice(),
					description);
		} else {
			jsonUtils.setRetErrorCode(json, errorCodeConstants,
					InventoryErrorCodeConstants.RET_CODE_DB_ERROR, null);
			return json;
		}
		return json;
	}

}
