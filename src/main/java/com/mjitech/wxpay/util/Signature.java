package com.mjitech.wxpay.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.common.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.mjitech.wxpay.JsApiParams;

/**
 * User: rizenguo Date: 2014/10/29 Time: 15:23
 */
public class Signature {

	private static Logger logger = LoggerFactory.getLogger(Signature.class);

	public static String getJSAPISign(Object o) throws IllegalAccessException {
		ArrayList<String> list = new ArrayList<String>();
		Class cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.get(o) != null && !"".equals(f.get(o))) {
				list.add(f.getName() + "=" + f.get(o));
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append("&");
			}
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result = DigestUtils.sha1Hex(result);

		return result;
	}

	/**
	 * 签名算法
	 * 
	 * @param o
	 *            要参与签名的数据对象
	 * @return 签名
	 * @throws IllegalAccessException
	 */
	public static String getSign(Object o, String key)
			throws IllegalAccessException {
		ArrayList<String> list = new ArrayList<String>();
		Class cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.get(o) != null && !"".equals(f.get(o))) {
				String name = f.getName();
				//this is walkaround for property name is package
				if(name.equals("packageProperty")){
					name = "package";
				}
				Object value = f.get(o);
//				if(value instanceof String){
//					try {
//						value = URLEncoder.encode((String)value,"UTF-8");
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}
//				}
				list.add(name + "=" + value);
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append("&");
			}
			sb.append(arrayToSort[i]);
		}
		System.out.println(sb);
		if (StringUtils.isNotEmpty(key)) {
			sb.append("&key=").append(key);
		}
		String result = sb.toString();
		System.out.println(result);
		result = MD5.MD5Encode(result).toUpperCase();
		System.out.println(result);
		return result;
	}

	public static String getSign(Map<String, Object> map, String key) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null && !"".equals(entry.getValue())) {
				list.add(entry.getKey() + "=" + entry.getValue());
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append("&");
			}
			sb.append(arrayToSort[i]);
		}
		if (StringUtils.isNotEmpty(key)) {
			sb.append("&key=").append(key);
		}
		String result = sb.toString();
		result = MD5.MD5Encode(result).toUpperCase();
		return result;
	}

	/**
	 * 从API返回的XML数据里面重新计算一次签名
	 * 
	 * @param responseString
	 *            API返回的XML数据
	 * @return 新鲜出炉的签名
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static String getSignFromResponseString(String responseString,
			String key) throws IOException, SAXException,
			ParserConfigurationException {
		Map<String, Object> map = XMLParser.getMapFromXML(responseString);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		return Signature.getSign(map, key);
	}

	/**
	 * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
	 * 
	 * @param responseString
	 *            API返回的XML数据字符串
	 * @return API签名是否合法
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static boolean checkIsSignValidFromResponseString(
			String responseString, String key)
			throws ParserConfigurationException, IOException, SAXException {

		Map<String, Object> map = XMLParser.getMapFromXML(responseString);
		if (logger.isDebugEnabled()) {
			logger.debug(map.toString());
		}

		String signFromAPIResponse = map.get("sign").toString();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			logger.warn("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("服务器回包里面的签名是:" + signFromAPIResponse);
		}
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		String signForAPIResponse = Signature.getSign(map, key);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {
			// 签名验不过，表示这个API返回的数据有可能已经被篡改了
			logger.warn("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("恭喜，API返回的数据签名验证通过!!!");
		}
		return true;
	}

	public static void main(String[] args) throws IllegalAccessException {
		JsApiParams parm = new JsApiParams();
		parm.setJsapi_ticket("kgt8ON7yVITDhtdwci0qeUSmE1nksITlP3xXB2ChRPMr7736xxWtJ19NMRx1_XPfSXKITxlaHUMFs4VIGUuBcg");
		parm.setNoncestr("8k4lzt0ap3j7e5yqhj7ql8auj9w2x2");
		parm.setTimestamp(1477056268);
		parm.setUrl("http://www.baidu.com");
		System.out.println(Signature.getJSAPISign(parm));
		String str = "jsapi_ticket=kgt8ON7yVITDhtdwci0qeUSmE1nksITlP3xXB2ChRPMr7736xxWtJ19NMRx1_XPfSXKITxlaHUMFs4VIGUuBcg&noncestr=8k4lzt0ap3j7e5yqhj7ql8auj9w2x2&timestamp=1477056268&url=http://www.baidu.com";
		System.out.println(DigestUtils.sha1Hex(str));
	}

}
