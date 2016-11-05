package com.mjitech.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.lib.SellOrderStatusHistoryLib;
import com.mjitech.model.SellOrderStatusHistory;

@Service("sellOrderStatusHistoryService")
public class SellOrderStatusHistoryServiceImpl implements
		com.mjitech.service.SellOrderStatusHistoryService {
	@Autowired
	private SellOrderStatusHistoryLib sellOrderStatusHistoryLib;

	@Override
	public void addHistory(int sellOrderId, int userid, int status) {
		if (sellOrderId > 0 && userid > 0 && status > 0) {
			SellOrderStatusHistory history = new SellOrderStatusHistory();
			history.setSellOrderId(sellOrderId);
			history.setUserid(userid);
			history.setStatus(status);
			history.setStatusDatetime(new Date());
			this.sellOrderStatusHistoryLib.add(history);
		}

	}

}
