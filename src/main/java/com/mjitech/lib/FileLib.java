package com.mjitech.lib;

import java.io.File;

import org.json.JSONObject;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface FileLib {

	public JSONObject uploadFileToTmp(CommonsMultipartFile file, int userId);

	public String moveTmpImageToSkuImage(String tmpImage);

	public void deleteImage(String imagePath);

	public void deleteFile(String filePath);

	public void deleteVideo(String filePath);

	public void deleteFileByFileType(String filePath, int fileType);

	public File getLocalStaticDir();

}
