package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component("inventoryErrorCodeConstants")
@Singleton
public class InventoryErrorCodeConstants implements ReturnErrorCode{
	
	public final static int RET_CODE_DB_ERROR = -88;
	public final static int RET_CODE_NOAUTH = -99;
	public final static int RET_CODE_WRONG_PARAM = -77;
	
	
	public final static int RET_CODE_NEW_INVENTORY_OLD_EXISTED = -1;
	
	
	public final static Map<Integer, String> RET_CODES_MESSAGE_KEY_MAPPING = new HashMap<Integer, String>();
	
	static{
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_DB_ERROR, "inventory.message.dberror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_NOAUTH, "inventory.message.noauth");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_WRONG_PARAM, "inventory.message.wrongparam");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_NEW_INVENTORY_OLD_EXISTED, "inventory.message.newinventory.oldexisted");
	}

	@Override
	public String getMessageKey(int errorCode) {
		if(RET_CODES_MESSAGE_KEY_MAPPING.containsKey(errorCode)){
			return RET_CODES_MESSAGE_KEY_MAPPING.get(errorCode);
		}
		return null;
	}
	
}
