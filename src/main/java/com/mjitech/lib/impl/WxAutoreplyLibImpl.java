package com.mjitech.lib.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mjitech.constant.RedisKeyConstants;
import com.mjitech.constant.WXConstants;
import com.mjitech.dao.WxAutoreplyDao;
import com.mjitech.lib.RedisLib;
import com.mjitech.lib.WxAutoreplyLib;
import com.mjitech.model.WxAutoreply;

@Component("wxautoreplyLib")
public class WxAutoreplyLibImpl implements WxAutoreplyLib {
	@Autowired
	private WxAutoreplyDao wxautoreplyDao;
	@Autowired
	private RedisLib redisLib;

	private void removeCache(WxAutoreply autoreply) {
		if (autoreply == null) {
			return;
		}
		if (autoreply.getId() > 0) {
			this.redisLib.removeCache(this.getIdKey(autoreply.getId()));
		}
		if (autoreply.getType() > 0) {
			this.redisLib.removeCache(this.getTypeKey(autoreply.getType()));
		}

	}

	private String getIdKey(int id) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_WXAUTOREPLY_ID)
				.append(id).toString();
	}

	private String getTypeKey(int type) {
		return new StringBuilder(RedisKeyConstants.REDIS_KEY_WXAUTOREPLY_TYPE)
				.append(type).toString();
	}

	@Override
	public WxAutoreply getById(int id) {
		String key = this.getIdKey(id);
		WxAutoreply value = (WxAutoreply) this.redisLib.getCache(key);
		if (value == null) {
			value = this.wxautoreplyDao.getById(id);
			if (value != null) {
				this.redisLib.addCache(key, value);
			}
		}
		return value;
	}

	@Override
	public WxAutoreply add(WxAutoreply t) {
		if (t.getCreateDatetime() == null) {
			t.setCreateDatetime(new Date());
		}
		if (t.getUpdateDatetime() == null) {
			t.setUpdateDatetime(t.getCreateDatetime());
		}
		if (t.getType() > 0) {
			WxAutoreply old = this.getByType(t.getType());
			if (old != null) {
				old.setEnabled(WXConstants.AUTOREPLY_MESSAGE_DISABLED);
				old.setUpdateDatetime(new Date());
				this.update(old);
			}
		}
		this.wxautoreplyDao.add(t);
		if (t.getId() > 0) {
			this.removeCache(t);
			return t;
		}
		return null;
	}

	@Override
	public int update(WxAutoreply t) {
		if (t.getId() > 0) {
			WxAutoreply old = this.getById(t.getId());
			if (old != null) {
				if (t.getUpdateDatetime() == null) {
					t.setUpdateDatetime(new Date());
				}
				int ret = this.wxautoreplyDao.update(t);
				if (ret > 0) {
					this.removeCache(old);
					return ret;
				}

			}

		}
		return 0;
	}

	@Override
	public int delete(int id) {
		if (id > 0) {
			WxAutoreply old = this.getById(id);
			if (old != null) {
				int ret = this.wxautoreplyDao.delete(id);
				if (ret > 0) {
					this.removeCache(old);
					return ret;
				}
			}
		}
		return 0;
	}

	@Override
	public WxAutoreply getByType(int type) {
		WxAutoreply value = null;
		if (type > 0) {
			String key = this.getTypeKey(type);
			value = (WxAutoreply) this.redisLib.getCache(key);
			if (value == null) {
				WxAutoreply condition = new WxAutoreply();
				condition.setType(type);
				condition.setEnabled(WXConstants.AUTOREPLY_MESSAGE_ENABLED);
				List<WxAutoreply> dbs = this.wxautoreplyDao
						.getByCondition(condition);
				if (dbs != null && dbs.size() > 0) {
					value = dbs.get(0);
					this.redisLib.addCache(key, value);
				}
			}

		}

		return value;
	}

	@Override
	public String getMessageByType(int type) {
		WxAutoreply autoreply = this.getByType(type);
		if (autoreply == null) {
			switch (type) {
			case WXConstants.AUTOREPLY_TYPE_SUBSCRIBE:
				return "您好，欢迎加入怪兽家便利店的大家庭！有什么购买问题和建议随时问我哟。";
			default:
				return "";
			}
		} else {
			return autoreply.getContent();
		}
	}

}
