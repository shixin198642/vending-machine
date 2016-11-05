package com.mjitech.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.MenuConstants;
import com.mjitech.constant.ViewConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.service.MenuService;

@Controller("deliveryController")
public class DeliveryController {
	
	@Autowired
	private MenuService menuService;
		
	@RequestMapping(value = WebPageConstants.DELIVERY_HOME)
	public ModelAndView delivery(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.DELIVERY_LIST_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.DELIVERY_MENU_ID);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.GET_DELIVERY)
	public ModelAndView getDelivery(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.DELIVERY_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.DELIVERY_MENU_ID);
		return mv;
	}
	
}
