package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.MenuDao;
import com.mjitech.lib.MenuLib;
import com.mjitech.lib.RedisLib;
import com.mjitech.model.Menu;

@Component("menuLib")
public class MenuLibImpl implements MenuLib {
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RedisLib redisLib;

	private String getMenuIdCacheKey(int id) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_MENU_ID).append(id);
		return sb.toString();
	}

	private String getMenuAllCacheKey() {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_MENU_ALL);
		return sb.toString();
	}

	private String getMenuParentCacheKey(int parentId) {
		StringBuilder sb = new StringBuilder(
				RedisKeyConstants.REDIS_CACHE_KEY_PREFIX_MENU_PARENTID)
				.append(parentId);
		return sb.toString();
	}

	private void removeCache(Menu menu) {
		this.redisLib.removeCache(this.getMenuAllCacheKey());
		if (menu.getId() > 0) {
			this.redisLib.removeCache(this.getMenuIdCacheKey(menu.getId()));
		}
		if (menu.getParentId() > 0) {
			this.redisLib.removeCache(this.getMenuParentCacheKey(menu
					.getParentId()));
		}
	}

	@Override
	public Menu getById(int id) {
		String key = this.getMenuIdCacheKey(id);
		Menu cache = (Menu) this.redisLib.getCache(key);
		if (cache != null) {
			return cache;
		}
		cache = this.menuDao.getById(id);
		this.redisLib.addCache(key, cache);
		return cache;
	}

	@Override
	public Menu add(Menu menu) {
		if (menu.getCreateDatetime() == null) {
			menu.setCreateDatetime(new Date());
		}
		if (menu.getUpdateDatetime() == null) {
			menu.setUpdateDatetime(menu.getCreateDatetime());
		}
		this.menuDao.add(menu);
		if (menu.getId() > 0) {
			this.removeCache(menu);
			return menu;
		}
		return null;
	}

	@Override
	public int update(Menu menu) {
		if (menu.getId() > 0) {
			if (menu.getUpdateDatetime() == null) {
				menu.setUpdateDatetime(new Date());
			}
			this.menuDao.update(menu);
			this.removeCache(menu);
		}
		return 0;
	}

	@Override
	public int delete(int id) {
		Menu old = this.getById(id);
		if (old != null) {
			int ret = this.menuDao.delete(id);
			this.removeCache(old);
			return ret;
		}
		return 0;
	}

	@Override
	public List<Menu> getByParent(int parentId) {
		String key = this.getMenuParentCacheKey(parentId);
		List<Menu> menus = (List<Menu>) this.redisLib.getCache(key);
		if (menus != null && menus.size() > 0) {
			return menus;
		}
		menus = this.menuDao.getByParent(parentId);
		this.redisLib.addCache(key, (ArrayList) menus);
		return menus;
	}

	@Override
	public List<Menu> getAll() {
		String key = this.getMenuAllCacheKey();
		List<Menu> menus = (List<Menu>) this.redisLib.getCache(key);
		if (menus!=null) {
			return menus;
		}
		menus = this.menuDao.getAll();
		this.redisLib.addCache(key, (ArrayList) menus);
		return menus;
	}

	@Override
	public List<Menu> getFirstMenus() {

		return this.getByParent(0);
	}

	@Override
	public List<Menu> getSubMenus(int parent) {

		return this.getByParent(parent);
	}

	@Override
	public void setCommonMenu(ModelAndView mv, int menuId) {
		
		
	}

}
