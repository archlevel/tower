package com.tower.service.domain;

public class ServiceResponse<T extends IResult> extends AbsResult implements IResult {

	public static String SUCCESS = "0000";

	public static String SUCCESS_TEXT = "操作成功!";

	public static String FAILURE = "0001";

	public static String FAILURE_TEXT = "操作失败!";

	public static String SKIP = "0002";

	public static String SKIP_TEXT = "忽略记录!";

	public static String SYSTEM_ERROR = "系统出错!";

	private String code = SUCCESS;

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

	private String msg = SUCCESS_TEXT;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5709436619120979415L;

	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	public boolean isNull(){
		return result==null;
	}
}
