package com.mjitech.model;

import java.util.List;

public class SkuBrand extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 761289930943153844L;

	private String name;
	private String story;
	private String url;
	private int sortNumber;
	private int skuTypeId;
	private String description;
	private String imagePath;
	
	private List<FileModel> images;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	public void setSkuTypeId(int skuTypeId) {
		this.skuTypeId = skuTypeId;
	}

	public int getSkuTypeId() {
		return skuTypeId;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getStory() {
		return story;
	}

}
