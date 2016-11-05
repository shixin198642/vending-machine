package com.mjitech.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 零售订单
 * 
 * @author zhangzhi
 * 
 */
public class SellOrder extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4443240801683105790L;
	private int parentId;
	private int isParent;
	private int warehouseId;
	private Warehouse store;
	private int sellerId;
	private Userinfo seller;
	private Date sellTime;
	private int status;
	private String orderNumber;
	private int payStatus;
	private Date payTime;
	private Date cancelTime;
	private double totalPrice;
	private String wxpayUrl;
	private String wxprepayId;
	private Date requestWxpayTime;
	private int buyerId;
	private String openId;
	private int cancelUserId;
	private List<SellOrder> childOrders = null;
	/**
	 * 取货码
	 */
	private String takeGoodsNumber;

	public String getWxpayUrl() {
		return wxpayUrl;
	}

	public void setWxpayUrl(String wxpayUrl) {
		this.wxpayUrl = wxpayUrl;
	}

	public String getWxprepayId() {
		return wxprepayId;
	}

	public void setWxprepayId(String wxprepayId) {
		this.wxprepayId = wxprepayId;
	}

	public Date getRequestWxpayTime() {
		return requestWxpayTime;
	}

	public void setRequestWxpayTime(Date requestWxpayTime) {
		this.requestWxpayTime = requestWxpayTime;
	}

	private List<SellOrderSku> skus = new ArrayList<SellOrderSku>();

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public Date getSellTime() {
		return sellTime;
	}

	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setSkus(List<SellOrderSku> skus) {
		this.skus = skus;
	}

	public List<SellOrderSku> getSkus() {
		return skus;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setCancelUserId(int cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	public int getCancelUserId() {
		return cancelUserId;
	}

	public void setSeller(Userinfo seller) {
		this.seller = seller;
	}

	public Userinfo getSeller() {
		return seller;
	}

	public void setTakeGoodsNumber(String takeGoodsNumber) {
		this.takeGoodsNumber = takeGoodsNumber;
	}

	public String getTakeGoodsNumber() {
		return takeGoodsNumber;
	}

	public void setStore(Warehouse store) {
		this.store = store;
	}

	public Warehouse getStore() {
		return store;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setChildOrders(List<SellOrder> childOrders) {
		this.childOrders = childOrders;
	}

	public List<SellOrder> getChildOrders() {
		return childOrders;
	}

}
