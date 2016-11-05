package com.mjitech.constant;

import java.util.HashSet;
import java.util.Set;

public class WebPageConstants {

	public final static String BASE_URL = "http://www.mjitech.com";

	public final static String DEFAULT_ENCODING = "UTF-8";

	/**
	 * controller produces: json
	 */
	public final static String JSON_PRODUCES = "application/json;charset=UTF-8";

	/**
	 * controller produces: xml
	 */
	public final static String XML_PRODUCES = "text/xml;charset=UTF-8";

	/**
	 * spring dispatcher will handle url under this prefix
	 */
	public final static String COMMON_PREFIX = "/web";

	/**
	 * 支付url, only test for demo machine
	 */
	public final static String PAY_PAGE = "/pay_page.action";

	public final static String PAY_SUBMIT = "/pay_submit.action";

	/**
	 * 通用没有权限查看页面
	 */
	public final static String COMMON_NOAUTH_PAGE = "/noauth.jsp";

	/**
	 * 登录注册相关url
	 */
	public final static String LOGIN_PAGE = "/login_page.action";
	// 登录路径
	public final static String LOGIN = "/login.action";
	// 检查是否登录
	public final static String CHECK_IS_LOGIN = "/is_login.action";
	// 登出
	public final static String LOGOUT = "/logout.action";

	/**
	 * 用户信息
	 */
	public final static String FIND_USERINFO = "/find_userinfo.action";
	
	/**
	 * 供应商
	 */
	public final static String LIST_SUPPLIER = "/list_supplier.action";
	public final static String ADD_SUPPLIER = "/add_supplier.action";
	public final static String ADDINIT_SUPPLIER = "/addinit_supplier.action";
	public final static String FIND_SUPPLIER = "/find_supplier.action";
	public final static String DETAIL_SUPPLIER = "/detail_supplier.action";
	public final static String DEL_SUPPLIER = "/delete_supplier.action";
	public final static String ALTER_SUPPLIER = "/update_supplier.action";
	public final static String ALTERINIT_SUPPLIER = "/updateinit_supplier.action";
	public final static String CONDITIONLIST_SUPPLIER = "/conditionlist_supplier.action";

	/**
	 * 供应商联系人
	 */
	public final static String ADD_SUPPLIERCONTACT = "/add_suppliercontact.action";
	public final static String FIND_SUPPLIERCONTACT = "/find_suppliercontact.action";
	public final static String DEL_SUPPLIERCONTACT = "/delete_suppliercontact.action";
	public final static String ALTER_SUPPLIERCONTACT = "/update_suppliercontact.action";
	// 查询供应商下多个联系人
	public final static String FIND_SUPPLIERCONTACTLIST = "/findlist_suppliercontact.action";

	/**
	 * 文件上传
	 */
	public final static String UPLOAD_TMP_IMAGE = "upload_tmp_image.action";
	/**
	 * 商品
	 */
	public final static String SKU_LIST = "sku_list.action";

	public final static String ADD_SKU_PAGE = "add_sku_page.action";

	public final static String UPDATE_SKU_PAGE = "update_sku_page.action";

	public final static String ADD_UPDATE_SKU = "add_update_sku.action";

	public final static String SKU_ATTRIBUTES_INC = "sku_attributes.action";

	public final static String GET_SUB_TYPES = "get_subtypes.action";

	public final static String GET_SKU_BY_SKUNUMBER = "get_sku_by_skunumber.action";
	
	public final static String CONDITIONLIST_SKU = "/conditionlist_sku.action";

	/**
	 * 商品规格
	 */
	public final static String ADD_SKUSPEC = "/add_skuspec.action";
	public final static String FIND_SKUSPEC = "/find_skuspec.action";
	public final static String DEL_SKUSPEC = "/del_skuspec.action";
	public final static String FIND_SKUSPECLIST = "/findlist_skuspec.action";

	/**
	 * 仓库/门店/机器
	 */
	public final static String WAREHOUSE_INVENTORY_HOME = "/warehouse_inventory_home.action";
	public static final String GET_INVENTORY_BY_WAREHOUSE = "/get_inventory_by_warehouse.action";
	public static final String GET_INVENTORY_BY_MACHINE = "/get_inventory_by_machine.action";
	public final static String WAREHOUSE_ADD = "/warehouse_add.action";
	public final static String WAREHOUSE_ADDFORM = "/warehouse_addform.action";
	public final static String WAREHOUSE_GET = "/warehouse_get.action";
	public final static String FIND_WAREHOUSE = "/find_warehouse.action";

	public final static String WAREHOUSE_DELETE = "/warehouse_delete.action";
	public final static String WAREHOUSE_UPDATE = "/warehouse_update.action";
	public final static String WAREHOUSE_REQUEST = "/warehouse_request.action";
	public static final String WAREHOUSE_REQUEST_HISTORY = "warehouse_request_history.action";
	public final static String WAREHOUSE_REQUEST_LIST = "/warehouse_requestlist.action";
	public final static String GET_REQUST_BY_MACHINE = "/get_request_by_machine.action";
	public final static String GET_NEW_REQUST_BY_WAREHOUSE = "/get_new_request_by_warehouse.action";
	public final static String GET_HISTORY_REQUST_BY_WAREHOUSE = "/get_history_request_by_warehouse.action";
	public final static String SELLER_SELL = "/seller_sell.action";
	public final static String SELLER_REQUEST = "/seller_request.action";
	public final static String GENERATE_PICKING = "/generate_picking.action";
	public final static String GET_PICKING = "/get_picking.action";
	public final static String CREATE_PICKING = "/create_picking.action";
	public final static String CREATE_PICKING_FORM = "/create_picking_form.action";
	public final static String LIST_PICKING = "/list_picking.action";

	/**
	 * 收货单
	 */
	public final static String RECEIPT_HOME = "/receipt_home.action";
	public final static String RECEIPT_BY_WAREHOUSE = "/receipt_by_warehouse.action";
	public final static String RECEIPT_HISTORY = "/receipt_history.action";
	public final static String RECEIPT_SEARCH_BY_ORDER = "/receipt_search_by_order.action";
	public final static String RECEIPT_SEARCH_BY_SKU = "/receipt_search_by_sku.action";
	public final static String RECEIPT_ADD = "/receipt_add.action";
	public final static String RECEIPT_GET = "/receipt_get.action";
	public final static String RECEIPT_LIST = "/receipt_list.action";
	public final static String RECEIPT_DELETE = "/receipt_delete.action";
	public final static String RECEIPT_UPDATE = "/receipt_update.action";
	public final static String RECEIPT_CHECKING = "/receipt_checking.action";
	public final static String RECEIPT_RECEIVING = "/receipt_receiving.action";
	public final static String RECEIPT_INSPECTING = "/receipt_inspecting.action";
	public static final String RECEIPT_RRINT = "/receipt_print.action";

	public final static String STOCKIN_ADD = "/stockin_add.action";
	public final static String STOCKIN_ENTER = "/stockin_enter.action";
	public final static String STOCKIN_LIST = "/stockin_list.action";

	public final static String STOCKOUT_ADD = "/stockout_add.action";
	public final static String STOCKOUT_EXIT = "/stockout_exit.action";
	public final static String STOCKOUT_LIST = "/stockout_list.action";

	public static final String STOCKIN_REQUEST_HOME = "/stockin_request_home.action";
	public static final String STOCKIN_HOME = "/stockin_home.action";
	public static final String WAREHOUSE_REQUEST_HOME = "/warehouse_request_home.action";
	public static final String PICKING_HOME = "/picking_home.action";
	public static final String CREATE_REQUEST = "create_request.action";

	public static final String MACHINE_INVENTORY_HOME = "/machine_inventory_home.action";
	public static final String MACHINE_REQUEST_HOME = "/machine_request_home.action";
	public static final String MACHINE_DETAIL_HOME = "/machine_detail_home.action";
	public static final String SELL_HISTORY_HOME = "/sell_history_home.action";
	public static final String DELIVERY_HOME = "/delivery_home.action";
	public static final String GET_DELIVERY = "/get_delivery.action";

	/**
	 * 采购订单
	 */
	public final static String LIST_ORDER = "/list_order.action";
	public final static String ADDINIT_ORDER = "/addinit_order.action";
	public final static String ADD_ORDER = "/add_order.action";
	public final static String FIND_ORDER = "/find_order.action";
	public final static String DEL_ORDER = "/del_order.action";
	public final static String ALTERINIT_ORDER = "/updateinit_order.action";
	public final static String ALTER_ORDER = "/alter_order.action";
	public final static String DETAIL_ORDER = "/detail_order.action";

	/**
	 * 采购订单中商品
	 */
	public final static String ADD_ORDERSKU = "/add_ordersku.action";
	public final static String FIND_ORDERSKU = "/find_ordersku.action";
	public final static String DEL_ORDERSKU = "/del_ordersku.action";
	public final static String ALTER_ORDERSKU = "/alter_ordersku.action";
	public final static String FIND_ORDERSKULIST = "/findlist_ordersku.action";

	/**
	 * 库存
	 */

	public final static String ADD_INVENTORY_QUANTITY = "add_inventory_quantity.action";

	public final static String DEDUCT_INVENTORY_QUANTITY = "deduct_inventory_quantity.action";

	public final static String MODIFY_INVENTORY_PRICE = "modify_inventory_price.action";

	public final static String ADD_NEW_INVENTORY = "add_new_inventory.action";

	/**
	 * seller api
	 */
	// 微信绑定账号
	public final static String SELLER_BIND_ACCOUNT = "/seller_api/wx_bind_account.action";
	// 通过skuid获取商品详情
	public final static String SELLER_GET_SKU_DETAIL = "/seller_api/wx_get_sku_detail.action";
	// 直接用openid登录，测试用，正式时会禁用
	public final static String SELLER_TEST_OPENID = "/seller_api/wx_test_openid.action";
	// 下单
	public final static String SELLER_ADD_ORDER = "/seller_api/wx_add_order.action";
	// 生成支付链接
	public final static String SELLER_REQUEST_PAY = "/seller_api/wx_request_pay.action";
	// 订单状态
	public final static String SELLER_ORDER_STATUS = "/seller_api/wx_order_status.action";
	// 订单列表
	public final static String SELLER_ORDER_LIST = "/seller_api/wx_order_list.action";

	// 订单详情
	public final static String SELLER_ORDER_DETAIL = "/seller_api/wx_order_detail.action";

	/**
	 * buyer api
	 */
	// 获得jsapi config params
	public final static String BUYER_GET_JSAPI_PARAMS = "/buyer_api/get_jsapi_config_params.ction";
	// 卖家主页数据
	public final static String BUYER_MAINPAGE_DATA = "/buyer_api/get_mainpage_data.action";
	
	// 卖家主页数据by geo
	public final static String BUYER_MAINPAGE_DATA_BY_GEO = "/buyer_api/get_mainpage_data_by_geo.action";
	// 获得所有店铺机器
	public final static String BUYER_GET_ALL_STORES = "/buyer_api/get_all_stores.action";
	// 添加商品到购物车
	public final static String BUYER_ADD_SKU_TO_CART = "/buyer_api/add_sku_to_cart.action";

	// 从购物车删除商品
	public final static String BUYER_REMOVE_SKU_FROM_CART = "/buyer_api/remove_sku_from_cart.action";

	// 清空购物车
	public final static String BUYER_CLEAR_CART = "/buyer_api/clear_cart.ction";

	// 获得当前购物车
	public final static String BUYER_GET_CART = "/buyer_api/get_cart.ction";

	// 提交购物车
	public final static String BUYER_SUBMIT_CART = "/buyer_api/submit_cart.ction";
	
	// 提交多个购物车
	public final static String BUYER_SUBMIT_CARTS = "/buyer_api/submit_carts.ction";
	
	// 申请支付
	public final static String BUYER_REQUEST_PAY = "/buyer_api/get_jsapi_pay_params.ction";
	//订单列表
	public final static String BUYER_ORDER_LIST = "/buyer_api/order_list.ction";
	//订单详情
	public final static String BUYER_ORDER_DETAIL = "/buyer_api/order_detail.ction";
	//商品详情
	public final static String BUYER_SKU_DETAIL = "/buyer_api/sku_detail.ction";
	//我的详情
	public final static String BUYER_GET_MY_INFO = "/buyer_api/get_my_info.ction";
	
	//品牌详情
	public final static String BUYER_BRAND_DETAIL = "/buyer_api/brand_detail.ction";
	
	// 测试专用
	// 绑定openid
	public final static String BUYER_TEST_LOING_WITH_OPENID = "/buyer_api/test_login_with_openid.ction";

	// 微信支付回调url
	public final static String WXPAY_CALLBACK = "/wxpay_callback.action";

	public final static String WEIXIN_INTERFACE = "/wxinterface.action";
	public final static String WEIXIN_AUTHORIZEPAGE = "/wxauthorize.action";
	public final static String WEIXIN_TESTPAGE = "/wxtest.action";

	// 通用查询
	public final static String COMMON_SEARCH = "/common_search.action";

	public static Set<String> ADMIN_URLS = new HashSet<String>();
	static {
		ADMIN_URLS.add(SKU_LIST);
	}

	public static Set<String> URLS_NONEED_LOGIN = new HashSet<String>();
	static {
		URLS_NONEED_LOGIN.add(LOGIN);
		URLS_NONEED_LOGIN.add(LOGOUT);
		URLS_NONEED_LOGIN.add(CHECK_IS_LOGIN);
		URLS_NONEED_LOGIN.add(LOGIN_PAGE);
		URLS_NONEED_LOGIN.add(PAY_PAGE);
		URLS_NONEED_LOGIN.add(PAY_SUBMIT);
		URLS_NONEED_LOGIN.add(WEIXIN_INTERFACE);
		URLS_NONEED_LOGIN.add(WEIXIN_TESTPAGE);
		URLS_NONEED_LOGIN.add(WEIXIN_AUTHORIZEPAGE);
		URLS_NONEED_LOGIN.add(SELLER_GET_SKU_DETAIL);
		URLS_NONEED_LOGIN.add(SELLER_TEST_OPENID);
		URLS_NONEED_LOGIN.add(SELLER_ADD_ORDER);
		URLS_NONEED_LOGIN.add(SELLER_REQUEST_PAY);
		URLS_NONEED_LOGIN.add(SELLER_ORDER_LIST);
		URLS_NONEED_LOGIN.add(SELLER_ORDER_STATUS);
		URLS_NONEED_LOGIN.add(SELLER_ORDER_DETAIL);
		URLS_NONEED_LOGIN.add(WXPAY_CALLBACK);
		URLS_NONEED_LOGIN.add(BUYER_MAINPAGE_DATA);
		URLS_NONEED_LOGIN.add(BUYER_ADD_SKU_TO_CART);
		URLS_NONEED_LOGIN.add(BUYER_CLEAR_CART);
		URLS_NONEED_LOGIN.add(BUYER_GET_ALL_STORES);
		URLS_NONEED_LOGIN.add(BUYER_GET_CART);
		URLS_NONEED_LOGIN.add(BUYER_REMOVE_SKU_FROM_CART);
		URLS_NONEED_LOGIN.add(BUYER_SUBMIT_CART);
		URLS_NONEED_LOGIN.add(BUYER_TEST_LOING_WITH_OPENID);
	}

	public static final String buildURL(String context, String url) {
		return new StringBuilder(context)
				.append(WebPageConstants.COMMON_PREFIX).append(url).toString();
	}
}
