package com.mjitech.lib.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.constant.SellOrderConstants;
import com.mjitech.dao.SellOrderDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;

@Component("sellOrderLib")
public class SellOrderLibImpl implements SellOrderLib {
	private static Logger logger = LoggerFactory
			.getLogger(SellOrderLibImpl.class);
	@Autowired
	private SellOrderDao sellOrderDao;
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;

	@Value(value = "${cancel_time}")
	private int cancelSeconds;

	private void removeCache(SellOrder sellOrder) {
		if (sellOrder.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(sellOrder.getId()));
		}
		if (StringUtils.isNotEmpty(sellOrder.getOrderNumber())) {
			this.redisLib.removeCache(this.getOrderNumberKey(sellOrder
					.getOrderNumber()));
		}
		if (sellOrder.getSellerId() > 0) {
			this.redisLib.removeCache(this.getSellerIdKey(sellOrder
					.getSellerId()));
		}
		if (sellOrder.getWarehouseId() > 0) {
			this.redisLib.removeCache(this.getWarehouseIdKey(sellOrder
					.getWarehouseId()));
		}
		if (StringUtils.isNotEmpty(sellOrder.getTakeGoodsNumber())) {
			this.redisLib.removeCache(this.getTakeGoodsNumberKey(sellOrder
					.getTakeGoodsNumber()));
		}
		if (sellOrder.getBuyerId() > 0) {
			this.redisLib
					.removeCache(this.getBuyerIdKey(sellOrder.getBuyerId()));
		}
		if (sellOrder.getParentId() > 0) {
			this.redisLib.removeCache(this.getParentIdKey(sellOrder
					.getParentId()));
		}

	}

	private String getIdKey(int id) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SELLORDER_ID)
				.append(id).toString();
	}

	private String getTakeGoodsNumberKey(String number) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_SELLORDER_TAKEGOODSNUMBER).append(
				number).toString();
	}

	private String getOrderNumberKey(String orderNumber) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_SELLORDER_ORDERNUMBER).append(
				orderNumber).toString();
	}

	private String getSellerIdKey(int sellerId) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SELLORDER_SELLERID)
				.append(sellerId).toString();

	}

	private String getBuyerIdKey(int buyerId) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SELLORDER_BUYERID)
				.append(buyerId).toString();

	}

	private String getWarehouseIdKey(int warehouseId) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_SELLORDER_WAREHOUSEID).append(
				warehouseId).toString();
	}

	private String getParentIdKey(int parentId) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SELLORDER_PARENTID)
				.append(parentId).toString();
	}

	@Override
	public SellOrder getById(int id) {
		String key = this.getIdKey(id);
		SellOrder so = (SellOrder) this.redisLib.getCache(key);
		if (so == null) {
			so = this.sellOrderDao.getById(id);
			if (so != null) {
				this.redisLib.addCache(key, so);
			}
		}
		return so;
	}
	@Override
	public List<SellOrder> getByParent(int parentId) {
		String key = this.getParentIdKey(parentId);
		List<SellOrder> value = (List<SellOrder>) this.redisLib.getCache(key);
		if (value == null) {
			SellOrder condition = new SellOrder();
			condition.setParentId(parentId);
			List<SellOrder> dbs = this.sellOrderDao.getByCondition(condition);
			if (dbs != null) {
				value = dbs;
				this.redisLib.addCache(key, (ArrayList<SellOrder>)value);
			}
		}
		return value;
	}

	private String generateOrderNumber() {
		StringBuilder sb = new StringBuilder("SO");
		sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		String prefix = sb.toString();
		int index = 1;
		String orderNumber = prefix + String.format("%03d", index);
		SellOrder old = this.getByOrderNumber(orderNumber);
		while (old != null) {
			index++;
			orderNumber = prefix + String.format("%03d", index);
			old = this.getByOrderNumber(orderNumber);
		}
		return orderNumber;
	}

	@Override
	public SellOrder add(SellOrder t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		if (StringUtils.isNotEmpty(t.getOrderNumber())) {
			SellOrder old = this.getByOrderNumber(t.getOrderNumber());
			if (old != null) {
				return null;
			}
		} else {
			t.setOrderNumber(this.generateOrderNumber());
		}
		if (StringUtils.isEmpty(t.getTakeGoodsNumber())) {
			t.setTakeGoodsNumber("");
		}
		if (t.getWxpayUrl() == null) {
			t.setWxpayUrl("");
		}
		if (t.getWxprepayId() == null) {
			t.setWxprepayId("");
		}
		this.sellOrderDao.add(t);
		if (t.getId() > 0) {
			this.removeCache(t);
			return t;
		}
		return null;
	}

	@Override
	public int update(SellOrder t) {
		if (t.getId() > 0) {
			if (t.getUpdateDatetime() == null) {
				t.setUpdateDatetime(new Date());
			}
			if (StringUtils.isNotEmpty(t.getOrderNumber())) {
				SellOrder old = this.getByOrderNumber(t.getOrderNumber());
				if (old != null && old.getId() != t.getId()) {
					return 0;
				}
			}
			SellOrder old = this.getById(t.getId());
			int ret = this.sellOrderDao.update(t);
			if (ret > 0) {

				this.removeCache(old);
				return ret;
			}

		}

		return 0;
	}

	@Override
	public int delete(int id) {
		if (id == 0) {
			return 0;
		}
		SellOrder old = this.getById(id);
		if (old != null) {
			int ret = this.sellOrderDao.delete(old.getId());
			if (ret > 0) {
				List<SellOrderSku> skus = this.sellOrderSkuLib
						.getBySellOrder(old.getId());
				for (SellOrderSku sku : skus) {
					this.sellOrderSkuLib.delete(sku.getId());
				}
				this.removeCache(old);
				return ret;
			}

		}
		return 0;
	}

	@Override
	public SellOrder getByOrderNumber(String orderNumber) {
		if (StringUtils.isEmpty(orderNumber)) {
			return null;
		}
		String key = this.getOrderNumberKey(orderNumber);
		SellOrder so = (SellOrder) this.redisLib.getCache(key);
		if (so == null) {
			SellOrder condition = new SellOrder();
			condition.setOrderNumber(orderNumber);
			List<SellOrder> sos = this.sellOrderDao.getByCondition(condition);
			
			if (sos != null && sos.size() > 0) {
				so = sos.get(0);
				this.redisLib.addCache(key, so);
			}
			else{
				condition.setIsParent(SellOrderConstants.IS_PARENT_YES);
				List<SellOrder> newsos = this.sellOrderDao.getByCondition(condition);
				if (newsos != null && newsos.size() > 0) {
					so = newsos.get(0);
					this.redisLib.addCache(key, so);
				}
			}
		}
		return so;
	}

	@Override
	public List<SellOrder> getBySeller(int sellerId) {
		if (sellerId == 0) {
			return null;
		}
		String key = this.getSellerIdKey(sellerId);
		List<SellOrder> sos = (List<SellOrder>) this.redisLib.getCache(key);
		if (sos == null) {
			SellOrder condition = new SellOrder();
			condition.setSellerId(sellerId);
			List<SellOrder> dbs = this.sellOrderDao.getByCondition(condition);
			if (dbs != null && dbs.size() > 0) {
				sos = dbs;
				this.redisLib.addCache(key, (ArrayList<SellOrder>) sos);
			}
		}
		return sos;
	}

	@Override
	public List<SellOrder> getByBuyer(int buyerId) {
		if (buyerId <= 0) {
			return null;
		}
		String key = this.getBuyerIdKey(buyerId);
		List<SellOrder> sos = (List<SellOrder>) this.redisLib.getCache(key);
		if (sos == null) {
			SellOrder condition = new SellOrder();
			condition.setBuyerId(buyerId);
			List<SellOrder> dbs = this.sellOrderDao.getByCondition(condition);
			if (dbs != null && dbs.size() > 0) {
				sos = dbs;
				this.redisLib.addCache(key, (ArrayList<SellOrder>) sos);
			}
		}
		return sos;
	}

	@Override
	public List<SellOrder> getToCancelList() {
		if (this.cancelSeconds <= 0) {
			this.cancelSeconds = 300;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -this.cancelSeconds);
		Date cancelDate = cal.getTime();
		List<SellOrder> orders = this.sellOrderDao
				.getAllUnpayOrders(cancelDate);
		if (logger.isInfoEnabled() && orders != null) {
			logger.info(orders.size() + " to be canceled.");
		}
		return orders;
	}

	@Override
	public List<SellOrder> getByWarehouse(int warehouseId) {
		if (warehouseId == 0) {
			return null;
		}
		String key = this.getWarehouseIdKey(warehouseId);
		List<SellOrder> list = (List<SellOrder>) this.redisLib.getCache(key);
		if (list == null) {
			SellOrder condition = new SellOrder();
			condition.setWarehouseId(warehouseId);
			list = this.sellOrderDao.getByCondition(condition);
			if (list != null) {
				this.redisLib.addCache(key, (ArrayList<SellOrder>) list);
			}
		}
		return list;
	}

	@Override
	public List<SellOrder> getByCondition(SellOrder condition) {

		return this.sellOrderDao.getByCondition(condition);
	}

	@Override
	public SellOrder getByTakeGoodsNumber(String number) {
		String key = this.getTakeGoodsNumberKey(number);
		SellOrder value = (SellOrder) this.redisLib.getCache(key);
		if (value == null) {
			SellOrder condition = new SellOrder();
			condition.setTakeGoodsNumber(number);
			List<SellOrder> dbs = this.sellOrderDao.getByCondition(condition);
			if (dbs != null && dbs.size() > 0) {
				value = dbs.get(0);
				this.redisLib.addCache(key, value);
			}
		}
		return value;
	}

}
