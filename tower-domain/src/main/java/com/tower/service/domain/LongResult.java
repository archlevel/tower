package com.tower.service.domain;


public class LongResult extends AbsResult implements IResult {
	
	private Long data;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1061449957155392958L;
	
	public LongResult(Long data){
		this.data = data;
	}

	public Long getData() {
		return data;
	}

}
