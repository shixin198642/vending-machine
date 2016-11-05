package com.mjitech.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.mjitech.model.Supplier;
import com.mjitech.model.SupplierContact;
import com.mjitech.service.MenuService;
import com.mjitech.service.SupplierService;
import com.mjitech.utils.CommonKeyUtils;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;


/**
 * @author Roy
 *
 */
@Controller
public class SupplierController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SupplierController.class);
	
	
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private MenuService menuService;
	@Autowired
	private SessionUtils sessionUtils;
	@Autowired
	private CommonKeyUtils commonKeyUtils;
	
	/**
	 * 显示所有供应商
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.LIST_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isTraceEnabled()) {
			logger.trace("enter list supplier." );
		}
		List<Supplier> list = this.supplierService.listSuppliers(new Supplier());
		if (logger.isTraceEnabled()) {
			logger.trace("exit list supplier.");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("supplier_list");
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("list", list);
		mv.addAllObjects(modelMap);
		
		this.menuService.leftMenus(mv, request, 20);
		return mv;
	}
	
	
	/**
	 * 新增供应商--初始化
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ADDINIT_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	ModelAndView addinit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("supplierid", this.commonKeyUtils.getCommonKey(CommonConstants.COMMONKEY_SUPPLIER)) ;
		mv.setViewName("supplier_addinit");
		mv.addAllObjects(modelMap);
		this.menuService.leftMenus(mv, request, 21);
		return mv;  
	}
	
	/**
	 * 修改供应商--初始化
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ALTERINIT_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	ModelAndView updateinit(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
			Supplier s = new Supplier();
			s.setId(Integer.parseInt(id));
			List<Supplier> lists = this.supplierService.listSuppliers(s);
			List<SupplierContact> list = null;
			ModelAndView mv = new ModelAndView();
			Map<String,Object> modelMap = new HashMap<String,Object>();
			if(lists.size()==1){
				s = lists.get(0);
				modelMap.put("supplier", s);
				list = this.supplierService.findContactListOBySupplierId(Integer.parseInt(id));
			}else{
				modelMap.put("supplier", null);
			}
			modelMap.put("list", list);
			mv.addAllObjects(modelMap);
			mv.setViewName("supplier_update");
			this.menuService.leftMenus(mv, request, 20);
			return mv;  
	}
	
	/**
	 * 查询供应商明细
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.DETAIL_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	ModelAndView detail(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) {
		Supplier s = new Supplier();
		s.setId(Integer.parseInt(id));
		List<Supplier> lists = this.supplierService.listSuppliers(s);
		List<SupplierContact> list = null;
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(lists.size()==1){
			s = lists.get(0);
			modelMap.put("supplier", s);
			list = this.supplierService.findContactListOBySupplierId(Integer.parseInt(id));
		}else{
			modelMap.put("supplier", null);
		}
		modelMap.put("list", list);
		mv.addAllObjects(modelMap);
		mv.setViewName("supplier_detail");
		this.menuService.leftMenus(mv, request, 20);
		return mv;  
	}
	
	
	/**
	 * 新增供应商
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ADD_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String add(@RequestBody Supplier supplier,HttpServletRequest request, HttpServletResponse response) {

		supplier.setCreator(sessionUtils.getUserid(request));
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter add supplier:" + supplier.getSname());
		}
		JSONObject retJson = this.supplierService.addSupplier(supplier);
		if (logger.isTraceEnabled()) {
			logger.trace("exit add supplier:" + supplier.getSname());
		}
		ret = retJson.toString();
		return ret;
	}
	
	
	/**
	 * 根据ID查询供应商明细
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.FIND_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String find(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail supplier-id:" + id);
		}
		JSONObject retJson = this.supplierService.findSupplierDeail(Integer.parseInt(id));;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail supplier-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 根据ID删除供应商
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.DEL_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String delete(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter delete supplier-id:" + id);
		}
		JSONObject retJson = this.supplierService.deleteSupplier(Integer.parseInt(id));
		if (logger.isTraceEnabled()) {
			logger.trace("exit delete supplier-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	

	/**
	 * 修改供应商信息
	 * @param supplier
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ALTER_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String update(@RequestBody Supplier supplier,HttpServletRequest request, HttpServletResponse response) {

		String ret = "";
		supplier.setUpdator(sessionUtils.getUserid(request));
		if (logger.isTraceEnabled()) {
			logger.trace("enter update supplier:" + supplier.getSname());
		}
		JSONObject retJson = this.supplierService.updateSupplier(supplier);
		if (logger.isTraceEnabled()) {
			logger.trace("exit update supplier:" + supplier.getSname());
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 新增供应商联系人
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ADD_SUPPLIERCONTACT, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String addsc(@RequestBody SupplierContact suppliercontact,HttpServletRequest request, HttpServletResponse response) {

		String ret = "";
		suppliercontact.setCreator(sessionUtils.getUserid(request));
		if (logger.isTraceEnabled()) {
			logger.trace("enter add supplierContact:" + suppliercontact.getCname());
		}
		JSONObject retJson = this.supplierService.addSupplierContact(suppliercontact);
		if (logger.isTraceEnabled()) {
			logger.trace("exit add supplierContact:" + suppliercontact.getCname());
		}
		ret = retJson.toString();
		return ret;
	}
	
	
	/**
	 * 根据ID查询供应商联系人明细
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.FIND_SUPPLIERCONTACT, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String findsc(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail supplierContact-id:" + id);
		}
		JSONObject retJson = this.supplierService.findSupplierContactDeail(Integer.parseInt(id));;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail supplierContact-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 根据ID删除供应商联系人
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.DEL_SUPPLIERCONTACT, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String deletesc(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter delete supplierContact-id:" + id);
		}
		JSONObject retJson = this.supplierService.deleteSupplierContact(Integer.parseInt(id));
		if (logger.isTraceEnabled()) {
			logger.trace("exit delete supplierContact-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	

	/**
	 * 修改供应商信息联系人
	 * @param supplier
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ALTER_SUPPLIERCONTACT, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String updatesc(@RequestBody SupplierContact supplierContact,HttpServletRequest request, HttpServletResponse response) {

		String ret = "";
		supplierContact.setUpdator(sessionUtils.getUserid(request));
		if (logger.isTraceEnabled()) {
			logger.trace("enter update supplierContact:" + supplierContact.getCname());
		}
		JSONObject retJson = this.supplierService.updateSupplierContact(supplierContact);
		if (logger.isTraceEnabled()) {
			logger.trace("exit update supplierContact:" + supplierContact.getCname());
		}
		ret = retJson.toString();
		return ret;
	}
	
	
	/**
	 * 根据ID查询供应商下多个联系人
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.FIND_SUPPLIERCONTACTLIST, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String findsclist(@RequestBody Map<String, String> params,HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail supplier-id:" + id);
		}
		JSONObject retJson = this.supplierService.findContactListBySupplierId(Integer.parseInt(id));;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail supplier-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 根据供应商名称等条件查询供应商List
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.CONDITIONLIST_SUPPLIER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	List<Supplier> conditionlist(@RequestBody String condition,HttpServletRequest request, HttpServletResponse response) {
		try {
			condition = URLDecoder.decode(condition, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String sname = condition.replace("condition=", "");
		if (logger.isTraceEnabled()) {
			logger.trace("enter find list supplier-sname:" + sname);
		}
		Supplier supplier = new Supplier();
		supplier.setSname(sname);
		List<Supplier> list = this.supplierService.listSuppliers(supplier);

		return list;
	}
	
	
}
