-- 2016/6/15 menu data --
INSERT INTO `mt_menu` (`id`, `parent_id`, `name`, `url`, `icon_class`, `sort_number`, `creator`, `create_datetime`, `updator`, `update_datetime`) VALUES
(1, 0, '商品', '', 'icon-2', 1, 0, '2016-06-14 00:04:10', 0, '2016-06-14 00:04:10'),
(2, 0, '仓储库存', '', 'icon-3', 2, 0, '2016-06-14 09:19:26', 0, '2016-06-14 09:19:26'),
(3, 0, '采购订单', '', 'icon-4', 3, 0, '2016-06-14 09:19:44', 0, '2016-06-14 09:19:44'),
(4, 0, '物流配送', '', 'icon-5', 4, 0, '2016-06-14 09:20:00', 0, '2016-06-14 09:20:00'),
(5, 0, '门店机器', '', 'icon-6', 5, 0, '2016-06-14 09:20:13', 0, '2016-06-14 09:20:13'),
(6, 0, '供应商', '', 'icon-7', 6, 0, '2016-06-14 09:20:22', 0, '2016-06-14 09:20:22'),
(7, 1, '商品列表', '/sku_list.action', '', 7, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(8, 1, '添加商品', '/add_sku.action', '', 8, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40');

INSERT INTO `mt_menu` (`id`, `parent_id`, `name`, `url`, `icon_class`, `sort_number`, `creator`, `create_datetime`, `updator`, `update_datetime`) VALUES
(9,  2, '商品库存', '/warehouse_inventory_home.action', '', 9, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(10, 2, '收货单', '/receipt_home.action', '', 10, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(11, 2, '入库请求', '/stockin_request_home.action', '', 11, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(12, 2, '入库单', '/stockin_home.action', '', 12, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(13, 2, '机器补货请求', '/warehouse_request_home.action', '', 13, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(14, 2, '分拣单', '/sorting_home.action', '', 14, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),

(15, 5, '机器详情', '/machine_detail_home.action', '', 15, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(16, 5, '机器补货请求', '/machine_request_home.action', '', 16, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(17, 5, '商品库存', '/machine_inventory_home.action', '', 17, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(18, 5, '销售订单', '/sell_history_home.action', '', 18, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40'),
(19, 5, '配送单', '/delivery_home.action', '', 19, 0, '2016-06-14 09:21:40', 0, '2016-06-14 09:21:40');


INSERT INTO `mt_menu` (`id`, `parent_id`, `name`, `url`, `icon_class`, `sort_number`, `creator`, `create_datetime`, `updator`, `update_datetime`) VALUES
(20, 6, '供应商列表', '/list_supplier.action', '', 20, 0, '2016-06-14 09:21:40', 0, '2016-06-18 10:20:00'),
(21, 6, '添加供应商', '/addinit_supplier.action', '', 21, 0, '2016-06-14 09:21:40', 0, '2016-06-18 10:20:00');

-- 2016/6/27 zz --
UPDATE `mt_menu` SET `name` = '编辑商品' WHERE `mt_menu`.`id` = 8;
UPDATE `mt_menu` SET `url` = '/add_sku_page.action' WHERE `mt_menu`.`id` = 8;
UPDATE `maxbox`.`mt_menu` SET `name` = '编辑商品' WHERE `mt_menu`.`id` = 8;

-- 2016/10/13 shen --
UPDATE `mt_menu` SET `url`="/picking_home.action" where id=14;

-- 2016/10/21 luotong add menu for order --
INSERT INTO `mt_menu` (`id`, `parent_id`, `name`, `url`, `icon_class`, `sort_number`, `creator`, `create_datetime`, `updator`, `update_datetime`) VALUES ('22', '3', '订单列表', '/list_order.action', '', '22', '0', '2016-10-21 11:15:26', '0', '2016-10-21 11:15:26');