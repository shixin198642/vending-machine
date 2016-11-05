package com.mjitech.lib.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.CookieConstants;
import com.mjitech.constant.SessionConstants;
import com.mjitech.lib.LoginLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.model.Userinfo;
import com.mjitech.utils.CookieUtils;
import com.mjitech.utils.PasswordUtils;

@Component("loginLib")
public class LoginLibImpl implements LoginLib {
	private static Logger logger = LoggerFactory.getLogger(LoginLibImpl.class);
	@Autowired
	private CookieUtils cookieUtils;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private PasswordUtils passwordUtils;

	@Override
	public boolean isLogin(HttpServletRequest request) {
		boolean is = false;
		if (request == null) {
			return is;
		}
		Object sessionUserid = request.getSession().getAttribute(
				SessionConstants.SESSION_KEY_USER_ID);
		int userId = 0;
		if (sessionUserid != null) {
			userId = (Integer) sessionUserid;
		}
		if (userId > 0) {
			is = true;
			return is;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (CookieConstants.COOKIE_NAME_USER_TOKEN
						.equalsIgnoreCase(cookie.getName())) {
					String token = cookie.getValue();
					userId = cookieUtils.getUseridFromToken(token);
					if (userId > 0) {
						if (logger.isDebugEnabled()) {
							logger.debug("userid:" + userId + " relogin");
						}
						request.getSession().setAttribute(
								SessionConstants.SESSION_KEY_USER_ID, userId);
						is = true;
						return is;
					}
				}
			}
		}

		return is;
	}

	@Override
	public boolean checkPassword(String hashed, String password) {

		return this.passwordUtils.validatePassword(password, hashed);
	}

	@Override
	public int checkUser(String username, String password) {
		Userinfo user = this.userinfoLib.getByUsername(username);
		if (user == null) {
			return LoginLib.CHECK_USER_RETURN_NOUSERNAME;
		}
		if (!this.checkPassword(user.getPassword(), password)) {
			return LoginLib.CHECK_USER_RETURN_WRONGPASSWORD;
		}

		return LoginLib.RETURN_SUCCESS;
	}

}
