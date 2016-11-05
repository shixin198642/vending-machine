package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component("errorCodeConstants")
@Singleton
public class ErrorCodeConstants implements ReturnErrorCode{
	
	public final static int RET_CODE_ADDFAILTURE = -1;
	public final static int RET_CODE_DELETEFAILTURE = -2;
	public final static int RET_CODE_FINDFAILTURE = -3;
	public final static int RET_CODE_UPDATEFAILTURE = -4;
	public final static int RET_CODE_NODATA = -5;

public final static Map<Integer, String> RET_CODES_MESSAGE_KEY_MAPPING = new HashMap<Integer, String>();
	
	static{
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ADDFAILTURE, "json.message.addfailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_DELETEFAILTURE, "json.message.deltefailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_FINDFAILTURE, "json.message.findfailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_UPDATEFAILTURE, "json.message.updatefailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_NODATA, "json.message.idfinedfailture");

	}

	@Override
	public String getMessageKey(int errorCode) {
		if(RET_CODES_MESSAGE_KEY_MAPPING.containsKey(errorCode)){
			return RET_CODES_MESSAGE_KEY_MAPPING.get(errorCode);
		}
		return null;
	}
}
