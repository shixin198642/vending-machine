package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.WxAutoreply;

public interface WxAutoreplyDao extends BaseDao<WxAutoreply> {
	
	public List<WxAutoreply> getByCondition(WxAutoreply condition);
}
