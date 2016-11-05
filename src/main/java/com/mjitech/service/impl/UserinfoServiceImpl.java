package com.mjitech.service.impl;

import java.util.Date;
import java.util.List;

import org.elasticsearch.common.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.CommonConstants;
import com.mjitech.constant.ErrorCodeConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.UserinfoConstants;
import com.mjitech.constant.UserinfoErrorCodeConstants;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.model.Userinfo;
import com.mjitech.service.UserinfoService;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.PasswordUtils;
import com.mjitech.utils.UserinfoUtils;

@Component("userinfoService")
public class UserinfoServiceImpl implements UserinfoService {
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private UserinfoErrorCodeConstants userinfoErrorCodeConstants;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private PasswordUtils passwordUtils;
	@Autowired
	private UserinfoUtils userinfoUtils;

	@Override
	public JSONObject addUserWithOpenid(String openid) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		if (StringUtils.isEmpty(openid)) {
			jsonUtils.setRetErrorCode(json, userinfoErrorCodeConstants,
					UserinfoErrorCodeConstants.RET_CODE_ADD_WRONGPARAM,
					new Object[] { "openid" });
			return json;
		}
		Userinfo user = this.userinfoLib.getByOpenId(openid);
		if (user != null) {
			jsonUtils.setRetErrorCode(json, userinfoErrorCodeConstants,
					UserinfoErrorCodeConstants.RET_CODE_ADD_OPENIDEXISTED,
					new Object[] { "openid:" + openid });
			return json;
		}
		user = new Userinfo();
		user.setUsername(openid);
		user.setOpenId(openid);
		user.setPassword(passwordUtils
				.hashPassword(UserinfoConstants.DEFAULT_PASSWOD));
		user.setUserType(UserinfoConstants.USER_TYPE_USER);
		user.setDisplayName("wxuser_" + openid);
		user.setEmail(openid + "@mjitech.com");
		user.setGender(CommonConstants.GENDER_UNKNOW);
		user.setWxBindTime(new Date());
		user.setImage("");
		user = this.userinfoLib.addUser(user);
		if (user.getId() <= 0) {
			jsonUtils.setRetErrorCode(json, userinfoErrorCodeConstants,
					UserinfoErrorCodeConstants.RET_CODE_DBERROR, null);
			return json;
		}
		json.put("userinfo", userinfoUtils.getUserJSON(user));
		return json;
	}

	@Override
	public List<Userinfo> queryUserList(Userinfo userinfo) {
		return this.userinfoLib.getUserinfoList(userinfo);
	}

	@Override
	public JSONObject findUserinfoDeail(int id) {
		Userinfo s = userinfoLib.getById(id);
		JSONObject ret = new JSONObject();
		if(s!=null){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			ret.put(JSONConstants.RETURN_KEY_RESULT, userinfoUtils.getUserJSON(s));
		}else{
			jsonUtils.setRetErrorCode(ret, userinfoErrorCodeConstants,
					ErrorCodeConstants.RET_CODE_FINDFAILTURE, null);
		}
		return ret;
	}

}
