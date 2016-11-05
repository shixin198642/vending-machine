package com.mjitech.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinyinUtils {

	private static Logger logger = LoggerFactory.getLogger(PinyinUtils.class);
	private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	static {
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	public static String getPinyin(String str) {

		char[] input = str.trim().toCharArray();
		StringBuffer output = new StringBuffer("");

		try {
			for (int i = 0; i < input.length; i++) {
				String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i],
						format);
				if (temp != null) {
					output.append(temp[0]);
					output.append(" ");
				} else {
					output.append(input[i]);
				}

			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error("error in converting chinese to pinyin", e);
		}
		return output.toString();
	}

}
