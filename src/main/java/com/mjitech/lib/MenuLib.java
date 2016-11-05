package com.mjitech.lib;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.mjitech.model.Menu;

public interface MenuLib extends BaseModelLib<Menu> {
	
	public List<Menu> getByParent(int parentId);
	
	public List<Menu> getAll();
	
	public List<Menu> getFirstMenus();
	
	public List<Menu> getSubMenus(int parent);
	
	public void setCommonMenu(ModelAndView mv, int menuId);
	
}
