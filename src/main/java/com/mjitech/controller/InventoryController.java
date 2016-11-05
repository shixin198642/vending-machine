package com.mjitech.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.common.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.SkuLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.Sku;
import com.mjitech.service.InventoryService;
import com.mjitech.service.RequestService;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;

@Controller("inventoryController")
public class InventoryController {
	private static final Logger logger = LoggerFactory
			.getLogger(InventoryController.class);
	@Autowired
	RequestService requestService;
	@Autowired
	InventoryService inventoryService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private SkuLib skuLib;

	/**
	 * 手动增加库存
	 * 
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ADD_INVENTORY_QUANTITY, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String addInventoryQuantity(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> params) {

		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter addInventoryQuantity ");
		}
		int inventoryId = this.requestUtils.getIntValue(params, "inv_id");
		String description = this.requestUtils.getStringValue(params,
				"description");
		int quantity = this.requestUtils.getIntValue(params, "quantity");
		JSONObject retJson = this.inventoryService.modifyQuantity(
				sessionUtils.getUserid(request),
				requestUtils.getRealRemoteIP(request), inventoryId, quantity,
				description);
		if (logger.isTraceEnabled()) {
			logger.trace("exit addInventoryQuantity ");
		}
		ret = retJson.toString();
		return ret;
	}

	/**
	 * 手动减少库存
	 * 
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.DEDUCT_INVENTORY_QUANTITY, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String deductInventoryQuantity(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> params) {

		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter deductInventoryQuantity ");
		}

		int inventoryId = this.requestUtils.getIntValue(params, "inv_id");
		String description = this.requestUtils.getStringValue(params,
				"description");
		int quantity = this.requestUtils.getIntValue(params, "quantity");
		JSONObject retJson = this.inventoryService.modifyQuantity(
				sessionUtils.getUserid(request),
				requestUtils.getRealRemoteIP(request), inventoryId, -quantity,
				description);
		if (logger.isTraceEnabled()) {
			logger.trace("exit deductInventoryQuantity ");
		}
		ret = retJson.toString();
		return ret;
	}

	/**
	 * 更改价格
	 * 
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.MODIFY_INVENTORY_PRICE, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String modifyInventoryPrice(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> params) {

		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter modifyInventoryPrice ");
		}
		int inventoryId = this.requestUtils.getIntValue(params, "inv_id");
		float price = this.requestUtils.getFloatValue(params, "price");
		JSONObject retJson = this.inventoryService.modifySellprice(
				sessionUtils.getUserid(request),
				requestUtils.getRealRemoteIP(request), inventoryId, price);
		if (logger.isTraceEnabled()) {
			logger.trace("exit modifyInventoryPrice ");
		}
		ret = retJson.toString();
		return ret;
	}

	private Inventory parseInventory(Map<String, String> params) {
		Inventory inventory = new Inventory();
		inventory.setWarehouse_id(requestUtils.getIntValue(params,
				"warehouseId"));
		inventory.setSellprice(requestUtils.getFloatValue(params, "price"));
		String skuNumber = this.requestUtils
				.getStringValue(params, "skunumber");
		Sku sku = null;
		if (skuNumber.toLowerCase().startsWith("u")) {
			sku = this.skuLib.getSkuBySkuNumber(skuNumber);
		} else if (StringUtils.isNumeric(skuNumber)) {
			sku = skuLib.getById(Integer.parseInt(skuNumber));
		}
		if (sku != null) {
			inventory.setSkuId(sku.getId());
		}
		inventory.setQuantity(requestUtils.getIntValue(params, "quantity"));
		inventory.setPosition("abcd");
		inventory.setMax_stock(100);
		inventory.setMin_stock(10);
		inventory.setSafe_stock(20);
		inventory.setStatus("ready");
		return inventory;
	}

	/**
	 * 增加库存
	 * 
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ADD_NEW_INVENTORY, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String addNewInventory(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> params) {

		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter addNewInventory ");
		}
		JSONObject retJson = this.inventoryService.addNewInventory(
				sessionUtils.getUserid(request),
				requestUtils.getRealRemoteIP(request),
				this.parseInventory(params),
				requestUtils.getStringValue(params, "description"));
		if (logger.isTraceEnabled()) {
			logger.trace("exit addNewInventory ");
		}
		ret = retJson.toString();
		return ret;
	}

}
