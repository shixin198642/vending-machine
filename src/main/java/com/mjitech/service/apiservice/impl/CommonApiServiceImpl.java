package com.mjitech.service.apiservice.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.mjitech.constant.CommonApiErrorCodeConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.SellOrderConstants;
import com.mjitech.constant.WarehouseConstants;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.lib.WarehouseManagerLib;
import com.mjitech.lib.WxLib;
import com.mjitech.lib.WxpayHistoryLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;
import com.mjitech.model.Sku;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WxpayHistory;
import com.mjitech.service.OutstoreService;
import com.mjitech.service.PointsService;
import com.mjitech.service.SellOrderStatusHistoryService;
import com.mjitech.service.UserinfoService;
import com.mjitech.service.apiservice.CommonApiService;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.MapEntryConverter;
import com.mjitech.utils.SellOrderUtils;
import com.mjitech.wxpay.util.Signature;
import com.mjitech.wxpay.util.XMLParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

@Service("commonApiService")
public class CommonApiServiceImpl implements CommonApiService {
	@Autowired
	private InventoryLib inventoryLib;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private CommonApiErrorCodeConstants commonApiErrorCodeConstants;
	@Autowired
	private SkuLib skuLib;
	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private SellOrderLib sellOrderLib;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;
	@Autowired
	private SellOrderUtils sellOrderUtils;
	@Autowired
	private OutstoreService outstoreService;
	@Autowired
	private WarehouseManagerLib warehouseManagerLib;

	@Autowired
	private WxpayHistoryLib wxpayHistoryLib;
	@Autowired
	private WxLib wxLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private SellOrderStatusHistoryService sellOrderStatusHistoryService;
	@Autowired
	private PointsService pointsService;

	private static Logger logger = LoggerFactory
			.getLogger(CommonApiServiceImpl.class);

	@Override
	public JSONObject addParentOrder(List<Integer> orderids, int buyerId,
			int sellerId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (orderids == null || orderids.size() == 0) {
			jsonUtils
					.setRetErrorCode(
							json,
							commonApiErrorCodeConstants,
							CommonApiErrorCodeConstants.RET_CODE_ADD_PARENTORDER_NO_ORDERS,
							null);
			return json;
		}
		List<SellOrder> orders = new ArrayList<SellOrder>();
		double total = 0;
		for (int orderid : orderids) {
			SellOrder order = this.sellOrderLib.getById(orderid);
			if (order != null) {
				orders.add(order);
				total += order.getTotalPrice();
			}
		}
		SellOrder parent = new SellOrder();
		parent.setSellerId(sellerId);
		parent.setBuyerId(buyerId);
		parent.setTotalPrice(total);
		parent.setSellTime(new Date());
		parent.setStatus(SellOrderConstants.SELLORDER_STATUS_NEW);
		parent.setPayStatus(SellOrderConstants.SELLORDER_PAYSTATUS_UNPAY);
		parent.setIsParent(SellOrderConstants.IS_PARENT_YES);
		parent = this.sellOrderLib.add(parent);
		if (parent == null) {
			jsonUtils.setRetErrorCode(json, commonApiErrorCodeConstants,
					CommonApiErrorCodeConstants.RET_CODE_ADD_ORDER_DB_ERROR,
					null);
			return json;
		}
		for (SellOrder order : orders) {
			SellOrder update = new SellOrder();
			update.setId(order.getId());
			update.setParentId(parent.getId());
			update.setIsParent(SellOrderConstants.IS_PARENT_NO);
			order.setParentId(parent.getId());
			order.setIsParent(SellOrderConstants.IS_PARENT_NO);
			this.sellOrderLib.update(update);
			List<SellOrderSku> skus = this.sellOrderSkuLib.getBySellOrder(order
					.getId());
			for (SellOrderSku sku : skus) {
				sku.setSku(this.skuLib.getById(sku.getSkuId()));
			}
			order.setSkus(skus);
			order.setStore(this.warehouseLib.getById(order.getWarehouseId()));
		}
		parent.setChildOrders(orders);
		json.put("order", this.sellOrderUtils.getSellOrderJSON(parent));
		return json;
	}

	@Override
	public JSONObject addOrder(Map<Integer, Integer> skuCounts,
			int warehouseId, int sellerId, int buyerId, int parentId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (skuCounts == null || skuCounts.size() == 0) {
			jsonUtils.setRetErrorCode(json, commonApiErrorCodeConstants,
					CommonApiErrorCodeConstants.RET_CODE_ADD_ORDER_NO_SKUS,
					null);
			return json;
		}
		Warehouse store = this.warehouseLib.getById(warehouseId);
		if (store == null || !WarehouseConstants.isStoreOrMachine(store)) {
			jsonUtils.setRetErrorCode(json, commonApiErrorCodeConstants,
					CommonApiErrorCodeConstants.RET_CODE_ADD_ORDER_DB_ERROR,
					null);
			return json;
		}
		if (sellerId > 0
				&& !this.warehouseManagerLib.isWarehouseManager(store.getId(),
						sellerId)) {
			jsonUtils.setRetErrorCode(json, commonApiErrorCodeConstants,
					CommonApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return json;
		}
		double total = 0;
		List<SellOrderSku> sellOrderSkus = new ArrayList<SellOrderSku>();
		Map<Integer, Inventory> invMap = new HashMap<Integer, Inventory>();
		for (int skuId : skuCounts.keySet()) {
			Sku sku = this.skuLib.getById(skuId);
			if (sku == null) {
				jsonUtils
						.setRetErrorCode(
								json,
								commonApiErrorCodeConstants,
								CommonApiErrorCodeConstants.RET_CODE_ADD_ORDER_SKU_NOTFOUND,
								new Object[] { skuId });
				return json;
			}
			Inventory inv = inventoryLib
					.getBySkuWarehouse(skuId, store.getId());
			if (inv == null || inv.getQuantity() < skuCounts.get(skuId)) {
				jsonUtils
						.setRetErrorCode(
								json,
								commonApiErrorCodeConstants,
								CommonApiErrorCodeConstants.RET_CODE_ADD_ORDER_OUT_OF_STOCK,
								new Object[] { skuId });
				return json;
			}
			invMap.put(inv.getId(), inv);
			SellOrderSku sos = new SellOrderSku();
			sos.setCount(skuCounts.get(skuId));
			sos.setSkuId(sku.getId());
			sos.setSellOrderId(0);
			sos.setSellPrice(inv.getSellprice());
			sos.setInventoryId(inv.getId());
			sellOrderSkus.add(sos);
			total += (sos.getSellPrice() * sos.getCount());

		}

		SellOrder so = new SellOrder();
		so.setStatus(SellOrderConstants.SELLORDER_STATUS_NEW);
		so.setPayStatus(SellOrderConstants.SELLORDER_PAYSTATUS_UNPAY);
		so.setSellerId(sellerId);
		so.setBuyerId(buyerId);
		so.setSellTime(new Date());
		so.setWarehouseId(store.getId());
		so.setTotalPrice(total);
		so.setIsParent(SellOrderConstants.IS_PARENT_NO);
		so = this.sellOrderLib.add(so);

		if (so == null) {
			jsonUtils.setRetErrorCode(json, commonApiErrorCodeConstants,
					CommonApiErrorCodeConstants.RET_CODE_ADD_ORDER_DB_ERROR,
					null);
			return json;
		}
		int userid = buyerId;
		if (sellerId > 0) {
			// this is order from seller
			userid = sellerId;
		}
		this.sellOrderStatusHistoryService.addHistory(so.getId(), userid,
				so.getStatus());
		for (SellOrderSku sos : sellOrderSkus) {
			sos.setSellOrderId(so.getId());
			this.sellOrderSkuLib.add(sos);
			Inventory updateInv = new Inventory();
			updateInv.setId(sos.getInventoryId());
			updateInv.setQuantity(invMap.get(sos.getInventoryId())
					.getQuantity() - sos.getCount());
			inventoryLib.update(updateInv);

		}

		this.outstoreService.addSellOrderOutstore(so.getId());
		json.put("order", this.sellOrderUtils.getSellOrderJSON(so));

		return json;
	}

	private boolean handleWxReturnResult(Map<String, Object> retMap, String xml) {

		String orderNumber = (String) retMap.get("out_trade_no");
		String resultCode = (String) retMap.get("result_code");
		SellOrder order = this.sellOrderLib.getByOrderNumber(orderNumber);
		if (order == null) {
			if (logger.isWarnEnabled()) {
				logger.warn(orderNumber + " notfound for return xml:" + xml);
			}
			return false;
		}

		WxpayHistory wh = new WxpayHistory();
		wh.setSellOrderId(order.getId());
		wh.setSellOrderNumber(orderNumber);
		wh.setWxpayReturn(xml);
		wxpayHistoryLib.add(wh);
		if (resultCode != null && "SUCCESS".equals(resultCode)) {
			String openid = (String) retMap.get("openid");
			int buyerid = 0;
			if (StringUtils.isNotEmpty(openid)) {
				Userinfo userinfo = this.userinfoLib.getByOpenId(openid);
				if (userinfo == null) {
					JSONObject retJson = this.userinfoService
							.addUserWithOpenid(openid);
					if (retJson.getBoolean(JSONConstants.RETURN_KEY_IS_SUCCESS)) {
						buyerid = retJson.getJSONObject("userinfo")
								.getInt("id");
					}
				} else {
					buyerid = userinfo.getId();
				}
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("openid is null");
				}
			}
			int status = SellOrderConstants.SELLORDER_STATUS_UNTAKEN;
			if (order.getSellerId() > 0) {
				// this is order from seller
				status = SellOrderConstants.SELLORDER_STATUS_PAYED;
			}
			SellOrder update = new SellOrder();
			update.setBuyerId(buyerid);
			update.setId(order.getId());
			update.setPayStatus(SellOrderConstants.SELLORDER_PAYSTATUS_PAYED);
			update.setStatus(status);
			update.setPayTime(new Date());
			update.setTakeGoodsNumber(this.generateTakeGoodsNumber());
			this.sellOrderLib.update(update);
			this.sellOrderStatusHistoryService.addHistory(order.getId(),
					buyerid, status);
			if (order.getIsParent() == SellOrderConstants.IS_PARENT_YES
					&& order.getParentId() == 0) {
				List<SellOrder> childOrders = this.sellOrderLib
						.getByParent(order.getId());
				if (childOrders != null && childOrders.size() > 0) {
					for (SellOrder child : childOrders) {
						SellOrder childUpdate = new SellOrder();
						childUpdate.setId(child.getId());
						childUpdate
								.setPayStatus(SellOrderConstants.SELLORDER_PAYSTATUS_PAYED);
						childUpdate.setStatus(status);
						childUpdate.setPayTime(new Date());
						childUpdate.setTakeGoodsNumber(this
								.generateTakeGoodsNumber());
						this.sellOrderLib.update(childUpdate);
						this.sellOrderStatusHistoryService.addHistory(
								child.getId(), buyerid, status);
					}
				}
			}
			this.pointsService.addUserPoints(buyerid,
					(int) java.lang.Math.round(order.getTotalPrice()));
			// this.outstoreService.addSellOrderOutstore(order.getId());

		} else {
			return false;
		}
		return true;
	}

	private String generateTakeGoodsNumber() {
		StringBuilder number = null;
		boolean sameExisted = true;
		while (sameExisted) {
			number = new StringBuilder("");
			for (int i = 0; i < SellOrderConstants.TAKE_GOODS_NUMBER_LENGTH; i++) {
				number.append(new Random().nextInt(10));
			}
			SellOrder old = this.sellOrderLib.getByTakeGoodsNumber(number
					.toString());
			sameExisted = old != null;
		}

		return number.toString();
	}

	@Override
	public String wxpayCallback(String callbackInput) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("return_code", "SUCCESS");
		ret.put("return_msg", "OK");
		XStream xstream = new XStream(new DomDriver("UTF-8",
				new XmlFriendlyNameCoder("-_", "_")));
		xstream.alias("xml", Map.class);
		xstream.registerConverter(new MapEntryConverter());

		try {
			if (Signature.checkIsSignValidFromResponseString(callbackInput,
					this.wxLib.getKey())) {
				Map<String, Object> retMap = XMLParser
						.getMapFromXML(callbackInput);
				String retCode = (String) retMap.get("return_code");
				if (retCode != null && "SUCCESS".equals(retCode)) {

					if (this.handleWxReturnResult(retMap, callbackInput)) {

					} else {
						ret.put("return_code", "FAIL");
						ret.put("return_msg", "NOT OK");
					}
				} else {
					if (logger.isWarnEnabled()) {
						logger.warn(callbackInput);
					}
				}

			}

		} catch (IOException e) {
			logger.error("", e);
			e.printStackTrace();
		} catch (SAXException e) {
			logger.error("", e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			logger.error("", e);
			e.printStackTrace();
		}
		String retXML = xstream.toXML(ret);
		return retXML;
	}

}
