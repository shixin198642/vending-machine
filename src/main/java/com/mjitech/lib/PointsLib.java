package com.mjitech.lib;

import com.mjitech.model.Points;

public interface PointsLib extends BaseModelLib<Points> {
	
	public Points getByUserid(int userid);
	
	
	
}
