package com.mjitech.lib.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.dao.OperationLogDao;
import com.mjitech.lib.OperationLogLib;
import com.mjitech.model.OperationLog;

@Component("operationLogLib")
public class OperationLogLibImpl implements OperationLogLib {
	@Autowired
	private OperationLogDao operationLogDao;

	@Override
	public OperationLog getById(int id) {

		return this.operationLogDao.getById(id);
	}

	@Override
	public OperationLog add(OperationLog t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		this.operationLogDao.add(t);
		if (t.getId() > 0) {
			return t;
		}
		return null;
	}

	@Override
	public int update(OperationLog t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {

		return this.operationLogDao.delete(id);
	}

}
