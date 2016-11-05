package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component("loginErrorCodeConstants")
@Singleton
public class LoginErrorCodeConstants implements ReturnErrorCode{
	
	public final static int RET_CODE_USER_NOT_EXIST = -1;
	public final static int RET_CODE_PASSWORD_NOT_VALID = -1;
	public final static int RET_CODE_OPENID_NOT_VALID = -3;
	
	public final static Map<Integer, String> RET_CODES_MESSAGE_KEY_MAPPING = new HashMap<Integer, String>();
	
	static{
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_USER_NOT_EXIST, "json.message.usernotexist");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_PASSWORD_NOT_VALID, "json.message.wrongpassword");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_OPENID_NOT_VALID, "json.message.openidnotvalid");
	}

	@Override
	public String getMessageKey(int errorCode) {
		if(RET_CODES_MESSAGE_KEY_MAPPING.containsKey(errorCode)){
			return RET_CODES_MESSAGE_KEY_MAPPING.get(errorCode);
		}
		return null;
	}
	
}
