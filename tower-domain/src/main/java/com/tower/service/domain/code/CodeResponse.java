package com.tower.service.domain.code;

import java.util.List;

import com.tower.service.domain.AbsResult;
import com.tower.service.domain.IResult;

public class CodeResponse extends AbsResult implements IResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2233172608097378104L;

	public static String SUCCESS = "0000";
	
	public static String SUCCESS_TEXT = "操作成功!";
	
	public static String FAILURE = "0001";
	
	public static String FAILURE_TEXT = "操作失败!";
	
	public static String SKIP = "0002";
	
	public static String SKIP_TEXT = "忽略记录!";
	
	public static String SYSTEM_ERROR = "系统出错!";
	
	private String code = SUCCESS;
	
	private String msg = SUCCESS_TEXT;

	private List<?> list;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public CodeResponse() {
		
	}
	
	public CodeResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
