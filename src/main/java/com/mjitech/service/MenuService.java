package com.mjitech.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface MenuService {
	/**
	 * 
	 * @param mv
	 * @param request
	 * @param menuId can be 0
	 */
	public void leftMenus(ModelAndView mv, HttpServletRequest request, int menuId);
	
}
