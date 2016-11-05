package com.mjitech.model;

import java.util.List;

public class Menu extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1448470763262678417L;

	private int parentId;
	private String name;
	private String url;
	private String iconClass;
	private int sortNumber;

	private List<Menu> subMenus;
	private boolean selected;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
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

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("Menu: id=").append(this.getId())
				.append(" name=").append(this.getName()).append(" url=")
				.append(this.getUrl()).append(" sortNumber=")
				.append(this.getSortNumber()).append(" subMenus:")
				.append(this.getSubMenus()).append(" selected=")
				.append(this.isSelected()).append(" iconClass=")
				.append(this.getIconClass());
		return sb.toString();
	}
}
