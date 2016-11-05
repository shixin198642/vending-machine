package com.mjitech.model;

import java.util.Date;

import com.mjitech.utils.PinyinUtils;

public class Userinfo extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4259477693597110163L;
	private String username;
	private String password = "";
	private String displayName = "";
	private String displayNamePinyin = "";
	private int gender = 0;
	private int userType = 0;

	private String email = "";
	private String mobile = "";
	private String image = "";
	private String openId = "";
	private Date wxBindTime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		this.displayNamePinyin = PinyinUtils.getPinyin(displayName);
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int sex) {
		this.gender = sex;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDisplayNamePinyin() {
		return displayNamePinyin;
	}

	public void setDisplayNamePinyin(String displayNamePinyin) {
		this.displayNamePinyin = displayNamePinyin;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenId() {
		return openId;
	}

	public Date getWxBindTime() {
		return wxBindTime;
	}

	public void setWxBindTime(Date wxBindTime) {
		this.wxBindTime = wxBindTime;
	}

}
