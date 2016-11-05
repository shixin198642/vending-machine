package com.mjitech.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.constant.WXConstants;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WxAutoreplyLib;
import com.mjitech.lib.WxLib;
import com.mjitech.model.Userinfo;
import com.mjitech.service.WxService;
import com.mjitech.utils.XmlUtils;

@Service("wxService")
public class WxServiceImpl implements WxService {

	private static Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private WxLib wxLib;
	@Autowired
	private XmlUtils xmlUtils;
	@Autowired
	private WxAutoreplyLib wxAutoreplyLib;

	@Override
	public String handleMessage(Map<String, Object> inputMsg) {
		String type = (String) inputMsg.get("MsgType");
		Map<String, Object> retMsg = null;
		if ("text".equalsIgnoreCase(type)) {
			retMsg = this.handleTextMsg(inputMsg);
		} else if ("event".equalsIgnoreCase(type)) {
			retMsg = this.handleEventMsg(inputMsg);
		}
		String ret = this.xmlUtils.convertMapToXMLString(retMsg);
		if (logger.isInfoEnabled()) {
			logger.info("return msg:" + ret);
		}
		return ret;
	}

	private Map<String, Object> handleTextMsg(Map<String, Object> inputMsg) {
		Map<String, Object> retMsg = new HashMap<String, Object>();
		String content = (String) inputMsg.get("Content");
		String from = (String) inputMsg.get("FromUserName");
		String to = (String) inputMsg.get("ToUserName");
		if (content == null) {
			content = "";
		}
		content = content.toLowerCase();
		if (content.startsWith("tell me")) {
			retMsg.put("Content",
					new StringBuilder("your username:").append(from).toString());
		} else if (content.startsWith("add me to:")) {
			String tmp = content.substring("add me to:".length()).trim();
			if (StringUtils.isNumeric(tmp)) {
				int id = Integer.parseInt(tmp);
				Userinfo userinfo = userinfoLib.getById(id);
				if (userinfo != null) {
					Userinfo update = new Userinfo();
					update.setId(userinfo.getId());
					update.setOpenId(from);

					userinfoLib.updateById(update);
					retMsg.put("Content",
							"you are set to user:" + userinfo.getUsername());

				}
			}

		} else if (content.startsWith("show me seller button")) {
			boolean is = this.wxLib.addTagToUser(from,
					this.wxLib.getSellerTagId());
			if (is) {
				retMsg.put("Content", new StringBuilder(
						"seller button will be shown for you soon").toString());
			} else {
				retMsg.put("Content",
						new StringBuilder("some errors occuring").toString());
			}
		} else if (content.startsWith("remove me from seller")) {
			boolean is = this.wxLib.removeUserTag(from,
					this.wxLib.getSellerTagId());
			if (is) {
				retMsg.put("Content", new StringBuilder(
						"you have been remove from seller list.").toString());
			} else {
				retMsg.put("Content",
						new StringBuilder("some errors occuring").toString());
			}
		} else {
			retMsg.put("Content", this.wxAutoreplyLib
					.getMessageByType(WXConstants.AUTOREPLY_TYPE_SUBSCRIBE));
		}
		retMsg.put("MsgType", "text");
		retMsg.put("CreateTime", "" + (System.currentTimeMillis() / 1000));
		retMsg.put("FromUserName", to);
		retMsg.put("ToUserName", from);
		return retMsg;
	}

	private Map<String, Object> handleEventMsg(Map<String, Object> inputMsg) {
		Map<String, Object> retMsg = new HashMap<String, Object>();
		String event = (String) inputMsg.get("Event");
		String from = (String) inputMsg.get("FromUserName");
		String to = (String) inputMsg.get("ToUserName");
		if ("subscribe".equalsIgnoreCase(event)) {
			retMsg.put("MsgType", "text");
			retMsg.put("CreateTime", "" + (System.currentTimeMillis() / 1000));
			retMsg.put("FromUserName", to);
			retMsg.put("ToUserName", from);
			retMsg.put("Content", this.wxAutoreplyLib
					.getMessageByType(WXConstants.AUTOREPLY_TYPE_SUBSCRIBE));
		}
		return retMsg;
	}

}
