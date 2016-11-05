package com.mjitech.utils;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.model.FileModel;
import com.mjitech.utils.CommonUtils;

@Component
@Scope("singleton")
public class ImageUtils {

	private static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

	public boolean generateThumbnail(File image, int width, String postfix) {
		boolean is = false;
		String filename = image.getName();
		String filenameWithoutExtension = filename.substring(0,
				filename.lastIndexOf("."));
		String ext = FilenameUtils.getExtension(filename);
		String thumbnailFilename = new StringBuilder(filenameWithoutExtension)
				.append(postfix).append(".").append(ext).toString();
		File thumbnail = new File(image.getParentFile(), thumbnailFilename);
		try {
			Thumbnails.of(image).outputQuality(0.9).width(width)
					.toFile(thumbnail);
			is = true;
		} catch (IOException e) {
			logger.error("error in generating thumnail:" + thumbnail, e);
			is = false;
		}
		return is;
	}

	public JSONObject getImageJSON(FileModel image) {
		JSONObject json = new JSONObject(image);
		CommonUtils.removeCommonUselessProperties(json);
		json.remove("type");
		json.remove("fileType");
		json.remove("relateId");
		return json;
	}

}
