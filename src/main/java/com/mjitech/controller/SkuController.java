package com.mjitech.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.mjitech.model.Sku;
import com.mjitech.model.SkuAttribute;
import com.mjitech.model.SkuSpec;
import com.mjitech.service.MenuService;
import com.mjitech.service.SkuService;
import com.mjitech.utils.RequestUtils;
import com.mjitech.utils.SessionUtils;

@Controller
public class SkuController {

	private static final Logger logger = LoggerFactory
			.getLogger(SkuController.class);

	@Autowired
	private SkuService skuService;
	@Autowired
	private RequestUtils requestUtils;
	@Autowired
	private MenuService menuService;
	@Autowired
	private SessionUtils sessionUtils;
	
	private Sku parseListCondition(Map<String, String> params){
		Sku condition = new Sku();
		requestUtils.setPagination(params, condition);
		condition.setParentCategory(requestUtils.getIntValue(params, "parent_type"));
		condition.setCategory(requestUtils.getIntValue(params, "sku_type"));
		return condition;
	}
	
	@RequestMapping(value = WebPageConstants.SKU_LIST)
	public ModelAndView listSkus(HttpServletRequest request,
			@RequestParam Map<String, String> params) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.SKU_LIST_VIEW);
		this.menuService.leftMenus(mv, request, 0);
		this.skuService.prepareSkuListData(
				sessionUtils.getUserid(request), this.parseListCondition(params), mv);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.ADD_SKU_PAGE)
	public ModelAndView addSkuPage(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.ADD_UPDATE_SKU_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.ADD_SKU_MENU_ID);
		this.skuService.prepareAddUpdateSkuData(
				sessionUtils.getUserid(request), mv, 0);
		return mv;
	}

	@RequestMapping(value = WebPageConstants.UPDATE_SKU_PAGE)
	public ModelAndView updateSkuPage(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		int skuId = requestUtils.getIntValue(params, "sku_id");
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.ADD_UPDATE_SKU_VIEW);
		this.menuService.leftMenus(mv, request, MenuConstants.ADD_SKU_MENU_ID);
		this.skuService.prepareAddUpdateSkuData(
				sessionUtils.getUserid(request), mv, skuId);
		return mv;
	}

	private Sku readSkuFromRequest(Map<String, String> params) {
		Sku sku = new Sku();
		sku.setId(requestUtils.getIntValue(params, "sku_id"));
		sku.setTags(requestUtils.getStringValue(params, "tags"));
		sku.setMsrp(requestUtils.getFloatValue(params, "msrp"));
		sku.setCountry(requestUtils.getIntValue(params, "country"));
		sku.setRemarks(requestUtils.getStringValue(params, "remarks"));
		sku.setBarcode(requestUtils.getStringValue(params, "barcode"));
		sku.setBrand(requestUtils.getIntValue(params, "brand"));
		sku.setName(requestUtils.getStringValue(params, "name"));
		sku.setShortName(requestUtils.getStringValue(params, "shortName"));
		sku.setCategory(requestUtils.getIntValue(params, "category"));
		sku.setUnit(requestUtils.getStringValue(params, "unit"));
		sku.setLength(requestUtils.getIntValue(params, "length"));
		sku.setWidth(requestUtils.getIntValue(params, "width"));
		sku.setHeight(requestUtils.getIntValue(params, "height"));
		sku.setMinStock(requestUtils.getIntValue(params, "minStock"));
		sku.setMaxStock(requestUtils.getIntValue(params, "maxStock"));
		sku.setSafeStock(requestUtils.getIntValue(params, "safeStock"));
		sku.setExpirationDays(requestUtils
				.getIntValue(params, "expirationDays"));
		String imagePath = requestUtils.getStringValue(params, "new_sku_image");
		if (StringUtils.isNotEmpty(imagePath)) {
			sku.setImagePath(imagePath);
		}
		sku.setImageId(requestUtils.getIntValue(params, "new_sku_image_id"));
		List<SkuSpec> specs = new ArrayList<SkuSpec>();
		for (int i = 1; i <= 10; i++) {
			String unit = requestUtils.getStringValue(params, "spec_unit_" + i);
			if (StringUtils.isNotEmpty(unit)) {
				SkuSpec spec = new SkuSpec();
				spec.setSkuid(sku.getId());
				spec.setId(requestUtils.getIntValue(params, "spec_id_" + i));
				spec.setAmount(requestUtils.getIntValue(params, "spec_amount_"
						+ i));
				spec.setUnit(unit);
				spec.setType(requestUtils.getIntValue(params, "spec_type_" + i));
				specs.add(spec);
			}
		}
		sku.setSpecs(specs);
		List<SkuAttribute> atts = skuService.getSkuAttributes(sku.getId(),
				sku.getCategory());
		List<SkuAttribute> attributes = new ArrayList<SkuAttribute>();
		for (int i = 1; i <= atts.size(); i++) {
			SkuAttribute att = new SkuAttribute();
			att.setSkuId(sku.getId());
			att.setId(requestUtils.getIntValue(params, "attribute_id_" + i));
			att.setSkuTypeAttributeId(requestUtils.getIntValue(params,
					"attribute_skuattributetype_" + i));
			att.setSkuTypeId(requestUtils.getIntValue(params,
					"attribute_skutype_" + i));
			att.setValue(requestUtils.getStringValue(params, "attribute_value_"
					+ i));
			att.setRemarks("");
			attributes.add(att);
		}
		sku.setAttributes(attributes);
		return sku;
	}

	/**
	 * 新增商品
	 * 
	 * @param skuSpec
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ADD_UPDATE_SKU, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String addSku(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> params) {

		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter add sku ");
		}
		Sku sku = this.readSkuFromRequest(params);
		JSONObject retJson = this.skuService.addUpdateSku(sku,
				sessionUtils.getUserid(request));
		if (logger.isTraceEnabled()) {
			logger.trace("exit add sku ");
		}
		ret = retJson.toString();
		return ret;
	}

	/**
	 * 新增商品
	 * 
	 * @param skuSpec
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.GET_SUB_TYPES, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String getSubSkuTypes(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Map<String, String> params) {

		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter getSubSkuTypes ");
		}
		int type = requestUtils.getIntValue(params, "type_id");
		JSONObject retJson = this.skuService.getSubSkuTypes(type);
		if (logger.isTraceEnabled()) {
			logger.trace("exit getSubSkuTypes ");
		}
		ret = retJson.toString();
		return ret;
	}

	@RequestMapping(value = WebPageConstants.SKU_ATTRIBUTES_INC)
	public ModelAndView skuAttributesPage(HttpServletRequest request,
			@RequestParam Map<String, String> params) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewConstants.SKU_ATTRIBUTES_INC_VIEW);
		int skuId = requestUtils.getIntValue(params, "sku_id");
		int typeId = requestUtils.getIntValue(params, "type_id");
		mv.addObject("attributes",
				this.skuService.getSkuAttributes(skuId, typeId));
		return mv;
	}

	/**
	 * 新增商品规格
	 * 
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.ADD_SKUSPEC, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String addsc(@RequestBody SkuSpec skuSpec, HttpServletRequest request,
			HttpServletResponse response) {

		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter add skuSpec - skuid:" + skuSpec.getSkuid());
		}
		JSONObject retJson = this.skuService.addSkuSpec(skuSpec);
		if (logger.isTraceEnabled()) {
			logger.trace("exit add skuSpec - skuid:" + skuSpec.getSkuid());
		}
		ret = retJson.toString();
		return ret;
	}

	/**
	 * 根据ID查询商品规格
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.FIND_SKUSPEC, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String findsc(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail SkuSpec-id:" + id);
		}
		JSONObject retJson = this.skuService.findSkuSpec(Integer.parseInt(id));
		;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail SkuSpec-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}

	/**
	 * 根据ID删除商品规格
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.DEL_SKUSPEC, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String deletesc(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter delete SkuSpec-id:" + id);
		}
		JSONObject retJson = this.skuService
				.deleteSkuSpec(Integer.parseInt(id));
		if (logger.isTraceEnabled()) {
			logger.trace("exit delete SkuSpec-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}

	/**
	 * 根据ID查询商品的所有规格
	 * 
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.FIND_SKUSPECLIST, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String findsclist(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {

		String id = requestUtils.getStringValue(params, "id");
		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter finddetail sku-id:" + id);
		}
		JSONObject retJson = this.skuService.findSkuSpecListBySkuId(Integer
				.parseInt(id));
		;
		if (logger.isTraceEnabled()) {
			logger.trace("exit finddetail sku-id:" + id);
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 获取商品详情
	 * 
	 * @param skunumber
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.GET_SKU_BY_SKUNUMBER, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	String getSkuBySkuNumber(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> params) {

		String ret = "";
		if (logger.isTraceEnabled()) {
			logger.trace("enter getSkuBySkuNumber ");
		}
		Sku sku = this.readSkuFromRequest(params);
		JSONObject retJson = this.skuService.addUpdateSku(sku,
				sessionUtils.getUserid(request));
		if (logger.isTraceEnabled()) {
			logger.trace("exit getSkuBySkuNumber ");
		}
		ret = retJson.toString();
		return ret;
	}
	
	/**
	 * 根据供应商名称等条件查询供应商List
	 * @return
	 */
	@RequestMapping(value = WebPageConstants.CONDITIONLIST_SKU, produces = WebPageConstants.JSON_PRODUCES)
	public @ResponseBody
	List<Sku> conditionlist(@RequestBody String condition,HttpServletRequest request, HttpServletResponse response) {
		try {
			condition = URLDecoder.decode(condition, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String skuNumber = condition.replace("condition=", "");
		if (logger.isTraceEnabled()) {
			logger.trace("enter find list sku-number:" + skuNumber);
		}
		Sku sku = new Sku();
		sku.setSkuNumber(skuNumber);
		List<Sku> list = this.skuService.listSkus(sku);

		return list;
	}
}
