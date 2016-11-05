package com.mjitech.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.model.Sku;
import com.mjitech.model.SkuAttribute;
import com.mjitech.model.SkuSpec;
import com.mjitech.model.SkuType;

public interface SkuService {

	// Sku相关操作
	public JSONObject addUpdateSku(Sku sku, int userId);
	// public JSONObject findSku(int id);
	// public JSONObject deleteSku(int id);
	// public JSONObject updateSku(Sku sku);

	// Sku规格相关操作
	public JSONObject addSkuSpec(SkuSpec skuSpec);

	public JSONObject findSkuSpec(int id);

	public JSONObject deleteSkuSpec(int id);

	// 读取一个Sku的所有spec
	public JSONObject findSkuSpecListBySkuId(int id);

	/**
	 * 
	 * @param mv
	 */
	public List<Sku> listSkus(Sku condition);
	
	/**
	 * prepare data for add/update sku page
	 * @param mv
	 * @param skuId add:0, update:>0
	 */
	public void prepareAddUpdateSkuData(int userid, ModelAndView mv, int skuId);

	List<SkuAttribute> getSkuAttributes(int skuId, int typeId);
	
	JSONObject getSubSkuTypes(int typeId);
	
	void prepareSkuListData(int userid, Sku condition, ModelAndView mv);

	void regenerateSkuNumber(Sku sku);

	JSONObject getSku(String skuNumber);
	
	Sku getById(int skuId);

}
