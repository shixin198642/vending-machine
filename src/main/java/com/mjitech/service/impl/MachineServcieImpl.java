package com.mjitech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.mjitech.constant.WarehouseConstants;
import com.mjitech.dao.SaleDao;
import com.mjitech.dao.WarehouseDao;
import com.mjitech.lib.SellOrderLib;
import com.mjitech.lib.SellOrderSkuLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.lib.WarehouseManagerLib;
import com.mjitech.model.Sale;
import com.mjitech.model.SellOrder;
import com.mjitech.model.SellOrderSku;
import com.mjitech.model.Warehouse;
import com.mjitech.service.MachineService;

@Service("machineService")
public class MachineServcieImpl implements MachineService {

	@Autowired
	private WarehouseDao warehouseDao;
	@Autowired
	private SaleDao saleDao;
	@Autowired
	private WarehouseLib warehouseLib;
	@Autowired
	private WarehouseManagerLib warehouseManagerLib;
	@Autowired
	private UserinfoLib userinfoLib;
	@Autowired
	private SellOrderLib sellOrderLib;
	@Autowired
	private SellOrderSkuLib sellOrderSkuLib;
	@Autowired
	private SkuLib skuLib;

	@Override
	public List<Warehouse> listAll() {
		return warehouseDao.listMachineStore();
	}

	@Override
	public Warehouse getMachine(int id) {
		Warehouse w = this.warehouseLib.getById(id);
		if (WarehouseConstants.WAREHOUSE_TYPE_MACHINE.equalsIgnoreCase(w
				.getType())) {
			Warehouse parent = this.warehouseLib.getById(w
					.getWarehouse_parent());
			w.setWarehouseParent(parent);
			return w;
		}
		return null;
	}

	@Override
	public List<Sale> listSaleByMachine(int id) {
		return saleDao.listSaleByMachine(id);
	}

	@Override
	public void prepareSellOrderData(int userId, SellOrder condition,
			ModelAndView mv) {
		List<Warehouse> storeMachines = this.warehouseLib.listAllMachineStore();
		Warehouse selected = null;
		for (Warehouse w : storeMachines) {
			if (w.getId() == condition.getWarehouseId() || selected == null) {
				selected = w;
			}
		}
		condition.setSort("id");
		condition.setSortDir("desc");
		List<SellOrder> orders = this.sellOrderLib.getByCondition(condition);
		for (SellOrder order : orders) {
			List<SellOrderSku> skus = this.sellOrderSkuLib.getBySellOrder(order
					.getId());
			for (SellOrderSku sku : skus) {
				sku.setSku(this.skuLib.getById(sku.getSkuId()));
			}
			order.setSkus(skus);
			order.setSeller(this.userinfoLib.getById(order.getSellerId()));
		}
		mv.addObject("selected", selected);
		mv.addObject("all_store_machines", storeMachines);
		mv.addObject("orders", orders);
		mv.addObject("condition",condition);
	}

	@Override
	public void prepareMachineStoreDetailData(int userId, int warehouseId,
			ModelAndView mv) {
		List<Warehouse> all = this.warehouseLib.listAllMachineStore();
		mv.addObject("all_store_machines", all);
		Warehouse selected = null;
		for (Warehouse w : all) {
			if (w.getId() == warehouseId || selected == null) {
				selected = w;
			}
		}
		if (selected != null) {
			Warehouse parent = this.warehouseLib.getById(selected
					.getWarehouse_parent());
			if (parent != null) {
				this.warehouseLib.setWarehouseManagers(parent);

			}
			selected.setWarehouseParent(parent);
			this.warehouseLib.setWarehouseManagers(selected);
		}
		mv.addObject("selected_store_machine", selected);

	}

}
