package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component("sellerApiErrorCodeConstants")
@Singleton
public class SellerApiErrorCodeConstants implements ReturnErrorCode{
	
	public final static int RET_CODE_NOT_SELLER = -1;
	public final static int RET_CODE_OUT_OF_STOCK = -2;
	public final static int RET_CODE_SKU_NOTFOUND = -3;
	
	
	public final static int RET_CODE_ADD_ORDER_SKU_NOTFOUND = -11;
	public final static int RET_CODE_ADD_ORDER_OUT_OF_STOCK = -12;
	public final static int RET_CODE_ADD_ORDER_DB_ERROR = -13;
	public final static int RET_CODE_ADD_ORDER_NO_SKUS = -14;
	
	public final static int RET_CODE_REQUEST_PAY_URL_DB_ERROR = -21;
	public final static int RET_CODE_REQUEST_PAY_URL_NO_AUTH = -22;
	public final static int RET_CODE_REQUEST_PAY_URL_WRONG_SELLORDER = -23;
	public final static int RET_CODE_REQUEST_PAY_URL_WX_ERROR = -24;
	
	public final static int RET_CODE_ORDER_NOAUTH = -88;
	public final static int RET_CODE_ORDER_NOTFOUND = -99;
	
	
	public final static Map<Integer, String> RET_CODES_MESSAGE_KEY_MAPPING = new HashMap<Integer, String>();
	
	static{
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_NOT_SELLER, "sellerapi.message.notseller");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_OUT_OF_STOCK, "sellerapi.message.outofstock");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SKU_NOTFOUND, "sellerapi.message.skunotfound");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ADD_ORDER_SKU_NOTFOUND, "sellerapi.message.addorder.skunotfound");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ADD_ORDER_OUT_OF_STOCK, "sellerapi.message.addorder.outofstock");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ADD_ORDER_DB_ERROR, "json.message.dberror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_REQUEST_PAY_URL_DB_ERROR, "json.message.dberror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_REQUEST_PAY_URL_NO_AUTH, "json.message.noauth");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_REQUEST_PAY_URL_WRONG_SELLORDER, "sellerapi.message.requestpayurl.wrongsellorder");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ORDER_NOAUTH, "json.message.noauth");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ORDER_NOTFOUND, "sellerapi.message.requestpayurl.wrongsellorder");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_REQUEST_PAY_URL_WX_ERROR, "sellerapi.message.requestpayurl.wxerror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ADD_ORDER_NO_SKUS, "sellerapi.message.addorder.noskus");
	}

	@Override
	public String getMessageKey(int errorCode) {
		if(RET_CODES_MESSAGE_KEY_MAPPING.containsKey(errorCode)){
			return RET_CODES_MESSAGE_KEY_MAPPING.get(errorCode);
		}
		return null;
	}
	
}
