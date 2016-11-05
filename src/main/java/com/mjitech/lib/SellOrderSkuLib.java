package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.SellOrderSku;

public interface SellOrderSkuLib  extends BaseModelLib<SellOrderSku>{
	
	public List<SellOrderSku> getBySellOrder(int sellOrder);
	
}
