package com.mjitech.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mjitech.constant.WebPageConstants;
import com.mjitech.service.CommonService;
import com.mjitech.service.MenuService;
import com.mjitech.utils.RequestUtils;

@Controller
public class CommonController {
	private static final Logger logger = LoggerFactory
			.getLogger(SupplierController.class);
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private MenuService menuService;
	@Autowired
	private CommonService commonService;
	
	@ResponseBody
	@RequestMapping(value = WebPageConstants.COMMON_SEARCH, produces = WebPageConstants.JSON_PRODUCES)
	public String commonSearch(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String input = requestUtils.getStringValue(params, "input");
		return commonService.commonSearch(input).toString();
	}

}
