package com.mjitech.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mjitech.constant.SellOrderConstants;
import com.mjitech.constant.UserinfoConstants;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.model.Inventory;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;
import com.mjitech.model.Userinfo;
import com.mjitech.service.InstoreService;
import com.mjitech.service.SellOrderService;

@Service("sellOrderService")
public class SellOrderServiceImpl implements SellOrderService {

	private static Logger logger = LoggerFactory
			.getLogger(SellOrderServiceImpl.class);

	@Autowired
	private SellOrderLib sellOrderLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private InventoryLib inventoryLib;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;
	@Autowired
	private InstoreService instoreService;

	public final static int DEFAULT_CANCEL_USERID = 1;
	@Value(value = "${enable_cancel}")
	private boolean enableCancel;

	private static Object lock = new Object();

	@Override
	public SellOrder addOrder(SellOrder order, int userId) {

		return null;
	}

	@Override
	@Scheduled(cron = "0 0/15 * * * ?")
	public void batchCancelOrders() {
		synchronized (lock) {
			if (!enableCancel) {
				return;
			}
			if (logger.isInfoEnabled()) {
				logger.info("start batch cancel orders");
			}
			List<SellOrder> tobecanceleds = this.sellOrderLib.getToCancelList();
			if (logger.isInfoEnabled() && tobecanceleds != null) {
				logger.info(tobecanceleds.size() + " orders to be canceled");
			}
			for (SellOrder order : tobecanceleds) {
				int ret = this
						.cancelOrder(order.getId(), DEFAULT_CANCEL_USERID);
				if (ret != CANCEL_RETURNCODE_SUCC) {
					if (logger.isWarnEnabled()) {
						logger.warn("order:" + order.getId()
								+ " is failed to be canceled:" + ret);
					}
				}
			}
		}

	}

	@Override
	public int cancelOrder(int orderId, int userId) {
		SellOrder order = this.sellOrderLib.getById(orderId);
		if (order == null) {
			return SellOrderService.CANCEL_RETURNCODE_ORDERNOTFOUND;
		}
		if (userId != order.getBuyerId() && userId != order.getSellerId()) {
			Userinfo user = this.userinfoLib.getById(userId);
			if (user.getUserType() != UserinfoConstants.USER_TYPE_ADMIN) {
				return SellOrderService.CANCEL_RETURNCODE_NOAUTH;
			}

		}
		if (order.getStatus() != SellOrderConstants.SELLORDER_STATUS_NEW) {
			return SellOrderService.CANCEL_RETURNCODE_WRONGSTATUS;
		}
		List<SellOrderSku> sellOrderSkus = this.sellOrderSkuLib
				.getBySellOrder(order.getId());
		if (sellOrderSkus == null || sellOrderSkus.size() == 0) {

		} else {
			for (SellOrderSku sos : sellOrderSkus) {
				Inventory inv = this.inventoryLib.getBySkuWarehouse(
						sos.getSkuId(), order.getWarehouseId());
				Inventory update = new Inventory();
				update.setId(inv.getId());
				update.setQuantity(inv.getQuantity() + sos.getCount());
				this.inventoryLib.update(update);
			}
		}
		SellOrder update = new SellOrder();
		update.setId(orderId);
		update.setStatus(SellOrderConstants.SELLORDER_STATUS_CANCELED);
		update.setCancelTime(new Date());
		update.setCancelUserId(userId);
		this.sellOrderLib.update(update);
		this.instoreService.addCancelInstore(userId, orderId);
		return SellOrderService.CANCEL_RETURNCODE_SUCC;
	}
}
