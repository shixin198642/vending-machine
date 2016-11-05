package com.mjitech.lib.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.dao.SellOrderSkuDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.model.SellOrderSku;

@Component("sellOrderSkuLib")
public class SellOrderSkuLibImpl implements SellOrderSkuLib {
	@Autowired
	private RedisLib redisLib;
	@Autowired
	private SellOrderSkuDao sellOrderSkuDao;

	private void removeCache(SellOrderSku sellOrderSku) {
		if (sellOrderSku.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(sellOrderSku.getId()));
		}
		if (sellOrderSku.getSellOrderId() > 0) {
			this.redisLib.removeCache(this.getSellOrderIdKey(sellOrderSku
					.getSellOrderId()));
		}

	}

	private String getIdKey(int id) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_SELLORDERSKU_ID)
				.append(id).toString();
	}

	private String getSellOrderIdKey(int sellOrderId) {
		return new StringBuilder(
				RedisKeyConstants.REDIS_KEY_SELLORDERSKU_SELLORDERID).append(
				sellOrderId).toString();
	}

	@Override
	public SellOrderSku getById(int id) {
		String key = this.getIdKey(id);
		SellOrderSku sok = (SellOrderSku) this.redisLib.getCache(key);
		if (sok == null) {
			sok = this.sellOrderSkuDao.getById(id);
			if (sok != null) {
				this.redisLib.addCache(key, sok);
			}
		}
		return sok;
	}

	@Override
	public SellOrderSku add(SellOrderSku t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		this.sellOrderSkuDao.add(t);
		if (t.getId() > 0) {
			this.removeCache(t);
			return t;
		}
		return null;
	}

	@Override
	public int update(SellOrderSku t) {

		return 0;
	}

	@Override
	public int delete(int id) {

		return 0;
	}

	@Override
	public List<SellOrderSku> getBySellOrder(int sellOrderId) {
		String key = this.getSellOrderIdKey(sellOrderId);
		List<SellOrderSku> soss = (List<SellOrderSku>) this.redisLib
				.getCache(key);
		if (soss == null) {
			SellOrderSku condition = new SellOrderSku();
			condition.setSellOrderId(sellOrderId);
			soss = this.sellOrderSkuDao.getByCondition(condition);
			if (soss != null) {
				this.redisLib.addCache(key, (ArrayList<SellOrderSku>) soss);
			}
		}
		return soss;
	}

}
