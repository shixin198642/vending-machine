package com.mjitech.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.MenuConstants;
import com.mjitech.constant.ViewConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.WarehouseManagerLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.Picking;
import com.mjitech.model.Request;
import com.mjitech.model.Sku;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseEnum;
import com.mjitech.model.WarehouseManager;
import com.mjitech.service.InventoryService;
import com.mjitech.service.MenuService;
import com.mjitech.service.PickingService;
import com.mjitech.service.RequestService;
import com.mjitech.service.SkuService;
import com.mjitech.service.WarehouseService;
import com.mjitech.utils.RequestUtils;

@Controller("pickingController")
public class PickingController {

	@Autowired
	WarehouseService warehouseService;
	@Autowired
	InventoryService inventoryService;
	@Autowired
	SkuService skuService;
	@Autowired
	RequestService requestService;
	@Autowired
	PickingService pickingService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private WarehouseManagerLib warehouseManagerLib;
	
	//TODO could we create picking by request IDs coming from different Machine/Store?
	@RequestMapping(value = WebPageConstants.GENERATE_PICKING)
	public ModelAndView generatePicking(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		//we need to make sure all requests coming from same machine/store
		String[] requestArr = request.getParameterValues("requestId"); 
		Integer[] requestIds=new Integer[requestArr.length];
	    int i=0;
	    for(String str:requestArr){
	    	requestIds[i]=Integer.parseInt(str);//Exception in this line
	        i++;
	    }
	    
	    pickingService.generatePicking(requestIds);

	    if(requestIds!=null && requestIds.length>0){
	    	Request r = requestService.getById(requestIds[0]);
		    List<Picking> pickingList = pickingService.listPickingByWarehouse(r.getWarehouse_id());
		    mv.addObject("pickingList", pickingList);
			mv.addObject("warehouseId", r.getWarehouse_id());
	    }

		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
	    
		mv.setViewName(ViewConstants.PICKING_LIST_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.PICKING_MENU_ID);
		return mv;
	}
	
	//TODO could we create picking by request IDs coming from different Machine/Store?
	@RequestMapping(value = WebPageConstants.CREATE_PICKING)
	public ModelAndView createPicking(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		
		int warehouse_id = requestUtils.getIntValue(params, "warehouse_id");
		int userId = requestUtils.getIntValue(params, "userId");
		//we need to make sure all requests coming from same machine/store
		String[] skuArray = request.getParameterValues("skuId"); 
		int[] skuIds=new int[skuArray.length];
	    int i=0;
	    for(String str:skuArray){
	    	skuIds[i]=Integer.parseInt(str);//Exception in this line
	        i++;
	    }
	    
	    String[] quantityArray = request.getParameterValues("quantity"); 
		int[] quantities=new int[quantityArray.length];
	    int j=0;
	    for(String str:quantityArray){
	    	quantities[j]=Integer.parseInt(str);//Exception in this line
	        j++;
	    }
	    
	    pickingService.createPicking(warehouse_id, userId, skuIds, quantities);
	    List<Picking> pickingList = pickingService.listPickingByWarehouse(warehouse_id);
	    mv.addObject("pickingList", pickingList);

		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		mv.addObject("warehouseId", warehouse_id);	
		mv.setViewName(ViewConstants.PICKING_LIST_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.PICKING_MENU_ID);
		return mv;
	}	
	
		
	@RequestMapping(value = WebPageConstants.LIST_PICKING)
	public ModelAndView listPicking(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		
		int id = requestUtils.getIntValue(params, "warehouse_id");
		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);
		mv.addObject("warehouseId", id);	
		
	    List<Picking> pickingList = pickingService.listPickingByWarehouse(id);
	    mv.addObject("pickingList", pickingList);
	    
		mv.setViewName(ViewConstants.PICKING_LIST_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.PICKING_MENU_ID);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.PICKING_HOME)
	public ModelAndView pickingHome(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.PICKING_LIST_VIEW);
		
		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);

		if (warehouseList.size() > 0) {
			Warehouse wh = warehouseList.get(0);
			mv.addObject("warehouseId", wh.getId());
		    List<Picking> pickingList = pickingService.listPickingByWarehouse(wh.getId());
			mv.addObject("pickingList", pickingList);
		}		
		
		this.menuService.leftMenus(mv, request, MenuConstants.PICKING_MENU_ID);
		return mv;	
	}
	
	@RequestMapping(value = WebPageConstants.GET_PICKING)
	public ModelAndView getPicking(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.PICKING_VIEW);
		
		List<Warehouse> warehouseList = warehouseService
				.listAll(WarehouseEnum.WAREHOUSE);
		mv.addObject("warehouseList", warehouseList);

		if (warehouseList.size() > 0) {
			Warehouse wh = warehouseList.get(0);
			mv.addObject("warehouseId", wh.getId());
		}		
		
		this.menuService.leftMenus(mv, request, MenuConstants.PICKING_MENU_ID);
		return mv;	
	}	
	
	
	@RequestMapping(value = WebPageConstants.CREATE_PICKING_FORM)
	public ModelAndView createPickingForm(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		
		int warehouse_id = requestUtils.getIntValue(params, "warehouse_id");
		List<WarehouseManager> managerList = warehouseManagerLib.getByWarehouse(warehouse_id);
		List<Userinfo> userList = new ArrayList<Userinfo>();
	    for(WarehouseManager manager: managerList){
	    	Userinfo user = manager.getManager();
	    	userList.add(user); //TODO, we need to remove confidential information
	    }
		JSONArray userArray = new JSONArray(userList);		
	    mv.addObject("userArray", userArray);
	    
	    List<Inventory> inventoryList = inventoryService.getByWarehouse(warehouse_id);
	    for(Inventory inventory: inventoryList){
	    	Sku sku = skuService.getById(inventory.getSkuId());
	    	inventory.setSku(sku);
	    }
	    
	    JSONArray inventoryArray = new JSONArray(inventoryList);
	    mv.addObject("inventoryArray", inventoryArray);
	    
		mv.setViewName(ViewConstants.PICKING_FORM_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.PICKING_MENU_ID);
		return mv;
	}
		
}
