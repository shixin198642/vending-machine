package com.mjitech.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RequestConstants;
import com.mjitech.model.BaseModel;

@Component
public class RequestUtils {
	public String getRealRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public int getIntValue(Map<String, String> params, String name) {
		int value = 0;
		String tmp = params.get(name);
		if (StringUtils.isNumeric(tmp)) {
			value = Integer.parseInt(tmp);
		}
		return value;
	}

	public long getLongValue(Map<String, String> params, String name) {
		long value = 0;
		String tmp = params.get(name);
		if (StringUtils.isNumeric(tmp)) {
			value = Long.parseLong(tmp);
		}
		return value;
	}

	public String getStringValue(Map<String, String> params, String name) {
		return params.get(name);
	}

	public float getFloatValue(Map<String, String> params, String name) {
		float value = 0f;
		String tmp = params.get(name);
		if (NumberUtils.isNumber(tmp)) {
			value = Float.parseFloat(tmp);
		}
		return value;
	}

	public void setPagination(Map<String, String> params, BaseModel model) {
		model.setBegin(this.getIntValue(params,
				RequestConstants.PARAMETER_NAME_BEGIN));
		model.setPerpage(this.getIntValue(params,
				RequestConstants.PARAMETER_NAME_PERPAGE));
		if (model.getPerpage() == 0) {
			model.setPerpage(RequestConstants.DEFAULT_PERPAGE);
		}
	}

	public String readBody(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder("");
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			isr = new InputStreamReader(request.getInputStream());
			br = new BufferedReader(isr);
			String line = br.readLine();
			while (line != null) {
				sb.append(line).append(System.getProperty("line.separator"));
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
}
