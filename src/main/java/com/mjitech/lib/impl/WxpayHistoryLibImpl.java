package com.mjitech.lib.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.dao.WxpayHistoryDao;
import com.mjitech.lib.WxpayHistoryLib;
import com.mjitech.model.WxpayHistory;

@Component("wxpayHistoryLib")
public class WxpayHistoryLibImpl implements WxpayHistoryLib {
	@Autowired
	private WxpayHistoryDao wxpayHistoryDao;

	@Override
	public WxpayHistory getById(int id) {

		return wxpayHistoryDao.getById(id);
	}

	@Override
	public WxpayHistory add(WxpayHistory t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		this.wxpayHistoryDao.add(t);
		if (t.getId() > 0) {
			return t;
		}
		return null;
	}

	@Override
	public int update(WxpayHistory t) {
		if (t.getId() > 0) {
			if (t.getUpdateDatetime() == null) {
				t.setUpdateDatetime(new Date());
			}
			int ret = this.wxpayHistoryDao.update(t);
			return ret;
		}
		return 0;
	}

	@Override
	public int delete(int id) {

		return this.wxpayHistoryDao.delete(id);
	}

}
