package com.mjitech.lib;

import com.mjitech.wxpay.JsApiParams;
import com.mjitech.wxpay.JsApiPayParams;
import com.mjitech.wxpay.UnifiedOrderParams;

public interface WxLib {

	public String getKey();

	public String getAppid();

	public String getOpenIdByCode(String code);

	public String requestPay(UnifiedOrderParams params);

	public String requestPayUrl(int sellOrderId);

	boolean addTagToUser(String openId, String tagId);
	
	boolean removeUserTag(String openId, String tagId);

	String getSellerTagId();

	JsApiParams getJsApiParams(String url);

	String requestBuyerPayParams(int sellOrderId);

	JsApiPayParams getJsApiPayParams(String prepayid);

}
