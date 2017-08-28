package com.tower.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.tower.service.util.dict.SexDict;

public class IDUtil {
	/**
	 * {@link SexDict}
	 * 
	 * @param id
	 * @return
	 */
	public static int getSex(String id) {
		id = id.trim();
		if (id.length() == 15) {
			id = IDUtil.id15To18(id);
		}
		// 612322197905290914
		String seq = id.substring(14, 17);
		Integer seqN = Integer.parseInt(seq);
		if (seqN % 2 == 0) {
			return SexDict.FEMAILE;
		} else {
			return SexDict.MALE;
		}
	}
	
	public static int getAge(String id) {
		id = id.trim();
		if (id.length() == 15) {
			id = IDUtil.id15To18(id);
		}
		String birthDay = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		birthDay = id.substring(6, 14);
		try {
			Calendar current = Calendar.getInstance();
			int cYear = current.get(Calendar.YEAR);
			Date birth = df.parse(birthDay);
			current.setTime(birth);
			int bYear = current.get(Calendar.YEAR);
			int age = (cYear - bYear);
			return age;
		} catch (ParseException e) {
		    throw new RuntimeException(e);
		}
	}

	public static final String id15To18(String id) {
		final int[] W = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
				1 };
		final String[] A = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
				"2" };
		int i, j, s = 0;
		String newid;
		newid = id;
		newid = newid.substring(0, 6) + "19" + newid.substring(6, id.length());
		for (i = 0; i < newid.length(); i++) {
			j = Integer.parseInt(newid.substring(i, i + 1)) * W[i];
			s = s + j;
		}
		s = s % 11;
		newid = newid + A[s];
		return newid;
	}
	/**
	 * 随机产生一个由字母、数字或者下划线组成且长度为len_的字符串
	 * @return
	 */
	public static String next(int len_) {		
		StringBuffer id_ = new StringBuffer();		
		for(int i=0;i<len_;i++){
			Double _index = Math.random()*len;
			Integer index = _index.intValue();
			id_.append(id[index]);
		}
		return id_.toString();
	}

	static String[] id = { "A", "a", "B", "b", "C", "c", "D", "d", "E", "e",
			"F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k", "L",
			"l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r",
			"S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y",
			"y", "Z", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	static Integer len = id.length;
	
	/**
	 * 随机产生一个由字母、数字或者下划线组成且长度为len_的字符串
	 * @return
	 */
	public static String nnext(int len_) {		
		StringBuffer id_ = new StringBuffer();		
		for(int i=0;i<len_;i++){
			Double _index = Math.random()*nlen;
			Integer index = _index.intValue();
			id_.append(nid[index]);
		}
		return id_.toString();
	}
	static String[] nid = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	static Integer nlen = nid.length;
	
	public static String nextUuid(){
		 UUID uuid = UUID.randomUUID(); 
		 return uuid.toString();
	}
	
}
