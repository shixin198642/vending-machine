package com.mjitech.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.MenuConstants;
import com.mjitech.lib.LoginLib;
import com.mjitech.lib.MenuLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.model.Menu;
import com.mjitech.model.Userinfo;
import com.mjitech.service.MenuService;
import com.mjitech.utils.SessionUtils;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	private static final Logger logger = LoggerFactory
			.getLogger(MenuServiceImpl.class);
	@Autowired
	private MenuLib menuLib;
	@Autowired
	private LoginLib loginLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private SessionUtils sessionUtils;

	@Override
	public void leftMenus(ModelAndView mv, HttpServletRequest request,
			int menuId) {
		int selectedMenuId = 0;
		int selectedParentId = 0;
		if (menuId > 0) {
			Menu menu = menuLib.getById(menuId);
			if (menu != null && menu.getParentId() == 0) {
				selectedParentId = menuId;
			} else if (menu != null) {
				selectedParentId = menu.getParentId();
				selectedMenuId = menu.getId();
			}
		}
		if (selectedMenuId > 0 || selectedParentId > 0) {
			String param = request.getParameter(MenuConstants.MENU_PARAM_NAME);

			if (param != null && NumberUtils.isDigits(param)) {
				selectedMenuId = Integer.parseInt(param);
			}
			if (selectedMenuId > 0) {
				// selectedParentId = selectedParentId;
				Menu selectedMenu = menuLib.getById(selectedMenuId);
				if (selectedMenu != null && selectedMenu.getParentId() > 0) {
					selectedParentId = selectedMenu.getParentId();
				}
			}
		}

		List<Menu> menus = menuLib.getByParent(0);
		int index = 0;
		for (Menu menu : menus) {
			if (menu.getId() == selectedParentId
					|| (selectedParentId == 0 && index == 0)) {
				menu.setSelected(true);
				List<Menu> subs = this.menuLib.getSubMenus(menu.getId());
				int subIndex = 0;
				for (Menu subMenu : subs) {
					if (subMenu.getId() == selectedMenuId
							|| (selectedMenuId == 0 && subIndex == 0)) {
						subMenu.setSelected(true);
					}
					subIndex++;
				}
				menu.setSubMenus(subs);
			} else {
				menu.setSubMenus(this.menuLib.getSubMenus(menu.getId()));
			}
			index++;
		}
		if (mv != null) {
			mv.addObject("menus", menus);
		} else {
			request.setAttribute("menus", menus);
		}

		if (loginLib.isLogin(request)) {
			Userinfo user = userinfoLib
					.getById(sessionUtils.getUserid(request));
			if (mv != null) {
				mv.addObject("myinfo", user);
			} else {
				request.setAttribute("myinfo", user);
			}
		}
	}

}
