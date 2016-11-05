package com.mjitech.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.model.Userinfo;

@Component
@Scope("singleton")
public class UserinfoUtils {

	public JSONObject getUserJSON(Userinfo userinfo) {
		return this.getUserJSON(userinfo, false);
	}

	public JSONObject getUserJSON(Userinfo userinfo, boolean isShowMobile) {
		JSONObject json = new JSONObject(userinfo);
		json.remove("password");
		CommonUtils.removeCommonUselessProperties(json);

		if (CommonUtils.isMobile(userinfo.getMobile())) {
			json.put("mobile", isShowMobile ? userinfo.getMobile()
					: CommonUtils.hideMobile(userinfo.getMobile()));
		}

		return json;
	}
	public JSONArray getUsersJSONArray(List<Userinfo> users, boolean isShowMobile){
		JSONArray array = new JSONArray();
		for(Userinfo user : users){
			array.put(this.getUserJSON(user, isShowMobile));
		}
		return array;
	}

}
