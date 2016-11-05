package com.mjitech.lib;

public interface SmsLib {
	public static final String VALIDATION_CONTENT_MESSAGE_KEY = "sms.message.validation";
	
	public String sendSms(String mobile, String content, boolean isSync);

}
