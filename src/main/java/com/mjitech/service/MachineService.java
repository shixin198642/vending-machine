package com.mjitech.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.mjitech.model.Sale;
import com.mjitech.model.SellOrder;
import com.mjitech.model.Warehouse;

public interface MachineService {

	public List<Warehouse> listAll();

	public Warehouse getMachine(int id);

	public List<Sale> listSaleByMachine(int id);

	public void prepareMachineStoreDetailData(int userId, int warehouseId,
			ModelAndView mv);

	void prepareSellOrderData(int userId, SellOrder condition, ModelAndView mv);

}
