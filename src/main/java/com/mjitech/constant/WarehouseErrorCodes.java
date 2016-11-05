package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component("warehouseErrorCodes")
@Singleton
public class WarehouseErrorCodes implements ReturnErrorCode{

	public final static int RET_CODE_WAREHOUSE_ADDFAILTURE = -1;
	public final static int RET_CODE_WAREHOUSE_DELETEFAILTURE = -2;
	public final static int RET_CODE_WAREHOUSE_GETFAILTURE = -3;
	public final static int RET_CODE_WAREHOUSE_UPDATEFAILTURE = -4;

	public final static Map<Integer, String> RET_CODES_MESSAGE_KEY_MAPPING = new HashMap<Integer, String>();
	
	static{
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_WAREHOUSE_ADDFAILTURE, "json.message.warehouseaddfailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_WAREHOUSE_DELETEFAILTURE, "json.message.warehousedeltefailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_WAREHOUSE_GETFAILTURE, "json.message.warehosuegetfailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_WAREHOUSE_UPDATEFAILTURE, "json.message.warehouseupdatefailture");
	}

	@Override
	public String getMessageKey(int errorCode) {
		if(RET_CODES_MESSAGE_KEY_MAPPING.containsKey(errorCode)){
			return RET_CODES_MESSAGE_KEY_MAPPING.get(errorCode);
		}
		return null;
	}
	
}
