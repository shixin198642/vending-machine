package com.mjitech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mjitech.constant.OutstoreConstants;
import com.mjitech.constant.SellOrderConstants;
import com.mjitech.lib.OutstoreLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.model.Outstore;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;
import com.mjitech.service.OutstoreService;

@Service("outstoreService")
public class OutstoreServiceImpl implements OutstoreService {
	@Autowired
	private OutstoreLib outstoreLib;
	@Autowired
	private SellOrderLib sellOrderLib;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;

	@Override
	public List<Outstore> listOutStore(int warehouse) {

		return this.outstoreLib.getByWarehouse(warehouse);
	}

	@Override
	public void addManualOutstore(int userid, int warehouseId, int skuId,
			int quantity, String description) {
		Outstore outstore = new Outstore();
		outstore.setUserid(userid);
		outstore.setWarehouseId(warehouseId);
		outstore.setSkuId(skuId);
		outstore.setQuantity(quantity);
		outstore.setType(OutstoreConstants.OUTSTORE_TYPE_MANUAL);
		if (description == null) {
			description = "";
		}
		outstore.setDescription(description);
		this.outstoreLib.add(outstore);
	}

	@Override
	public void addSellOrderOutstore(int sellOrderId) {
		SellOrder so = this.sellOrderLib.getById(sellOrderId);
		if (so != null
				&& so.getStatus() == SellOrderConstants.SELLORDER_STATUS_PAYED) {
			List<Outstore> olds = this.outstoreLib.getBySellorder(sellOrderId);
			if (olds != null && olds.size() > 0) {
				return;
			}
			List<SellOrderSku> sellOrderSkus = this.sellOrderSkuLib
					.getBySellOrder(so.getId());
			if (sellOrderSkus != null && sellOrderSkus.size() > 0) {
				for (SellOrderSku sos : sellOrderSkus) {
					Outstore outstore = new Outstore();
					outstore.setUserid(so.getBuyerId());
					outstore.setPrice(sos.getSellPrice());
					outstore.setDescription("");
					outstore.setSellOrderId(so.getId());
					outstore.setQuantity(sos.getCount());
					outstore.setReceiptId(0);
					outstore.setSkuId(sos.getSkuId());
					outstore.setWarehouseId(so.getWarehouseId());
					outstore.setType(OutstoreConstants.OUTSTORE_TYPE_SELL);
					this.outstoreLib.add(outstore);
				}
			}
		}

	}

}
