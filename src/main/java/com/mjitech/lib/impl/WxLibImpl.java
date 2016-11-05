package com.mjitech.lib.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.constant.SellOrderConstants;
import com.mjitech.constant.WXConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.lib.WxLib;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.utils.Config;
import com.mjitech.utils.HttpUtils;
import com.mjitech.wxpay.JsApiParams;
import com.mjitech.wxpay.JsApiPayParams;
import com.mjitech.wxpay.UnifiedOrderParams;
import com.mjitech.wxpay.util.RandomStringGenerator;
import com.mjitech.wxpay.util.Signature;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

@Component("wxLib")
public class WxLibImpl implements WxLib {
	@Autowired
	private HttpUtils httpUtils;
	@Value("${appID}")
	private String appid;
	@Value("${secret}")
	private String secret;
	@Value("${mchID}")
	private String mchId;
	@Value("${key}")
	private String key;
	@Value("${unifiedorderurl}")
	private String unifiedOrderUrl;
	@Value("${nonce_str_length}")
	private int nonceLength;
	@Autowired
	private SellOrderLib sellOrderLib;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;
	@Autowired
	private SkuLib skuLib;
	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private Config config;
	@Autowired
	private RedisLib redisLib;
	@Value("${seller_tag_id}")
	private String sellerTagId;
	@Autowired
	private UserinfoLib userinfoLib;

	public int getNonceLength() {
		return nonceLength;
	}

	public void setNonceLength(int nonceLength) {
		this.nonceLength = nonceLength;
	}

	public String getUnifiedOrderUrl() {
		return unifiedOrderUrl;
	}

	public void setUnifiedOrderUrl(String unifiedOrderUrl) {
		this.unifiedOrderUrl = unifiedOrderUrl;
	}

	@Override
	public String getAppid() {
		return appid;
	}

	@Override
	public String getSellerTagId() {
		return sellerTagId;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	@Override
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private static Logger logger = LoggerFactory.getLogger(WxLibImpl.class);

	@Override
	public String getOpenIdByCode(String code) {
		String ret = null;
		StringBuilder getUrl = new StringBuilder(
				"https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
				.append(this.getAppid()).append("&secret=")
				.append(this.getSecret()).append("&code=").append(code)
				.append("&grant_type=authorization_code");
		String httpret = httpUtils.sendGet(getUrl.toString());
		if (StringUtils.isNotEmpty(httpret)) {
			JSONObject json = new JSONObject(httpret);
			if (json.has("openid")) {
				ret = json.getString("openid");
			} else {
				logger.warn("get code ret:" + json.toString());
			}
		}
		return ret;
	}

	@Override
	public String requestPay(UnifiedOrderParams params) {
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8",
				new XmlFriendlyNameCoder("-_", "_")));
		if (logger.isDebugEnabled()) {
			logger.debug("unifiedorder params:" + params);
		}
		// 将要提交给API的数据对象转换成XML格式数据Post给API
		String postDataXML = xStreamForRequestPostData.toXML(params);
		String result = HttpUtils.sendXMLPost(this.getUnifiedOrderUrl(),
				postDataXML);
		return result;
	}

	private String getAccessToken() {
		String key = RedisKeyConstants.REDIS_CACHE_KEY_ACCESS_TOKEN;
		String ret = (String) this.redisLib.getCache(key);
		if (StringUtils.isEmpty(ret)) {
			StringBuilder url = new StringBuilder(
					"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&")
					.append("appid=").append(this.getAppid())
					.append("&secret=").append(this.getSecret());
			httpUtils.sendGet(url.toString());
			String httpret = httpUtils.sendGet(url.toString());
			JSONObject json = new JSONObject(httpret);

			if (json.has("access_token")) {
				ret = json.getString("access_token");
				redisLib.addCache(key, ret, 3600);
			}
		}

		return ret;
	}

	private void refreshJSApiTicket() {
		this.redisLib
				.removeCache(RedisKeyConstants.REDIS_CACHE_KEY_JSAPI_TICKET);
	}

	private String getJSApiTicket() {
		String key = RedisKeyConstants.REDIS_CACHE_KEY_JSAPI_TICKET;
		String ret = (String) this.redisLib.getCache(key);
		if (StringUtils.isEmpty(ret)) {
			StringBuilder url = new StringBuilder(
					"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=")
					.append(this.getAccessToken()).append("&type=jsapi");
			httpUtils.sendGet(url.toString());
			String httpret = httpUtils.sendGet(url.toString());
			JSONObject json = new JSONObject(httpret);

			if (json.has("ticket")) {
				ret = json.getString("ticket");
				redisLib.addCache(key, ret, 3600);
			}
		}

		return ret;
	}

	@Override
	public JsApiParams getJsApiParams(String url) {
		JsApiParams params = new JsApiParams();
		params.setUrl(url);
		params.setNoncestr(RandomStringGenerator.getRandomStringByLength(this
				.getNonceLength()));
		params.setTimestamp(System.currentTimeMillis() / 1000);
		params.setJsapi_ticket(this.getJSApiTicket());
		try {
			params.setSign(Signature.getJSAPISign(params));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return params;
	}

	@Override
	public JsApiPayParams getJsApiPayParams(String prepayid) {
		JsApiPayParams params = new JsApiPayParams();
		params.setAppId(this.getAppid());
		params.setNonceStr(RandomStringGenerator.getRandomStringByLength(this
				.getNonceLength()));
		params.setTimeStamp(Long.toString(System.currentTimeMillis() / 1000));
		params.setSignType("MD5");
		params.setPackageProperty("prepay_id=" + prepayid);
		try {
			params.setPaySign(Signature.getSign(params, this.getKey()));
		} catch (IllegalAccessException e) {
			logger.error("", e);
			e.printStackTrace();
		}
		return params;
	}

	@Override
	public boolean addTagToUser(String openId, String tagId) {
		String token = this.getAccessToken();
		StringBuilder url = new StringBuilder(
				"https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=")
				.append(token);
		JSONObject json = new JSONObject();
		JSONArray openids = new JSONArray();
		openids.put(openId);
		json.put("openid_list", openids);
		json.put("tagid", tagId);
		JSONObject ret = httpUtils.postJSON(url.toString(), json);
		if (ret.has("errcode") && ret.getInt("errcode") == 0) {
			return true;
		} else {
			return false;
		}
	}

	private UnifiedOrderParams generateWXPayParams(int sellOrderId,
			String wxPayType) {
		SellOrder order = this.sellOrderLib.getById(sellOrderId);
		if (order == null) {
			return null;
		}
		if (order.getIsParent() == SellOrderConstants.IS_PARENT_YES
				&& order.getParentId() == 0) {
			List<SellOrder> childs = this.sellOrderLib.getByParent(order
					.getId());
			if (childs == null || childs.size() == 0) {
				return null;
			}
		} else {
			Warehouse warehouse = this.warehouseLib.getById(order
					.getWarehouseId());
			if (warehouse == null) {
				return null;
			}
		}
		List<SellOrderSku> allSkus = new ArrayList<SellOrderSku>();

		List<SellOrderSku> sellOrderSkus = this.sellOrderSkuLib
				.getBySellOrder(sellOrderId);
		if (sellOrderSkus != null) {
			for (SellOrderSku sellOrderSku : sellOrderSkus) {
				sellOrderSku
						.setSku(this.skuLib.getById(sellOrderSku.getSkuId()));
			}
			allSkus.addAll(sellOrderSkus);
		}
		if (order.getChildOrders() != null) {
			for (SellOrder child : order.getChildOrders()) {
				List<SellOrderSku> childSellOrderSkus = this.sellOrderSkuLib
						.getBySellOrder(sellOrderId);
				if (childSellOrderSkus != null) {
					for (SellOrderSku sellOrderSku : childSellOrderSkus) {
						sellOrderSku.setSku(this.skuLib.getById(sellOrderSku
								.getSkuId()));
					}
					allSkus.addAll(childSellOrderSkus);
				}
			}
		}
		UnifiedOrderParams param = new UnifiedOrderParams();
		param.setAppid(this.getAppid());
		param.setMch_id(this.getMchId());
		param.setDevice_info("WEB");
		param.setNonce_str(RandomStringGenerator.getRandomStringByLength(this
				.getNonceLength()));
		param.setBody("怪兽家-商品" + allSkus.size() + "件");
		param.setDetail(this.getDetailJSON(sellOrderSkus));
		param.setOut_trade_no(order.getOrderNumber());
		param.setTotal_fee((int) Math.round((order.getTotalPrice() * 100)));
		try {
			param.setSpbill_create_ip(java.net.InetAddress.getLocalHost()
					.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("", e);
		}
		param.setNotify_url(config.getBaseUrl()
				+ WebPageConstants.COMMON_PREFIX
				+ WebPageConstants.WXPAY_CALLBACK);
		if (WXConstants.WX_PAYTYPES.contains(wxPayType)) {
			param.setTrade_type(wxPayType);
		} else {
			param.setTrade_type(WXConstants.WX_PAYTYPE_NATIVE);
		}
		if (param.getTrade_type().equals(WXConstants.WX_PAYTYPE_JSAPI)) {
			Userinfo buyer = this.userinfoLib.getById(order.getBuyerId());
			param.setOpenid(buyer.getOpenId());
		}
		param.setProduct_id(order.getOrderNumber());
		try {
			param.setSign(Signature.getSign(param, this.getKey()));
		} catch (IllegalAccessException e) {
			logger.error("", e);
		}
		return param;
	}

	@Override
	public String requestBuyerPayParams(int sellOrderId) {
		UnifiedOrderParams param = this.generateWXPayParams(sellOrderId,
				WXConstants.WX_PAYTYPE_JSAPI);
		if (param == null) {
			return null;
		}
		return this.requestPay(param);
	}

	@Override
	public String requestPayUrl(int sellOrderId) {
		UnifiedOrderParams param = this.generateWXPayParams(sellOrderId,
				WXConstants.WX_PAYTYPE_NATIVE);
		if (param == null) {
			return null;
		}
		return this.requestPay(param);
	}

	private String getDetailJSON(List<SellOrderSku> sellOrderSkus) {
		StringBuilder sb = new StringBuilder("<![CDATA[");
		JSONArray goods = new JSONArray();
		for (SellOrderSku sellOrderSku : sellOrderSkus) {
			JSONObject good = new JSONObject();
			good.put("goods_id", sellOrderSku.getSku().getSkuNumber());
			good.put("goods_name", sellOrderSku.getSku().getName());
			good.put("goods_num", sellOrderSku.getCount());
			good.put("price", (int) (sellOrderSku.getSellPrice() * 100));
			goods.put(good);
		}
		JSONObject goodsjson = new JSONObject();
		goodsjson.put("goods_detail", goods);
		sb.append(goodsjson.toString());
		sb.append("]]>");
		return sb.toString();
	}

	@Override
	public boolean removeUserTag(String openId, String tagId) {
		String token = this.getAccessToken();
		StringBuilder url = new StringBuilder(
				"https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=")
				.append(token);
		JSONObject json = new JSONObject();
		JSONArray openids = new JSONArray();
		openids.put(openId);
		json.put("openid_list", openids);
		json.put("tagid", tagId);
		JSONObject ret = httpUtils.postJSON(url.toString(), json);
		if (ret.has("errcode") && ret.getInt("errcode") == 0) {
			return true;
		} else {
			return false;
		}
	}
}
