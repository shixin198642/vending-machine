package com.mjitech.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.JSONConstants;
import com.mjitech.lib.SkuLib;
import com.mjitech.model.Sku;
import com.mjitech.service.CommonService;
import com.mjitech.utils.SkuUtils;

@Component("commonService")
public class CommonServiceImpl implements CommonService {
	@Autowired
	private SkuLib skuLib;
	@Autowired
	private SkuUtils skuUtils;

	@Override
	public JSONObject commonSearch(String input) {
		JSONObject ret = new JSONObject();
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
		if (StringUtils.isNotEmpty(input)) {
			if (StringUtils.isNumeric(input)) {
				int id = Integer.parseInt(input);
				Sku sku = this.skuLib.getById(id);
				if (sku != null) {
					ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
					ret.put("sku", skuUtils.getSkuJSON(sku));
				}
			} else if (input.toLowerCase().startsWith("u")) {
				Sku sku = this.skuLib.getSkuBySkuNumber(input);
				if (sku != null) {
					ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
					ret.put("sku", skuUtils.getSkuJSON(sku));
				}
			}
		}

		return ret;
	}

}
