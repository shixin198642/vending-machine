package com.mjitech.constant;

public class RedisKeyConstants {

	/**
	 * sku key group
	 */
	public final static String REDIS_KEY_SKU_ID = "sku:id:";
	public final static String REDIS_KEY_SKU_BARCODE = "sku:barcode:";
	public final static String REDIS_KEY_SKU_SPEC_ID = "skuspec:id:";
	public final static String REDIS_KEY_SKU_SPEC_SKUID = "skuspec:skuid:";
	public final static String REDIS_KEY_SKU_SKUNUMBER = "sku:skunumber:";
	public final static String REDIS_KEY_SKU_BRAND = "sku:brand:";

	/**
	 * userinfo key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_USERINFO_ID = "userinfo:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_USERINFO_USERNAME = "userinfo:username:";
	public final static String REDIS_CACHE_KEY_PREFIX_USERINFO_OPENID = "userinfo:openid:";

	/**
	 * supplier key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_SUPPLIER_ID = "supplier:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_SUPPLIER_SNAME = "supplier:suppliername:";
	public final static String REDIS_CACHE_KEY_PREFIX_SUPPLIERCONTACT_ID = "suppliercontact:id:";

	/**
	 * menu key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_MENU_ID = "menu:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_MENU_ALL = "menu:all";
	public final static String REDIS_CACHE_KEY_PREFIX_MENU_PARENTID = "menu:parentid:";

	/**
	 * sku type key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_SKUTYPE_ID = "skutype:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_SKUTYPE_PARENTID = "skutype:parentid:";
	public final static String REDIS_CACHE_KEY_PREFIX_SKUTYPE_NAME = "skutype:name:";

	/**
	 * sku brand key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_SKUBRAND_ID = "skubrand:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_SKUBRAND_ALL = "skubrand:all";
	public final static String REDIS_CACHE_KEY_PREFIX_SKUBRAND_NAME = "skubrand:name:";

	/**
	 * dict area key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_DICTAREA_ID = "dictarea:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_DICTAREA_ALLCOUNTRY = "dictarea:allcountry";
	public final static String REDIS_CACHE_KEY_PREFIX_DICTAREA_LISTBYPARENT = "dictarea:listbyparent:";
	public final static String REDIS_CACHE_KEY_PREFIX_DICTAREA_PROVINCELIST = "dictarea:provinceslist:";
	public final static String REDIS_CACHE_KEY_PREFIX_DICTAREA_CITYLIST = "dictarea:citylist";
	public final static String REDIS_CACHE_KEY_PREFIX_DICTAREA_NAME = "dictarea:name:";

	/**
	 * sku type attribute key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_SKUTYPEATTRIBUTE_ID = "skutypeattribute:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_SKUTYPEATTRIBUTE_TYPEID = "skutypeattribute:typeid:";

	/**
	 * sku attribute key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_SKUATTRIBUTE_ID = "skuattribute:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_SKUATTRIBUTE_SKUID = "skuattribute:skuid:";

	/**
	 * file model key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_FILEMODEL_ID = "filemodel:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_FILEMODEL_TYPE_RELATEID = "filemodel:type:relatedid:";

	/**
	 * commonkey group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_COMMONKEY_NAME = "commonkey:keyname:";

	/**
	 * sellorder key group
	 */
	public final static String REDIS_KEY_SELLORDER_ID = "sellorder:id:";
	public final static String REDIS_KEY_SELLORDER_ORDERNUMBER = "sellorder:ordernumber:";
	public final static String REDIS_KEY_SELLORDER_SELLERID = "sellorder:sellerid:";
	public final static String REDIS_KEY_SELLORDER_WAREHOUSEID = "sellorder:warehouseid:";
	public final static String REDIS_KEY_SELLORDER_TAKEGOODSNUMBER = "sellorder:takegoodsnumber:";
	public final static String REDIS_KEY_SELLORDER_BUYERID = "sellorder:buyerid:";
	public final static String REDIS_KEY_SELLORDER_PARENTID = "sellorder:parentid:";
	
	/**
	 * sellordersku key group
	 */
	public final static String REDIS_KEY_SELLORDERSKU_ID = "sellordersku:id:";
	public final static String REDIS_KEY_SELLORDERSKU_SELLORDERID = "sellordersku:sellorderid:";

	/**
	 * inventory key group
	 */
	public final static String REDIS_KEY_INVENTORY_ID = "inventory:id:";
	public final static String REDIS_KEY_INVENTORY_SKU_WAREHOUSE = "inventory:skuid:warehouseid:";
	public final static String REDIS_KEY_INVENTORY_WAREHOUSE = "inventory:warehouseid:";

	/**
	 * warehouse key group
	 */
	public final static String REDIS_KEY_WAREHOUSE_ID = "warehouse:id:";
	public final static String REDIS_KEY_WAREHOUSE_ALL = "warehouse:all";
	public final static String REDIS_KEY_WAREHOUSE_MANAGER = "warehouse:manager:";

	public final static String REDIS_KEY_MACHINESTORE_ALL = "warehouse:machinestore:all";

	/**
	 * instore key group
	 */
	public final static String REDIS_KEY_INSTORE_LIST_ID = "instore:list:warehouseid:";
	public final static String REDIS_KEY_INSTORE_ID = "instore:id:";

	/**
	 * outstore key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_OUTSTORE_ID = "outstore:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_OUTSTORE_WAREHOUSEID = "outstore:warehouseid:";
	public final static String REDIS_CACHE_KEY_PREFIX_OUTSTORE_SELLORDERID = "outstore:sellorderid:";

	/**
	 * warehousemanager key group
	 */
	public final static String REDIS_KEY_WAREHOUSEMANAGER_ID = "warehousemanager:id:";
	public final static String REDIS_KEY_WAREHOUSEMANAGER_MANAGER = "warehousemanager:manager";
	public final static String REDIS_KEY_WAREHOUSEMANAGER_WAREHOUSE = "warehousemanager:warehouse";

	/**
	 * wxautoreply key group
	 */
	public final static String REDIS_KEY_WXAUTOREPLY_ID = "wxautoreply:id:";
	public final static String REDIS_KEY_WXAUTOREPLY_TYPE = "wxautoreply:type:";

	/**
	 * buyer cart key
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_CART = "buyercart:userid:";

	/**
	 * weixin access token cache
	 */
	public final static String REDIS_CACHE_KEY_ACCESS_TOKEN = "wx:accesstoken";

	/**
	 * weixin jsapi ticket cache
	 */
	public final static String REDIS_CACHE_KEY_JSAPI_TICKET = "wx:jsapiticket";
	
	/**
	 * points key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_POINTS_ID = "points:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_POINTS_USERID = "outstore:points:userid";
	
	
	/**
	 * points history key group
	 */
	public final static String REDIS_CACHE_KEY_PREFIX_POINTSHISTORY_ID = "pointshistory:id:";
	public final static String REDIS_CACHE_KEY_PREFIX_POINTSHISTORY_USERID = "outstore:pointshistory:userid";
}
