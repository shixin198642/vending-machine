package com.mjitech.lib;

import com.mjitech.model.Userinfo;

public interface UserTypeLib {
	
	public boolean canAccessAdmin(int userId);
	public boolean canAccessAdmin(Userinfo user);
	
}
