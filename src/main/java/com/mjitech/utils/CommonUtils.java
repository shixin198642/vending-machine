package com.mjitech.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class CommonUtils {
	private static SimpleDateFormat todayFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat frontFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String getTodayString() {
		return todayFormat.format(new Date());
	}

	public static String formatDateToFront(Date date) {
		if (date != null) {
			return frontFormat.format(date);
		}
		return "";
	}

	public static String convertHTMLUnicode(String source) {
		String regExp = "&#\\d*;";
		Matcher m = Pattern.compile(regExp).matcher(source);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String s = m.group(0);
			s = s.replaceAll("(&#)|;", "");
			char c = (char) Integer.parseInt(s);
			m.appendReplacement(sb, Character.toString(c));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 验证手机号
	 * 
	 * @param mobile
	 *            手机号
	 * @return true/false
	 */
	public static boolean isMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		if (mobile.length() < 11) {
			return false;
		}
		return Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$").matcher(mobile)
				.matches();

	}

	/**
	 * convert mobile number to format like 139xxxx1111
	 * 
	 * @param mobile
	 * @return
	 */
	public static String hideMobile(String mobile) {
		StringBuilder ret = new StringBuilder("");
		int length = mobile.length();
		if (length >= 8) {
			ret.append(mobile.substring(0, length - 8)).append("****")
					.append(mobile.substring(length - 4));
		} else if (length > 4) {
			ret.append("****").append(mobile.substring(length - 4));
		} else {
			ret.append(mobile);
		}
		return ret.toString();
	}

	/**
	 * this will return 1970-01-01 00:00:00
	 * 
	 * @return 1970-01-01 00:00:00
	 */
	public static Date getDefaultDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static void removeCommonUselessProperties(JSONObject json) {
		json.remove("updateDatetime");
		json.remove("updator");
		json.remove("createDatetime");
		json.remove("creator");
		json.remove("perpage");
		json.remove("begin");
		json.remove("sort");
		json.remove("sortDir");
	}

	public static String str2unicode(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		StringBuilder unicodeBytes = new StringBuilder("");
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			StringBuilder hexB = new StringBuilder(
					Integer.toHexString(utfBytes[byteIndex]));
			if (hexB.length() <= 2) {
				hexB.append("00");
			}
			unicodeBytes = unicodeBytes.append("\\u").append(hexB);
		}
		return unicodeBytes.toString();
	}

}
