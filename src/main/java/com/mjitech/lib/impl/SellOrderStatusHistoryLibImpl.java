package com.mjitech.lib.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.dao.SellOrderStatusHistoryDao;
import com.mjitech.lib.SellOrderStatusHistoryLib;
import com.mjitech.model.SellOrderStatusHistory;

@Component("sellOrderStatusHistoryLib")
public class SellOrderStatusHistoryLibImpl implements SellOrderStatusHistoryLib {
	@Autowired
	private SellOrderStatusHistoryDao sellOrderStatusHistoryDao;

	@Override
	public SellOrderStatusHistory getById(int id) {

		return sellOrderStatusHistoryDao.getById(id);
	}

	@Override
	public SellOrderStatusHistory add(SellOrderStatusHistory t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		if (t.getStatusDatetime() == null) {
			t.setStatusDatetime(t.getCreateDatetime());
		}
		this.sellOrderStatusHistoryDao.add(t);
		if (t.getId() > 0) {
			return t;
		}
		return null;
	}

	@Override
	public int update(SellOrderStatusHistory t) {
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(new Date());
		}

		return this.sellOrderStatusHistoryDao.update(t);
	}

	@Override
	public int delete(int id) {

		return this.sellOrderStatusHistoryDao.delete(id);
	}

}
