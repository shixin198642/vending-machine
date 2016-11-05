package com.mjitech.service.impl;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.common.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjitech.constant.CookieConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.LoginErrorCodeConstants;
import com.mjitech.constant.SessionConstants;
import com.mjitech.constant.UserlogConstants;
import com.mjitech.lib.LoginLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.UserlogLib;
import com.mjitech.logdbmodel.Userlog;
import com.mjitech.model.Userinfo;
import com.mjitech.service.LoginService;
import com.mjitech.utils.CookieUtils;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.PasswordUtils;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;
import com.mjitech.utils.UserinfoUtils;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	private static Logger logger = LoggerFactory
			.getLogger(LoginServiceImpl.class);

	@Autowired
	private UserinfoLib userinfoLib;

	@Autowired
	private UserlogLib userlogLib;
	@Autowired
	private CookieUtils cookieUtils;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private UserinfoUtils userinfoUtils;
	@Autowired
	private LoginLib loginLib;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private LoginErrorCodeConstants loginErrorCodeContants;
	@Autowired
	private PasswordUtils passwordUtils;

	private int expireSeconds;
	@Value("${remember_login_exprie_time}")
	private int rememberLoginHours;

	private Userlog getCommonLog(int userid, HttpServletRequest request) {
		Userlog log = new Userlog();
		log.setUserId(userid);
		log.setIp(requestUtils.getRealRemoteIP(request));
		log.setSourceId(userid);
		log.setLogTime(new Date());
		log.setDescription("");
		return log;
	}

	private void addUserLoginLog(int userid, HttpServletRequest request) {
		Userlog log = this.getCommonLog(userid, request);
		log.setType(UserlogConstants.USERLOG_TYPE_LOGIN);
		this.userlogLib.addUserlog(log);
	}

	private void addUserRegisterLog(int userid, HttpServletRequest request) {
		Userlog log = this.getCommonLog(userid, request);
		log.setType(UserlogConstants.USERLOG_TYPE_REGISTRATION);
		this.userlogLib.addUserlog(log);
	}

	private void addUserLogoutLog(int userid, HttpServletRequest request) {
		Userlog log = this.getCommonLog(userid, request);
		log.setType(UserlogConstants.USERLOG_TYPE_LOGOUT);
		this.userlogLib.addUserlog(log);
	}

	@Transactional
	@Override
	public JSONObject login(String username, String password,
			boolean rememberLogin, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);

		switch (this.loginLib.checkUser(username, password)) {
		case LoginLib.CHECK_USER_RETURN_NOUSERNAME:
			jsonUtils.setRetErrorCode(ret, loginErrorCodeContants,
					LoginErrorCodeConstants.RET_CODE_USER_NOT_EXIST, null);
			return ret;
		case LoginLib.CHECK_USER_RETURN_WRONGPASSWORD:
			jsonUtils.setRetErrorCode(ret, loginErrorCodeContants,
					LoginErrorCodeConstants.RET_CODE_USER_NOT_EXIST, null);
			return ret;
		}
		Userinfo user = this.loginRoutine(username, rememberLogin, request,
				response);
		this.addUserLoginLog(user.getId(), request);
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		ret.put("userinfo", userinfoUtils.getUserJSON(user));
		return ret;
	}

	private Userinfo loginRoutine(String username, boolean rememberLogin,
			HttpServletRequest request, HttpServletResponse response) {
		Userinfo user = this.userinfoLib.getByUsername(username);
		request.getSession().setAttribute(SessionConstants.SESSION_KEY_USER_ID,
				user.getId());
		if (rememberLogin) {
			// set cookie here
			Cookie cookie = new Cookie(CookieConstants.COOKIE_NAME_USER_TOKEN,
					cookieUtils.getTokenFromUserid(user.getId()));
			cookie.setMaxAge(rememberLoginHours * 3600);
			response.addCookie(cookie);
		}
		// set username cookie here
		Cookie cookie = new Cookie(CookieConstants.COOKIE_NAME_USERNAME,
				username);
		cookie.setMaxAge(rememberLoginHours * 3600);
		response.addCookie(cookie);
		return user;
	}

	@Override
	public JSONObject isLogin(HttpServletRequest request) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		ret.put(JSONConstants.RETURN_KEY_IS_LOGIN, false);
		boolean isLogin = this.loginLib.isLogin(request);
		if (isLogin) {
			ret.put(JSONConstants.RETURN_KEY_IS_LOGIN, true);
			int userid = sessionUtils.getUserid(request);
			ret.put("userinfo",
					userinfoUtils.getUserJSON(userinfoLib.getById(userid)));
		}
		return ret;
	}

	@Override
	public JSONObject logout(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		Integer userid = (Integer) request.getSession().getAttribute(
				SessionConstants.SESSION_KEY_USER_ID);
		if (userid != null && userid > 0) {
			request.getSession().removeAttribute(
					SessionConstants.SESSION_KEY_USER_ID);
			this.addUserLogoutLog(userid, request);
		}

		// remove cookie here
		Cookie cookie = new Cookie(CookieConstants.COOKIE_NAME_USER_TOKEN, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);

		return ret;
	}

	@Override
	public JSONObject loginWithOpenId(String openId, boolean rememberLogin,
			HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (StringUtils.isEmpty(openId)) {
			jsonUtils.setRetErrorCode(json, loginErrorCodeContants,
					LoginErrorCodeConstants.RET_CODE_OPENID_NOT_VALID, null);
			return json;
		}
		Userinfo user = this.userinfoLib.getByOpenId(openId);
		if (user == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("openid:" + openId + " not found");
			}
			jsonUtils.setRetErrorCode(json, loginErrorCodeContants,
					LoginErrorCodeConstants.RET_CODE_OPENID_NOT_VALID, null);
			return json;
		}
		this.loginRoutine(user.getUsername(), rememberLogin, request, response);
		json.put("userinfo", userinfoUtils.getUserJSON(user));
		return json;
	}

}
