package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.PointsHistory;

public interface PointsHistoryDao extends BaseDao<PointsHistory> {
	
	public List<PointsHistory> getByUserid(int userid);
	
}
