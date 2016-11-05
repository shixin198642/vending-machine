package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.PointsHistory;

public interface PointsHistoryLib extends BaseModelLib<PointsHistory> {
	public List<PointsHistory> getByUserid(int userid);
}
