package com.mjitech.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.google.common.io.CharStreams;
import com.mjitech.constant.WXConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.LoginLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.WxLib;
import com.mjitech.service.LoginService;
import com.mjitech.service.UserinfoService;
import com.mjitech.service.WxService;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;
import com.mjitech.wxpay.util.XMLParser;

@Controller
public class WxController {
	private static final Logger logger = LoggerFactory
			.getLogger(WxController.class);
	// 与接口配置信息中的Token要一致
	private static String token = "felixzztoken";
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private WxLib wxLib;
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private WxService wxService;
	@Autowired
	private LoginLib loginLib;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private RedisLib redisLib;


	@ResponseBody
	@RequestMapping(value = WebPageConstants.WEIXIN_INTERFACE, produces = WebPageConstants.XML_PRODUCES)
	public String wxInterface(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> params) {
		String ret = "false";
		if (logger.isInfoEnabled()) {
			logger.info("input params:" + params);
		}
		// 微信加密签名
		String signature = requestUtils.getStringValue(params, "signature");
		// 时间戳
		String timestamp = requestUtils.getStringValue(params, "timestamp");
		// 随机数
		String nonce = requestUtils.getStringValue(params, "nonce");
		// 随机字符串
		String echostr = requestUtils.getStringValue(params, "echostr");

		if (StringUtils.isNotEmpty(signature)) {
			if (checkSignature(signature, timestamp, nonce)) {
				if (request.getMethod().equalsIgnoreCase("post")) {
					try {
						String postContent = CharStreams.toString(request
								.getReader());
						if (logger.isDebugEnabled()) {
							logger.debug(postContent);
						}
						Map<String, Object> postMap = XMLParser
								.getMapFromXML(postContent);
						if (logger.isInfoEnabled()) {
							logger.info("post map:" + postMap);
						}
						return wxService.handleMessage(postMap);

					} catch (IOException e) {
						e.printStackTrace();
						logger.error("read content from post error:", e);
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					}
				}
				return echostr;
			}
		}
		return ret;
	}

	@RequestMapping(value = WebPageConstants.WEIXIN_TESTPAGE)
	public ModelAndView wxTest(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("params", params);
		mv.setViewName("wxtest");

		return mv;
	}

	private void logonWithCode(String code, HttpServletRequest request,
			HttpServletResponse response) {
		String key = "wxcode:" + code;
		String openidcache = (String) this.redisLib.getCache("wxcode:" + code);

		if (StringUtils.isEmpty(openidcache)) {
			String openId = this.wxLib.getOpenIdByCode(code);
			if (logger.isInfoEnabled()) {
				logger.info("openid:" + openId);
			}
			if (StringUtils.isNotEmpty(openId)) {
				this.redisLib.addCache(key, openId, 30);
				this.userinfoService.addUserWithOpenid(openId);
				this.loginService.loginWithOpenId(openId, false, request,
						response);
				if (logger.isInfoEnabled()) {
					logger.info("user:" + sessionUtils.getUserid(request)
							+ " logon");
				}
			}
		}
	}

	@RequestMapping(value = WebPageConstants.WEIXIN_AUTHORIZEPAGE)
	public String wxAuthorize(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> params) {
		String code = params.get("code");
		String state = params.get("state");
		String redirect = "redirect:" + WXConstants.COMMON_URL;
		if (logger.isInfoEnabled()) {
			logger.info("params:" + params);
		}
		this.logonWithCode(code, request, response);

		if ("common".equalsIgnoreCase(state)) {
			redirect = "redirect:" + WXConstants.COMMON_URL;
		} else if ("orderlist".equalsIgnoreCase(state)) {
			redirect = "redirect:" + WXConstants.ORDERLIST_URL;
		} else if ("checkin".equalsIgnoreCase(state)) {
			redirect = "redirect:" + WXConstants.CHECKIN_URL;
		} else if ("buyerhome".equalsIgnoreCase(state)) {
			redirect = "redirect:" + WXConstants.BUYER_HOMEPAGE;
		}
		// Userinfo user = userinfoLib.getByOpenId(openId);
		// if (user == null) {
		// if (logger.isWarnEnabled()) {
		// logger.warn("openid" + openId + " not binding any user.");
		// }
		// redirect = "redirect:/noauth.html";
		// } else {
		// this.loginService.loginWithOpenId(openId, false, request, response);
		// }
		return redirect;
	}

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		// Arrays.sort(arr);
		sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	public static void sort(String a[]) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[j].compareTo(a[i]) < 0) {
					String temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
	}
}
