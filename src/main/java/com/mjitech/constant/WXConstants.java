package com.mjitech.constant;

import java.util.HashSet;
import java.util.Set;

public class WXConstants {
	
	public final static String APPID = "wx4da5ecd6305e620a";
	public final static String SECRET = "15a2f5a94b4c862cd9e8eff865d11eb9";
	
	
	public final static String CHECKIN_URL = "/seller_checkin/index.html";
	
	public final static String ORDERLIST_URL = "/seller_orderlist/index.html";
	
	public final static String COMMON_URL = "/introduction/index.html";
	
	public final static String BUYER_HOMEPAGE = "/buyer_home/index.html";
	
	
	public final static int AUTOREPLY_TYPE_SUBSCRIBE = 1;
	
	public final static int AUTOREPLY_MESSAGE_ENABLED = 1;
	
	public final static int AUTOREPLY_MESSAGE_DISABLED = 2;
	
	public final static String WX_PAYTYPE_NATIVE = "NATIVE";
	public final static String WX_PAYTYPE_JSAPI = "JSAPI";
	public final static String WX_PAYTYPE_APP = "APP";
	
	public static Set<String> WX_PAYTYPES = new HashSet<String>();
	static{
		WX_PAYTYPES.add(WX_PAYTYPE_NATIVE);
		WX_PAYTYPES.add(WX_PAYTYPE_JSAPI);
		WX_PAYTYPES.add(WX_PAYTYPE_APP);
	}
}
