package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component("skuErrorCodeConstants")
@Singleton
public class SkuErrorCodeConstants implements ReturnErrorCode{
	
	public final static int RET_CODE_NOAUTH = -99;
	public final static int RET_CODE_DBERROR = -88;
	
	public final static int RET_CODE_DELETE_HASINVENTORY = -1;
	
	
	public final static Map<Integer, String> RET_CODES_MESSAGE_KEY_MAPPING = new HashMap<Integer, String>();
	
	static{
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_NOAUTH, "json.message.noauth");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_DBERROR, "json.message.dberror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_DELETE_HASINVENTORY, "sku.message.delete.hasinventory");
	}

	@Override
	public String getMessageKey(int errorCode) {
		if(RET_CODES_MESSAGE_KEY_MAPPING.containsKey(errorCode)){
			return RET_CODES_MESSAGE_KEY_MAPPING.get(errorCode);
		}
		return null;
	}
	
}
