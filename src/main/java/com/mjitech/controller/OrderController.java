package com.mjitech.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.mjitech.constant.CommonConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.model.Order;
import com.mjitech.model.OrderSku;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseEnum;
import com.mjitech.service.MenuService;
import com.mjitech.service.OrderService;
import com.mjitech.service.UserinfoService;
import com.mjitech.service.WarehouseService;
import com.mjitech.utils.CommonKeyUtils;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;

/**
 * @author Roy
 *
 */
@Controller
public class OrderController {

	private static final Logger logger = LoggerFactory
			.getLogger(OrderController.class);
	
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private MenuService menuService;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private CommonKeyUtils commonKeyUtils;
	
	
	/**
	 * 列表：显示所有订单
	 */
	@RequestMapping(value = WebPageConstants.LIST_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isTraceEnabled()) {
			logger.trace("enter list order." );
		}
		List<Order> list = this.orderService.listOrder(new Order());
		if (logger.isTraceEnabled()) {
			logger.trace("exit list order.");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("order_list");
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("list", list);
		mv.addAllObjects(modelMap);
		
		this.menuService.leftMenus(mv, request, 22);
		return mv;
	}
	
	/**
	 * 新增订单--初始化
	 */
	@RequestMapping(value = WebPageConstants.ADDINIT_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	ModelAndView addinit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//设置新增订单初始id
		modelMap.put("orderid", this.commonKeyUtils.getCommonKey(CommonConstants.COMMONKEY_ORDER));
		//获取人员集合List，用于前台 select 显示
		List<Userinfo> userList = userinfoService.queryUserList(new Userinfo());
		modelMap.put("userList", userList);
		//获取仓库集合List，用于前台 select 显示
		List<Warehouse> storeList = warehouseService.listAll(WarehouseEnum.WAREHOUSE);
		modelMap.put("storeList", storeList);
		
		mv.setViewName("order_addinit");
		mv.addAllObjects(modelMap);
		this.menuService.leftMenus(mv, request, 22);
		return mv;  
	}
	
	/**
	 * 新增订单--保存
	 */
	@RequestMapping(value = WebPageConstants.ADD_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String add(@RequestBody Order order,HttpServletRequest request, HttpServletResponse response) {
		//设置订单创建日期为当前服务器日期、支付方式为银行转账
		order.setPaymode(1);
		order.setOrderdate(new Date());
		order.setContract("");
		order.setCreator(sessionUtils.getUserid(request));
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter add order:" + order.getPayname());
		}
		JSONObject retJson = this.orderService.addOrder(order);
		if (logger.isTraceEnabled()) {
			logger.trace("exit add order:" + order.getPayname());
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 根据订单ID查询订单明细
	 */
	@RequestMapping(value = WebPageConstants.FIND_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String find(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {
		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail order-id:" + id);
		}
		JSONObject retJson = this.orderService.findOrderDeail(Integer.parseInt(id));;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail order-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 根据订单id查询订单明细（含商品清单）
	 */
	@RequestMapping(value = WebPageConstants.DETAIL_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	ModelAndView detail(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
		Order s = new Order();
		s.setId(Integer.parseInt(id));
		List<Order> lists = this.orderService.listOrder(s);
		List<OrderSku> list = null;
		ModelAndView mv = new ModelAndView();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (lists.size() == 1) {
			s = lists.get(0);
			modelMap.put("order", s);
			list = this.orderService.findOrderSkuListOByOrderId(Integer.parseInt(id));
		} else {
			modelMap.put("order", null);
		}
		modelMap.put("list", list);
		mv.addAllObjects(modelMap);
		mv.setViewName("order_detail");
		this.menuService.leftMenus(mv, request, 22);
		return mv;
	}
	
	/**
	 * 删除订单
	 */
	@RequestMapping(value = WebPageConstants.DEL_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String delete(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {
		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter delete order-id:" + id);
		}
		JSONObject retJson = this.orderService.deleteOrder(Integer.parseInt(id));
		if (logger.isTraceEnabled()) {
			logger.trace("exit delete order-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 修改订单--初始化
	 */
	@RequestMapping(value = WebPageConstants.ALTERINIT_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody 
	ModelAndView updateinit(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
		Order s = new Order();
		s.setId(Integer.parseInt(id));
		List<Order> lists = this.orderService.listOrder(s);
		List<OrderSku> list = null;
		ModelAndView mv = new ModelAndView();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (lists.size() == 1) {
			s = lists.get(0);
			modelMap.put("order", s);
			list = this.orderService.findOrderSkuListOByOrderId(Integer.parseInt(id));
		} else {
			modelMap.put("order", null);
		}
		modelMap.put("list", list);
		//获取人员集合List，用于前台 select 显示
		List<Userinfo> userList = userinfoService.queryUserList(new Userinfo());
		modelMap.put("userList", userList);
		//获取仓库集合List，用于前台 select 显示
		List<Warehouse> storeList = warehouseService.listAll(WarehouseEnum.WAREHOUSE);
		modelMap.put("storeList", storeList);
		
		mv.addAllObjects(modelMap);
		mv.setViewName("order_update");
		this.menuService.leftMenus(mv, request, 22);
		return mv;
	}

	/**
	 * 修改订单--保存
	 */
	@RequestMapping(value = WebPageConstants.ALTER_ORDER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String update(@RequestBody Order order,HttpServletRequest request, HttpServletResponse response) {
		order.setUpdator(sessionUtils.getUserid(request));
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter update order:" + order.getPayname());
		}
		JSONObject retJson = this.orderService.updateOrder(order);
		if (logger.isTraceEnabled()) {
			logger.trace("exit update order:" + order.getPayname());
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 新增订单商品明细保存
	 */
	@RequestMapping(value = WebPageConstants.ADD_ORDERSKU, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String addsc(@RequestBody OrderSku orderSku,HttpServletRequest request, HttpServletResponse response) {
		String ret = "";
		orderSku.setCreator(sessionUtils.getUserid(request));
		if (logger.isTraceEnabled()) {
			logger.trace("enter add orderSku - orderid:" + orderSku.getOrderid());
		}
		JSONObject retJson = this.orderService.addOrderSku(orderSku);
		if (logger.isTraceEnabled()) {
			logger.trace("exit add orderSku - orderid:" + orderSku.getOrderid());
		}
		ret = retJson.toString();
		return ret;
	}
	
	
	/**
	 * 根据订单商品ID查询订单商品明细
	 */
	@RequestMapping(value = WebPageConstants.FIND_ORDERSKU, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String findsc(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {
		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail OrderSku-id:" + id);
		}
		JSONObject retJson = this.orderService.findOrderSkuDeail(Integer.parseInt(id));
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail OrderSku-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 根据订单商品ID删除订单商品明细
	 */
	@RequestMapping(value = WebPageConstants.DEL_ORDERSKU, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String deletesc(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {
		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter delete OrderSku-id:" + id);
		}
		JSONObject retJson = this.orderService.deleteOrderSku(Integer.parseInt(id));
		if (logger.isTraceEnabled()) {
			logger.trace("exit delete OrderSku-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 修改订单商品明细
	 */
	@RequestMapping(value = WebPageConstants.ALTER_ORDERSKU, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String updatesc(@RequestBody OrderSku orderSku,HttpServletRequest request, HttpServletResponse response) {
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter update orderSku:" + orderSku.getId());
		}
		JSONObject retJson = this.orderService.updateOrderSku(orderSku);
		if (logger.isTraceEnabled()) {
			logger.trace("exit update orderSku:" + orderSku.getId());
		}
		ret = retJson.toString();
		return ret;
	}
	
	
	/**
	 * 根据OrderID查询Order下的所有订单商品
	 */
	@RequestMapping(value = WebPageConstants.FIND_ORDERSKULIST, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String findsclist(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail order-id:" + id);
		}
		JSONObject retJson = this.orderService.findOrderSkuListByOrderId(Integer.parseInt(id));;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail order-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
}
