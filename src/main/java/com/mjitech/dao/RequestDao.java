package com.mjitech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mjitech.model.Request;

@Repository("requestDao")
public interface RequestDao extends BaseDao<Request>{
	
	public List<Request> getNewByMachine(@Param("machine_id") int machine_id);

	public List<Request> getHistoryByMachine(@Param("machine_id") int machine_id);

	public List<Request> getByMachine(@Param("machine_id") int machine_id);

	public void createRequest(Request req);

	public void setPickingId(@Param("request_id") int requestId, @Param("picking_id") int picking_id);

	
}
