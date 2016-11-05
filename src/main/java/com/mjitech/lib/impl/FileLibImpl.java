package com.mjitech.lib.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mjitech.constant.FileConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.lib.FileLib;
import com.mjitech.lib.FileModelLib;
import com.mjitech.model.FileModel;
import com.mjitech.utils.CommonUtils;
import com.mjitech.utils.FileUtils;
import com.mjitech.utils.ImageUtils;

@Component("fileLib")
public class FileLibImpl implements FileLib {
	private static Logger logger = LoggerFactory.getLogger(FileLibImpl.class);
	@Value("${local_static_dir}")
	private String localStaticDir;
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	private FileModelLib fileModelLib;
	@Autowired
	private ImageUtils imageUtils;

	public File getLocalStaticDir() {
		return new File(this.localStaticDir);
	}

	public void deleteImage(String imagePath) {
		this.deleteFileByFileType(imagePath, FileConstants.FILE_TYPE_IMAGE);
	}

	private File getTodayTmpDir() {
		File tmpDir = new File(this.getLocalStaticDir(),
				FileConstants.getDirByType(FileConstants.TYPE_TMP));
		if (!tmpDir.exists()) {
			if (!tmpDir.mkdirs()) {
				return null;
			}
		}
		File todayDir = new File(tmpDir, CommonUtils.getTodayString());
		if (!todayDir.exists()) {
			if (!todayDir.mkdirs()) {
				return null;
			}
		}
		return todayDir;
	}

	private String getTmpFileName(File dir, String ext) {
		StringBuilder baseName = new StringBuilder("").append(System
				.currentTimeMillis());
		String filename = new StringBuilder(baseName).append(".").append(ext)
				.toString();
		int index = 1;
		while (new File(dir, filename).exists()) {
			baseName.append("_").append(index);
			filename = new StringBuilder(baseName).append("_").append(index)
					.append(".").append(ext).toString();
			index++;
		}
		return filename;
	}

	@Override
	public JSONObject uploadFileToTmp(CommonsMultipartFile file, int userId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		File tmpDir = this.getTodayTmpDir();
		String filename = this.getTmpFileName(tmpDir,
				FilenameUtils.getExtension(file.getOriginalFilename()));

		try {
			File targetFile = new File(tmpDir, filename);
			file.transferTo(targetFile);
			String filePath = new StringBuilder(FileConstants.TMP_DIR)
					.append("/").append(tmpDir.getName()).append("/")
					.append(filename).toString();
			FileModel fileModel = new FileModel();
			fileModel.setCreator(userId);
			fileModel.setUpdator(userId);
			fileModel.setFilePath(filePath);
			fileModel.setType(FileConstants.TYPE_TMP);
			fileModel.setFileType(FileConstants.FILE_TYPE_FILE);
			fileModel.setFileSize(targetFile.getTotalSpace());
			fileModel.setOriginalName(file.getOriginalFilename());
			fileModelLib.add(fileModel);
			json.put("file", fileUtils.getFileJSON(fileModel));

		} catch (Exception e) {
			e.printStackTrace();
			json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
			json.put(JSONConstants.RETURN_KEY_ERROR_MESSAGE, "server error");
		}
		return json;
	}

	

	public String moveTmpImageByType(int type, int id, String tmpImage) {
		String newName = null;
		if (tmpImage.startsWith(FileConstants.TMP_DIR)) {
			String targetBaseDirName = FileConstants.getDirByType(type);
			if (targetBaseDirName == null) {
				return newName;
			}
			String idDir = fileUtils.computeDirById(id);

		}
		return newName;
	}

	public String moveTmpImageToSkuImage(String tmpImage) {
		String newName = "";

		if (tmpImage.startsWith(FileConstants.TMP_DIR)) {
			File dir = new File(this.localStaticDir
					+ FileConstants.SKU_IMAGE_DIR);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File from = new File(this.localStaticDir + tmpImage);
			if (from.exists()) {
				File target = new File(dir, from.getName());
				try {
					org.apache.commons.io.FileUtils.moveFile(from, target);
					newName = FileConstants.SKU_IMAGE_DIR + "/"
							+ target.getName();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return newName;
	}

	@Override
	public void deleteFile(String filePath) {
		this.deleteFileByFileType(filePath, FileConstants.FILE_TYPE_FILE);
	}

	@Override
	public void deleteVideo(String filePath) {
		this.deleteFileByFileType(filePath, FileConstants.FILE_TYPE_VIDEO);
	}

	@Override
	public void deleteFileByFileType(String filePath, int fileType) {
		if (fileType == FileConstants.FILE_TYPE_IMAGE
				|| fileType == FileConstants.FILE_TYPE_VIDEO) {
			File fileDir = new File(this.getLocalStaticDir(), filePath)
					.getParentFile();
			if (fileDir.exists()) {
				try {
					org.apache.commons.io.FileUtils.deleteDirectory(fileDir);
				} catch (IOException e) {
					logger.error("error in deleting dir:" + fileDir, e);
				}
			}
		} else if (fileType == FileConstants.FILE_TYPE_FILE) {
			File file = new File(this.getLocalStaticDir(), filePath);
			if (file.exists()) {
				file.delete();
			}
		}
	}

}
