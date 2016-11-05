package com.mjitech.dao;

import java.util.List;

import com.mjitech.model.FileModel;

public interface FileModelDao extends BaseDao<FileModel> {
	public List<FileModel> getByCondition(FileModel condition);
}
