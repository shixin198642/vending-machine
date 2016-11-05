package com.mjitech;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.mjitech.utils.HttpUtils;
import com.mjitech.wxpay.UnifiedOrderParams;
import com.mjitech.wxpay.util.RandomStringGenerator;
import com.mjitech.wxpay.util.Signature;
import com.mjitech.wxpay.util.XMLParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class Testwx {

	public static void main(String[] args) throws IllegalAccessException, ParserConfigurationException, IOException, SAXException {
		String appID = "wx1cc64cd8dc80eb77"; // NEW

		String secret = "9df2bc15e5b353508d2a9e44740054b8"; // NEW

		String mchID = "1290825501"; // NEW

		String key = "SZc1bee7d0f3f0b05ab9886e32512dfb"; // NEW
		UnifiedOrderParams param = new UnifiedOrderParams();
		param.setAppid(appID);
		param.setMch_id(mchID);
		param.setDevice_info("WEB");
		param.setNonce_str(""
				+ RandomStringGenerator.getRandomStringByLength(16));
		JSONObject detail = new JSONObject();
		JSONArray gdarray = new JSONArray();
		JSONObject goodjson = new JSONObject();
		goodjson.put("goods_id", "111");
		goodjson.put("goods_name", "good goods");
		goodjson.put("goods_num", 1);
		goodjson.put("price", 160);
		gdarray.put(goodjson);
		detail.put("goods_detail", gdarray);
		param.setBody("我的测试商品");
		param.setDetail("<![CDATA["+detail.toString()+"]]>");
		param.setOut_trade_no("123459");
		param.setTotal_fee(160);
		param.setSpbill_create_ip(java.net.InetAddress.getLocalHost()
				.getHostAddress());
		param.setNotify_url("http://www.mjitech.com/web/wx_callback.action");
		param.setTrade_type("NATIVE");
		param.setProduct_id("123459");
		param.setSign(Signature.getSign(param, key));
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8",
				new XmlFriendlyNameCoder("-_", "_")));

		// 将要提交给API的数据对象转换成XML格式数据Post给API
		String postDataXML = xStreamForRequestPostData.toXML(param);
		String result = HttpUtils.sendXMLPost("https://api.mch.weixin.qq.com/pay/unifiedorder", postDataXML);
		Map<String, Object> map = XMLParser.getMapFromXML(result);
		
		System.out.println(map);

	}

}
