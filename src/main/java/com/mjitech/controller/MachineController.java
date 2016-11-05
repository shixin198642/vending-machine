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
import com.mjitech.model.Request;
import com.mjitech.model.SellOrder;
import com.mjitech.model.Sku;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseEnum;
import com.mjitech.service.InventoryService;
import com.mjitech.service.MachineService;
import com.mjitech.service.MenuService;
import com.mjitech.service.RequestService;
import com.mjitech.service.SkuService;
import com.mjitech.service.WarehouseService;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;

@Controller("machineController")
public class MachineController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private MachineService machineService;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	RequestService requestService;
	@Autowired
	InventoryService inventoryService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private SkuService skuService;

	@RequestMapping(value = WebPageConstants.MACHINE_DETAIL_HOME)
	public ModelAndView getMachine(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.MACHINE_DETAIL_VIEW);
		int warehouseId = requestUtils.getIntValue(params, "warehouseId");
		this.machineService.prepareMachineStoreDetailData(
				this.sessionUtils.getUserid(request), warehouseId, mv);
		this.menuService.leftMenus(mv, request,
				MenuConstants.MACHINE_DETAIL_MENU_ID);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.MACHINE_REQUEST_HOME)
	public ModelAndView machineRequest(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.MACHINE_REQUEST_VIEW);

		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.MACHINE);
		mv.addObject("warehouseList", warehouseList);

		if (warehouseList.size() > 0) {
			Warehouse wh = warehouseList.get(0);
			mv.addObject("machineId", wh.getId());
			List<Request> requestList = requestService.getByMachine(wh.getId());
			mv.addObject("requestList", requestList);
		}
		List<Sku> skuList = skuService.listSkus(new Sku());
		mv.addObject("skuList", skuList);

		this.menuService.leftMenus(mv, request,
				MenuConstants.MACHINE_REQUEST_MENU_ID);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.MACHINE_INVENTORY_HOME)
	public ModelAndView machineInventory(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.MACHINE_INVENTORY_VIEW);

		int id = requestUtils.getIntValue(params, "warehouse_id");
		this.warehouseService.prepareMachineStoreInventoryList(
				sessionUtils.getUserid(request), id, mv);
		this.menuService.leftMenus(mv, request,
				MenuConstants.MACHINE_INVENTORY_MENU_ID);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.GET_INVENTORY_BY_MACHINE)
	public ModelAndView getInventoryByMachine(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.MACHINE_INVENTORY_VIEW);
		int id = requestUtils.getIntValue(params, "warehouse_id");
		this.warehouseService.prepareMachineStoreInventoryList(
				sessionUtils.getUserid(request), id, mv);

		this.menuService.leftMenus(mv, request,
				MenuConstants.MACHINE_INVENTORY_MENU_ID);
		return mv;
	}

	private SellOrder parseSellOrderListCondition(
			@RequestParam Map<String, String> params) {
		SellOrder condition = new SellOrder();
		condition.setWarehouseId(this.requestUtils.getIntValue(params, "warehouseId"));
		condition.setStatus(this.requestUtils.getIntValue(params, "status"));
		return condition;
	}

	@RequestMapping(value = WebPageConstants.SELL_HISTORY_HOME)
	public ModelAndView saleHistory(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.SELL_HISTORY_VIEW);

		this.machineService.prepareSellOrderData(
				this.sessionUtils.getUserid(request),
				this.parseSellOrderListCondition(params), mv);

		this.menuService.leftMenus(mv, request,
				MenuConstants.SELL_HISTORY_MENU_ID);
		return mv;
	}

}
