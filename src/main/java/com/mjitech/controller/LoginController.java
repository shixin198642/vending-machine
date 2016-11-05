package com.mjitech.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.CookieConstants;
import com.mjitech.constant.ViewConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.LoginLib;
import com.mjitech.service.LoginService;
import com.mjitech.utils.CookieUtils;
import com.mjitech.utils.RequestUtils;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private LoginLib loginLib;
	@Autowired
	private CookieUtils cookieUtils;

	@RequestMapping(value = WebPageConstants.LOGIN_PAGE)
	public ModelAndView loginPage(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		if (this.loginLib.isLogin(request)) {
			mv.setViewName("redirect:" + WebPageConstants.COMMON_PREFIX
					+ "/"+WebPageConstants.SKU_LIST);
		} else {
			mv.addObject("username", cookieUtils.getCookieValue(request,
					CookieConstants.COOKIE_NAME_USERNAME));
			mv.setViewName(ViewConstants.LOGIN_VIEW);
		}
		return mv;
	}

	@RequestMapping(value = WebPageConstants.LOGIN, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String login(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		String username = requestUtils.getStringValue(params, "username");
		String password = requestUtils.getStringValue(params, "password");
		boolean rememberLogin = true;
		String rememberLoginStr = (String) params.get("remember_login");
		if (StringUtils.isNotEmpty(rememberLoginStr)
				&& rememberLoginStr.toLowerCase().startsWith("n")) {
			rememberLogin = false;
		}
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter login:" + username);
		}
		JSONObject retJson = this.loginService.login(username, password,
				rememberLogin, request, response);
		ret = retJson.toString();
		if (logger.isTraceEnabled()) {
			logger.trace("exit login:" + retJson);
		}
		return ret;
	}

	@RequestMapping(value = WebPageConstants.LOGOUT)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		this.loginService.logout(request, response);

		return "redirect:/";
	}
}
