package com.tower.service.domain.auth;

import java.util.List;

import com.tower.service.domain.AbsResult;
import com.tower.service.domain.IResult;
import com.tower.service.domain.PageResult;

public class AuthResponse<T extends IResult> extends AbsResult implements
		IResult {

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

	private List<T> list;

	private PageResult<T> pager;

	private T model;

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

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public AuthResponse() {

	}

	public AuthResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public PageResult<T> getPager() {
		return pager;
	}

	public void setPager(PageResult<T> pager) {
		this.pager = pager;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

}
