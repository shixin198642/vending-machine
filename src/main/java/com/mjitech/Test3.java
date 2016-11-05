package com.mjitech;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mjitech.constant.FileConstants;
import com.mjitech.lib.FileModelLib;
import com.mjitech.lib.SkuBrandLib;
import com.mjitech.model.FileModel;
import com.mjitech.model.SkuBrand;
import com.mjitech.utils.FileUtils;

public class Test3 {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
	public static void main(String[] args) throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		String tmp = "add me to:";
		// System.out.println(tmp.substring("add me to:".length()));
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"application-context.xml");
		// UserinfoLib ul = (UserinfoLib) ctx.getBean("userinfoLib");
		// System.out.println(ul.getByOpenId("o41Mgv5qXayH9P9C6IGYhN1Ujz3g"));
		// WarehouseManagerLib wml =
		// (WarehouseManagerLib)ctx.getBean("warehouseManagerLib");
		// WarehouseLib wl = (WarehouseLib)ctx.getBean("warehouseLib");
		// WarehouseManager wm = new WarehouseManager();
		// wm.setManagerId(5);
		// wm.setWarehouseId(7);
		// wml.add(wm);
		// System.out.println(wl.getByManager(3));
		// SellOrderLib sellOrderLib =
		// (SellOrderLib)ctx.getBean("sellOrderLib");
		// // System.out.println(sellOrderLib.getToCancelList());
		// SellOrderService sos =
		// (SellOrderService)ctx.getBean("sellOrderService");
		// System.out.println(sos.cancelOrder(39, 1));
		File base = new File("e:/skubrand");
		File imagedir = new File("e:/brand_images");
		SkuBrandLib sbl = (SkuBrandLib) ctx.getBean("skuBrandLib");
		FileModelLib fl = (FileModelLib) ctx.getBean("fileModelLib");
		FileUtils fu = (FileUtils) ctx.getBean("fileUtils");
		String excelFileName = "E:\\brands1.xlsx";
		File excelFile = new File(excelFileName); // 创建文件对象
		FileInputStream is = new FileInputStream(excelFile); // 文件流
		Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();
		int type = FileConstants.TYPE_SKUBRAND_IMAGE;
		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			int brandid = (int) row.getCell(0).getNumericCellValue();
			String name = row.getCell(1).getStringCellValue().trim();
			String story = row.getCell(2).getStringCellValue().trim();
			SkuBrand sb = sbl.getById(brandid);
			SkuBrand update = new SkuBrand();
			update.setId(sb.getId());
			update.setStory(story);
			sbl.update(update);
			File image = getImage(imagedir, sb.getId());
			if (image != null) {
				String iddir = fu.computeDirById(sb.getId());
				String dirName = FileConstants.getDirByType(type) + "/" + iddir;
				File targetDir = new File(base, dirName);
				if(!targetDir.exists()){
					targetDir.mkdirs();
				}
				File target = new File(targetDir, image.getName());
				org.apache.commons.io.FileUtils.copyFile(image, target);
				FileModel fm = new FileModel();
				fm.setFileType(FileConstants.FILE_TYPE_IMAGE);
				fm.setType(FileConstants.TYPE_SKUBRAND_IMAGE);
				fm.setRelateId(sb.getId());
				fm.setFilePath(dirName+"/"+image.getName());
				fm.setFileSize(image.length());
				fm.setOriginalName(image.getName());
				fl.add(fm);
			}
			System.out.println(name+" finished");

		}

	}

	private static File getImage(File dir, int id) {
		String[] exts = new String[] { ".jpg", ".png", ".jpeg" };
		for (int i = 0; i < exts.length; i++) {
			File file = new File(dir, id + exts[i]);
			if (file.exists()) {
				return file;
			}
		}
		return null;
	}
}
