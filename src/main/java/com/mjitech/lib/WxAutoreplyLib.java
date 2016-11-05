package com.mjitech.lib;

import com.mjitech.model.WxAutoreply;

public interface WxAutoreplyLib extends BaseModelLib<WxAutoreply> {
	
	public WxAutoreply getByType(int type);
	
	public String getMessageByType(int type);
	
}
