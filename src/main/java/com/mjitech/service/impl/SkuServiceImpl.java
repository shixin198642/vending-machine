package com.mjitech.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.ErrorCodeConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.SkuConstants;
import com.mjitech.constant.SkuErrorCodeConstants;
import com.mjitech.constant.WebPageConstants;
import com.mjitech.lib.DictAreaLib;
import com.mjitech.lib.FileModelLib;
import com.mjitech.lib.ImageLib;
import com.mjitech.lib.SkuAttributeLib;
import com.mjitech.lib.SkuBrandLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.SkuSpecLib;
import com.mjitech.lib.SkuTypeAttributeLib;
import com.mjitech.lib.SkuTypeLib;
import com.mjitech.lib.UserTypeLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.model.FileModel;
import com.mjitech.model.Pagination;
import com.mjitech.model.Sku;
import com.mjitech.model.SkuAttribute;
import com.mjitech.model.SkuSpec;
import com.mjitech.model.SkuType;
import com.mjitech.model.SkuTypeAttribute;
import com.mjitech.model.Userinfo;
import com.mjitech.service.SkuService;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.SkuTypeUtils;
import com.mjitech.utils.SkuUtils;

@Service("skuService")
public class SkuServiceImpl implements SkuService {

	@Autowired
	private SkuSpecLib skuSpecLib;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private ErrorCodeConstants errorCodeConstants;
	@Autowired
	private SkuErrorCodeConstants skuErrorCodeConstants;
	@Autowired
	private SkuUtils skuUtils;
	@Autowired
	private SkuLib skuLib;
	@Autowired
	private SkuTypeLib skuTypeLib;
	@Autowired
	private SkuBrandLib skuBrandLib;
	@Autowired
	private DictAreaLib dictAreaLib;
	@Autowired
	private SkuTypeAttributeLib skuTypeAttributeLib;
	@Autowired
	private SkuAttributeLib skuAttributeLib;
	@Autowired
	private SkuTypeUtils skuTypeUtils;
	@Autowired
	private ImageLib imageLib;
	@Autowired
	private FileModelLib fileModelLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private UserTypeLib userTypeLib;

	// TODO SKU增删查改

	@Override
	@Transactional
	public JSONObject addSkuSpec(SkuSpec skuSpec) {
		SkuSpec s = skuSpecLib.add(skuSpec);

		JSONObject ret = new JSONObject();
		if (s != null && s.getId() > 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		} else {
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_ADDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject findSkuSpec(int id) {
		SkuSpec s = skuSpecLib.getById(id);
		JSONObject ret = new JSONObject();
		if (s != null) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			ret.put(JSONConstants.RETURN_KEY_RESULT, skuUtils.getSkuSpecJSON(s));
		} else {
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_FINDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject deleteSkuSpec(int id) {
		int i = skuSpecLib.delete(id);
		JSONObject ret = new JSONObject();
		if (i > 0) {
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		} else {
			jsonUtils.setRetErrorCode(ret, errorCodeConstants,
					ErrorCodeConstants.RET_CODE_DELETEFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject findSkuSpecListBySkuId(int id) {
		SkuSpec s = new SkuSpec();
		s.setSkuid(id);

		List<SkuSpec> list = skuSpecLib.getByCondition(s);

		JSONObject ret = new JSONObject();
		// 查询永远返回成功，只是JSON可能为{}
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		ret.put(JSONConstants.RETURN_KEY_RESULT,
				skuUtils.getSkuSpecListJSON(list));
		return ret;
	}

	@Override
	public List<Sku> listSkus(Sku condition) {
		List<Sku> skus = this.skuLib.getSkus(condition);
		return skus;
	}

	private String generateUniqueSkuNumber() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	private Sku createEmptySku(int userid) {

		Sku sku = this.skuLib.getDraftSku(userid);
		if (sku == null) {
			sku = new Sku();
			sku.setSkuNumber(this.generateUniqueSkuNumber());
			sku.setName("");
			sku.setShortName("");
			sku.setBarcode("");
			sku.setImagePath("");
			sku.setRemarks("");
			sku.setStatus(SkuConstants.STATUS_DRAFT);
			sku.setUnit("");
			sku.setTags("");
			sku.setCreator(userid);
			sku = this.skuLib.add(sku);
		}

		return sku;
	}

	@Override
	public List<SkuAttribute> getSkuAttributes(int skuId, int typeId) {
		List<SkuAttribute> attributes = new ArrayList<SkuAttribute>();
		List<SkuTypeAttribute> tas = this.skuTypeAttributeLib.getByType(typeId);
		List<SkuAttribute> sas = this.skuAttributeLib.getBySku(skuId);
		Map<Integer, SkuAttribute> cache = new HashMap<Integer, SkuAttribute>();
		for (SkuAttribute a : sas) {
			cache.put(a.getSkuTypeAttributeId(), a);
		}
		for (SkuTypeAttribute sta : tas) {
			if (cache.containsKey(sta.getId())) {
				SkuAttribute a = cache.get(sta.getId());
				a.setSkuTypeAttribute(sta);
				attributes.add(a);
			} else {
				SkuAttribute a = new SkuAttribute();
				a.setSkuTypeAttributeId(sta.getId());
				a.setSkuTypeAttribute(sta);
				a.setSkuTypeId(typeId);
				a.setValue("");
				a.setSkuId(skuId);
				attributes.add(a);
			}
		}
		return attributes;
	}

	public List<SkuSpec> getSkuSpecs(int skuid) {
		int maxSpecSize = 5;
		List<SkuSpec> specs = this.skuSpecLib.getSkuSpecs(skuid);
		if (specs == null) {
			specs = new ArrayList<SkuSpec>();
		}
		if (specs.size() < maxSpecSize) {
			int lackNumber = maxSpecSize - specs.size();
			for (int i = 0; i < lackNumber; i++) {
				SkuSpec spec = new SkuSpec();
				spec.setSkuid(skuid);
				spec.setType(0);
				spec.setUnit("");
				spec.setAmount(0);
				specs.add(spec);
			}
		}
		return specs;
	}

	public JSONObject deleteSku(int userid, int skuId) {
		//TODO
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		Userinfo user = this.userinfoLib.getById(userid);
		if (!this.userTypeLib.canAccessAdmin(user)) {
			this.jsonUtils.setRetErrorCode(json, skuErrorCodeConstants,
					SkuErrorCodeConstants.RET_CODE_NOAUTH, null);
			return json;
		}
		Sku sku = this.skuLib.getById(skuId);
		if (sku == null) {
			this.jsonUtils.setRetErrorCode(json, skuErrorCodeConstants,
					SkuErrorCodeConstants.RET_CODE_NOAUTH, null);
			return json;
		}
		
		
		return json;
	}

	@Override
	public void prepareAddUpdateSkuData(int userid, ModelAndView mv, int skuId) {
		Sku sku = null;
		if (skuId == 0) {
			sku = this.createEmptySku(userid);
		} else {
			sku = this.skuLib.getById(skuId);
		}
		mv.addObject("sku", sku);
		mv.addObject("all_brands", this.skuBrandLib.getAll());
		SkuType selectType = null;
		List<SkuType> types = this.skuTypeLib.getByParentId(0);
		for (SkuType type : types) {
			type.setSubTypes(this.skuTypeLib.getByParentId(type.getId()));
			for (SkuType tmp : type.getSubTypes()) {
				if (tmp.getId() == sku.getCategory() || selectType == null) {
					selectType = tmp;
				}
			}
		}
		mv.addObject("selected_type", selectType);
		mv.addObject("all_types", types);
		mv.addObject("all_countries", this.dictAreaLib.getAllCountries());
		List<SkuSpec> specs = this.getSkuSpecs(sku.getId());

		mv.addObject("specs", specs);
		mv.addObject("attributes",
				this.getSkuAttributes(skuId, selectType.getId()));

	}

	@Override
	public JSONObject addUpdateSku(Sku sku, int userId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		Sku old = this.skuLib.getById(sku.getId());
		if (old == null) {
			json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);

			return json;
		}
		if (old.getStatus() == SkuConstants.STATUS_DRAFT) {
			sku.setStatus(SkuConstants.STATUS_PUBLISHED);
			sku.setPublishTime(new Date());
		}
		List<SkuSpec> specs = sku.getSpecs();
		if (specs != null) {
			for (SkuSpec spec : specs) {
				if (spec.getId() > 0) {
					skuSpecLib.update(spec);
				} else {
					skuSpecLib.add(spec);
				}
			}
		}

		List<SkuAttribute> atts = sku.getAttributes();
		List<SkuAttribute> oldAtts = this.skuAttributeLib.getBySku(sku.getId());
		if (oldAtts != null) {
			for (SkuAttribute oldAtt : oldAtts) {
				if (oldAtt.getSkuTypeId() != sku.getCategory()) {
					this.skuAttributeLib.delete(oldAtt.getId());
				}
			}
		}
		if (atts != null) {
			for (SkuAttribute att : atts) {
				if (att.getId() > 0) {
					this.skuAttributeLib.update(att);
				} else {
					this.skuAttributeLib.add(att);
				}
			}
		}
		if (sku.getImageId() != 0) {
			FileModel image = fileModelLib.getById(sku.getImageId());
			image = imageLib.converTmpImageToSkuImage(image, sku.getId(),
					userId);
			sku.setImagePath(image.getFilePath());
			sku.setImageId(image.getId());
		}
		this.regenerateSkuNumber(sku);
		this.skuLib.update(sku);
		return json;
	}

	@Override
	public JSONObject getSku(String skuNumber) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, false);
		if (skuNumber != null) {
			Sku sku = null;
			if (skuNumber.toUpperCase().startsWith("U")) {
				sku = skuLib.getSkuBySkuNumber(skuNumber);
			} else if (StringUtils.isNumeric(skuNumber)) {
				sku = skuLib.getById(Integer.parseInt(skuNumber));
			}
			if (sku != null) {
				json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
				json.put("sku", skuUtils.getSkuJSON(sku));
			}

		}
		return json;
	}

	@Override
	public void regenerateSkuNumber(Sku sku) {
		if (sku == null) {
			return;
		}
		SkuType st = this.skuTypeLib.getById(sku.getCategory());
		if (st == null) {
			return;
		}
		if (st.getParentId() == 0) {
			return;
		}
		SkuType parent = this.skuTypeLib.getById(st.getParentId());
		if (parent == null) {
			return;
		}
		StringBuilder prefix = new StringBuilder("U");
		if (StringUtils.isNotEmpty(parent.getCode())) {
			prefix.append(parent.getCode());
		} else {
			prefix.append(parent.getId());
		}
		if (sku.getSkuNumber() != null
				&& sku.getSkuNumber().toUpperCase()
						.startsWith(prefix.toString())) {
			return;
		}
		if (sku.getId() > 0) {
			String skuNumber = prefix.toString()
					+ String.format("%06d", sku.getId());
			sku.setUniqueNumber(sku.getId());
			sku.setSkuNumber(skuNumber);
		} else {
			if (StringUtils.isEmpty(sku.getSkuNumber())) {
				sku.setSkuNumber(System.currentTimeMillis() + ""
						+ new Random().nextInt(9999));
			}
			this.skuLib.add(sku);
			if (sku.getId() > 0) {
				String skuNumber = prefix.toString()
						+ String.format("%06d", sku.getId());
				sku.setUniqueNumber(sku.getId());
				sku.setSkuNumber(skuNumber);
			} else {
				throw new RuntimeException("db error");
			}
		}
	}

	@Override
	public JSONObject getSubSkuTypes(int typeId) {
		JSONObject json = new JSONObject();
		json.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		List<SkuType> types = this.skuTypeLib.getByParentId(typeId);
		json.put("types", skuTypeUtils.getTypesJSONArray(types));
		return json;
	}

	@Override
	public void prepareSkuListData(int userid, Sku condition, ModelAndView mv) {
		List<Sku> skus = this.skuLib.getSkus(condition);
		for (Sku sku : skus) {

			sku.setBrandName(this.skuBrandLib.getById(sku.getBrand()).getName());
			sku.setCountryName(this.dictAreaLib.getById(sku.getCountry())
					.getName());
			SkuType type = this.skuTypeLib.getById(sku.getCategory());
			SkuType parent = this.skuTypeLib.getById(type.getParentId());
			sku.setCategoryName(parent.getName() + "-" + type.getName());
		}
		List<SkuType> parentTypes = this.skuTypeLib.getByParentId(0);
		mv.addObject("parentTypes", parentTypes);
		List<SkuType> childTypes = null;
		if (condition.getParentCategory() > 0) {
			childTypes = this.skuTypeLib.getByParentId(condition
					.getParentCategory());
		}
		mv.addObject("childTypes", childTypes);
		mv.addObject("skus", skus);
		int total = this.skuLib.getSkusTotal(condition);
		mv.addObject("total", total);
		mv.addObject("condition", condition);
		Pagination pagination = new Pagination(this.buildBaseUrl(condition),
				total, condition.getBegin(), condition.getPerpage());
		mv.addObject("pagination", pagination);

	}

	private String buildBaseUrl(Sku condition) {
		StringBuilder sb = new StringBuilder(WebPageConstants.COMMON_PREFIX)
				.append("/").append(WebPageConstants.SKU_LIST).append("?");
		if (condition.getCategory() > 0) {
			sb.append("&sku_type=").append(condition.getCategory());
		}
		if (condition.getParentCategory() > 0) {
			sb.append("&parent_type=").append(condition.getParentCategory());
		}
		return sb.toString();
	}

	@Override
	public Sku getById(int skuId) {
		return skuLib.getById(skuId);
	}
}
