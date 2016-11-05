package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.DictArea;

public interface DictAreaLib extends BaseModelLib<DictArea> {

	

	public List<DictArea> getAllCountries();

	public List<DictArea> getAllProvinces(int countryId);

	public List<DictArea> getAllCities(int provinceId);

	public List<DictArea> getAllDistricts(int cityId);
	
	public List<DictArea> getChildren(int parentId);
	
	public List<DictArea> getByName(String name);

}
