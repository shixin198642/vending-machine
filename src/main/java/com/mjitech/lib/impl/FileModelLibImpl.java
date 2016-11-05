package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.FileConstants;
import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.FileModelDao;
import com.mjitech.lib.FileLib;
import com.mjitech.lib.FileModelLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.FileModel;

@Component("fileModelLib")
public class FileModelLibImpl implements FileModelLib {
	private static Logger logger = LoggerFactory
			.getLogger(FileModelLibImpl.class);
	@Autowired
	private FileModelDao fileModelDao;
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private FileLib fileLib;

	private String getFileModelIdKey(int id) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_FILEMODEL_ID)
				.append(id);
		return sb.toString();
	}

	private String getTypeRelateIdKey(int type, int relateId) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_FILEMODEL_TYPE_RELATEID)
				.append(type).append(":").append(relateId);
		return sb.toString();
	}

	@Override
	public FileModel add(FileModel fileModel) {
		if (fileModel.getCreateDatetime() == null) {
			fileModel.setCreateDatetime(new Date());
		}
		if (fileModel.getUpdateDatetime() == null) {
			fileModel.setUpdateDatetime(fileModel.getCreateDatetime());
		}
		this.fileModelDao.add(fileModel);
		if (fileModel.getId() > 0) {
			String key = this.getFileModelIdKey(fileModel.getId());
			this.redisLib.addCache(key, fileModel);
			key = this.getTypeRelateIdKey(fileModel.getType(),
					fileModel.getRelateId());
			this.redisLib.removeCache(key);
			return fileModel;
		}
		return null;
	}

	@Override
	public FileModel getById(int id) {
		String key = this.getFileModelIdKey(id);
		FileModel cache = (FileModel) this.redisLib.getCache(key);
		if (cache == null) {
			cache = this.fileModelDao.getById(id);
			if (cache != null) {
				this.redisLib.addCache(key, cache);
			}
		}
		return cache;
	}

	@Override
	public int update(FileModel file) {
		if (file.getId() > 0) {
			String key = this.getFileModelIdKey(file.getId());
			FileModel old = this.getById(file.getId());
			if (old != null) {
				String typeRelateidKey = this.getTypeRelateIdKey(old.getType(),
						old.getRelateId());
				this.redisLib.removeCache(key);
				this.redisLib.removeCache(typeRelateidKey);
				file.setUpdateDatetime(new Date());
				return this.fileModelDao.update(file);
			}

		}
		return 0;
	}

	@Override
	public int delete(int id) {
		FileModel old = this.getById(id);
		if (old != null) {
			String key = this.getFileModelIdKey(id);
			String typeRelateidKey = this.getTypeRelateIdKey(old.getType(),
					old.getRelateId());
			this.redisLib.removeCache(key);
			this.redisLib.removeCache(typeRelateidKey);
			int deleteRet = this.fileModelDao.delete(id);
			if (deleteRet > 0) {
				fileLib.deleteFileByFileType(old.getFilePath(),
						old.getFileType());
			}

			return deleteRet;
		}
		return 0;
	}

	public List<FileModel> getFilesByTypeRelatedId(int type, int relatedId) {
		String key = this.getTypeRelateIdKey(type, relatedId);
		List<FileModel> files = (List<FileModel>) this.redisLib.getCache(key);
		if (files == null) {
			FileModel condition = new FileModel();
			condition.setType(type);
			condition.setRelateId(relatedId);
			files = this.fileModelDao.getByCondition(condition);
			if (files != null) {
				this.redisLib.addCache(key, (ArrayList<FileModel>) files);
			}
		}
		return files;
	}

	@Override
	public List<FileModel> getSkuImages(int skuId) {
		return this
				.getFilesByTypeRelatedId(FileConstants.TYPE_SKU_IMAGE, skuId);
	}

	@Override
	public List<FileModel> getSkuBrandImages(int skuBrandId) {
		return this.getFilesByTypeRelatedId(FileConstants.TYPE_SKUBRAND_IMAGE,
				skuBrandId);
	}
}
