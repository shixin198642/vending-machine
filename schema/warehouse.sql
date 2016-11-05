INSERT INTO mt_warehouse(id, name, type,province, city,region,address,manager,latitude,longitude,width, 
                         height,remarks, creator, create_datetime, updator, update_datetime) VALUES
       (1, "北京第一仓库", "warehouse", 1,1,1, "address",1,1,1,1,1,"remarks", 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (2, "北京第二仓库", "warehouse", 1,1,1, "address",1,1,1,1,1,"remarks", 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (3, "北京第三仓库", "warehouse", 1,1,1, "address",1,1,1,1,1,"remarks", 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (4, "北京第四仓库", "warehouse", 1,1,1, "address",1,1,1,1,1,"remarks", 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (5, "北京第五仓库", "machine", 1,1,1, "address",1,1,1,1,1,"remarks", 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (6, "北京第六仓库", "machine", 1,1,1, "address",1,1,1,1,1,"remarks", 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');
       
INSERT INTO mt_inventory(id,warehouse_id,sku_id,sku_spec_id,quantity,max_stock,safe_stock,min_stock,type,position,sellprice,
                         creator, create_datetime, updator, update_datetime) VALUES
       (1, 1, 1, 2, 83, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (2, 1, 2, 2, 35, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (3, 2, 1, 2, 83, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (4, 2, 2, 2, 26, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (5, 2, 3, 2, 15, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (6, 2, 4, 2, 92, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (7, 5, 1, 2, 92, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (8, 5, 2, 2, 92, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (9, 5, 3, 2, 92, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (10, 5, 4, 2, 92, 100, 40, 10, "ready", "top1234", 12, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');
       
INSERT INTO mt_sku(id, sku_number, barcode, name, status, image_id,image_path, brand, country,category,unit, msrp,expiration_days,length,width,height,
                   min_stock,safe_stock,max_stock,tags,remarks,publish_time,  creator, create_datetime, updator, update_datetime) VALUES
       (1, "1", "3244321", "可口可乐", 1, 1, "",  1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 22, 100, "tags", "remarks", '2016-06-14 00:04:10',
             1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (2, "2", "4453226", "农夫山泉", 1, 1, "",  1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 22, 100, "tags", "remarks", '2016-06-14 00:04:10',
             1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (3, "3", "9282823", "旺旺雪饼", 1, 1, "",  1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 22, 100, "tags", "remarks", '2016-06-14 00:04:10',
             1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (4, "4", "1820231", "大白兔奶糖", 1, 1, "",  1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 22, 100, "tags", "remarks", '2016-06-14 00:04:10',
             1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');
             
INSERT INTO mt_request(id, machine_id, sorting_id,  sku_id, sku_spec_id, quantity, creator, create_datetime, updator, update_datetime) VALUES
       (1, 5, 0, 1, 1, 24, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (2, 5, 0, 2, 1, 24, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (3, 5, 0, 3, 1, 24, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (4, 5, 0, 4, 1, 24, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (5, 6, 0, 1, 1, 24, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (6, 6, 0, 2, 1, 24, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (7, 6, 0, 3, 1, 24, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');
       
INSERT INTO mt_receipt(id, warehouse_id, employee_id, order_id, tracking_id, remark, status, creator, create_datetime, updator, update_datetime) VALUES
       (1, 1, 1, 1, 2341345, "remark 111",  "status 11",    1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),  
       (2, 1, 1, 1, 5443567, "remark 222",  "status 22",    1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),  
       (3, 2, 1, 1, 6544467, "remark 333",  "status 41",    1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (4, 2, 1, 1, 6544467, "remark 444",  "complete",    1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');     
       
INSERT INTO mt_receipt_item(id, receipt_id,sku_id, sku_spec_id, quantity,  creator, create_datetime, updator, update_datetime) VALUES
       (1, 1, 1, 1, 29, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (2, 1, 2, 1, 37, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (3, 1, 3, 1, 89, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
       (4, 1, 4, 1, 43, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');
       
INSERT INTO mt_supplier(id, sname, address, remarks, account, bank, shipaddress, creator, create_datetime, updator, update_datetime) VALUES
      (1, '北京好滋味食品商贸公司', 'address', 'remarks', 'account', 'bank' ,'shipaddress',      1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');  
       
       
INSERT INTO mt_stockin(id, receipt_id, type, manager_id,  creator, create_datetime, updator, update_datetime) VALUES
      (1, 1, "unready", 2,      1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10'),
      (2, 1, "done", 2,      1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');
       
INSERT INTO mt_sale(id, machine_id, username,total_amount, discounted_amount, actual_amount, quantity, status,creator, create_datetime, updator, update_datetime) VALUES
      (1, 1, "User 1", 93, 21, 72, 5, "支付失败", 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');        
       
       
       
       
       
INSERT INTO mt_order(id, orderdate, supplierid, suppliercontactid,storehouseid,storehousemanagerid,paydate,payname,payaccount,paybank,paymode,invoicetype,
                     receivedate,contract,payamt,distamt,orderstate, creator, create_datetime, updator, update_datetime) VALUES
       (1, '2016-06-14', 1, 1, 1, 1, '2016-06-14', 'name','account','bank',1,'invoice','2016-06-14','contract', 1, 1, 1, 1,'2016-06-14 00:04:10',1,'2016-06-14 00:04:10');
       
             