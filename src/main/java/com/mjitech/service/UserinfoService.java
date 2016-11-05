package com.mjitech.service;

import java.util.List;

import org.json.JSONObject;

import com.mjitech.model.Userinfo;

public interface UserinfoService {
	
	public JSONObject addUserWithOpenid(String openid);
	public List<Userinfo> queryUserList(Userinfo userinfo);
	public JSONObject findUserinfoDeail(int id);
}
