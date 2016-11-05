package com.mjitech.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Order extends BaseModel {

	private static final long serialVersionUID = 3465456123154246546L;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderdate;
	private int supplierid;
	private int suppliercontactid;
	private String supplierusername;
	private String supplierusertel;
	private int storehouseid;
	private int storehousemanagerid;
	private String storehousemanagername;
	private String storehousemanagertel;
	private int userid;
	private String usertel;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date paydate;
	private String payname;
	private String payaccount;
	private String paybank;
	private int settlemode;
	private int paymode;
	private String invoicetype;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date receivedate;
	private String contract;
	private float payamt;
	private float distamt;
	private int orderstate;

	private String suppliername;
	private String supplieraddress;
	private String username;
	private String storehousename;
	private String storehouseaddress;
	private String orderstatename;
	

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public int getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(int supplierid) {
		this.supplierid = supplierid;
	}

	public int getSuppliercontactid() {
		return suppliercontactid;
	}

	public void setSuppliercontactid(int suppliercontactid) {
		this.suppliercontactid = suppliercontactid;
	}

	public String getSupplierusername() {
		return supplierusername;
	}

	public void setSupplierusername(String supplierusername) {
		this.supplierusername = supplierusername;
	}

	public String getSupplierusertel() {
		return supplierusertel;
	}

	public void setSupplierusertel(String supplierusertel) {
		this.supplierusertel = supplierusertel;
	}

	public int getStorehouseid() {
		return storehouseid;
	}

	public void setStorehouseid(int storehouseid) {
		this.storehouseid = storehouseid;
	}

	public int getStorehousemanagerid() {
		return storehousemanagerid;
	}

	public void setStorehousemanagerid(int storehousemanagerid) {
		this.storehousemanagerid = storehousemanagerid;
	}

	public String getStorehousemanagername() {
		return storehousemanagername;
	}

	public void setStorehousemanagername(String storehousemanagername) {
		this.storehousemanagername = storehousemanagername;
	}

	public String getStorehousemanagertel() {
		return storehousemanagertel;
	}

	public void setStorehousemanagertel(String storehousemanagertel) {
		this.storehousemanagertel = storehousemanagertel;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsertel() {
		return usertel;
	}

	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}

	public Date getPaydate() {
		return paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}

	public String getPayname() {
		return payname;
	}

	public void setPayname(String payname) {
		this.payname = payname;
	}

	public String getPayaccount() {
		return payaccount;
	}

	public void setPayaccount(String payaccount) {
		this.payaccount = payaccount;
	}

	public String getPaybank() {
		return paybank;
	}

	public void setPaybank(String paybank) {
		this.paybank = paybank;
	}

	public int getSettlemode() {
		return settlemode;
	}

	public void setSettlemode(int settlemode) {
		this.settlemode = settlemode;
	}

	public int getPaymode() {
		return paymode;
	}

	public void setPaymode(int paymode) {
		this.paymode = paymode;
	}

	public String getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}

	public Date getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public float getPayamt() {
		return payamt;
	}

	public void setPayamt(float payamt) {
		this.payamt = payamt;
	}

	public float getDistamt() {
		return distamt;
	}

	public void setDistamt(float distamt) {
		this.distamt = distamt;
	}

	public int getOrderstate() {
		return orderstate;
	}

	public void setOrderstate(int orderstate) {
		this.orderstate = orderstate;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStorehousename() {
		return storehousename;
	}

	public void setStorehousename(String storehousename) {
		this.storehousename = storehousename;
	}

	public String getOrderstatename() {
		return orderstatename;
	}

	public void setOrderstatename(String orderstatename) {
		this.orderstatename = orderstatename;
	}

	public String getSupplieraddress() {
		return supplieraddress;
	}

	public void setSupplieraddress(String supplieraddress) {
		this.supplieraddress = supplieraddress;
	}

	public String getStorehouseaddress() {
		return storehouseaddress;
	}

	public void setStorehouseaddress(String storehouseaddress) {
		this.storehouseaddress = storehouseaddress;
	}

}
