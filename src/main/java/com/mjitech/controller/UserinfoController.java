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
import com.mjitech.service.UserinfoService;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;

@Controller
public class UserinfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserinfoController.class);
	
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SessionUtils sessionUtils;

	/**
	 * 根据ID查询用户信息
	 */
	@RequestMapping(value = WebPageConstants.FIND_USERINFO, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String find(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail userinfo-id:" + id);
		}
		JSONObject retJson = this.userinfoService.findUserinfoDeail(Integer.parseInt(id));;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail userinfo-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
}
