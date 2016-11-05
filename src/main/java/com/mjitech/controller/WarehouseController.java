package com.mjitech.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.mjitech.model.StockIn;
import com.mjitech.model.StockOut;
import com.mjitech.model.Warehouse;
import com.mjitech.service.InventoryService;
import com.mjitech.service.MenuService;
import com.mjitech.service.RequestService;
import com.mjitech.service.WarehouseService;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;

@Controller
public class WarehouseController {
	
	private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);
	
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	InventoryService inventoryService;
	@Autowired
	private MenuService menuService;	
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	RequestService requestService;

	
	@RequestMapping(value = WebPageConstants.WAREHOUSE_INVENTORY_HOME)
	public ModelAndView warehouseInventory(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.WAREHOUSE_INVENTORY_VIEW);
		
		int id = requestUtils.getIntValue(params, "warehouse_id");
		this.warehouseService.prepareWarehouseInventoryList(sessionUtils.getUserid(request), id, mv);	
		
		this.menuService.leftMenus(mv, request, MenuConstants.WAREHOUSE_INVENTORY_MENU_ID);
		return mv;
	}	
	
	
	@RequestMapping(value = WebPageConstants.GET_INVENTORY_BY_WAREHOUSE)
	public ModelAndView getInventoryById(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.WAREHOUSE_INVENTORY_VIEW);
		int id = requestUtils.getIntValue(params, "warehouse_id");
		this.warehouseService.prepareWarehouseInventoryList(sessionUtils.getUserid(request), id, mv);
//		List<Warehouse> warehouseList = warehouseService.listAll(WarehouseEnum.WAREHOUSE);
//		mv.addObject("warehouseList", warehouseList);
//		mv.addObject("warehouseId", id);
//		
//		if(id >0 ){
//			List<Inventory> inventoryList = inventoryService.getByWarehouse(id);
//			mv.addObject("inventoryList", inventoryList);
//		}		
		this.menuService.leftMenus(mv, request, MenuConstants.WAREHOUSE_INVENTORY_MENU_ID);
		return mv;
	}
	
//	@RequestMapping(value = WebPageConstants.GET_INVENTORY_BY_WAREHOUSE, produces = WebPageConstants.JSON_PRODUCES)
//	public @ResponseBody
//	       String getInventoryById(@RequestParam("id") String id, HttpServletRequest request,
//			HttpServletResponse response) {
//		int sid = Integer.parseInt(id);
//		List<Inventory> inventoryList = inventoryService.getByWarehouse(sid);
//		return inventoryList.toString();
//	}
		
	
	@RequestMapping(value = WebPageConstants.WAREHOUSE_ADD, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String add(@RequestBody Warehouse warehouse,
			HttpServletRequest request, HttpServletResponse response){

		if (logger.isTraceEnabled()) {
			logger.trace("enter add warehouse:" + warehouse.toString());
		}
		
		JSONObject retJson = warehouseService.add(warehouse);
		String ret = retJson.toString();
		
		if (logger.isTraceEnabled()) {
			logger.trace("exit add warehouse:" + ret);
		}
		return ret;
	}

	@RequestMapping(value = WebPageConstants.WAREHOUSE_GET)
	public ModelAndView get(@RequestParam("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		int sid = Integer.parseInt(id);
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail warehouse-id:" + id);
		}
		warehouseService.get(sid);;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail warehouse-id:" + id);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.WAREHOUSE_INFO_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.WAREHOUSE_INVENTORY_MENU_ID);
		
		return mv;		
	}
	


	@RequestMapping(value = WebPageConstants.WAREHOUSE_DELETE, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String delete(@RequestBody Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) {
		int id = requestUtils.getIntValue(params, "id");
		if (logger.isTraceEnabled()) {
			logger.trace("enter delete warehouse-id:" + id);
		}
		JSONObject retJson = warehouseService.delete(id);
		if (logger.isTraceEnabled()) {
			logger.trace("exit delete warehouse-id:" + id);
		}
		String ret = retJson.toString();
		return ret;		
	}
	
	@RequestMapping(value = WebPageConstants.WAREHOUSE_REQUEST, produces = WebPageConstants.JSON_PRODUCES)
	public String request(HttpServletRequest request, HttpServletResponse response) {
		return "";	
	}	


	@RequestMapping(value = WebPageConstants.WAREHOUSE_UPDATE, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String update(@RequestBody Warehouse warehouse, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isTraceEnabled()) {
			logger.trace("enter update warehouse:" + warehouse.getName());
		}
		JSONObject retJson = warehouseService.update(warehouse);
		if (logger.isTraceEnabled()) {
			logger.trace("exit update warehouse:" + warehouse.getName());
		}
		String ret = retJson.toString();
		return ret;		
	}
	
	@RequestMapping(value = WebPageConstants.STOCKIN_ADD, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String addStockin(@RequestBody StockIn stockin, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject retJson = warehouseService.addStockin(stockin);
		String ret = retJson.toString();
		return ret;		
	}

	@RequestMapping(value = WebPageConstants.STOCKIN_ENTER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String enterStockin(@RequestBody Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) {
		int id = requestUtils.getIntValue(params, "id");
		JSONObject retJson = warehouseService.enterStockin(id);
		String ret = retJson.toString();
		return ret;		
	}
	
	@RequestMapping(value = WebPageConstants.STOCKIN_LIST, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String listStockin(@RequestBody Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject retJson = warehouseService.listStockin();
		String ret = retJson.toString();
		return ret;		
	}	
	
	@RequestMapping(value = WebPageConstants.STOCKOUT_ADD, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String addStockout(@RequestBody StockOut stockout, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject retJson = warehouseService.addStockout(stockout);
		String ret = retJson.toString();
		return ret;		
	}
	
	@RequestMapping(value = WebPageConstants.STOCKOUT_EXIT, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String exitStockout(@RequestBody Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) {
		int id = requestUtils.getIntValue(params, "id");
		JSONObject retJson = warehouseService.exitStockout(id);
		String ret = retJson.toString();
		return ret;		
	}	

	@RequestMapping(value = WebPageConstants.STOCKOUT_LIST, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody String listStockout(@RequestBody Map<String, String> params, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject retJson = warehouseService.listStockout();
		String ret = retJson.toString();
		return ret;		
	}

	/**
	 * 根据ID查询仓库信息
	 */
	@RequestMapping(value = WebPageConstants.FIND_WAREHOUSE, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String find(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail warehouse-id:" + id);
		}
		JSONObject retJson = this.warehouseService.get(Integer.parseInt(id));;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail warehouse-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
}
