package com.mjitech.lib;

import java.util.List;

import com.mjitech.model.Instore;

public interface InstoreLib extends BaseModelLib<Instore>{
	List<Instore> listInStore(int warehouse);
}
