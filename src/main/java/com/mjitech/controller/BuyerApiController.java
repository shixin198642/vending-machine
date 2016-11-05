package com.mjitech.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.LoginLib;
import com.mjitech.service.LoginService;
import com.mjitech.service.UserinfoService;
import com.mjitech.service.apiservice.BuyerApiService;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;

@Controller
public class BuyerApiController {

	private static Logger logger = LoggerFactory
			.getLogger(BuyerApiController.class);
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private LoginService loginService;
	@Autowired
	private LoginLib loginLib;
	@Autowired
	private BuyerApiService buyerApiService;
	@Autowired
	private UserinfoService userinfoService;

	@RequestMapping(value = WebPageConstants.BUYER_MAINPAGE_DATA, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String mainpageData(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter mainpageData");
		}

		int userid = sessionUtils.getUserid(request);
		int storeId = requestUtils.getIntValue(params, "storeId");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (storeId == 0) {
				storeId = bodyJson.getInt("storeId");
			}
		}
		retJson = this.buyerApiService.getMainpageData(userid, storeId)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit mainpageData");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_MAINPAGE_DATA_BY_GEO, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String mainpageDataByGeo(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter mainpageDataByGeo");
		}

		int userid = sessionUtils.getUserid(request);
		long longitude = requestUtils.getLongValue(params, "longitude");
		long latitude = requestUtils.getLongValue(params, "latitude");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (longitude == 0) {
				longitude = bodyJson.getLong("longitude");
			}
			if (latitude == 0) {
				latitude = bodyJson.getLong("latitude");
			}
		}
		retJson = this.buyerApiService.getMainpageDataByGeo(userid, longitude,
				latitude).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit mainpageDataByGeo");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_GET_ALL_STORES, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String getAllStores(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter getAllStores");
		}

		int userid = sessionUtils.getUserid(request);
		retJson = this.buyerApiService.getStoreList(userid).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit getAllStores");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_ADD_SKU_TO_CART, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String addSkuToCart(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter addSkuToCart");
		}

		int userid = sessionUtils.getUserid(request);
		int storeId = requestUtils.getIntValue(params, "storeId");
		String skuId = requestUtils.getStringValue(params, "skuId");
		int count = requestUtils.getIntValue(params, "count");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (storeId == 0) {
				storeId = bodyJson.getInt("storeId");
			}
			if (StringUtils.isEmpty(skuId)) {
				skuId = bodyJson.getString("skuId");
			}
			if (count == 0) {
				count = bodyJson.getInt("count");
			}
		}
		retJson = this.buyerApiService.addSkuToCart(userid, storeId, skuId,
				count).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit addSkuToCart");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_GET_CART, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String getStoreCart(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter getStoreCart");
		}

		int userid = sessionUtils.getUserid(request);
		retJson = this.buyerApiService.getStoreCarts(userid).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit getStoreCart");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_REMOVE_SKU_FROM_CART, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String removeSkuFromCart(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter removeSkuFromCart");
		}

		int userid = sessionUtils.getUserid(request);
		int storeId = requestUtils.getIntValue(params, "storeId");
		int skuId = requestUtils.getIntValue(params, "skuId");
		int count = requestUtils.getIntValue(params, "count");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (storeId == 0) {
				storeId = bodyJson.getInt("storeId");
			}
			if (skuId == 0) {
				skuId = bodyJson.getInt("skuId");
			}
			if (count == 0) {
				count = bodyJson.getInt("count");
			}
		}
		retJson = this.buyerApiService.removeSkuFromCart(userid, storeId,
				skuId, count).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit removeSkuFromCart");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_CLEAR_CART, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String clearCart(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter clearCart");
		}

		int userid = sessionUtils.getUserid(request);
		int storeId = requestUtils.getIntValue(params, "storeId");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (storeId == 0) {
				storeId = bodyJson.getInt("storeId");
			}
		}
		retJson = this.buyerApiService.clearStoreCart(userid, storeId)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit clearCart");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_TEST_LOING_WITH_OPENID, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String testLoginWithOpenid(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter testLoginWithOpenid");
		}

		int userid = sessionUtils.getUserid(request);
		String openid = requestUtils.getStringValue(params, "openid");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(openid) && bodyJson.has("openid")) {
				openid = bodyJson.getString("openid");
			}
		}
		JSONObject userinfoRet = this.userinfoService.addUserWithOpenid(openid);
		retJson = this.loginService.loginWithOpenId(openid, false, request,
				response).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit testLoginWithOpenid");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_SUBMIT_CART, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String submitCart(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter submitCart");
		}

		int userid = sessionUtils.getUserid(request);
		int storeId = requestUtils.getIntValue(params, "storeId");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (storeId == 0) {
				storeId = bodyJson.getInt("storeId");
			}
		}
		retJson = this.buyerApiService.submitStoreCart(userid, storeId)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit submitCart");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_SUBMIT_CARTS, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String submitCarts(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter submitCart");
		}

		int userid = sessionUtils.getUserid(request);
		List<Integer> storeIds = null;

		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (storeIds == null) {
				storeIds = new ArrayList<Integer>();
				JSONArray array = bodyJson.getJSONArray("storeIds");
				for (int i = 0; i < array.length(); i++) {
					storeIds.add(array.getInt(i));
				}
			}
		}
		retJson = this.buyerApiService.submitStoreCarts(userid, storeIds)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit submitCart");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_GET_JSAPI_PARAMS, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String getJSAPIParams(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter getJSAPIParams");
		}

		int userid = sessionUtils.getUserid(request);
		String url = requestUtils.getStringValue(params, "url");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(url)) {
				url = bodyJson.getString("url");
			}
		}
		retJson = this.buyerApiService.getJSAPIParams(userid, url).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit getJSAPIParams");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_REQUEST_PAY, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String requestPayParams(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter requestPayParams");
		}

		int userid = sessionUtils.getUserid(request);
		String orderNumber = requestUtils
				.getStringValue(params, "order_number");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(orderNumber)) {
				orderNumber = bodyJson.getString("order_number");
			}
		}
		retJson = this.buyerApiService.requestJSPayParams(userid, orderNumber)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit requestPayParams");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_GET_MY_INFO, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String getMyInfo(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter getMyInfo");
		}

		int userid = sessionUtils.getUserid(request);

		retJson = this.buyerApiService.getBuyerInfo(userid).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit getMyInfo");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_ORDER_LIST, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String orderList(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter orderList");
		}

		int userid = sessionUtils.getUserid(request);
		int storeId = requestUtils.getIntValue(params, "storeId");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (storeId == 0) {
				storeId = bodyJson.getInt("storeId");
			}
		}
		retJson = this.buyerApiService.getOrderList(userid, storeId).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit orderList");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_ORDER_DETAIL, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String orderDetail(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter orderDetail");
		}

		int userid = sessionUtils.getUserid(request);
		String orderNumber = requestUtils
				.getStringValue(params, "order_number");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(orderNumber)) {
				orderNumber = bodyJson.getString("order_number");
			}
		}
		retJson = this.buyerApiService.getOrderDetail(userid, orderNumber)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit orderDetail");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_SKU_DETAIL, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String skuDetail(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter skuDetail");
		}

		int userid = sessionUtils.getUserid(request);
		String skuNumber = requestUtils.getStringValue(params, "sku_number");
		int storeId = requestUtils.getIntValue(params, "storeId");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(skuNumber)) {
				skuNumber = bodyJson.getString("sku_number");
			}
			if (storeId == 0) {
				storeId = bodyJson.getInt("storeId");
			}
		}
		retJson = this.buyerApiService.getSkuDetail(userid, skuNumber, storeId)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit skuDetail");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.BUYER_BRAND_DETAIL, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String brandDetail(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter brandDetail");
		}

		int userid = sessionUtils.getUserid(request);
		int brandId = requestUtils.getIntValue(params, "brandId");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (brandId == 0) {
				brandId = bodyJson.getInt("brandId");
			}
		}
		retJson = this.buyerApiService.getBrandDetail(userid, brandId)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit brandDetail");
		}
		return retJson;
	}
}
