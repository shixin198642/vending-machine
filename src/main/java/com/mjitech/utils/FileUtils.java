package com.mjitech.utils;

import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjitech.model.FileModel;

@Component
@Scope("singleton")
public class FileUtils {

	public JSONObject getFileJSON(FileModel file) {
		JSONObject json = new JSONObject(file);
		json.remove("updateDatetime");
		json.remove("updator");
		json.remove("createDatetime");
		json.remove("creator");
		json.remove("perpage");
		json.remove("begin");
		return json;
	}

	public String computeDirById(int id) {
		StringBuilder dir = new StringBuilder("");
		int firstDigit = id / 1000 / 1000;
		int secondDigit = (id - firstDigit * 1000 * 1000) / 1000;
		int thirdDigit = id - firstDigit * 1000 * 1000 - secondDigit * 1000;
		dir.append(firstDigit).append("/").append(secondDigit).append("/")
				.append(thirdDigit).append("/");
		return dir.toString();
	}
}
