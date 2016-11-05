package com.mjitech.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.MenuConstants;
import com.mjitech.constant.ViewConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.Request;
import com.mjitech.model.Sku;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseEnum;
import com.mjitech.service.MenuService;
import com.mjitech.service.RequestService;
import com.mjitech.service.SkuService;
import com.mjitech.service.WarehouseService;
import com.mjitech.utils.RequestUtils;

@Controller("requestController")
public class RequestController {

	@Autowired
	WarehouseService warehouseService;
	@Autowired
	RequestService requestService;
	@Autowired
	SkuService skuService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private InventoryLib inventoryLib;
	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private SkuLib skuLib;

	@RequestMapping(value = WebPageConstants.WAREHOUSE_REQUEST_HOME)
	public ModelAndView machineRequest(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.WAREHOUSE_REQUEST_VIEW);

		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);

		if (warehouseList.size() > 0) {
			Warehouse wh = warehouseList.get(0);
			mv.addObject("warehouseId", wh.getId());
			List<Request> requestList = requestService.getByWarehouse(
					wh.getId(), false);
			mv.addObject("requestList", requestList);
		}

		this.menuService.leftMenus(mv, request,
				MenuConstants.WAREHOUSE_REQUEST_MENU_ID);
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = WebPageConstants.CREATE_REQUEST, produces = WebPageConstants.JSON_PRODUCES)
	public String createRequest(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		int storeId = requestUtils.getIntValue(params, "machineId");
		int quantity = requestUtils.getIntValue(params, "quantity");
		int skuId = requestUtils.getIntValue(params, "skuId");

		String result = "";
		// TODO, need to validate the sku id

		Request req = new Request();
		req.setMachine_id(storeId);
		Warehouse warehouse = warehouseLib.getById(storeId);
		req.setWarehouse_id(warehouse.getWarehouse_parent());
		req.setQuantity(quantity);
		req.setSku_id(skuId);
		req.setCreateDatetime(new Date());
		req.setUpdateDatetime(new Date());
		requestService.createRequest(req);
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		result = obj.toString();

		return result;
	}

	@RequestMapping(value = WebPageConstants.GET_REQUST_BY_MACHINE)
	public ModelAndView getRequestByMachine(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.MACHINE_REQUEST_VIEW);
		int id = requestUtils.getIntValue(params, "machine_id");
		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.MACHINE);
		mv.addObject("warehouseList", warehouseList);
		mv.addObject("machineId", id);

		List<Request> requestList = requestService.getByMachine(id);
		mv.addObject("requestList", requestList);
		List<Inventory> invs = this.inventoryLib.getByWarehouse(id);
		List<Sku> skuList = new ArrayList<Sku>();

		if (invs != null) {
			for (Inventory inv : invs) {
				skuList.add(this.skuLib.getById(inv.getSkuId()));
			}
		}

		mv.addObject("skuList", skuList);

		this.menuService.leftMenus(mv, request,
				MenuConstants.MACHINE_REQUEST_MENU_ID);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.GET_NEW_REQUST_BY_WAREHOUSE)
	public ModelAndView getNewRequestByWarehouse(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.WAREHOUSE_REQUEST_VIEW);
		int id = requestUtils.getIntValue(params, "warehouse_id");
		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		mv.addObject("warehouseId", id);

		List<Request> requestList = requestService.getByWarehouse(id, false);
		mv.addObject("requestList", requestList);

		this.menuService.leftMenus(mv, request,
				MenuConstants.WAREHOUSE_REQUEST_MENU_ID);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.WAREHOUSE_REQUEST_HISTORY)
	public ModelAndView warehouseRequestHistory(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.WAREHOUSE_REQUEST_HISTORY_VIEW);

		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);

		if (warehouseList.size() > 0) {
			Warehouse wh = warehouseList.get(0);
			mv.addObject("warehouseId", wh.getId());
			List<Request> requestList = requestService.getByWarehouse(
					wh.getId(), true);
			mv.addObject("requestList", requestList);
		}

		this.menuService.leftMenus(mv, request,
				MenuConstants.WAREHOUSE_REQUEST_MENU_ID);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.GET_HISTORY_REQUST_BY_WAREHOUSE)
	public ModelAndView getHistoryRequestByWarehouse(
			HttpServletRequest request, @RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.WAREHOUSE_REQUEST_HISTORY_VIEW);
		int id = requestUtils.getIntValue(params, "warehouse_id");
		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		mv.addObject("warehouseId", id);

		List<Request> requestList = requestService.getByWarehouse(id, true);
		mv.addObject("requestList", requestList);

		this.menuService.leftMenus(mv, request,
				MenuConstants.WAREHOUSE_REQUEST_MENU_ID);
		return mv;
	}

}
