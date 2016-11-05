package com.mjitech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.constant.InstoreConstants;
import com.mjitech.lib.InstoreLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.model.Instore;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;
import com.mjitech.service.InstoreService;

@Service("inStoreService")
public class InstoreServiceImpl implements InstoreService {

	@Autowired
	private InstoreLib instoreLib;
	@Autowired
	private SellOrderLib sellOrderLib;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;

	@Override
	public List<Instore> listInstore(int warehouseId) {
		return instoreLib.listInStore(warehouseId);
	}

	@Override
	public void addManualInstore(int userid, int warehouseId, int skuId,
			int quantity, double price, String description) {
		Instore instore = new Instore();
		instore.setUserid(userid);
		instore.setWarehouseId(warehouseId);
		instore.setSkuId(skuId);
		instore.setQuantity(quantity);
		instore.setType(InstoreConstants.INSTORE_TYPE_MANUAL);
		if (description == null) {
			description = "";
		}
		instore.setDescription(description);
		instore.setPrice(price);
		this.instoreLib.add(instore);
	}

	@Override
	public void addCancelInstore(int userid, int sellOrderId) {
		SellOrder so = this.sellOrderLib.getById(sellOrderId);
		if (so == null) {
			return;
		}
		List<SellOrderSku> orderSkus = this.sellOrderSkuLib
				.getBySellOrder(sellOrderId);
		if (orderSkus == null || orderSkus.size() == 0) {
			return;
		}
		for (SellOrderSku os : orderSkus) {
			Instore instore = new Instore();
			instore.setUserid(userid);
			instore.setWarehouseId(so.getWarehouseId());
			instore.setSkuId(os.getSkuId());
			instore.setQuantity(os.getCount());
			instore.setType(InstoreConstants.INSTORE_TYPE_CANCEL);
			instore.setDescription("auto cancel by system");
			instore.setPrice(os.getSellPrice());
			this.instoreLib.add(instore);
		}

	}

}
