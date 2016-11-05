package com.mjitech.utils;

import java.util.Locale;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.ReturnErrorCode;

@Component
@Scope("singleton")
public class JsonUtils {
	@Autowired
	private MessageSource messageSource;

	public String getMessageByKey(String key, Object[] args) {

		return this.messageSource.getMessage(key, args, Locale.getDefault());
	}

	public void setRetErrorCode(JSONObject ret,
			ReturnErrorCode errorCodeConstants, int errorCode, Object[] params) {
		if (errorCode < 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
		}
		ret.put(JSONConstants.RETURN_KEY_ERROR_MESSAGE, "");
		ret.put(JSONConstants.RETURN_KEY_ERROR_CODE, errorCode);
		String key = errorCodeConstants.getMessageKey(errorCode);
		if (key != null) {
			ret.put(JSONConstants.RETURN_KEY_ERROR_MESSAGE,
					this.getMessageByKey(key, params));
		}
	}

}
