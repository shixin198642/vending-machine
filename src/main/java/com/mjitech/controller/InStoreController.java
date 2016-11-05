package com.mjitech.controller;

import java.util.List;
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
import com.mjitech.model.Instore;
import com.mjitech.model.Receipt;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseEnum;
import com.mjitech.service.MenuService;
import com.mjitech.service.ReceiptService;
import com.mjitech.service.InstoreService;
import com.mjitech.service.WarehouseService;

@Controller("instoreController")
public class InStoreController {

	@Autowired
	WarehouseService warehouseService;
	@Autowired
	private ReceiptService receiptService;	
	@Autowired
	private MenuService menuService;
	@Autowired
	private InstoreService instoreService;

	
	@RequestMapping(value = WebPageConstants.STOCKIN_REQUEST_HOME)
	public ModelAndView stockinRequest(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.STOCKIN_REQUEST_VIEW);
		List<Warehouse> warehouseList = warehouseService.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		
		if(warehouseList.size() >0 ){
			Warehouse wh = warehouseList.get(0);
			List<Receipt> receiptList = receiptService.listReceiptItemByWarehouse(wh.getId(), "submitted");
			mv.addObject("receiptList", receiptList);
		}		
		this.menuService.leftMenus(mv, request, MenuConstants.STOCKIN_REQUEST_MENU_ID);
		return mv;	
	}

	@RequestMapping(value = WebPageConstants.STOCKIN_HOME)
	public ModelAndView stockin(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.STOCKIN_VIEW);
		
		List<Warehouse> warehouseList = warehouseService.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		
		if(warehouseList.size() >0 ){
			Warehouse wh = warehouseList.get(0);
			List<Instore> instoreList = instoreService.listInstore(wh.getId());
			mv.addObject("instoreList", instoreList);
		}		
		
		this.menuService.leftMenus(mv, request, MenuConstants.STOCKIN_MENU_ID);
		return mv;	
	}	
	
}
