package com.tower.service.domain;


public class StringResult extends AbsResult implements IResult {
	private String data;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1876365234125973672L;
	
	public StringResult(String data){
		this.data = data;
	}

	public String getData() {
		return data;
	}

}
