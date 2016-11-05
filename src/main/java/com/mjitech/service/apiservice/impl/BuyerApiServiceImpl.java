package com.mjitech.service.apiservice.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.elasticsearch.common.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.mjitech.constant.BuyerApiErrorCodeConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.constant.SellOrderConstants;
import com.mjitech.constant.WarehouseConstants;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.PointsLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.lib.SkuBrandLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.SkuTypeLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.lib.WxLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;
import com.mjitech.model.Sku;
import com.mjitech.model.SkuBrand;
import com.mjitech.model.SkuType;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.service.apiservice.BuyerApiService;
import com.mjitech.service.apiservice.CommonApiService;
import com.mjitech.utils.InventoryUtils;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.SellOrderUtils;
import com.mjitech.utils.SkuBrandUtils;
import com.mjitech.utils.SkuUtils;
import com.mjitech.utils.UserinfoUtils;
import com.mjitech.utils.WarehouseUtils;
import com.mjitech.wxpay.JsApiParams;
import com.mjitech.wxpay.util.XMLParser;

@Service("buyerApiService")
public class BuyerApiServiceImpl implements BuyerApiService {
	@Autowired
	private SkuTypeLib skuTypeLib;
	@Autowired
	private SkuLib skuLib;
	@Autowired
	private SkuUtils skuUtils;
	@Autowired
	private SkuBrandLib skuBrandLib;
	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private WarehouseUtils warehouseUtils;
	@Autowired
	private BuyerApiErrorCodeConstants buyerApiErrorCodeContants;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private InventoryLib inventoryLib;
	@Autowired
	private InventoryUtils inventoryUtils;
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private CommonApiService commonApiService;
	@Autowired
	private WxLib wxLib;
	@Autowired
	private SellOrderLib sellOrderLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private SellOrderUtils sellOrderUtils;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;
	@Autowired
	private UserinfoUtils userinfoUtils;
	@Autowired
	private PointsLib pointsLib;
	@Autowired
	private SkuBrandUtils skuBrandUtils;

	private static Logger logger = LoggerFactory
			.getLogger(BuyerApiServiceImpl.class);

	@Override
	public JSONObject getStoreList(int userid) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		List<Warehouse> warehouses = this.warehouseLib.listAllMachineStore();
		json.put("stores", this.warehouseUtils.getWarehouseListJSON(warehouses));
		return json;
	}

	private List<Inventory> getStoreInventories(int storeId, int count) {
		List<Inventory> invs = this.inventoryLib.getByWarehouse(storeId);
		List<Inventory> rets = new ArrayList<Inventory>();
		for (int i = 0; invs != null && i < invs.size() && i < count; i++) {
			invs.get(i).setSku(this.skuLib.getById(invs.get(i).getSkuId()));
			rets.add(invs.get(i));
		}
		return rets;
	}

	@Override
	public JSONObject getMainpageData(int userid, int warehouseId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (warehouseId == 0) {
			List<Warehouse> allstores = this.warehouseLib.listAllMachineStore();
			if (allstores != null && allstores.size() > 0) {
				warehouseId = allstores.get(0).getId();
			} else {
				jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
						BuyerApiErrorCodeConstants.RET_CODE_STORE_ERROR, null);
				return json;
			}
		}
		Warehouse store = this.warehouseLib.getById(warehouseId);
		if (store == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_STORE_ERROR, null);
			return json;
		}
		json.put("selectedStore", this.warehouseUtils.getWarehouseJSON(store));
		JSONArray banners = new JSONArray();
		for (int i = 0; i < 4; i++) {
			JSONObject banner = new JSONObject();
			banner.put("destUrl", "http://www.baidu.com");
			banner.put(
					"imagePath",
					"http://photos.cntraveler.com/2014/09/29/5429c32b425f183f61bf7316_new-york-city-skyline.jpg");
			banners.put(banner);
		}
		json.put("banners", banners);
		List<SkuType> categories = skuTypeLib.getByParentId(0);
		JSONArray catArray = new JSONArray();
		// add all cat obj
		JSONObject rootObj = new JSONObject();
		rootObj.put("id", 0);
		rootObj.put("name", "全部");
		JSONObject rootBanner = new JSONObject();
		rootBanner.put("destUrl", "http://www.baidu.com");
		rootBanner
				.put("imagePath",
						"http://photos.cntraveler.com/2014/09/29/5429c32b425f183f61bf7316_new-york-city-skyline.jpg");
		rootObj.put("banner", rootBanner);
		JSONArray rootFreeItems = new JSONArray();
		List<Inventory> invs = this.getStoreInventories(store.getId(), 5);
		for (int i = 1; i <= 5 && i <= invs.size(); i++) {
			rootFreeItems.put(this.inventoryUtils.getProductJSON(invs
					.get(i - 1)));
		}
		rootObj.put("freeItems", rootFreeItems);
		JSONArray rootItems = new JSONArray();
		for (int i = 1; i <= 5 && i <= invs.size(); i++) {
			rootItems.put(this.inventoryUtils.getProductJSON(invs.get(i - 1)));
		}
		rootObj.put("items", rootItems);
		catArray.put(rootObj);
		for (SkuType category : categories) {
			JSONObject catObj = new JSONObject();
			catObj.put("id", category.getId());
			catObj.put("name", category.getName());
			JSONObject catBanner = new JSONObject();
			catBanner.put("destUrl", "http://www.baidu.com");
			catBanner
					.put("imagePath",
							"http://photos.cntraveler.com/2014/09/29/5429c32b425f183f61bf7316_new-york-city-skyline.jpg");
			catObj.put("banner", catBanner);
			JSONArray freeItems = new JSONArray();
			for (int i = 1; i <= 5 && i <= invs.size(); i++) {
				freeItems.put(this.inventoryUtils.getProductJSON(invs
						.get(i - 1)));
			}
			catObj.put("freeItems", freeItems);
			JSONArray items = new JSONArray();
			for (int i = 1; i <= 5 && i <= invs.size(); i++) {
				items.put(this.inventoryUtils.getProductJSON(invs.get(i - 1)));
			}
			catObj.put("items", items);
			catArray.put(catObj);
		}

		json.put("categories", catArray);
		return json;
	}

	@Override
	public JSONObject getMainpageDataByGeo(int userid, long longitude,
			long latitude) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		Warehouse store = this.warehouseLib
				.getNearestStore(longitude, latitude);

		return this.getMainpageData(userid, store.getId());
	}

	private String getCartKey(int userid) {
		return new StringBuilder(RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_CART)
				.append(userid).toString();
	}

	@Override
	public JSONObject getStoreCarts(int userid) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_NO_LOGIN, null);
			return json;
		}
		String key = this.getCartKey(userid);
		Map<Integer, Map<Integer, Integer>> storeCarts = (Map<Integer, Map<Integer, Integer>>) this.redisLib
				.getCache(key);
		if (storeCarts == null) {
			storeCarts = new HashMap<Integer, Map<Integer, Integer>>();
		}
		JSONArray skus = new JSONArray();
		for (int storeId : storeCarts.keySet()) {
			Warehouse store = this.warehouseLib.getById(storeId);
			if (store == null) {
				continue;
			}
			Map<Integer, Integer> storeCart = storeCarts.get(storeId);
			JSONObject storeJSON = warehouseUtils.getWarehouseJSON(store);
			JSONArray products = new JSONArray();
			for (int skuId : storeCart.keySet()) {
				Inventory inv = this.inventoryLib.getBySkuWarehouse(skuId,
						storeId);
				if (inv == null) {
					continue;
				}
				JSONObject invJSON = this.inventoryUtils.getProductJSON(inv);
				invJSON.put("count", storeCart.get(skuId));
				products.put(invJSON);
			}
			storeJSON.put("productList", products);
			skus.put(storeJSON);
		}
		json.put("skus", skus);
		return json;
	}

	@Override
	public JSONObject addSkuToCart(int userid, int storeId, String skuId,
			int count) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_NO_LOGIN, null);
			return json;
		}
		Sku sku = null;
		if (skuId != null && skuId.startsWith("U")) {
			sku = this.skuLib.getSkuBySkuNumber(skuId);
		} else if (StringUtils.isNumeric(skuId)) {
			sku = this.skuLib.getById(Integer.parseInt(skuId));
		}
		if (sku == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_ADD_CART_NO_INVENTORY,
					null);
			return json;
		}
		Inventory inv = this.inventoryLib.getBySkuWarehouse(sku.getId(),
				storeId);
		if (inv == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_ADD_CART_NO_INVENTORY,
					null);
			return json;
		}
		String key = this.getCartKey(userid);
		Map<Integer, Map<Integer, Integer>> storeCarts = (Map<Integer, Map<Integer, Integer>>) this.redisLib
				.getCache(key);
		if (storeCarts == null) {
			storeCarts = new HashMap<Integer, Map<Integer, Integer>>();
		}
		Map<Integer, Integer> storeCart = (Map<Integer, Integer>) storeCarts
				.get(storeId);
		if (storeCart == null) {
			storeCart = new HashMap<Integer, Integer>();
		}
		int original = storeCart.get(new Integer(skuId)) == null ? 0
				: storeCart.get(new Integer(skuId));
		int total = original + count;
		if (total > inv.getQuantity()) {
			jsonUtils
					.setRetErrorCode(
							json,
							buyerApiErrorCodeContants,
							BuyerApiErrorCodeConstants.RET_CODE_ADD_CART_BEYOND_QUANTITY,
							null);
			return json;
		}
		storeCart.put(sku.getId(), total);
		storeCarts.put(storeId, storeCart);
		this.redisLib.addCache(key,
				(HashMap<Integer, Map<Integer, Integer>>) storeCarts);
		json.put("currentCount", total);
		return json;
	}

	@Override
	public JSONObject removeSkuFromCart(int userid, int storeId, int skuId,
			int count) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_NO_LOGIN, null);
			return json;
		}
		String key = this.getCartKey(userid);
		Map<Integer, Map<Integer, Integer>> storeCarts = (Map<Integer, Map<Integer, Integer>>) this.redisLib
				.getCache(key);
		if (storeCarts == null) {
			storeCarts = new HashMap<Integer, Map<Integer, Integer>>();
		}
		Map<Integer, Integer> storeCart = (Map<Integer, Integer>) storeCarts
				.get(storeId);
		if (storeCart == null) {
			storeCart = new HashMap<Integer, Integer>();
		}
		Inventory inv = this.inventoryLib.getBySkuWarehouse(skuId, storeId);
		if (inv == null) {
			storeCart.remove(skuId);
			storeCarts.put(storeId, storeCart);
			this.redisLib.addCache(key,
					(HashMap<Integer, Map<Integer, Integer>>) storeCarts);
			return json;
		}
		int original = storeCart.get(skuId) == null ? 0 : storeCart.get(skuId);
		int to = original - count;
		if (to <= 0) {
			storeCart.remove(skuId);
			storeCarts.put(storeId, storeCart);
			this.redisLib.addCache(key,
					(HashMap<Integer, Map<Integer, Integer>>) storeCarts);
			return json;
		}
		storeCart.put(skuId, to);
		storeCarts.put(storeId, storeCart);
		this.redisLib.addCache(key,
				(HashMap<Integer, Map<Integer, Integer>>) storeCarts);

		return json;
	}

	@Override
	public JSONObject clearStoreCart(int userid, int storeId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_NO_LOGIN, null);
			return json;
		}
		String key = this.getCartKey(userid);
		Map<Integer, Map<Integer, Integer>> storeCarts = (Map<Integer, Map<Integer, Integer>>) this.redisLib
				.getCache(key);
		if (storeCarts == null) {
			storeCarts = new HashMap<Integer, Map<Integer, Integer>>();
		}
		storeCarts.remove(storeId);
		this.redisLib.addCache(key,
				(HashMap<Integer, Map<Integer, Integer>>) storeCarts);

		return json;
	}

	@Override
	public JSONObject submitStoreCart(int userid, int storeId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_NO_LOGIN, null);
			return json;
		}
		String key = this.getCartKey(userid);
		Map<Integer, Map<Integer, Integer>> storeCarts = (Map<Integer, Map<Integer, Integer>>) this.redisLib
				.getCache(key);
		if (storeCarts == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_SUBMIT_CART_NO_CART,
					null);
			return json;
		}
		Warehouse store = this.warehouseLib.getById(storeId);
		if (store == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_STORE_ERROR, null);
			return json;
		}
		Map<Integer, Integer> storeCart = storeCarts.get(storeId);
		if (storeCart == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_SUBMIT_CART_NO_CART,
					null);
			return json;
		}
		JSONObject addret = this.commonApiService.addOrder(storeCart,
				store.getId(), 0, userid, 0);
		if (addret.getBoolean(JSONConstants.RETURN_KEY_IS_SUCCESS)) {
			this.clearStoreCart(userid, storeId);
		}

		return addret;
	}

	@Override
	public JSONObject submitStoreCarts(int userid, List<Integer> storeIds) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_NO_LOGIN, null);
			return json;
		}
		if (storeIds == null || storeIds.size() == 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_STORE_ERROR, null);
			return json;
		}
		String key = this.getCartKey(userid);
		Map<Integer, Map<Integer, Integer>> storeCarts = (Map<Integer, Map<Integer, Integer>>) this.redisLib
				.getCache(key);
		if (storeCarts == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_SUBMIT_CART_NO_CART,
					null);
			return json;
		}
		List<Integer> orderids = new ArrayList<Integer>();
		JSONObject orderResults = new JSONObject();
		for (int i = 0; i < storeIds.size(); i++) {
			int storeId = storeIds.get(i);
			Warehouse store = this.warehouseLib.getById(storeId);
			if (store == null) {
				jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
						BuyerApiErrorCodeConstants.RET_CODE_STORE_ERROR, null);
				return json;
			}
			Map<Integer, Integer> storeCart = storeCarts.get(storeId);
			if (storeCart == null) {
				jsonUtils
						.setRetErrorCode(
								json,
								buyerApiErrorCodeContants,
								BuyerApiErrorCodeConstants.RET_CODE_SUBMIT_CART_NO_CART,
								null);
				orderResults.put(Integer.toString(store.getId()), json);
				continue;
			}

			JSONObject addret = this.commonApiService.addOrder(storeCart,
					store.getId(), 0, userid, 0);
			if (addret.getBoolean(JSONConstants.RETURN_KEY_IS_SUCCESS)) {
				this.clearStoreCart(userid, storeId);
				orderids.add(addret.getJSONObject("order").getInt("id"));
			}
			orderResults.put(Integer.toString(store.getId()), addret);
		}
		json = this.commonApiService.addParentOrder(orderids, userid, 0);
		json.put("orderResults", orderResults);
		return json;
	}

	@Override
	public JSONObject getJSAPIParams(int userid, String url) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		JsApiParams params = this.wxLib.getJsApiParams(url);
		JSONObject paramsJson = new JSONObject(params);
		// paramsJson.remove("jsapi_ticket");
		json.put("params", paramsJson);
		return json;
	}

	private void prepareOrderDetail(SellOrder order) {
		List<SellOrderSku> skus = this.sellOrderSkuLib.getBySellOrder(order
				.getId());
		if (skus != null) {
			for (SellOrderSku sku : skus) {
				sku.setSku(this.skuLib.getById(sku.getSkuId()));
			}
			order.setSkus(skus);
		}
		if (order.getWarehouseId() > 0) {
			order.setStore(this.warehouseLib.getById(order.getWarehouseId()));
		}
		if (order.getChildOrders() != null && order.getChildOrders().size() > 0) {
			for (SellOrder child : order.getChildOrders()) {
				this.prepareOrderDetail(child);
			}

		}
	}

	@Override
	public JSONObject requestJSPayParams(int userid, String orderNumber) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		Userinfo buyer = this.userinfoLib.getById(userid);
		if (userid == 0 || buyer == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_USER_ERROR, null);
			return ret;
		}
		SellOrder order = null;
		if (orderNumber.startsWith("S")) {
			order = this.sellOrderLib.getByOrderNumber(orderNumber);
		} else if (StringUtils.isNumeric(orderNumber)) {
			order = this.sellOrderLib.getById(Integer.parseInt(orderNumber));
		}
		if (order == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_DBERROR, null);
			return ret;
		}
		if (order.getBuyerId() != userid) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_NOT_BUYER,
					null);
			return ret;
		}
		if (order.getPayStatus() != SellOrderConstants.SELLORDER_PAYSTATUS_UNPAY) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils
					.setRetErrorCode(
							ret,
							buyerApiErrorCodeContants,
							BuyerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_WRONGSTATUS,
							null);
			return ret;
		}
		if (order.getIsParent() == SellOrderConstants.IS_PARENT_YES
				&& order.getParentId() == 0) {
			List<SellOrder> childOrders = this.sellOrderLib.getByParent(order
					.getId());
			if (childOrders == null || childOrders.size() == 0) {
				ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
				jsonUtils.setRetErrorCode(ret, buyerApiErrorCodeContants,
						BuyerApiErrorCodeConstants.RET_CODE_DBERROR, null);
				return ret;
			}
			for (SellOrder child : childOrders) {
				List<SellOrderSku> skus = this.sellOrderSkuLib
						.getBySellOrder(child.getId());
				for (SellOrderSku sku : skus) {
					sku.setSku(skuLib.getById(sku.getId()));
				}
				child.setSkus(skus);
				child.setStore(this.warehouseLib.getById(child.getWarehouseId()));
			}
			order.setChildOrders(childOrders);

		} else {
			Warehouse wh = this.warehouseLib.getById(order.getWarehouseId());
			if (wh == null) {
				ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
				jsonUtils.setRetErrorCode(ret, buyerApiErrorCodeContants,
						BuyerApiErrorCodeConstants.RET_CODE_DBERROR, null);
				return ret;
			}
			ret.put("shopName", wh.getName());
		}
		this.prepareOrderDetail(order);
		ret.put("order", sellOrderUtils.getSellOrderJSON(order));
		String retXML = this.wxLib.requestBuyerPayParams(order.getId());
		if (retXML == null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			jsonUtils.setRetErrorCode(ret, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_DBERROR, null);
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
				ret.put("jssdkPayParams", this.wxLib
						.getJsApiPayParams(prepayid).toJSON());
			} else {
				String erromsg = (String) retMap.get("return_msg");
				logger.warn("request pay url no ok");
				logger.warn(retXML);
				jsonUtils
						.setRetErrorCode(
								ret,
								buyerApiErrorCodeContants,
								BuyerApiErrorCodeConstants.RET_CODE_REQUEST_PAY_WXERROR,
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
	public JSONObject getOrderList(int userid, int storeId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_USER_ERROR, null);
			return json;
		}
		List<SellOrder> rets = new ArrayList<SellOrder>();
		List<SellOrder> orders = this.sellOrderLib.getByBuyer(userid);
		if (orders == null) {
			orders = new ArrayList<SellOrder>();
		}
		for (SellOrder order : orders) {
			if (storeId == 0 || order.getWarehouseId() == storeId) {
				rets.add(order);
			}
			List<SellOrderSku> skus = this.sellOrderSkuLib.getBySellOrder(order
					.getId());
			for (SellOrderSku sku : skus) {
				sku.setSku(this.skuLib.getById(sku.getSkuId()));
			}
			order.setSkus(skus);
			order.setStore(this.warehouseLib.getById(order.getWarehouseId()));
		}
		json.put("orders", sellOrderUtils.getSellOrderJSONArray(rets));
		return json;
	}

	@Override
	public JSONObject getOrderDetail(int userid, String orderNumber) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_USER_ERROR, null);
			return json;
		}
		if (orderNumber == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_ORDER_NOT_FOUND, null);
			return json;
		}
		SellOrder order = null;
		if (orderNumber.startsWith("S")) {
			order = this.sellOrderLib.getByOrderNumber(orderNumber);
		} else if (StringUtils.isNumeric(orderNumber)) {
			order = this.sellOrderLib.getById(Integer.parseInt(orderNumber));
		}
		if (order == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_ORDER_NOT_FOUND, null);
			return json;
		}
		List<SellOrderSku> skus = this.sellOrderSkuLib.getBySellOrder(order
				.getId());
		for (SellOrderSku sku : skus) {
			sku.setSku(this.skuLib.getById(sku.getSkuId()));
		}
		order.setSkus(skus);
		order.setStore(this.warehouseLib.getById(order.getWarehouseId()));

		json.put("order", sellOrderUtils.getSellOrderJSON(order));
		return json;
	}

	@Override
	public JSONObject getBrandDetail(int userid, int brandId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_USER_ERROR, null);
			return json;
		}
		SkuBrand brand = this.skuBrandLib.getById(brandId);
		if (brand == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_DBERROR, null);
			return json;
		}
		json.put("brand", this.skuBrandUtils.getSkuBrandJSON(brand));
		json.put("skus", this.skuUtils.getSkuListJSON(this.skuLib
				.getByBrand(brand.getId())));
		return json;
	}

	@Override
	public JSONObject getSkuDetail(int userid, String skuNumber, int storeId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (userid <= 0) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_USER_ERROR, null);
			return json;
		}
		if (skuNumber == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_SKU_NOT_FOUND, null);
			return json;
		}
		Sku sku = null;
		if (skuNumber.startsWith("U")) {
			sku = skuLib.getSkuBySkuNumber(skuNumber);
		} else if (StringUtils.isNumeric(skuNumber)) {
			sku = this.skuLib.getById(Integer.parseInt(skuNumber));
		}
		if (sku == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_SKU_NOT_FOUND, null);
			return json;
		}
		Warehouse store = this.warehouseLib.getById(storeId);
		if (store == null || !WarehouseConstants.isStoreOrMachine(store)) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_STORE_ERROR, null);
			return json;
		}
		Inventory inv = this.inventoryLib.getBySkuWarehouse(sku.getId(),
				store.getId());
		if (inv == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_INVENTORY_NOT_FOUND,
					null);
			return json;
		}
		SkuBrand brand = this.skuBrandLib.getById(sku.getBrand());
		inv.setSku(sku);

		json.put("sku", inventoryUtils.getProductJSON(inv));
		json.put("brand", skuBrandUtils.getSkuBrandJSON(brand));
		return json;
	}

	@Override
	public JSONObject getBuyerInfo(int userid) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		Userinfo buyer = this.userinfoLib.getById(userid);
		if (buyer == null) {
			jsonUtils.setRetErrorCode(json, buyerApiErrorCodeContants,
					BuyerApiErrorCodeConstants.RET_CODE_INVENTORY_NOT_FOUND,
					null);
			return json;
		}
		List<SellOrder> orders = this.sellOrderLib.getByBuyer(userid);
		if (orders == null) {
			orders = new ArrayList<SellOrder>();
		}
		json.put("userinfo", this.userinfoUtils.getUserJSON(buyer));
		json.put("orders", this.sellOrderUtils.getSellOrderJSONArray(orders));
		json.put("points",
				this.pointsLib.getByUserid(buyer.getId()) == null ? 0
						: this.pointsLib.getByUserid(buyer.getId()).getTotal());
		return json;
	}

}
