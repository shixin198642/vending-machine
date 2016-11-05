package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;

@Component("buyerApiErrorCodeConstants")
@Singleton
public class BuyerApiErrorCodeConstants implements ReturnErrorCode{
	
	public final static int RET_CODE_STORE_ERROR = -1;
	
	public final static int RET_CODE_NO_LOGIN = -99;
	
	public final static int RET_CODE_ADD_CART_NO_INVENTORY = -2;
	public final static int RET_CODE_ADD_CART_BEYOND_QUANTITY = -3;
	
	public final static int RET_CODE_SUBMIT_CART_NO_CART = -4;
	public final static int RET_CODE_SUBMIT_CART_INVENTORY_NOT_ENOUGH = -5;
	
	public final static int RET_CODE_ORDER_NOT_FOUND = -6;
	
	public final static int RET_CODE_SKU_NOT_FOUND = -7;
	
	public final static int RET_CODE_INVENTORY_NOT_FOUND = -8;
	
	public final static int RET_CODE_USER_ERROR = -88;
	
	public final static int RET_CODE_REQUEST_PAY_NOT_BUYER = -9;
	public final static int RET_CODE_REQUEST_PAY_WXERROR = -10;
	
	public final static int RET_CODE_MYINFO_USERERROR = -11;
	
	public final static int RET_CODE_DBERROR = -77;
	
	public final static int RET_CODE_REQUEST_PAY_WRONGSTATUS = -12;
	
	public final static Map<Integer, String> RET_CODES_MESSAGE_KEY_MAPPING = new HashMap<Integer, String>();
	
	static{
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_STORE_ERROR, "buyerapi.message.storeerror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ADD_CART_NO_INVENTORY, "buyerapi.message.addcart.noinventory");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ADD_CART_BEYOND_QUANTITY, "buyerapi.message.addcart.beyondquantity");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SUBMIT_CART_NO_CART, "buyerapi.message.submitcart.nocart");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SUBMIT_CART_INVENTORY_NOT_ENOUGH, "buyerapi.message.submitcart.inventorynotenough");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_NO_LOGIN, "buyerapi.message.nologin");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_USER_ERROR, "buyerapi.message.usererror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_DBERROR, "buyerapi.message.dberror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_REQUEST_PAY_NOT_BUYER, "buyerapi.message.requestbuy.notbuyer");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_REQUEST_PAY_WXERROR, "buyerapi.message.requestbuy.wxerror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_ORDER_NOT_FOUND, "buyerapi.message.order.notfound");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_SKU_NOT_FOUND, "buyerapi.message.sku.notfound");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_INVENTORY_NOT_FOUND, "buyerapi.message.inventory.notfound");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_MYINFO_USERERROR, "buyerapi.message.myinfo.usererror");
		RET_CODES_MESSAGE_KEY_MAPPING.put(RET_CODE_REQUEST_PAY_WRONGSTATUS,"buyerapi.message.requestbuy.wrongstatus");
	}

	@Override
	public String getMessageKey(int errorCode) {
		if(RET_CODES_MESSAGE_KEY_MAPPING.containsKey(errorCode)){
			return RET_CODES_MESSAGE_KEY_MAPPING.get(errorCode);
		}
		return null;
	}
	
}
