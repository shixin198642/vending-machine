package com.mjitech.constant;

import java.util.HashMap;
import java.util.Map;

public class FileConstants {

	/**
	 * 临时文件目录
	 */
	public final static String TMP_DIR = "/tmp";
	
	public final static String SKU_IMAGE_DIR = "/sku";
	
	public final static String SKUBRAND_IMAGE_DIR = "/skubrand";

	/**
	 * 文件分类：临时文件
	 */
	public final static int TYPE_TMP = 1;
	/**
	 * 文件分类：商品图片
	 */
	public final static int TYPE_SKU_IMAGE = 2;
	/**
	 * 文件分类：品牌图片
	 */
	public final static int TYPE_SKUBRAND_IMAGE = 3;

	/**
	 * 文件类型：普通文件
	 */
	public final static int FILE_TYPE_FILE = 1;
	/**
	 * 文件类型：图片文件
	 */
	public final static int FILE_TYPE_IMAGE = 2;
	/**
	 * 文件类型：视频文件
	 */
	public final static int FILE_TYPE_VIDEO = 3;

	public static Map<Integer, String> TYPE_DIR_MAPPING = new HashMap<Integer, String>();
	static {
		TYPE_DIR_MAPPING.put(TYPE_SKU_IMAGE, SKU_IMAGE_DIR);
		TYPE_DIR_MAPPING.put(TYPE_TMP, TMP_DIR);
		TYPE_DIR_MAPPING.put(TYPE_SKUBRAND_IMAGE, SKUBRAND_IMAGE_DIR);
	}

	public final static String getDirByType(int type) {
		if (TYPE_DIR_MAPPING.containsKey(type)) {
			return TYPE_DIR_MAPPING.get(type);
		}
		return null;
	}

}
