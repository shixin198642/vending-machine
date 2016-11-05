package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.FileModel;

public interface FileModelLib {

	public FileModel add(FileModel fileModel);

	public FileModel getById(int id);

	public int update(FileModel file);

	public int delete(int id);

	public List<FileModel> getSkuImages(int skuId);

	List<FileModel> getSkuBrandImages(int skuBrandId);

}
