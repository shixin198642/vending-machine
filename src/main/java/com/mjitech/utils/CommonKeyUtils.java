package com.mjitech.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.lib.CommonKeyLib;
import com.mjitech.model.CommonKey;

@Component
@Scope("singleton")
public class CommonKeyUtils {
	
	@Autowired
	private CommonKeyLib commonKeyLib;
	
	public int getCommonKey(String keyName){
		CommonKey commonKey = new CommonKey();
		commonKey.setKeyName(keyName);
		return commonKeyLib.getCommonKey(commonKey);
	}

}
