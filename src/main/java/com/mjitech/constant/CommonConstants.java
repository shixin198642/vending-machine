package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;


public class CommonConstants {
	/**
	 * 性别：未知
	 */
	public final static int GENDER_UNKNOW = 0;
	/**
	 * 性别：男
	 */
	public final static int GENDER_MALE = 1;
	/**
	 * 性别：女
	 */
	public final static int GENDER_FEMALE = 2;
	
	//称呼
	public final static int TITLE_MRS = 1;
	public final static int TITLE_LADY = 2;
	public final static int TITLE_MADAM = 3;
	public final static int TITLE_MR = 4;
	public static Map<Integer, String> TITLE_NAME = new HashMap<Integer, String>();
	static{
		TITLE_NAME.put(TITLE_MRS, "女士");
//		TITLE_NAME.put(TITLE_LADY, "小姐");
//		TITLE_NAME.put(TITLE_MADAM, "太太");
		TITLE_NAME.put(TITLE_MR, "先生");
	}
	
	public final static String BASE_URL_PROPERTY_KEY = "base_url";
	public final static String BASE_STATIC_URL_PROPERTY_KEY = "base_static_url";
	public final static String STATIC_VERSION_PROPERTY_KEY = "static_version";
	
	//是否(主要联系人、默认商品规格等)
	public final static int YES = 1;
	public final static int NO = 2;
	
	//COMMONKEY 
	public final static String COMMONKEY_SUPPLIER = "mt_supplier";
	public final static String COMMONKEY_ORDER = "mt_order";
	
}
