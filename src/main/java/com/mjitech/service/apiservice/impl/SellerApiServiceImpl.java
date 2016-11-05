package com.mjitech.service.apiservice.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.SellOrderConstants;
import com.mjitech.constant.SellerApiErrorCodeConstants;
import com.mjitech.constant.WXJSONConstants;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.LoginLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
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
import com.mjitech.service.SkuService;
import com.mjitech.service.UserinfoService;
import com.mjitech.service.apiservice.CommonApiService;
import com.mjitech.service.apiservice.SellerApiService;
import com.mjitech.utils.InventoryUtils;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.MapEntryConverter;
import com.mjitech.utils.SellOrderUtils;
import com.mjitech.utils.SkuUtils;
import com.mjitech.wxpay.util.Signature;
import com.mjitech.wxpay.util.XMLParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

@Service("sellerApiService")
public class SellerApiServiceImpl implements SellerApiService {

	private static Logger logger = LoggerFactory
			.getLogger(SellerApiServiceImpl.class);

	@Autowired
	private LoginLib loginLib;
	@Autowired
	private SkuLib skuLib;
	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private InventoryLib inventoryLib;
	@Autowired
	private SellerApiErrorCodeConstants sellerApiErrorCodeContants;
	@Autowired
	private SkuUtils skuUtils;
	@Autowired
	private SellOrderLib sellOrderLib;
	@Autowired
	private SkuService skuService;
	@Autowired
	private SellOrderUtils sellOrderUtils;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;
	@Autowired
	private WxLib wxLib;
	@Autowired
	private WxpayHistoryLib wxpayHistoryLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private OutstoreService outstoreService;
	@Autowired
	private InventoryUtils inventoryUtils;
	@Autowired
	private CommonApiService commonApiService;

	@Override
	public JSONObject bindWXAccount(String openid, String username,
			String password) {
		JSONObject ret = new JSONObject();
		ret.put(WXJSONConstants.RETURN_KEY_IS_SUCCESS, true);
		switch (this.loginLib.checkUser(username, password)) {
		case LoginLib.CHECK_USER_RETURN_NOUSERNAME:

			return ret;
		}
		return ret;
	}

	@Override
	public JSONObject getSkuDetail(String skuNumber, int userId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userId == 0) {
			json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(json, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return json;
		}
		Warehouse warehouse = this.warehouseLib.getByManager(userId);
		if (warehouse == null) {
			json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(json, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return json;
		}
		if (StringUtils.isEmpty(skuNumber)) {
			json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(json, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_SKU_NOTFOUND, null);
			return json;
		}
		Sku sku = null;
		if (skuNumber.toUpperCase().startsWith("U")) {
			sku = this.skuLib.getSkuBySkuNumber(skuNumber);
		} else if (StringUtils.isNumeric(skuNumber)) {
			int skuId = Integer.parseInt(skuNumber);
			sku = this.skuLib.getById(skuId);
		}
		if (sku == null) {
			json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(json, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_SKU_NOTFOUND, null);
			return json;
		}
		Inventory inv = this.inventoryLib.getBySkuWarehouse(sku.getId(),
				warehouse.getId());

		if (inv == null || inv.getQuantity() == 0) {
			if (logger.isWarnEnabled()) {
				logger.warn("skuid:" + sku.getId() + " warehouseid:"
						+ warehouse.getId());
			}
			json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(json, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_OUT_OF_STOCK, null);
			return json;
		}

		json.put("sku", this.getProductJson(sku, inv));
		return json;
	}

	private JSONObject getProductJson(Sku sku, Inventory inv) {
		return this.inventoryUtils.getProductJSON(inv);
	}

	@Override
	public JSONObject addOrder(Map<Integer, Integer> skuCounts, int userId) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userId == 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return ret;
		}

		Warehouse warehouse = this.warehouseLib.getByManager(userId);
		if (warehouse == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return ret;
		}
		return this.commonApiService.addOrder(skuCounts, warehouse.getId(),
				userId, 0, 0);

	}

	@Override
	public JSONObject orderList(int userId) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userId == 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return ret;
		}
		Warehouse wh = this.warehouseLib.getByManager(userId);
		if (wh == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return ret;
		}
		List<SellOrder> orders = this.sellOrderLib.getBySeller(userId);
		if (orders != null) {
			for (SellOrder order : orders) {
				List<SellOrderSku> skus = this.sellOrderSkuLib
						.getBySellOrder(order.getId());
				for (SellOrderSku sku : skus) {
					sku.setSku(this.skuLib.getById(sku.getSkuId()));
				}
				order.setSkus(skus);
			}
		}
		ret.put("orders", sellOrderUtils.getSellOrderJSONArray(orders));
		return ret;
	}

	@Override
	public JSONObject orderDetail(String orderNumber, int userId) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		SellOrder order = this.sellOrderLib.getByOrderNumber(orderNumber);
		if (userId == 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return ret;
		}
		if (order == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils
					.setRetErrorCode(
							ret,
							sellerApiErrorCodeContants,
							SellerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_URL_DB_ERROR,
							null);
			return ret;
		}
		if (order.getSellerId() != userId) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils
					.setRetErrorCode(
							ret,
							sellerApiErrorCodeContants,
							SellerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_URL_NO_AUTH,
							null);
			return ret;
		}
		Warehouse wh = this.warehouseLib.getByManager(order.getSellerId());
		if (wh == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils
					.setRetErrorCode(
							ret,
							sellerApiErrorCodeContants,
							SellerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_URL_DB_ERROR,
							null);
			return ret;
		}
		List<SellOrderSku> skus = this.sellOrderSkuLib.getBySellOrder(order
				.getId());
		for (SellOrderSku sku : skus) {
			sku.setSku(this.skuLib.getById(sku.getSkuId()));
		}
		order.setSkus(skus);
		JSONObject orderJson = sellOrderUtils.getSellOrderJSON(order);
		orderJson.put("shopName", wh.getName());
		ret.put("order", orderJson);
		return ret;
	}

	@Override
	public JSONObject requestPayUrl(String orderNumber, int userId) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userId == 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return ret;
		}
		SellOrder order = this.sellOrderLib.getByOrderNumber(orderNumber);
		if (order == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils
					.setRetErrorCode(
							ret,
							sellerApiErrorCodeContants,
							SellerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_URL_DB_ERROR,
							null);
			return ret;
		}
		if (order.getSellerId() != userId) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils
					.setRetErrorCode(
							ret,
							sellerApiErrorCodeContants,
							SellerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_URL_NO_AUTH,
							null);
			return ret;
		}
		Warehouse wh = this.warehouseLib.getByManager(order.getSellerId());
		if (wh == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils
					.setRetErrorCode(
							ret,
							sellerApiErrorCodeContants,
							SellerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_URL_DB_ERROR,
							null);
			return ret;
		}
		ret.put("shopName", wh.getName());
		ret.put("order", sellOrderUtils.getSellOrderJSON(order));
		String retXML = this.wxLib.requestPayUrl(order.getId());
		if (retXML == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils
					.setRetErrorCode(
							ret,
							sellerApiErrorCodeContants,
							SellerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_URL_DB_ERROR,
							null);
			return ret;
		}
		try {
			Map<String, Object> retMap = XMLParser.getMapFromXML(retXML);
			String retcode = (String) retMap.get("return_code");
			if (retcode != null && "SUCCESS".equals(retcode)) {
				String payUrl = (String) retMap.get("code_url");
				String prepayid = (String) retMap.get("prepay_id");
				SellOrder update = new SellOrder();
				update.setId(order.getId());
				update.setWxpayUrl(payUrl);
				update.setWxprepayId(prepayid);
				update.setRequestWxpayTime(new Date());
				this.sellOrderLib.update(update);
				ret.put("payUrl", payUrl);
			} else {
				String erromsg = (String) retMap.get("return_msg");
				logger.warn("request pay url no ok");
				logger.warn(retXML);
				ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
				jsonUtils
						.setRetErrorCode(
								ret,
								sellerApiErrorCodeContants,
								SellerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_URL_WX_ERROR,
								new Object[] { erromsg });
				return ret;

			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			logger.error("", e);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("", e);
		} catch (SAXException e) {
			logger.error("", e);
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public JSONObject orderStatus(String orderNumber, int userId) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		SellOrder order = this.sellOrderLib.getByOrderNumber(orderNumber);
		if (userId == 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_NOT_SELLER, null);
			return ret;
		}
		if (order == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_ORDER_NOTFOUND, null);
			return ret;
		}
		if (order.getSellerId() != userId) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, sellerApiErrorCodeContants,
					SellerApiErrorCodeConstants.RET_CODE_ORDER_NOAUTH, null);
			return ret;
		}
		JSONObject orderJson = this.sellOrderUtils.getSellOrderJSON(order);
		ret.put("order", orderJson);
		return ret;
	}
}
