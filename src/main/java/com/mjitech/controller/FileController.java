package com.mjitech.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.FileLib;
import com.mjitech.utils.SessionUtils;

@Controller
public class FileController {
	@Autowired
	private FileLib fileLib;
	@Autowired
	private SessionUtils sessionUtils;

	@RequestMapping(value = WebPageConstants.UPLOAD_TMP_IMAGE, method = RequestMethod.POST)
	public void fileUpload(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject json = fileLib.uploadFileToTmp(file,
				sessionUtils.getUserid(request));
		// System.out.println(file.getName());
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(json.toString());
	}
}
