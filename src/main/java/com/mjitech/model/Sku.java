package com.mjitech.model;

import java.util.Date;
import java.util.List;

public class Sku extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4242072707841913627L;
	
	private String skuNumber;
	private int uniqueNumber;
	private int parentCategory;
	private String barcode;
	private String name;
	private String shortName;
	private int status;
	private int imageId;
	private String imagePath;
	private int brand;
	private String brandName;
	private SkuBrand skuBrand;
	private int country;
	private String countryName;
	private int category;
	private String categoryName;
	private String unit;
	private double msrp;
	private String tags;
	private String remarks;
	private Date publishTime;
	private int expirationDays;
	private int length;
	private int width;
	private int height;
	private int safeStock;
	private int minStock;
	private int maxStock;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(int safeStock) {
		this.safeStock = safeStock;
	}

	public int getMinStock() {
		return minStock;
	}

	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}

	public int getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(int maxStock) {
		this.maxStock = maxStock;
	}

	private List<SkuSpec> specs;

	private List<SkuAttribute> attributes;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name.length()>50){
			name = name.substring(0,50);
		}
		this.name = name;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String image) {
		this.imagePath = image;
	}

	public int getBrand() {
		return brand;
	}

	public void setBrand(int brand) {
		this.brand = brand;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getMsrp() {
		return msrp;
	}

	public void setMsrp(double msrp) {
		this.msrp = msrp;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public List<SkuSpec> getSpecs() {
		return specs;
	}

	public void setSpecs(List<SkuSpec> specs) {
		this.specs = specs;
	}

	public List<SkuAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<SkuAttribute> attributes) {
		this.attributes = attributes;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public void setExpirationDays(int expirationDays) {
		this.expirationDays = expirationDays;
	}

	public int getExpirationDays() {
		return expirationDays;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setSkuNumber(String skuNumber) {
		this.skuNumber = skuNumber;
	}

	public String getSkuNumber() {
		return skuNumber;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setParentCategory(int parentCategory) {
		this.parentCategory = parentCategory;
	}

	public int getParentCategory() {
		return parentCategory;
	}

	public void setUniqueNumber(int uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}

	public int getUniqueNumber() {
		return uniqueNumber;
	}

	public void setSkuBrand(SkuBrand skuBrand) {
		this.skuBrand = skuBrand;
	}

	public SkuBrand getSkuBrand() {
		return skuBrand;
	}

}
