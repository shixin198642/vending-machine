package com.mjitech.logdbdao;

import com.mjitech.logdbmodel.Userlog;

public interface UserlogDao {

	public Userlog getById(int id);

	public int add(Userlog userlog);

	public int update(Userlog userlog);

	public int delete(int id);
	/**
	 * this is for testing
	 * @return
	 */
	public Userlog getLatestLog();

}
