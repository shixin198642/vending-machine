package com.mjitech.lib.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.FileConstants;
import com.mjitech.constant.ImageConstants;
import com.mjitech.lib.FileLib;
import com.mjitech.lib.FileModelLib;
import com.mjitech.lib.ImageLib;
import com.mjitech.model.FileModel;
import com.mjitech.utils.FileUtils;
import com.mjitech.utils.ImageUtils;

@Component("imageLib")
public class ImageLibImpl implements ImageLib {
	private static Logger logger = LoggerFactory.getLogger(ImageLibImpl.class);
	@Autowired
	private FileLib fileLib;
	@Autowired
	private FileModelLib fileModelLib;
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	private ImageUtils imageUtils;

	@Override
	public FileModel converTmpImageToSkuImage(FileModel tmpImage, int skuId,
			int userid) {

		return this.converTmpImageToType(tmpImage, skuId, userid,
				FileConstants.TYPE_SKU_IMAGE);
	}

	private FileModel converTmpImageToType(FileModel tmpImage, int relateId,
			int userid, int type) {
		if (tmpImage != null && tmpImage.getType() == FileConstants.TYPE_TMP) {
			File imageFile = new File(fileLib.getLocalStaticDir(),
					tmpImage.getFilePath());
			if (imageFile.exists()) {
				String idDir = fileUtils.computeDirById(relateId);
				String dirName = FileConstants.getDirByType(type) + "/" + idDir;
				File dir = new File(fileLib.getLocalStaticDir(), dirName);
				if (!dir.exists()) {
					if (!dir.mkdirs()) {
						return null;
					}
				}
				try {
					File newImage = new File(dir, imageFile.getName());
					int index = 1;
					while (newImage.exists()) {
						newImage = new File(dir,
								FilenameUtils.getBaseName(imageFile.getName())
										+ "_"
										+ index
										+ "."
										+ FilenameUtils.getExtension(imageFile
												.getName()));
						index++;
					}
					org.apache.commons.io.FileUtils.moveFile(imageFile,
							newImage);
					// generate 3 thumbails of big, middle, small
					imageUtils.generateThumbnail(newImage,
							ImageConstants.DEFAULT_BIG_THUMBNAIL_WIDTH,
							ImageConstants.BIG_THUMBNAIL_POSTFIX);
					imageUtils.generateThumbnail(newImage,
							ImageConstants.DEFAULT_MIDDLE_THUMBNAIL_WIDTH,
							ImageConstants.MIDDLE_THUMBNAIL_POSTFIX);
					imageUtils.generateThumbnail(newImage,
							ImageConstants.DEFAULT_SMALL_THUMBNAIL_WIDTH,
							ImageConstants.SMALL_THUMBNAIL_POSTFIX);
					tmpImage.setFilePath(dirName + newImage.getName());
					tmpImage.setType(type);
					tmpImage.setRelateId(relateId);
					tmpImage.setUpdator(userid);
					fileModelLib.update(tmpImage);
					return tmpImage;
				} catch (IOException e) {
					logger.error("failed in moving tmp file to " + dir, e);
				}
			}

		}
		return null;

	}

}
