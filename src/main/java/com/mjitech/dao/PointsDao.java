package com.mjitech.dao;

import com.mjitech.model.Points;

public interface PointsDao extends BaseDao<Points> {

	public Points getByUserid(int userid);
}
