package com.mjitech.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mjitech.model.Outstore;

@Repository("outStoreDao")
public interface OutstoreDao extends BaseDao<Outstore> {
	List<Outstore> listOutstore();

	List<Outstore> getByCondition(Outstore condition);
}
