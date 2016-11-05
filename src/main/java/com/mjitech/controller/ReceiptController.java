package com.mjitech.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.MenuConstants;
import com.mjitech.constant.ViewConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.model.Receipt;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseEnum;
import com.mjitech.service.MenuService;
import com.mjitech.service.ReceiptService;
import com.mjitech.service.WarehouseService;
import com.mjitech.utils.RequestUtils;

@Controller("receiptController")
public class ReceiptController {

	@Autowired
	WarehouseService warehouseService;
	@Autowired
	private ReceiptService receiptService;	
	@Autowired
	private MenuService menuService;
	@Autowired
	private RequestUtils requestUtils;
		
	@RequestMapping(value = WebPageConstants.RECEIPT_HOME)
	public ModelAndView listNewReceipts(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.RECEITP_NEW_VIEW);
		List<Warehouse> warehouseList = warehouseService.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		
		if(warehouseList.size() >0 ){
			Warehouse wh = warehouseList.get(0);
			List<Receipt> receiptList = receiptService.listReceiptByWarehouse(wh.getId(), "all");
			mv.addObject("receiptList", receiptList);
		}
		this.menuService.leftMenus(mv, request, MenuConstants.RECEIPT_MENU_ID);
		return mv;	
	}
	
	
	@RequestMapping(value = WebPageConstants.RECEIPT_HISTORY)
	public ModelAndView listHistoryReceipts(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.RECEITP_HISTORY_VIEW);
		List<Warehouse> warehouseList = warehouseService.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		
		if(warehouseList.size() >0 ){
			Warehouse wh = warehouseList.get(0);
			List<Receipt> receiptList = receiptService.listReceiptByWarehouse(wh.getId(), "complete");
			mv.addObject("receiptList", receiptList);
		}
		this.menuService.leftMenus(mv, request, MenuConstants.RECEIPT_MENU_ID);
		return mv;	
	}		
	
	@RequestMapping(value=WebPageConstants.RECEIPT_RRINT)
	public ModelAndView printReceipt(HttpServletRequest request,
			@RequestParam Map<String, String> params){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.RECEIPT_PRINT_VIEW);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.RECEIPT_BY_WAREHOUSE)
	public ModelAndView getReceiptsByWarehouseID(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.RECEITP_NEW_VIEW);
		List<Warehouse> warehouseList = warehouseService.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		
		if(warehouseList.size() >0 ){
			
		}
		this.menuService.leftMenus(mv, request, MenuConstants.RECEIPT_MENU_ID);
		return mv;	
	}
	

	
	@RequestMapping(value = WebPageConstants.RECEIPT_SEARCH_BY_ORDER)
	public ModelAndView searchReceiptByOrder(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.RECEITP_NEW_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.RECEIPT_MENU_ID);
		return mv;	
	}
	
	@RequestMapping(value = WebPageConstants.RECEIPT_SEARCH_BY_SKU)
	public ModelAndView searchReceiptBySku(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.RECEITP_NEW_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.RECEIPT_MENU_ID);
		return mv;	
	}
	
	
	
	@RequestMapping(value = WebPageConstants.RECEIPT_LIST, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String list(@RequestBody Receipt receipt,
			HttpServletRequest request, HttpServletResponse response){	
		JSONObject retJson = receiptService.addReceipt(receipt);
		String ret = retJson.toString();
		return ret;
	}	
	
	@RequestMapping(value = WebPageConstants.RECEIPT_ADD, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String add(@RequestBody Receipt receipt,
			HttpServletRequest request, HttpServletResponse response){	
		JSONObject retJson = receiptService.addReceipt(receipt);
		String ret = retJson.toString();
		return ret;
	}
	
	@RequestMapping(value = WebPageConstants.RECEIPT_GET, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String get(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response){
		int id = requestUtils.getIntValue(params, "id");
		JSONObject retJson = receiptService.getReceipt(id);
		String ret = retJson.toString();
		return ret;
	}		

	
	@RequestMapping(value = WebPageConstants.RECEIPT_DELETE, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String delete(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response){	
		int id = requestUtils.getIntValue(params, "id");
		JSONObject retJson = receiptService.deleteReceipt(id);
		String ret = retJson.toString();
		return ret;
	}		

	@RequestMapping(value = WebPageConstants.RECEIPT_CHECKING, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String checking(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response){
		int id = requestUtils.getIntValue(params, "id");
		String result = requestUtils.getStringValue(params, "result");
		String comments = requestUtils.getStringValue(params, "comments");
		JSONObject retJson = receiptService.checking(id, result, comments);
		String ret = retJson.toString();
		return ret;
	}
	
	@RequestMapping(value = WebPageConstants.RECEIPT_RECEIVING, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String receiving(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response){
		int id = requestUtils.getIntValue(params, "id");
		String result = requestUtils.getStringValue(params, "result");
		String comments = requestUtils.getStringValue(params, "comments");
		JSONObject retJson = receiptService.receiving(id, result, comments);
		String ret = retJson.toString();
		return ret;
	}	

	@RequestMapping(value = WebPageConstants.RECEIPT_INSPECTING, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String inspecting(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response){
		int id = requestUtils.getIntValue(params, "id");
		int item_id = requestUtils.getIntValue(params, "item_id");
		int pass = requestUtils.getIntValue(params, "pass");
		int issue = requestUtils.getIntValue(params, "issue");
		int lost = requestUtils.getIntValue(params, "lost");
		
		JSONObject retJson = receiptService.inspecting(id, item_id, pass, issue, lost);
		String ret = retJson.toString();
		return ret;
	}	
	
}
