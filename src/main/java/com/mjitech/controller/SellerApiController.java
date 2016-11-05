package com.mjitech.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.LoginLib;
import com.mjitech.service.LoginService;
import com.mjitech.service.apiservice.CommonApiService;
import com.mjitech.service.apiservice.SellerApiService;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;

@Controller
public class SellerApiController {

	private static Logger logger = LoggerFactory
			.getLogger(SellerApiController.class);
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SellerApiService sellerApiService;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private LoginService loginService;
	@Autowired
	private LoginLib loginLib;
	@Autowired
	private CommonApiService commonApiService;

	@RequestMapping(value = WebPageConstants.SELLER_BIND_ACCOUNT, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String bindWXAccount(@RequestBody Map<String, String> params,
			HttpServletRequest request) {

		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter bindWXAccount");
		}
		String openid = this.requestUtils.getStringValue(params, "openid");
		String username = this.requestUtils.getStringValue(params, "username");
		String password = this.requestUtils.getStringValue(params, "password");

		if (logger.isTraceEnabled()) {
			logger.trace("exit addOrder");
		}
		return retJson;
	}

	private String readOpenId(Map<String, String> params, JSONObject bodyJson) {
		String openId = this.requestUtils.getStringValue(params, "open_id");
		if (StringUtils.isEmpty(openId) && bodyJson != null
				&& bodyJson.has("open_id")) {
			openId = bodyJson.getString("open_id");
		}
		// openId = "o41Mgv5qXayH9P9C6IGYhN1Ujz3g";
		return openId;
	}

	@RequestMapping(value = WebPageConstants.SELLER_GET_SKU_DETAIL, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String getSkuDetail(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {

		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter getSkuDetail");
		}
		JSONObject bodyJson = null;
		String skuNumber = this.requestUtils.getStringValue(params,
				"sku_number");

		String body = requestUtils.readBody(request);
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(skuNumber) && bodyJson.has("sku_number")) {
				skuNumber = bodyJson.getString("sku_number");
			}
		}
		String openId = this.readOpenId(params, bodyJson);
		if (StringUtils.isNotEmpty(openId) && !this.loginLib.isLogin(request)) {
			this.loginService.loginWithOpenId(openId, false, request, response);
		}
		retJson = this.sellerApiService.getSkuDetail(skuNumber,
				sessionUtils.getUserid(request)).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit getSkuDetail");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.SELLER_TEST_OPENID, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String testOpenId(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter testOpenId");
		}
		String openId = requestUtils.getStringValue(params, "open_id");
		String body = requestUtils.readBody(request);
		if (StringUtils.isNotEmpty(body)) {
			JSONObject bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(openId) && bodyJson.has("open_id")) {
				openId = bodyJson.getString("open_id");
			}
		}
		retJson = this.loginService.loginWithOpenId(openId, false, request,
				response).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit testOpenId");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.SELLER_ADD_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String addOrder(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter addOrder");
		}

		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		Map<Integer, Integer> cartMap = new HashMap<Integer, Integer>();
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (bodyJson.has("cart")) {
				JSONArray cart = bodyJson.getJSONArray("cart");
				for (int i = 0; i < cart.length(); i++) {
					JSONObject cartItem = cart.getJSONObject(i);
					cartMap.put(cartItem.getInt("sku_id"),
							cartItem.getInt("count"));
				}
			}

		}
		if (logger.isInfoEnabled()) {
			logger.info("cart:" + cartMap);
		}
		String openId = this.readOpenId(params, bodyJson);
		if (StringUtils.isNotEmpty(openId) && !this.loginLib.isLogin(request)) {
			this.loginService.loginWithOpenId(openId, false, request, response);
		}
		int userId = sessionUtils.getUserid(request);
		retJson = this.sellerApiService.addOrder(cartMap, userId).toString();
		if (logger.isInfoEnabled()) {
			logger.info(retJson);
		}
		if (logger.isTraceEnabled()) {
			logger.trace("exit addOrder");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.SELLER_ORDER_DETAIL, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String orderDetail(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter orderDetail");
		}

		String orderNumber = requestUtils
				.getStringValue(params, "order_number");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(orderNumber)) {
				if (bodyJson.has("order_number")) {
					orderNumber = bodyJson.getString("order_number");
				}
			}
		}
		String openId = this.readOpenId(params, bodyJson);
		if (StringUtils.isNotEmpty(openId) && !this.loginLib.isLogin(request)) {
			this.loginService.loginWithOpenId(openId, false, request, response);
		}
		int userId = sessionUtils.getUserid(request);
		retJson = this.sellerApiService.orderDetail(orderNumber, userId)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit orderDetail");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.SELLER_ORDER_STATUS, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String orderStatus(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter orderStatus");
		}

		String orderNumber = requestUtils
				.getStringValue(params, "order_number");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(orderNumber)) {
				if (bodyJson.has("order_number")) {
					orderNumber = bodyJson.getString("order_number");
				}
			}
		}
		String openId = this.readOpenId(params, bodyJson);
		if (StringUtils.isNotEmpty(openId) && !this.loginLib.isLogin(request)) {
			this.loginService.loginWithOpenId(openId, false, request, response);
		}
		int userId = sessionUtils.getUserid(request);
		retJson = this.sellerApiService.orderStatus(orderNumber, userId)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit orderStatus");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.SELLER_ORDER_LIST, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String orderList(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter orderList");
		}

		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);

		}
		String openId = this.readOpenId(params, bodyJson);
		if (StringUtils.isNotEmpty(openId) && !this.loginLib.isLogin(request)) {
			this.loginService.loginWithOpenId(openId, false, request, response);
		}
		int userId = sessionUtils.getUserid(request);
		retJson = this.sellerApiService.orderList(userId).toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit orderList");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.SELLER_REQUEST_PAY, produces = WebPageConstants.JSON_PRODUCES)
	@ResponseBody
	public String requestPay(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String retJson = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter requestPay");
		}
		String orderNumber = requestUtils
				.getStringValue(params, "order_number");
		String body = requestUtils.readBody(request);
		JSONObject bodyJson = null;
		if (StringUtils.isNotEmpty(body)) {
			bodyJson = new JSONObject(body);
			if (StringUtils.isEmpty(orderNumber)
					&& bodyJson.has("order_number")) {
				orderNumber = bodyJson.getString("order_number");
			}

		}
		String openId = this.readOpenId(params, bodyJson);
		if (StringUtils.isNotEmpty(openId) && !this.loginLib.isLogin(request)) {
			this.loginService.loginWithOpenId(openId, false, request, response);
		}
		int userId = sessionUtils.getUserid(request);
		retJson = this.sellerApiService.requestPayUrl(orderNumber, userId)
				.toString();

		if (logger.isTraceEnabled()) {
			logger.trace("exit requestPay");
		}
		return retJson;
	}

	@RequestMapping(value = WebPageConstants.WXPAY_CALLBACK, produces = WebPageConstants.XML_PRODUCES)
	@ResponseBody
	public String wxpayCallback(HttpServletRequest request,
			HttpServletResponse response) {
		String retXML = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter wxpayCallback");
		}

		String body = requestUtils.readBody(request);
		retXML = this.commonApiService.wxpayCallback(body);

		if (logger.isTraceEnabled()) {
			logger.trace("exit wxpayCallback");
		}
		return retXML;
	}

}
