package com.mjitech.lib.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.UserinfoConstants;
import com.mjitech.lib.UserTypeLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.model.Userinfo;

@Component("userTypeLib")
public class UserTypeLibImpl implements UserTypeLib {
	@Autowired
	private UserinfoLib userinfoLib;

	@Override
	public boolean canAccessAdmin(int userId) {
		Userinfo user = this.userinfoLib.getById(userId);
		return this.canAccessAdmin(user);
	}

	@Override
	public boolean canAccessAdmin(Userinfo user) {
		if (user != null) {
			return user.getUserType() == UserinfoConstants.USER_TYPE_ADMIN;
		}
		return false;
	}

}
