package com.mjitech.lib;

import javax.servlet.http.HttpServletRequest;

public interface LoginLib {
	
	public final static int RETURN_SUCCESS = 0;
	
	public final static int CHECK_USER_RETURN_NOUSERNAME = -1;
	
	public final static int CHECK_USER_RETURN_WRONGPASSWORD = -2;
	
	public boolean isLogin(HttpServletRequest request);

	public boolean checkPassword(String hashed, String password);
	
	public int checkUser(String username, String password);
}
