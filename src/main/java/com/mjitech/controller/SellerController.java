package com.mjitech.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mjitech.constant.WebPageConstants;
import com.mjitech.service.MenuService;
import com.mjitech.service.SellerService;
import com.mjitech.utils.RequestUtils;

@Controller
public class SellerController {

	private static final Logger logger = LoggerFactory
			.getLogger(SellerController.class);	
	@Autowired
	private SellerService sellerService;
	@Autowired
	private MenuService menuService;	
	@Autowired
	private RequestUtils requestUtils;
	
	@RequestMapping(value = WebPageConstants.SELLER_SELL, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String sell(@RequestBody Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) {
		int skuId = requestUtils.getIntValue(params, "skuId");
		int skuSpecId = requestUtils.getIntValue(params, "skuSpecId");
		int quantity = requestUtils.getIntValue(params, "quantity");
		
		if (logger.isTraceEnabled()) {
			logger.trace("enter delete warehouse-id:" + skuId);
		}
		JSONObject retJson = sellerService.sell(skuId, skuSpecId, quantity);
		// If inventory less than safe quantity, we will generate a request automatically.
		
		if (logger.isTraceEnabled()) {
			logger.trace("exit delete warehouse-id:" + skuId);
		}
		String ret = retJson.toString();
		return ret;		
	}	

	/**
	 * Manually create a SKU request. The request number should less than the MAX quantity.
	 */
	@RequestMapping(value = WebPageConstants.SELLER_REQUEST, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String request(@RequestBody Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) {
		
		return "";
	}
	
}
