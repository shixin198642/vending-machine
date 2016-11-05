package com.mjitech.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class CookieUtils {

	private static Base64 base64 = new Base64();

	public String getTokenFromUserid(int id) {
		String origin = new StringBuilder("")
				.append(System.currentTimeMillis()).append(id).toString();
		return base64.encodeAsString(origin.getBytes());
	}

	public String getCookieValue(HttpServletRequest request, String name) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase(name)) {
				value = cookie.getValue();
				break;
			}
		}
		return value;
	}

	public int getUseridFromToken(String token) {
		if (StringUtils.isEmpty(token)) {
			return 0;
		}
		String origin = new String(base64.decode(token));
		// 13 is system.currentimemillis length
		if (StringUtils.isNumeric(origin) && origin.length() > 13) {
			String idStr = origin.substring(13);
			return Integer.parseInt(idStr);
		}
		return 0;
	}

}
