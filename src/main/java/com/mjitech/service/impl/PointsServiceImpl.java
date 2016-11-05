package com.mjitech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.constant.PointsConstants;
import com.mjitech.constant.PointsHistoryConstants;
import com.mjitech.lib.PointsHistoryLib;
import com.mjitech.lib.PointsLib;
import com.mjitech.model.Points;
import com.mjitech.model.PointsHistory;
import com.mjitech.service.PointsService;

@Service("pointsService")
public class PointsServiceImpl implements PointsService {
	@Autowired
	private PointsLib pointsLib;
	@Autowired
	private PointsHistoryLib pointsHistoryLib;

	@Override
	public Points addUserPoints(int userid, int point) {
		if (point <= 0) {
			return null;
		}
		Points points = this.pointsLib.getByUserid(userid);
		if (points == null) {
			points = new Points();
			points.setUserid(userid);
			points.setTotal(point);
			points.setType(PointsConstants.POINTS_TYPE_DEFAULT);
			points = this.pointsLib.add(points);
		} else {
			Points update = new Points();
			update.setId(points.getId());
			update.setTotal(points.getTotal() + point);
			points.setTotal(update.getTotal());
			this.pointsLib.update(update);
		}
		if (points != null) {
			PointsHistory history = new PointsHistory();
			history.setUserid(userid);
			history.setCount(point);
			history.setType(PointsHistoryConstants.POINTS_HISTORY_TYPE_BUY_ADD);
			this.pointsHistoryLib.add(history);
		}
		return points;
	}

}
