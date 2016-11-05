-- 2016/5/7 1st version --
DROP TABLE IF EXISTS `mt_sku`;
CREATE TABLE `mt_sku` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `barcode` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `brand` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `country` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '产地国家/地区',
 `category` bigint(10) unsigned NOT NULL DEFAULT '0',
 `unit` varchar(5) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '单位',
 `msrp` float(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '建议零售价',
 `tags` varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '多个标签用,分割',
 `remarks` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 UNIQUE KEY `barcode` (`barcode`),
 KEY `country` (`country`),
 KEY `category` (`category`),
 KEY `msrp` (`msrp`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商品信息';

ALTER TABLE `mt_sku` ADD `image_id` BIGINT(10) UNSIGNED NOT NULL DEFAULT '0' AFTER `name`, ADD `image_path` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' AFTER `image_id`, ADD INDEX (`image_id`) ;

ALTER TABLE `mt_sku` ADD `status` TINYINT(4) UNSIGNED NOT NULL DEFAULT '0' AFTER `name`, ADD INDEX (`status`) ;
-- 2016/5/9 1st ver mt_storage --
-- 未处理仓库管理员与仓库的关联 --
DROP TABLE IF EXISTS `mt_storage`;

-- 2016/5/22 userinfo --
DROP TABLE IF EXISTS `mt_userinfo`;
CREATE TABLE `mt_userinfo` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `username` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '登录名',
 `user_type` int(6) unsigned NOT NULL DEFAULT '1' COMMENT '用户类别：管理员，库管员，消费者等',
 `display_name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '显示用的名字',
 `display_name_pinyin` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '名字的拼音',
 `gender` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '性别',
 `mobile` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
 `email` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `password` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `image` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 UNIQUE KEY `username` (`username`),
 KEY `display_name` (`display_name`),
 KEY `user_type` (`user_type`),
 KEY `gender` (`gender`),
 KEY `mobile` (`mobile`),
 UNIQUE KEY `email` (`email`),
 KEY `password` (`password`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2016/6/3v1.0 mt_sku_spec
DROP TABLE IF EXISTS `mt_sku_spec`;
CREATE TABLE `mt_sku_spec` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `amount` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '规格数量',
 `unit` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '规格单位',
 `type` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '是否为默认规格', 
 `skuid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '对应的商品ID',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `skuid` (`skuid`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商品规格';

-- 2016/6/5 v1.0 mt_order --
DROP TABLE IF EXISTS `mt_order`;
CREATE TABLE `mt_order` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `orderdate` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `supplierid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '供应商编号',
 `suppliercontactid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '供应商联系人',
 `storehouseid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '收货仓库编号',
 `storehousemanagerid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '仓库联系人',
 `paydate` datetime NOT NULL DEFAULT '1970-01-01 00:00:00', 
 `payname` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `payaccount` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '', 
 `paybank` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '', 
 `paymode` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '付款方式',
 `invoicetype` varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '发票类型',
 `receivedate` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '到货日期', 
 `contract` varchar(1000) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '合同条款',
 `payamt` float(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '合同总额',
 `distamt` float(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '物流总额', 
 `orderstate` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '订单状态', 
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00', 
 PRIMARY KEY (`id`),
 KEY `orderdate` (`orderdate`),
 KEY `supplierid` (`supplierid`),
 KEY `suppliercontactid` (`suppliercontactid`),
 KEY `storehouseid` (`storehouseid`),
 KEY `storehousemanagerid` (`storehousemanagerid`),
 KEY `paydate` (`paydate`),
 KEY `payname` (`payname`),
 KEY `payaccount` (`payaccount`),
 KEY `paymode` (`paymode`),
 KEY `receivedate` (`receivedate`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='采购合同';


-- 2016/6/5 v1.0 mt_order_sku --
DROP TABLE IF EXISTS `mt_order_sku`;
CREATE TABLE `mt_order_sku` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `skuid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '商品编号', 
 `skuspecid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '规格编号', 
 `orderamt` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '采购数量', 
 `specprice` float(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '规格报价',
 `orderid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '关联订单',  
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00', 
 PRIMARY KEY (`id`),
 KEY `skuid` (`skuid`),
 KEY `skuspecid` (`skuspecid`),
 KEY `orderid` (`orderid`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='采购订单明细';

-- 2016/5/14 v1.0 mt_supplier_ --
DROP TABLE IF EXISTS `mt_supplier`;
CREATE TABLE `mt_supplier` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `sname` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `address` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `remarks` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `account` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '', 
 `bank` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '', 
 `shipaddress` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '', 
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00', 
 `data_state` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '数据有效性', 
 PRIMARY KEY (`id`),
 UNIQUE KEY `sname` (`sname`),
 KEY `account` (`account`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='供应商信息';

-- 2016/5/14 v1.0 mt_supplier_contact --
DROP TABLE IF EXISTS `mt_supplier_contact`;
CREATE TABLE `mt_supplier_contact` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `cname` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `tel` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `fax` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `email` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `remarks` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `type` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '是否为主要联系人',
 `supplierid` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '供应商编号',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00', 
 `data_state` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '数据有效性', 
 PRIMARY KEY (`id`),
 UNIQUE KEY `cname` (`cname`),
 KEY `tel` (`tel`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='供应商联系人';

-- 2016/5/29 v2.0 mt_supplier/_contact 不采用假删除--
ALTER TABLE mt_supplier DROP COLUMN data_state;
ALTER TABLE mt_supplier_contact DROP COLUMN data_state;

DROP TABLE IF EXISTS `mt_warehouse`;
CREATE TABLE `mt_warehouse` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `type` enum('warehouse','store','machine') NOT NULL default 'warehouse', 
 `province` bigint(10) unsigned NOT NULL DEFAULT '0',
 `city` bigint(10) unsigned NOT NULL DEFAULT '0',
 `region` bigint(10) unsigned NOT NULL DEFAULT '0',
 `address` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `manager` bigint(10) unsigned NOT NULL DEFAULT '0',
 `latitude` float(10, 6) NOT NULL,
 `longitude` float(10, 6) NOT NULL,
 `width` float(10,2) unsigned NOT NULL DEFAULT '0.00',
 `height` float(10,2) unsigned NOT NULL DEFAULT '0.00',
 `remarks` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
   
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='warehouse';

DROP TABLE IF EXISTS `mt_receipt`;
CREATE TABLE `mt_receipt` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `employee_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `order_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `tracking_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='收货单';

DROP TABLE IF EXISTS `mt_receipt_item`;
CREATE TABLE `mt_receipt_item` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `receipt_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sku_spec_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `quantity` bigint(10) unsigned NOT NULL DEFAULT '0',
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='收货单明细';

DROP TABLE IF EXISTS `mt_checking`;
CREATE TABLE `mt_checking` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `receipt_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `result` enum('accept','reject') NOT NULL default 'reject',
  `comments` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='收货单审核';

DROP TABLE IF EXISTS `mt_receiving`;
CREATE TABLE `mt_receiving` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `receipt_id` bigint(10) unsigned NOT NULL DEFAULT '0',  
  `result` enum('received','na') NOT NULL default 'na',
  `comments` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='收货单收货';

DROP TABLE IF EXISTS `mt_inspecting`;
CREATE TABLE `mt_inspecting` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `receipt_id` bigint(10) unsigned NOT NULL DEFAULT '0',  
  `receipt_item_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `pass` bigint(10) unsigned NOT NULL DEFAULT '0',
  `issue` bigint(10) unsigned NOT NULL DEFAULT '0',
  `lost` bigint(10) unsigned NOT NULL DEFAULT '0',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='收货单验货';

DROP TABLE IF EXISTS `mt_sort`;
DROP TABLE IF EXISTS `mt_sorting`;
CREATE TABLE `mt_sorting` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `employee_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `status` enum('untreated','sorting','sorted') NOT NULL default 'untreated',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='分拣单';

DROP TABLE IF EXISTS `mt_replenishment`;
DROP TABLE IF EXISTS `mt_replenishment_item`;
DROP TABLE IF EXISTS `mt_replenish`;
DROP TABLE IF EXISTS `mt_replenish_item`;
DROP TABLE IF EXISTS `mt_request`;
CREATE TABLE `mt_request` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sorting_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sku_spec_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `quantity` bigint(10) unsigned NOT NULL DEFAULT '0',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='机器请求/分拣单明细';

DROP TABLE IF EXISTS `mt_delivery`;
CREATE TABLE `mt_delivery`(
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `sorting_id` bigint(10) unsigned NOT NULL DEFAULT '0',

  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00', 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='配送单';

DROP TABLE IF EXISTS `mt_stocktaking`;
CREATE TABLE `mt_stocktaking`(
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `sorting_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `loss` bigint(10) unsigned NOT NULL DEFAULT '0',
  `expire` bigint(10) unsigned NOT NULL DEFAULT '0',

  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',     
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='盘库';

DROP TABLE IF EXISTS `mt_stockin`;
CREATE TABLE `mt_stockin` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `receipt_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `type` enum('unready','done','error') NOT NULL default 'unready',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='入库单';

DROP TABLE IF EXISTS `mt_stockin_item`;
CREATE TABLE `mt_stockin_item` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `stockin_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `quantity` bigint(10) unsigned NOT NULL DEFAULT '0', 
   
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='入库单明细';

DROP TABLE IF EXISTS `mt_stockout`;
DROP TABLE IF EXISTS `mt_inventory`;
CREATE TABLE `mt_inventory` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `sku_spec_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `quantity` bigint(10) unsigned NOT NULL DEFAULT '0',
  `max_stock` bigint(10) unsigned NOT NULL DEFAULT '0',
  `safe_stock` bigint(10) unsigned NOT NULL DEFAULT '0',
  `min_stock` bigint(10) unsigned NOT NULL DEFAULT '0',
  `type` enum('ready','stopped','removed') NOT NULL default 'ready',
  `position` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `sellprice` float(10,2) unsigned NOT NULL DEFAULT '0.00',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='库存';


-- 2016/06/13 zz menu tables --
DROP TABLE IF EXISTS `mt_menu`;
CREATE TABLE `mt_menu` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `parent_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `url` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `sort_number` int(10) unsigned NOT NULL DEFAULT '0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
 PRIMARY KEY (`id`),
 KEY `parent_id` (`parent_id`),
 KEY `sort_number` (`sort_number`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2016/6/14 zz--
ALTER TABLE `mt_menu` ADD `icon_class` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' AFTER `url`;

-- 2016/6/19 zz--
DROP TABLE IF EXISTS `mt_sku_type`;
CREATE TABLE `mt_sku_type` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `parent_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `sort_number` int(10) unsigned NOT NULL DEFAULT '0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
 PRIMARY KEY (`id`),
 KEY `parent_id` (`parent_id`),
 KEY `sort_number` (`sort_number`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `mt_sku_brand`;
CREATE TABLE `mt_sku_brand` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT, 
 `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `url` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `sort_number` int(10) unsigned NOT NULL DEFAULT '0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',  
 PRIMARY KEY (`id`),
 KEY `sort_number` (`sort_number`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `mt_dict_area`;
CREATE TABLE IF NOT EXISTS `mt_dict_area` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `type` tinyint(4) unsigned NOT NULL DEFAULT '1',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `postcode` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `creator` (`creator`),
  KEY `updator` (`updator`),
  KEY `create_datetime` (`create_datetime`),
  KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='字典表：国家，省/州，城市';

ALTER TABLE `mt_sku` CHANGE `brand` `brand` BIGINT(10) UNSIGNED NOT NULL DEFAULT '0';
ALTER TABLE `mt_sku` ADD INDEX(`brand`);

ALTER TABLE mt_sku DROP INDEX barcode;

ALTER TABLE `mt_sku` ADD INDEX(`barcode`);

DROP TABLE IF EXISTS `mt_sku_type_attribute`;
CREATE TABLE `mt_sku_type_attribute` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `sku_type_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `unit` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `remarks` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `sku_type_id` (`sku_type_id`),
 KEY `creator` (`creator`),
  KEY `updator` (`updator`),
  KEY `create_datetime` (`create_datetime`),
  KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商品类别属性定义';

DROP TABLE IF EXISTS `mt_sku_attribute`;
CREATE TABLE `mt_sku_attribute` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `sku_type_attribute_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `value` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `remarks` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `sku_id` (`sku_id`),
 KEY `sku_type_attribute_id` (`sku_type_attribute_id`),
 KEY `creator` (`creator`),
  KEY `updator` (`updator`),
  KEY `create_datetime` (`create_datetime`),
  KEY `update_datetime` (`update_datetime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商品属性值,类别属性对应的属性值';

-- 2016/6/26 zz --
ALTER TABLE `mt_sku` ADD `publish_time` DATETIME NULL AFTER `remarks`, ADD INDEX (`publish_time`) ;

-- 2016/6/27 zz --
ALTER TABLE `mt_sku_attribute` ADD `sku_type_id` BIGINT(10) UNSIGNED NOT NULL DEFAULT '0' AFTER `sku_id`, ADD INDEX (`sku_type_id`) ;

-- 2016/7/28 zz--
ALTER TABLE `mt_sku` ADD `expiration_days` INT(10) NOT NULL DEFAULT '0' AFTER `msrp`, ADD `length` INT(10) NOT NULL COMMENT '单位毫米' AFTER `expiration_days`, ADD `width` INT(10) NOT NULL COMMENT '单位毫米' AFTER `length`, ADD `height` INT(10) NOT NULL COMMENT '单位毫米' AFTER `width`, ADD INDEX (`expiration_days`) , ADD INDEX (`length`) , ADD INDEX (`width`) , ADD INDEX (`height`) ;

ALTER TABLE `mt_sku` ADD `min_stock` INT(10) NOT NULL DEFAULT '0' COMMENT '最小库存' AFTER `height`, ADD `safe_stock` INT(10) NOT NULL COMMENT '安全库存' AFTER `min_stock`, ADD `max_stock` INT(10) NOT NULL COMMENT '最大库存' AFTER `safe_stock`, ADD INDEX (`safe_stock`) , ADD INDEX (`min_stock`) , ADD INDEX (`max_stock`) ;

-- 2016/7/30 zz --
DROP TABLE IF EXISTS `mt_file`;
CREATE TABLE `mt_file` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `original_name` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '原文件名',
 `file_path` varchar(300) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '文件路径',
 `type` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '类型，如临时文件，sku图片等',
 `file_type` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '文件类型：1-普通文件，2-图片，3-视频',
 `relate_id` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '与此文件关联的对象id',
 `image_width` int(8) unsigned NOT NULL DEFAULT '0',
 `image_height` int(8) unsigned NOT NULL DEFAULT '0',
 `file_size` bigint(10) NOT NULL DEFAULT '0' COMMENT '文件大小，字节数',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
 `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `file_type` (`file_type`),
 KEY `type` (`type`),
 KEY `relate_id` (`relate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2016/8/1 zz --
ALTER TABLE `mt_sku_type_attribute` ADD `type` INT(6) UNSIGNED NOT NULL DEFAULT '1' AFTER `sku_type_id`, ADD INDEX (`type`) ;
ALTER TABLE `mt_sku_type_attribute` ADD `options` VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' AFTER `type`;

-- 2016/8/6 yx 处理同名联系人 --
ALTER TABLE `mt_supplier_contact`  DROP INDEX `cname`,  ADD INDEX `cname` (`cname`); 

-- 2016/8/8 shshen --
ALTER TABLE `mt_receipt` ADD `remark`  varchar(100) CHARACTER SET utf8 DEFAULT '' AFTER `tracking_id`;
ALTER TABLE `mt_receipt` ADD `status`  varchar(20) CHARACTER SET utf8 DEFAULT '' AFTER `remark`;
ALTER TABLE `mt_stockin` ADD `manager_id`  bigint(10) unsigned AFTER `type`;
ALTER TABLE `mt_warehouse` ADD `warehouse_parent` bigint(10) unsigned AFTER `type`;
ALTER TABLE `mt_request` CHANGE `warehouse_id` `machine_id` bigint(10) unsigned;
DROP TABLE IF EXISTS `mt_sale`;
CREATE TABLE `mt_sale`(
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `warehouse_id` bigint(10) unsigned NOT NULL,
  `userid` bigint(10) unsigned NOT NULL, 
  `openid` varchar(64) CHARACTER SET utf8 NOT NULL,
  `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `total_amount` float(10,2) unsigned NOT NULL DEFAULT '0.00',
  `discounted_amount` float(10,2) unsigned NOT NULL DEFAULT '0.00',
  `actual_amount` float(10,2) unsigned NOT NULL DEFAULT '0.00',
  `quantity` bigint(10) unsigned DEFAULT '0',
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='销售记录';

DROP TABLE IF EXISTS `mt_sale_item`;
CREATE TABLE `mt_sale_item`(
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `sale_id` bigint(10) unsigned NOT NULL,
  `status` smallint NOT NULL default 0, 
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='销售记录明细'; 

-- 2016/8/14 zz --
ALTER TABLE `mt_sku` ADD `sku_number` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' AFTER `id`, ADD UNIQUE (`sku_number`) ;

-- 2016/8/27 --
ALTER TABLE `mt_userinfo` ADD `open_id` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' AFTER `image`, ADD INDEX (`open_id`) ;

-- 2016/8/31 zz --
ALTER TABLE `mt_sku` ADD `short_name` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' AFTER `name`, ADD INDEX (`short_name`) ;

-- 2016/9/1 zz --
ALTER TABLE `mt_sku_type` ADD `code` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' AFTER `name`, ADD INDEX (`code`) ;

-- 2016/9/5 zz --
ALTER TABLE `mt_userinfo` ADD `wx_bind_time` DATETIME NULL DEFAULT NULL AFTER `open_id`,  ADD INDEX (`wx_bind_time`) ;

-- 2016/9/5 zz --
DROP TABLE IF EXISTS `mt_sell_order`;
CREATE TABLE `mt_sell_order` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `order_number` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `seller_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `sell_time` datetime DEFAULT NULL,
 `status` int(5) unsigned NOT NULL DEFAULT '0',
 `pay_status` int(5) unsigned NOT NULL DEFAULT '0',
 `pay_time` datetime DEFAULT NULL,
 `cancel_time` datetime DEFAULT NULL,
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `order_number` (`order_number`),
 KEY `seller_id` (`seller_id`),
 KEY `warehouse_id` (`warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='零售订单';


DROP TABLE IF EXISTS `mt_sell_order_sku`;
CREATE TABLE `mt_sell_order_sku` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `sell_order_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `count` int(6) unsigned NOT NULL DEFAULT '0',
 `sell_price` float(8,2) unsigned NOT NULL DEFAULT '0.0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `sell_order_id` (`sell_order_id`),
 KEY `sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='零售订单商品';

-- 2016/9/8 zz --
ALTER TABLE `mt_sell_order` ADD `total_price` float(10,2) unsigned NOT NULL DEFAULT '0.0' AFTER `cancel_time`,  ADD INDEX (`total_price`) ;

ALTER TABLE `mt_sku` ADD `unique_number` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `sku_number`,  ADD INDEX (`unique_number`) ;

-- 2016/9/11 zz --
ALTER TABLE `mt_sell_order` ADD `wxpay_url` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' AFTER `total_price`,  ADD INDEX (`wxpay_url`) ;
ALTER TABLE `mt_sell_order` ADD `wxprepay_id` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' AFTER `wxpay_url`,  ADD INDEX (`wxprepay_id`) ;
ALTER TABLE `mt_sell_order` ADD `request_wxpay_time` datetime DEFAULT NULL AFTER `wxprepay_id`,  ADD INDEX (`request_wxpay_time`) ;

DROP TABLE IF EXISTS `mt_wxpay_history`;
CREATE TABLE `mt_wxpay_history` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `sell_order_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `sell_order_number` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `wxpay_return` varchar(1000) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `sell_order_id` (`sell_order_id`),
 KEY `sell_order_number` (`sell_order_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='微信支付历史';

-- 2016/9/22 zz --
DROP TABLE IF EXISTS `mt_warehouse_manager`;
CREATE TABLE `mt_warehouse_manager` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `manager_id` bigint(10) unsigned NOT NULL DEFAULT '0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `manager_id` (`manager_id`),
 KEY `warehouse_id` (`warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='warehouse manager';

-- 2016/9/25 zz--
ALTER TABLE `mt_sell_order` ADD `buyer_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `seller_id`,  ADD INDEX (`buyer_id`) ;

DROP TABLE IF EXISTS `mt_wx_autoreply`;
CREATE TABLE `mt_wx_autoreply` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `type` int(6) unsigned NOT NULL DEFAULT '0',
 `content` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `enabled` int(6) unsigned NOT NULL DEFAULT '0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `type` (`type`),
 KEY `enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='wx auto reply messsage';

-- 2016/9/26 zz --
ALTER TABLE `mt_sell_order` ADD `cancel_user_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `buyer_id`,  ADD INDEX (`cancel_user_id`) ;

-- 2016/9/30 zz --
ALTER TABLE `mt_warehouse_manager` ADD `type` int(6) unsigned NOT NULL DEFAULT '0' AFTER `manager_id`,  ADD INDEX (`type`) ;

ALTER TABLE `mt_warehouse` ADD `status` int(6) unsigned NOT NULL DEFAULT '0' AFTER `type`,  ADD INDEX (`status`) ;

-- 2016/10/1 zz --
DROP TABLE IF EXISTS `mt_outstore`;
CREATE TABLE `mt_outstore` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `type` int(6) unsigned NOT NULL DEFAULT '0',
 `to_warehouse_id` bigint(10) unsigned DEFAULT '0',
 `description` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `price` float(10,2) unsigned NOT NULL DEFAULT '0.0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `to_warehouse_id` (`to_warehouse_id`),
 KEY `type` (`type`),
 KEY `price` (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='出库记录';

DROP TABLE IF EXISTS `mt_instore`;
CREATE TABLE `mt_instore` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `type` int(6) unsigned NOT NULL DEFAULT '0',
 `from_warehouse_id` bigint(10) unsigned DEFAULT '0',
 `description` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `price` float(10,2) unsigned NOT NULL DEFAULT '0.0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `from_warehouse_id` (`from_warehouse_id`),
 KEY `type` (`type`),
 KEY `price` (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='入库记录';

DROP TABLE IF EXISTS `mt_stockin`;
DROP TABLE IF EXISTS `mt_stockin_item`;
ALTER TABLE `mt_outstore` ADD `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `type` ;
ALTER TABLE `mt_outstore` ADD `quantity` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `sku_id` ;
ALTER TABLE `mt_outstore` ADD `receipt_id` bigint(10) unsigned DEFAULT '0' AFTER `quantity` ;
ALTER TABLE `mt_instore` ADD `sku_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `type` ;
ALTER TABLE `mt_instore` ADD `quantity` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `sku_id` ;
ALTER TABLE `mt_instore` ADD `receipt_id` bigint(10) unsigned DEFAULT '0' AFTER `quantity` ;

-- 2016/10/8 zz --
DROP TABLE IF EXISTS `mt_operation_log`;
CREATE TABLE `mt_operation_log` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `type` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `action` varchar(40) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `userid` bigint(10) unsigned DEFAULT '0',
 `target_id` bigint(10) unsigned DEFAULT '0',
 `ip` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `description` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `type` (`type`),
 KEY `action` (`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='操作日志'
PARTITION BY RANGE (id) (PARTITION p0 VALUES LESS THAN (200000),  
PARTITION p1 VALUES LESS THAN (400000) , PARTITION p2 VALUES LESS THAN (600000) ,  
PARTITION p3 VALUES LESS THAN (800000) , PARTITION p4 VALUES LESS THAN (1000000) ,  
PARTITION p5 VALUES LESS THAN (1200000) , PARTITION p6 VALUES LESS THAN (1400000) ,  
PARTITION p7 VALUES LESS THAN (1600000) , PARTITION p8 VALUES LESS THAN (1800000) ,  
PARTITION p9 VALUES LESS THAN (2000000) , PARTITION p10 VALUES LESS THAN MAXVALUE);

ALTER TABLE `mt_outstore` ADD `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `type` ;
ALTER TABLE `mt_outstore` ADD `userid` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `warehouse_id` ;

ALTER TABLE `mt_instore` ADD `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `type` ;
ALTER TABLE `mt_instore` ADD `userid` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `warehouse_id` ;

ALTER TABLE `mt_operation_log` ADD `old_value` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' AFTER `description` ;
ALTER TABLE `mt_operation_log` ADD `new_value` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' AFTER `old_value` ;

DROP TABLE IF EXISTS `mt_sort`;
DROP TABLE IF EXISTS `mt_sorting`;
DROP TABLE IF EXISTS `mt_picking`;

CREATE TABLE `mt_picking` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `employee_id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `status` enum('untreated','picking','picked') NOT NULL default 'untreated',
  
  `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='分拣单';

DROP TABLE IF EXISTS `mt_sale`;
DROP TABLE IF EXISTS `mt_sale_item`;
ALTER TABLE mt_picking DROP COLUMN date;
ALTER TABLE `mt_picking` ADD `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `id` ;


-- 2016/10/14 zz --
ALTER TABLE `mt_outstore` ADD `sell_order_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `type` ;

DROP TABLE IF EXISTS `mt_market_banner`;
CREATE TABLE `mt_market_banner` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `warehouse_id` bigint(10) unsigned DEFAULT '0',
 `sku_id` bigint(10) unsigned DEFAULT '0',
 `type` int(6) unsigned NOT NULL DEFAULT '0',
 `image_path` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `description` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `url` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `warehouse_id` (`warehouse_id`),
 KEY `type` (`type`),
 KEY `sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='活动banner';

ALTER TABLE `mt_picking` ADD `machine_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `id` ;
ALTER TABLE `mt_request` ADD `warehouse_id` bigint(10) unsigned NOT NULL DEFAULT '0' AFTER `machine_id` ;

-- 2016/10/20 zz --

-- 2016/10/21 alter table (order and ordersku)
ALTER TABLE `mt_order`
MODIFY COLUMN `id`  bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id' FIRST ,
MODIFY COLUMN `orderdate`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '订单创建日期' AFTER `id`,
MODIFY COLUMN `supplierid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '供应商ID' AFTER `orderdate`,
MODIFY COLUMN `suppliercontactid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '供应商联系人ID' AFTER `supplierid`,
MODIFY COLUMN `storehouseid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '收货仓库ID' AFTER `suppliercontactid`,
MODIFY COLUMN `storehousemanagerid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '仓库联系人ID' AFTER `storehouseid`,
MODIFY COLUMN `paydate`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '付款日期' AFTER `storehousemanagerid`,
MODIFY COLUMN `payname`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收款方' AFTER `paydate`,
MODIFY COLUMN `payaccount`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收款帐号' AFTER `payname`,
MODIFY COLUMN `paybank`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '开户行' AFTER `payaccount`,
MODIFY COLUMN `paymode`  bigint(10) UNSIGNED NOT NULL COMMENT ' 付款方式' AFTER `paybank`,
MODIFY COLUMN `invoicetype`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT ' 发票类型' AFTER `paymode`,
MODIFY COLUMN `receivedate`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '最晚到货日期' AFTER `invoicetype`,
MODIFY COLUMN `contract`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT ' 合同条款' AFTER `receivedate`,
MODIFY COLUMN `payamt`  float(10,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '商品总合同总额' AFTER `contract`,
MODIFY COLUMN `distamt`  float(10,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT ' 物流总额' AFTER `payamt`,
MODIFY COLUMN `orderstate`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单状态 （0：新增  1生成合同  2：入库  3：放弃取消）' AFTER `distamt`,
ADD COLUMN `userid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单负责人' AFTER `storehousemanagerid`;


ALTER TABLE `mt_order_sku`
MODIFY COLUMN `skuid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品ID' AFTER `id`,
MODIFY COLUMN `skuspecid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品规格编号' AFTER `skuid`,
MODIFY COLUMN `orderamt`  float(10,0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品采购数量' AFTER `skuspecid`,
MODIFY COLUMN `specprice`  float(10,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '商品规格报价' AFTER `orderamt`,
MODIFY COLUMN `orderid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '关联订单ID' AFTER `specprice`;


-- 2016/10/23 zz sell order history--
DROP TABLE IF EXISTS `mt_sell_order_status_history`;
CREATE TABLE `mt_sell_order_status_history` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `sell_order_id` bigint(10) unsigned DEFAULT '0',
 `userid` bigint(10) unsigned DEFAULT '0',
 `status` int(6) unsigned NOT NULL DEFAULT '0',
 `status_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `sell_order_id` (`sell_order_id`),
 KEY `status` (`status`),
 KEY `userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='销售订单状态历史';

ALTER TABLE `mt_sell_order` ADD `take_goods_number` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '' AFTER `request_wxpay_time` ;
ALTER TABLE `mt_sell_order` ADD INDEX(`take_goods_number`);

-- 2016/10/25 zz --
ALTER TABLE `mt_sell_order` ADD `parent_id` bigint(10) unsigned DEFAULT '0' AFTER `id` ;
ALTER TABLE `mt_sell_order` ADD INDEX(`parent_id`);

-- 2016/10/27 zz --
ALTER TABLE `mt_sell_order` ADD `is_parent` int(4) unsigned DEFAULT '0' AFTER `parent_id` ;
ALTER TABLE `mt_sell_order` ADD INDEX(`is_parent`);

-- 2016/10/28 zz --
DROP TABLE IF EXISTS `mt_points`;
CREATE TABLE `mt_points` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `userid` bigint(10) unsigned DEFAULT '0',
 `type` int(6) unsigned DEFAULT '0',
 `total` int(8) NOT NULL DEFAULT '0',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `userid` (`userid`),
 KEY `total` (`total`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='积分';

DROP TABLE IF EXISTS `mt_points_history`;
CREATE TABLE `mt_points_history` (
 `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
 `userid` bigint(10) unsigned DEFAULT '0',
 `type` int(6) unsigned DEFAULT '0',
 `count` int(8) NOT NULL DEFAULT '0',
 `points_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 `creator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `create_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `updator` bigint(10) unsigned NOT NULL DEFAULT '0',
  `update_datetime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
 PRIMARY KEY (`id`),
 KEY `creator` (`creator`),
 KEY `updator` (`updator`),
 KEY `create_datetime` (`create_datetime`),
 KEY `update_datetime` (`update_datetime`),
 KEY `points_datetime` (`points_datetime`),
 KEY `type` (`type`),
 KEY `userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='积分消费历史';


-- 2016/11/1 supplier add column payname (户名) --
ALTER TABLE `mt_supplier` ADD `payname` VARCHAR(200) CHARACTER SET utf8 NULL COMMENT '户名';

-- 2016/11/2 zz --
ALTER TABLE `mt_sku_brand` ADD `story` varchar(2000) CHARACTER SET utf8 NOT NULL DEFAULT '' AFTER `name` ;
ALTER TABLE `mt_sku_brand` ADD `description` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '' AFTER `story` ;

-- 2016/11/03 order and ordersku --
ALTER TABLE `mt_order`
ADD COLUMN `supplierusername`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商法人代表' AFTER `suppliercontactid`,
ADD COLUMN `supplierusertel`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商法人代表联系电话' AFTER `supplierusername`,
ADD COLUMN `storehousemanagertel`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '仓库联系人联系电话' AFTER `storehousemanagerid`,
MODIFY COLUMN `userid`  bigint(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '法定代表人' AFTER `storehousemanagertel`,
ADD COLUMN `usertel`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '法定代表人联系电话' AFTER `userid`,
ADD COLUMN `settlemode`  bigint(10) UNSIGNED NOT NULL COMMENT '结算形式 1：先款后货   2：先货后款' AFTER `paybank`,
MODIFY COLUMN `invoicetype`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT ' 发票类型' AFTER `paymode`,
MODIFY COLUMN `receivedate`  datetime NULL DEFAULT '1970-01-01 00:00:00' COMMENT '最晚到货日期' AFTER `invoicetype`;

ALTER TABLE `mt_order_sku`
ADD COLUMN `skunumber`  varchar(20) NOT NULL COMMENT '商品SKU唯一编号' AFTER `skuid`,
ADD COLUMN `skuname`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称' AFTER `skunumber`,
ADD COLUMN `specname`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品箱规' AFTER `skuspecid`,
ADD COLUMN `unit`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品单位' AFTER `specname`,
ADD COLUMN `amount`  float(10,2) NOT NULL COMMENT '商品总价' AFTER `specprice`,
ADD COLUMN `remarks`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品备注' AFTER `amount`;

ALTER TABLE `mt_order`
ADD COLUMN `storehousemanagername`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '仓库联系人' AFTER `storehousemanagerid`,
MODIFY COLUMN `paydate`  datetime NULL DEFAULT '1970-01-01 00:00:00' COMMENT '付款日期' AFTER `usertel`;


