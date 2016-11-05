package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Warehouse;

public interface WarehouseLib extends BaseModelLib<Warehouse> {

	List<Warehouse> listAllWarehouse();

	Warehouse getByManager(int manager);

	Warehouse getMachineById(int id);

	Warehouse getStoreById(int id);

	List<Warehouse> listAllMachineStore();

	void setWarehouseManagers(Warehouse warehouse);

	Warehouse getNearestStore(long longitude, long latitude);

}
