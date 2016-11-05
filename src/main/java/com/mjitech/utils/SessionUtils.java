package com.mjitech.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.constant.SessionConstants;
import com.mjitech.lib.LoginLib;

@Component
@Scope("singleton")
public class SessionUtils {
	@Autowired
	private LoginLib loginLib;
	
	public int getUserid(HttpServletRequest request) {
		int userid = 0;
		if(loginLib.isLogin(request)){
			userid = this.getUserid(request.getSession());
		}
		return userid;
	}

	private int getUserid(HttpSession session) {
		int userid = 0;
		if (session != null) {
			Object sessionUserid = session
					.getAttribute(SessionConstants.SESSION_KEY_USER_ID);
			if (sessionUserid != null) {
				userid = (Integer) sessionUserid;
			}
		}
		return userid;
	}

}
