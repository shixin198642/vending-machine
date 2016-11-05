package com.mjitech.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mjitech.model.Userinfo;

public interface LoginService {

	public JSONObject login(String username, String password, boolean rememberLogin,
			HttpServletRequest request, HttpServletResponse response);

	public JSONObject isLogin(HttpServletRequest request);

	public JSONObject logout(HttpServletRequest request,
			HttpServletResponse response);
	
	public JSONObject loginWithOpenId(String openId, boolean rememberLogin, HttpServletRequest request, HttpServletResponse response);
	
}
