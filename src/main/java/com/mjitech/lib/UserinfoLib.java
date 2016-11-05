package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Userinfo;

public interface UserinfoLib {

	public Userinfo addUser(Userinfo userinfo);

	public Userinfo getByUsername(String username);

	public int updateById(Userinfo userinfo);

	public Userinfo getById(int id);

	public Userinfo getByOpenId(String openId);

	public List<Userinfo> getUserinfoList(Userinfo userinfo);
}
