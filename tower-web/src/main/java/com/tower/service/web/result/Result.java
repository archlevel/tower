package com.tower.service.web.result;

public class Result<T> {
	private boolean success;
	private String resultCode;

	private T resultModel;

	public Result() {

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public T getResultModel() {
		return resultModel;
	}

	public void setResultModel(T resultModel) {
		this.resultModel = resultModel;
	}

}
