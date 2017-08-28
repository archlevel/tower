package com.tower.service.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

	public static final int LOWERCASE = 0;
	public static final int UPPERCASE = 1;

	public static String toPinyin(String chinese) {
		return toPinyin(chinese, PinyinUtils.UPPERCASE);
	}
	
	public static String toLowerPinyin(String chinese) {
		return toPinyin(chinese, PinyinUtils.LOWERCASE);
	}

	public static String toShortPinyin(String chinese) {
		if( chinese == null ){
			return "";
		}
		return toShortPinyin(chinese, PinyinUtils.UPPERCASE);
	}

	/**
	 *获取汉字串拼音，其他字符不变
	 *
	 * @param chinese
	 * @param caseType
	 *            0-LOWERCASE 1-UPPERCASE
	 * @return
	 */
	public static String toPinyin(String chinese, int caseType) {
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		if (caseType == LOWERCASE) {
			defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		} else {
			defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		}
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		StringBuffer buf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (Character.toString(arr[i]).matches("[\\u4E00-\\u9FA5]+")) {
				try {
					buf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				buf.append(arr[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * 获取汉字串拼音首字母，其他字符不变
	 *
	 * @param chinese
	 * @param caseType
	 * @return
	 */
	public static String toShortPinyin(String chinese, int caseType) {
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		if (caseType == LOWERCASE) {
			defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		} else {
			defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		}
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		StringBuffer buf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (Character.toString(arr[i]).matches("[\\u4E00-\\u9FA5]+")) {
				try {
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (strs != null) {
						buf.append(strs[0].charAt(0));
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				buf.append(arr[i]);
			}
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		String chinese = "逆号";
		System.out.println(toShortPinyin(chinese));
		System.out.println(toPinyin(chinese,0));
	}
}
