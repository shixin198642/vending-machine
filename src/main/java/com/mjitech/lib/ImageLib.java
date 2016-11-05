package com.mjitech.lib;

import com.mjitech.model.FileModel;

public interface ImageLib {

	public FileModel converTmpImageToSkuImage(FileModel tmpImage,
			int skuId, int userid);

}
