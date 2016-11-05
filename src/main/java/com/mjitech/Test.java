package com.mjitech;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.constant.CommonConstants;
import com.mjitech.constant.DictAreaConstants;
import com.mjitech.constant.SkuConstants;
import com.mjitech.constant.UserinfoConstants;
import com.mjitech.dao.SkuDao;
import com.mjitech.exception.UserExistedException;
import com.mjitech.lib.DictAreaLib;
import com.mjitech.lib.InstoreLib;
import com.mjitech.lib.InventoryLib;
import com.mjitech.lib.SkuAttributeLib;
import com.mjitech.lib.SkuBrandLib;
import com.mjitech.lib.SkuLib;
import com.mjitech.lib.SkuTypeAttributeLib;
import com.mjitech.lib.SkuTypeLib;
import com.mjitech.lib.UserinfoLib;
import com.mjitech.lib.WarehouseLib;
import com.mjitech.lib.WarehouseManagerLib;
import com.mjitech.model.DictArea;
import com.mjitech.model.Instore;
import com.mjitech.model.Inventory;
import com.mjitech.model.Sku;
import com.mjitech.model.SkuBrand;
import com.mjitech.model.SkuType;
import com.mjitech.model.Userinfo;
import com.mjitech.model.Warehouse;
import com.mjitech.model.WarehouseManager;
import com.mjitech.service.SkuService;
import com.mjitech.utils.PasswordUtils;

public class Test {

	private static void addInStore() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		InstoreLib il = (InstoreLib) ctx.getBean("instoreLib");
		Instore i = new Instore();
		i.setFromWarehouseId(1);
		i.setPrice(2.2);
		i.setQuantity(2);
		i.setSkuId(1);
		i.setReceiptId(1);
		i.setType(1);
		i.setDescription("ddd");
		il.add(i);
		System.out.println(i);
	}

	private static void addUsers() throws UserExistedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		UserinfoLib ul = (UserinfoLib) ctx.getBean("userinfoLib");
		PasswordUtils pu = (PasswordUtils) ctx.getBean("passwordUtils");
		Userinfo user = new Userinfo();
		user.setUsername("wangmin@mjitech.com");
		user.setPassword(pu.hashPassword("123456"));
		user.setDisplayName("王敏");
		user.setEmail("wangmin@mjitech.com");
		user.setPassword(pu.hashPassword("123456"));
		user.setGender(CommonConstants.GENDER_FEMALE);
		user.setMobile("18201562561");
		user.setUserType(UserinfoConstants.USER_TYPE_USER);
		user.setImage("");
		user.setOpenId("");
		ul.addUser(user);
		System.out.println(user);

	}

	private static void addWarehouseManager() throws UserExistedException {
		int warehouseId = 11;
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		UserinfoLib ul = (UserinfoLib) ctx.getBean("userinfoLib");
		WarehouseLib wl = (WarehouseLib) ctx.getBean("warehouseLib");
		WarehouseManagerLib wml = (WarehouseManagerLib) ctx
				.getBean("warehouseManagerLib");

		PasswordUtils pu = (PasswordUtils) ctx.getBean("passwordUtils");
		Userinfo user = new Userinfo();
		user.setUsername("yangguangshangdong2@mjitech.com");
		user.setPassword(pu.hashPassword("123456"));
		user.setDisplayName("上东店员2");
		user.setEmail("yangguangshangdong2@mjitech.com");
		user.setPassword(pu.hashPassword("123456"));
		user.setGender(CommonConstants.GENDER_FEMALE);
		user.setMobile("13910000021");
		user.setUserType(UserinfoConstants.USER_TYPE_USER);
		user.setImage("");
		user.setOpenId("");
		ul.addUser(user);
		if (user.getId() > 0) {
			Warehouse w = wl.getById(warehouseId);
			if (w != null) {
				WarehouseManager wm = new WarehouseManager();
				wm.setWarehouseId(warehouseId);
				wm.setManagerId(user.getId());
				wml.add(wm);
			}
		}
		System.out.println(user);
	}

	private static void addInvs() throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		InventoryLib il = (InventoryLib) ctx.getBean("inventoryLib");
		int[] warehourseIds = new int[] { 8, 9, 10, 11 };
		for (int wid : warehourseIds) {
			Inventory inv = new Inventory();
			inv.setMax_stock(100);
			inv.setMin_stock(10);
			inv.setSafe_stock(20);
			inv.setPosition("abcd");
			inv.setQuantity(20);
			inv.setSellprice((float) 59);
			inv.setWarehouse_id(wid);
			inv.setSkuId(3);
			il.add(inv);
		}

	}

	private static void addInventory(ApplicationContext ctx, int wid)
			throws EncryptedDocumentException, InvalidFormatException,
			IOException {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext("application-context.xml");
		}
		InventoryLib il = (InventoryLib) ctx.getBean("inventoryLib");
		SkuLib sl = (SkuLib) ctx.getBean("skuLib");
		int warehourseId = wid;
		String excelFileName = "D:\\new_inventory.xlsx";
		File excelFile = new File(excelFileName); // 创建文件对象
		FileInputStream is = new FileInputStream(excelFile); // 文件流
		Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i <= rowCount; i++) {
			Row row = sheet.getRow(i);
			String skuNumber = row.getCell(0).getStringCellValue().trim();
			String name = row.getCell(1).getStringCellValue().trim();
			System.out.println(name);
			double price = row.getCell(2).getNumericCellValue();
			int count = (int) row.getCell(4).getNumericCellValue();
			if (StringUtils.isEmpty(skuNumber)) {
				continue;
			}
			int id = Integer.parseInt(skuNumber.substring(3));

			Sku sku = sl.getById(id);
			if (sku == null) {
				System.out.println(skuNumber);
				continue;
			}
			// System.out.println(sku.getSkuNumber());
			System.out.println(sku.getName() + ":" + name);
			Inventory inv = new Inventory();
			inv.setMax_stock(100);
			inv.setMin_stock(10);
			inv.setSafe_stock(20);
			inv.setPosition("abcd");
			inv.setQuantity(count);
			inv.setSellprice((float) price);
			inv.setWarehouse_id(warehourseId);
			inv.setSkuId(sku.getId());
			il.add(inv);
			System.out.println(inv);
		}
	}

	private static void updateInventoryPrices()
			throws EncryptedDocumentException, InvalidFormatException,
			IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		InventoryLib il = (InventoryLib) ctx.getBean("inventoryLib");
		SkuLib sl = (SkuLib) ctx.getBean("skuLib");
		int warehourseId = 11;
		String excelFileName = "D:\\real_store_skus.xlsx";
		File excelFile = new File(excelFileName); // 创建文件对象
		FileInputStream is = new FileInputStream(excelFile); // 文件流
		Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			String skuNumber = row.getCell(0).getStringCellValue().trim();

			float price = (float) row.getCell(2).getNumericCellValue();
			if (StringUtils.isEmpty(skuNumber)) {
				continue;
			}
			int id = Integer.parseInt(skuNumber.substring(3));

			Sku sku = sl.getById(id);
			if (sku == null) {
				System.out.println(skuNumber);
				continue;
			}
			// System.out.println(sku.getSkuNumber());
			// System.out.println(sku.getName()+":"+name);
			Inventory old = il.getBySkuWarehouse(sku.getId(), warehourseId);
			if (old != null) {
				if (old.getSellprice() != price) {
					Inventory update = new Inventory();
					update.setId(old.getId());
					update.setSellprice(price);
					il.update(update);
					System.out.println("update mt_inventory set sellprice="
							+ price + " where id=" + old.getId() + ";");
				}
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 * @throws UserExistedException
	 */
	public static void main(String[] args) throws EncryptedDocumentException,
			InvalidFormatException, IOException, UserExistedException {
		// updateskunumbers();
		// addStore();
		// updateInventoryPrices();
		// updateskunumbers();
		// importBrands();
		// addUsers();
		// addInventory(8);
		// addInventory(9);
		// addInventory(10);
		// addInventory(11);
		// addInvs();
		// exportInventory(7);
		// exportInventory(9);
		// exportInventory(10);
		// exportInventory(11);
		// exportSku();
		// addWarehouseManager();
		// ApplicationContext ctx = new ClassPathXmlApplicationContext(
		// "application-context.xml");
		// addInventory(ctx, 7);
		// addInventory(ctx, 8);
		// addInventory(ctx, 9);
		// addInventory(ctx, 10);
		// addInventory(ctx, 11);
		// addInStore();
		importBrands();
	}

	private static void createCell(HSSFRow row, int columindex, String value) {
		HSSFCell cell = row.createCell(columindex, HSSFCell.CELL_TYPE_STRING);
		HSSFRichTextString hts = new HSSFRichTextString(value);
		cell.setCellValue(hts);
	}

	private static void createCell(HSSFRow row, int columindex, double value) {
		HSSFCell cell = row.createCell(columindex, HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	private static void createCell(HSSFRow row, int columindex, int value) {
		HSSFCell cell = row.createCell(columindex, HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	private static void exportInventory(int warehouseId) throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		WarehouseLib warehouseLib = (WarehouseLib) ctx.getBean("warehouseLib");
		InventoryLib inventoryLib = (InventoryLib) ctx.getBean("inventoryLib");
		SkuLib skuLib = (SkuLib) ctx.getBean("skuLib");
		Warehouse wh = warehouseLib.getById(warehouseId);
		if (wh == null) {
			return;
		}
		List<Inventory> invs = inventoryLib.getByWarehouse(warehouseId);

		FileOutputStream fos = new FileOutputStream("d:\\" + wh.getName()
				+ ".xls");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "库存");
		HSSFRow row = s.createRow(0);
		createCell(row, 0, "sku号");
		createCell(row, 1, "商品名称");
		createCell(row, 2, "价格");
		createCell(row, 3, "数量");
		for (int i = 0; i < invs.size(); i++) {
			Sku sku = skuLib.getById(invs.get(i).getSkuId());
			if (sku == null) {
				System.out.println(invs.get(i).getSkuId());
				continue;
			}
			HSSFRow row1 = s.createRow(i + 1);
			createCell(row1, 0, sku.getSkuNumber());
			createCell(row1, 1, sku.getName());
			createCell(row1, 2, invs.get(i).getSellprice());
			createCell(row1, 3, invs.get(i).getQuantity());
		}

		wb.write(fos);
		fos.flush();
		fos.close();
	}

	private static void exportSku() throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SkuLib skuLib = (SkuLib) ctx.getBean("skuLib");
		DictAreaLib dal = (DictAreaLib) ctx.getBean("dictAreaLib");
		SkuTypeLib stl = (SkuTypeLib) ctx.getBean("skuTypeLib");
		SkuBrandLib sbl = (SkuBrandLib) ctx.getBean("skuBrandLib");
		Sku condition = new Sku();
		condition.setPerpage(1000);
		List<Sku> skus = skuLib.getSkus(condition);

		FileOutputStream fos = new FileOutputStream("d:\\商品列表.xls");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		wb.setSheetName(0, "列表");
		HSSFRow row = s.createRow(0);
		createCell(row, 0, "sku号");
		createCell(row, 1, "商品名称");
		createCell(row, 2, "商品短名");
		createCell(row, 3, "条形码");
		createCell(row, 4, "产地");
		createCell(row, 5, "品牌");
		createCell(row, 6, "类别");
		createCell(row, 7, "单位");
		createCell(row, 8, "建议零售价");
		for (int i = 0; i < skus.size(); i++) {
			Sku sku = skus.get(i);
			HSSFRow row1 = s.createRow(i + 1);
			createCell(row1, 0, sku.getSkuNumber());
			createCell(row1, 1, sku.getName());
			createCell(row1, 2, sku.getShortName());
			createCell(row1, 3, sku.getBarcode());
			createCell(row1, 4, dal.getById(sku.getCountry()).getName());
			createCell(row1, 5, sbl.getById(sku.getBrand()).getName());
			SkuType c = stl.getById(sku.getCategory());
			SkuType p = stl.getById(c.getParentId());
			createCell(row1, 6, p.getName() + "-" + c.getName());
			createCell(row1, 7, sku.getUnit());
			createCell(row1, 8, sku.getMsrp());

		}

		wb.write(fos);
		fos.flush();
		fos.close();
	}

	private static void updateskunumbers() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SkuBrandLib sbl = (SkuBrandLib) ctx.getBean("skuBrandLib");
		SkuTypeLib stl = (SkuTypeLib) ctx.getBean("skuTypeLib");
		DictAreaLib dal = (DictAreaLib) ctx.getBean("dictAreaLib");
		SkuLib sl = (SkuLib) ctx.getBean("skuLib");
		SkuDao sd = (SkuDao) ctx.getBean("skuDao");
		Sku condition = new Sku();
		condition.setPerpage(1000);
		List<Sku> skus = sl.getSkus(condition);
		for (Sku sku : skus) {
			SkuType st = stl.getById(sku.getCategory());
			if (st.getParentId() == 0) {
				System.out.println("parent is 0");
				continue;
			}
			SkuType parent = stl.getById(st.getParentId());
			if (parent == null) {
				System.out.println("parent is null");
				continue;
			}
			StringBuilder prefix = new StringBuilder("U");
			if (StringUtils.isNotEmpty(parent.getCode())) {
				prefix.append(parent.getCode());
			} else {
				prefix.append(parent.getId());
			}
			prefix.append(String.format("%06d", sku.getId()));
			if (!prefix.toString().equalsIgnoreCase(sku.getSkuNumber())) {
				Sku update = new Sku();
				update.setId(sku.getId());
				update.setSkuNumber(prefix.toString());
				System.out.println("update mt_sku set sku_number='"
						+ prefix.toString() + "' where id=" + sku.getId());
				sl.update(update);
			}

		}
		System.out.println("ok");
	}

	private static void updateskus() throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SkuBrandLib sbl = (SkuBrandLib) ctx.getBean("skuBrandLib");
		SkuTypeLib stl = (SkuTypeLib) ctx.getBean("skuTypeLib");
		DictAreaLib dal = (DictAreaLib) ctx.getBean("dictAreaLib");
		SkuLib sl = (SkuLib) ctx.getBean("skuLib");
		SkuDao sd = (SkuDao) ctx.getBean("skuDao");
		String excelFileName = "D:\\newskus.xlsx";
		File excelFile = new File(excelFileName); // 创建文件对象
		FileInputStream is = new FileInputStream(excelFile); // 文件流
		Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			Cell skuNumberCell = row.getCell(0);
			if (skuNumberCell == null) {
				continue;
			}
			String skunumber = skuNumberCell.getStringCellValue().trim();
			if (StringUtils.isEmpty(skunumber) || skunumber.length() <= 5) {
				continue;
			}

			String number = skunumber.substring(3);
			Cell nameCell = row.getCell(2);
			if (nameCell == null) {
				continue;
			}
			String name = nameCell.getStringCellValue().trim();
			if (StringUtils.isEmpty(name)) {
				continue;
			}
			Sku old = sl.getSkuBySkuNumber(number);
			if (old == null) {
				System.out.println(number + " not found");
				continue;
			}
			SkuType st = stl.getById(old.getCategory());
			if (st.getParentId() == 0) {
				System.out.println("parent is 0");
				continue;
			}
			Sku update = new Sku();
			update.setId(old.getId());
			update.setShortName(name);
			if (!old.getSkuNumber().toLowerCase()
					.startsWith("u" + st.getParentId())) {
				update.setSkuNumber("U" + st.getParentId()
						+ old.getSkuNumber().substring(1));
			}

			System.out.println(update);
			sl.update(update);

		}
		System.out.println("ok");

	}

	private static void testpost() {
		JSONObject json = new JSONObject();

		HttpPost post = new HttpPost(
				"https://api.weixin.qq.com/cgi-bin/menu/create?access_token=XKKS108zOWDvRZdfNsyP8z1_Im4re2fyeE-5fc2WhSIIfr4gRoyZ6A5BTlFltlRteauvJi082PJHHACpdLTT0pujkkFJdwF-lBJqjxacUgHRVYmlDNqUiwOgmHRo1h28DOUcAEAZKQ");
	}

	private static void printTypes() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SkuTypeLib stl = (SkuTypeLib) ctx.getBean("skuTypeLib");
		List<SkuType> ps = stl.getByParentId(0);
		for (SkuType p : ps) {
			List<SkuType> types = stl.getByParentId(p.getId());
			for (SkuType type : types) {
				System.out.println(p.getName() + "-" + type.getName());
			}
		}
	}

	private static void importBrands() {
		String[] brands = new String[] { "kissme" };
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SkuBrandLib sbl = (SkuBrandLib) ctx.getBean("skuBrandLib");
		for (String brand : brands) {
			SkuBrand sb = new SkuBrand();
			sb.setName(brand);
			sb.setUrl("");
			List<SkuBrand> olds = sbl.getByName(brand);
			if (olds != null && olds.size() > 0) {
				continue;
			}
			sbl.add(sb);
			System.out.println(sb);
		}
		// MenuDao menuDao = (MenuDao) ctx.getBean("menuDao");
		// Menu menu = new Menu();
		// menu.setName("商品");
		// menu.setUrl("");
		// menu.setSortNumber(1);
		// menuDao.add(menu);
		//
		// System.out.println(menu.getId());
	}

	private static void addAreas() {
		String[] countries = new String[] { "香港", "台湾", "澳门" };
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		DictAreaLib dictAreaLib = (DictAreaLib) ctx.getBean("dictAreaLib");
		for (String country : countries) {
			DictArea area = new DictArea();
			area.setName(country);
			area.setParentId(0);
			area.setPostcode("");
			area.setType(DictAreaConstants.AREA_TYPE_COUNTRY);
			dictAreaLib.add(area);
			System.out.println(area);
		}

	}

	private static void importSkus() throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SkuBrandLib sbl = (SkuBrandLib) ctx.getBean("skuBrandLib");
		SkuTypeLib stl = (SkuTypeLib) ctx.getBean("skuTypeLib");
		DictAreaLib dal = (DictAreaLib) ctx.getBean("dictAreaLib");
		SkuLib sl = (SkuLib) ctx.getBean("skuLib");
		SkuDao sd = (SkuDao) ctx.getBean("skuDao");
		String excelFileName = "D:\\work\\fangtang\\mjitech\\商品_20160816.xlsx";
		File excelFile = new File(excelFileName); // 创建文件对象
		FileInputStream is = new FileInputStream(excelFile); // 文件流
		Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < rowCount; i++) {
			Sku sku = new Sku();
			sku.setBarcode("");
			Row row = sheet.getRow(i);
			Cell skuNumberCell = row.getCell(0);
			if (skuNumberCell != null) {
				String skuNumber = skuNumberCell.getStringCellValue().trim();
				if (StringUtils.isNotEmpty(skuNumber)) {
					skuNumber = skuNumber.substring(3);
					sku.setSkuNumber(skuNumber);
				} else {
					continue;
				}
			} else {
				continue;
			}
			Sku old = sl.getSkuBySkuNumber(sku.getSkuNumber());
			if (old != null) {
				continue;
			}
			String name = row.getCell(2).getStringCellValue().trim();
			sku.setName(name);
			String brandName = row.getCell(3).getStringCellValue().trim();
			List<SkuBrand> bs = sbl.getByName(brandName);
			if (bs == null || bs.size() == 0) {
				System.out.println("sku" + sku.getSkuNumber() + " 品牌:"
						+ brandName + " 未找到");
				continue;
			}
			String typeName = row.getCell(4).getStringCellValue().trim();
			List<SkuType> ts = stl.getByName(typeName);
			SkuType skuType = null;
			if (ts == null || ts.size() == 0) {
				System.out.println("type:" + typeName + " not found");
				continue;
			}
			for (SkuType st : ts) {
				if (st.getParentId() == 0) {
					continue;
				}
				skuType = st;
			}

			if (skuType == null) {
				System.out.println("type:" + typeName + " not found");
				continue;
			}
			String countryName = row.getCell(5).getStringCellValue().trim();
			List<DictArea> ds = dal.getByName(countryName);
			if (ds == null || ds.size() == 0) {
				System.out.println(sku.getSkuNumber() + " area:" + countryName
						+ " not found");
				continue;
			}
			String unit = row.getCell(6).getStringCellValue().trim();
			sku.setBrand(bs.get(0).getId());
			sku.setCategory(skuType.getId());
			sku.setCountry(ds.get(0).getId());
			sku.setUnit(unit);
			sku.setMsrp(0f);
			sku.setImagePath("");
			sku.setRemarks("");
			sku.setStatus(SkuConstants.STATUS_UNPUBLISHED);
			sku.setTags("");

			if (old == null) {
				sl.add(sku);
			}

		}
	}

	private static void newskus() throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SkuBrandLib sbl = (SkuBrandLib) ctx.getBean("skuBrandLib");
		SkuTypeLib stl = (SkuTypeLib) ctx.getBean("skuTypeLib");
		DictAreaLib dal = (DictAreaLib) ctx.getBean("dictAreaLib");
		SkuAttributeLib sal = (SkuAttributeLib) ctx.getBean("skuAttributeLib");
		SkuTypeAttributeLib stal = (SkuTypeAttributeLib) ctx
				.getBean("skuTypeAttributeLib");

		SkuLib sl = (SkuLib) ctx.getBean("skuLib");
		SkuService ss = (SkuService) ctx.getBean("skuService");
		SkuDao sd = (SkuDao) ctx.getBean("skuDao");
		String excelFileName = "D:\\newskus.xlsx";
		File excelFile = new File(excelFileName); // 创建文件对象
		FileInputStream is = new FileInputStream(excelFile); // 文件流
		Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			String barcode = row.getCell(1).getStringCellValue();
			String name = row.getCell(2).getStringCellValue();
			String brandString = row.getCell(3).getStringCellValue();
			String categoryString = row.getCell(4).getStringCellValue();
			List<SkuBrand> sbs = sbl.getByName(brandString);
			if (sbs == null || sbs.size() == 0) {
				System.out.println(brandString);
				continue;
			}
			List<SkuType> sts = stl.getByName(categoryString);
			SkuType category = null;
			if (sts == null || sts.size() == 0) {
				System.out.println(categoryString);
			} else if (sts.size() == 2) {
				for (SkuType st : sts) {
					if (st.getParentId() == 0) {
						continue;
					}
					category = st;
				}
			} else if (sts.size() == 1) {
				category = sts.get(0);
			} else {
				System.out.println(sts);
			}
			String countryString = row.getCell(5).getStringCellValue();
			List<DictArea> das = dal.getByName(countryString);
			if (das == null || das.size() == 0) {
				System.out.println(countryString);
			}
			String unit = row.getCell(6).getStringCellValue();
			String expirationDayString = row.getCell(8).getStringCellValue();
			int expirationDays = 0;
			if ("一年".equals(expirationDayString)) {
				expirationDays = 365;
			} else if ("三年".equals(expirationDayString)) {
				expirationDays = 1095;
			} else if ("一年半".equals(expirationDayString)) {
				expirationDays = 548;
			} else if ("180天".equals(expirationDayString)) {
				expirationDays = 180;
			} else {
				System.out.println(expirationDayString);
			}
			// SkuType parentCategory = stl.getById(category.getParentId());
			// List<SkuTypeAttribute> stas = stal.getByType(category.getId());
			// if(stas==null || stas.size()==0){
			// System.out.println(category.getName()+" no atts");
			// }
			// String attName = row.getCell(15).getStringCellValue();
			// boolean foundAtt = false;
			// for(SkuTypeAttribute sta : stas){
			// if(attName.equals(sta.getName())){
			// foundAtt = true;
			// }
			// }
			// if(!foundAtt){
			// System.out.println(category.getName()+" not found:"+attName);
			// System.out.println(category.getName()+" found:"+stas);
			// }

			Sku sku = new Sku();
			sku.setBarcode(barcode);
			sku.setCategory(category.getId());
			sku.setCountry(das.get(0).getId());
			sku.setBrand(sbs.get(0).getId());
			sku.setName(name);
			sku.setExpirationDays(expirationDays);
			sku.setUnit(unit);
			sku.setStatus(SkuConstants.STATUS_UNPUBLISHED);
			sku.setShortName("");
			sku.setTags("");
			sku.setRemarks("");
			sku.setImagePath("");
			ss.regenerateSkuNumber(sku);
			sl.update(sku);

		}
	}

}
