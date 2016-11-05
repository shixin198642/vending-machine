package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component("supplierErrorCodeConstants")
@Singleton
public class SupplierErrorCodeConstants implements ReturnErrorCode{

	public final static int RET_CODE_SUPPLIER_ADDFAILTURE = -1;
	public final static int RET_CODE_SUPPLIER_DELETEFAILTURE = -2;
	public final static int RET_CODE_SUPPLIER_FINDFAILTURE = -3;
	public final static int RET_CODE_SUPPLIER_UPDATEFAILTURE = -4;
	public final static int RET_CODE_SUPPLIERCONTACT_NOSUPPLIER = -5;

	
	
	public final static Map<Integer, String> RET_CODES_MESSAGE_KEY_MAPPING = new HashMap<Integer, String>();
	
	static{
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SUPPLIER_ADDFAILTURE, "json.message.supplieraddfailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SUPPLIER_DELETEFAILTURE, "json.message.supplierdeltefailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SUPPLIER_FINDFAILTURE, "json.message.supplierfindfailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SUPPLIER_UPDATEFAILTURE, "json.message.supplierupdatefailture");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SUPPLIERCONTACT_NOSUPPLIER, "json.message.supplieridfinedfailture");

	}

	@Override
	public String getMessageKey(int errorCode) {
		if(RET_CODES_MESSAGE_KEY_MAPPING.containsKey(errorCode)){
			return RET_CODES_MESSAGE_KEY_MAPPING.get(errorCode);
		}
		return null;
	}
	
}
