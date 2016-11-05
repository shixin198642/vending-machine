package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Sku;

public interface SkuLib extends BaseModelLib<Sku>{
	public Sku getSkuByBarcode(String barcode);
	public Sku getDraftSku(int userid);
	public List<Sku> getSkus(Sku condition);
	Sku getSkuBySkuNumber(String skuNumber);
	int getSkusTotal(Sku condition);
	int getCountByParentType(int parentType);
	List<Sku> getByBrand(int brand);
	
}
