package com.mjitech.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.LoginLib;
import com.mjitech.lib.UserTypeLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WxLib;
import com.mjitech.model.Userinfo;
import com.mjitech.utils.SessionUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory
			.getLogger(LoginInterceptor.class);
	@Autowired
	private LoginLib loginLib;
	@Autowired
	private WxLib wxLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private UserTypeLib userTypeLib;

	private String getActionURL(String uri, String contextPath) {
		String action = uri.replace(contextPath, "");
		action = action.replace(WebPageConstants.COMMON_PREFIX, "");
		return action;
	}

	private boolean needLogin(String action) {
		boolean is = true;
		if (WebPageConstants.URLS_NONEED_LOGIN.contains(action)) {
			is = false;
		}
		else if (action.indexOf("serversocket") != -1) {
			is = false;
		}
		else if (action.indexOf("buyer_api/")!=-1){
			is = false;
		}
		return is;
	}

	private boolean needAdmin(String action) {
		boolean is = false;
		if (WebPageConstants.ADMIN_URLS.contains(action)) {
			is = true;
		}
		return is;
	}

	private String getWxLoginUrl() {
		String url = new StringBuilder(WebPageConstants.BASE_URL)
				.append(WebPageConstants.COMMON_PREFIX)
				.append(WebPageConstants.WEIXIN_AUTHORIZEPAGE).toString();
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return new StringBuilder(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
				.append(this.wxLib.getAppid())
				.append("&redirect_uri=")
				.append(url)
				.append("&response_type=code&scope=snsapi_base&state=relogin#wechat_redirect")
				.toString();
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestUri = request.getRequestURI();
		String action = this.getActionURL(requestUri, request.getContextPath());
		if (this.needLogin(action)) {
			boolean isLogin = this.loginLib.isLogin(request);
			if (!isLogin) {
				if (requestUri.indexOf("/seller_api") != -1
						|| requestUri.indexOf("/wx") != -1) {
					// wx return json
					response.setContentType("application/json; charset=UTF-8");
					JSONObject json = new JSONObject();
					json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
					json.put(JSONConstants.RETURN_KEY_NEED_LOGIN, true);
					json.put(JSONConstants.RETURN_KEY_LOGIN_URL,
							this.getWxLoginUrl());
					response.getWriter().println(json.toString());

					return false;
				} else {
					StringBuilder location = new StringBuilder(
							request.getContextPath()).append(
							WebPageConstants.COMMON_PREFIX).append(
							WebPageConstants.LOGIN_PAGE);
					response.sendRedirect(location.toString());
					return false;
				}
			}

			if (this.needAdmin(action)
					&& !this.userTypeLib.canAccessAdmin(sessionUtils
							.getUserid(request))) {
				StringBuilder location = new StringBuilder(
						request.getContextPath())
						.append(WebPageConstants.COMMON_NOAUTH_PAGE);
				response.sendRedirect(location.toString());
				return false;
			}

		}
		return true;
	}
}
