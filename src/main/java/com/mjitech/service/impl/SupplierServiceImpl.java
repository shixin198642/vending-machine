package com.mjitech.service.impl;


import java.util.List;

import org.elasticsearch.common.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjitech.constant.CommonConstants;
import com.mjitech.constant.JSONConstants;
import com.mjitech.constant.SupplierErrorCodeConstants;
import com.mjitech.lib.SupplierContactLib;
import com.mjitech.lib.SupplierLib;
import com.mjitech.model.Supplier;
import com.mjitech.model.SupplierContact;
import com.mjitech.service.SupplierService;
import com.mjitech.utils.JsonUtils;
import com.mjitech.utils.SupplierUtils;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService{

	private static Logger logger = LoggerFactory
			.getLogger(SupplierServiceImpl.class);
	
	
	@Autowired
	private SupplierLib supplierLib;
	@Autowired
	private SupplierContactLib supplierContactLib;
	@Autowired
	private JsonUtils jsonUtils;
	@Autowired
	private SupplierErrorCodeConstants supplierErrorCodeConstants;
	@Autowired
	private SupplierUtils supplierUtils;
	
	@Override
	@Transactional
	public JSONObject addSupplier(Supplier supplier) {

		Supplier s = supplierLib.add(supplier);

		JSONObject ret = new JSONObject();
		if(s!=null && s.getId()>0){
			
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret, supplierErrorCodeConstants,
					SupplierErrorCodeConstants.RET_CODE_SUPPLIER_ADDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject findSupplierDeail(int id) {

		Supplier s = supplierLib.getById(id);
		JSONObject ret = new JSONObject();
		if(s!=null){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			ret.put(JSONConstants.RETURN_KEY_RESULT, supplierUtils.getSupplierJSON(s));
		}else{
			jsonUtils.setRetErrorCode(ret, supplierErrorCodeConstants,
					SupplierErrorCodeConstants.RET_CODE_SUPPLIER_FINDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject deleteSupplier(int id) {
		int i = supplierLib.delete(id);
		JSONObject ret = new JSONObject();
		if(i>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret, supplierErrorCodeConstants,
					SupplierErrorCodeConstants.RET_CODE_SUPPLIER_DELETEFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject updateSupplier(Supplier supplier) {

		int i = supplierLib.update(supplier);
		JSONObject ret = new JSONObject();
		if(i>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret, supplierErrorCodeConstants,
					SupplierErrorCodeConstants.RET_CODE_SUPPLIER_UPDATEFAILTURE, null);
		}
		return ret;
		
	}

	@Override
	@Transactional
	public JSONObject addSupplierContact(SupplierContact supplierContact) {
		
		JSONObject ret = new JSONObject();

		if(supplierContact.getType()==1){
			//需要清空当前供应商其他联系人的主要联系人标志。
			SupplierContact sc = new SupplierContact();
			sc.setSupplierid(supplierContact.getSupplierid());
			sc.setUpdator(supplierContact.getCreator());
			this.supplierContactLib.removeAllSupplierMainContactTag(supplierContact);
		}
		
		//入库
		SupplierContact s = this.supplierContactLib.add(supplierContact);
		if(s!=null && s.getId()>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			JSONObject sjson = supplierUtils.getSupplierContactJSON(s);
			ret.put("suppliercontact", sjson);
		}else{
			jsonUtils.setRetErrorCode(ret, supplierErrorCodeConstants,
					SupplierErrorCodeConstants.RET_CODE_SUPPLIER_ADDFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject findSupplierContactDeail(int id) {
		
		SupplierContact s = supplierContactLib.getById(id);
		JSONObject ret = new JSONObject();
		if(s!=null){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
			ret.put(JSONConstants.RETURN_KEY_RESULT, supplierUtils.getSupplierContactJSON(s));
		}else{
			jsonUtils.setRetErrorCode(ret, supplierErrorCodeConstants,
					SupplierErrorCodeConstants.RET_CODE_SUPPLIER_FINDFAILTURE, null);
		}
		return ret;
		
	}

	@Override
	@Transactional
	public JSONObject deleteSupplierContact(int id) {
		int i = supplierContactLib.delete(id);
		JSONObject ret = new JSONObject();
		if(i>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret, supplierErrorCodeConstants,
					SupplierErrorCodeConstants.RET_CODE_SUPPLIER_DELETEFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject updateSupplierContact(SupplierContact supplierContact) {
		
		if(supplierContact.getType()==1){
			//需要清空其他联系人的主要联系人标志。
			SupplierContact sc = new SupplierContact();
			sc.setSupplierid(supplierContact.getSupplierid());
			sc.setUpdator(supplierContact.getCreator());
			this.supplierContactLib.removeAllSupplierMainContactTag(supplierContact);
		}
		
		
		int i = supplierContactLib.update(supplierContact);
		JSONObject ret = new JSONObject();
		if(i>0){
			ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		}else{
			jsonUtils.setRetErrorCode(ret, supplierErrorCodeConstants,
					SupplierErrorCodeConstants.RET_CODE_SUPPLIER_UPDATEFAILTURE, null);
		}
		return ret;
	}

	@Override
	@Transactional
	public JSONObject findContactListBySupplierId(int id) {
		

		SupplierContact s = new SupplierContact();
		s.setSupplierid(id);
		
		List<SupplierContact> list = supplierContactLib.getByCondition(s);
		
		JSONObject ret = new JSONObject();
		//查询永远返回成功，只是JSON可能为{}
		ret.put(JSONConstants.RETURN_KEY_IS_SUCCESS, true);
		ret.put(JSONConstants.RETURN_KEY_RESULT, supplierUtils.getSupplierContactListJSON(list));
		return ret;

	}
	
	@Override
	@Transactional
	public List<SupplierContact> findContactListOBySupplierId(int id) {
		
		SupplierContact s = new SupplierContact();
		s.setSupplierid(id);
		List<SupplierContact> list = supplierContactLib.getByCondition(s);
		return list;

	}
	

	@Override
	public List<Supplier> listSuppliers(Supplier supplier) {
		// TODO Auto-generated method stub
		List<Supplier> list = supplierLib.getWithMinScByCondition(supplier);
		return list;
	}

}
