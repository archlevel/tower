package com.tower.service.domain.code;

import com.tower.service.domain.AbsDTO;
import com.tower.service.domain.IResult;

public class CodeDto extends AbsDTO implements IResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1735997436621566416L;

	/**
	 * 主键
	 */
 	protected Integer id;
 	
  	public void setId(Integer id){
  		this.id=id;
  	}
  	
  	public Integer getId(){
  		return this.id;
  	}
	/**
	 * 编号
	 */
  	private String itemType;
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * 名称
	 */
  	private String name;
  	
  	public void setName(String name){
  		this.name=name;
  	}
  	
  	public String getName(){
  		return this.name;
  	}
	/**
	 * 节点编号
	 */
  	private String itemNo;
  	
  	public void setItemNo(String itemNo){
  		this.itemNo=itemNo;
  	}
  	
  	public String getItemNo(){
  		return this.itemNo;
  	}
	/**
	 * 节点键
	 */
  	private String itemKey;
  	
  	public void setItemKey(String itemKey){
  		this.itemKey=itemKey;
  	}
  	
  	public String getItemKey(){
  		return this.itemKey;
  	}
	/**
	 * 节点值
	 */
  	private String itemValue;
  	
  	public void setItemValue(String itemValue){
  		this.itemValue=itemValue;
  	}
  	
  	public String getItemValue(){
  		return this.itemValue;
  	}
	/**
	 * 排序号
	 */
  	private Integer sequence;
  	
  	public void setSequence(Integer sequence){
  		this.sequence=sequence;
  	}
  	
  	public Integer getSequence(){
  		return this.sequence;
  	}
	/**
	 * 是否使用
	 */
  	private String isUse;
  	
  	public void setIsUse(String isUse){
  		this.isUse=isUse;
  	}
  	
  	public String getIsUse(){
  		return this.isUse;
  	}

  	public CodeDto() {
  		
  	}
  	
	public CodeDto(String itemNo, String itemKey, String itemValue) {
		this.itemNo = itemNo;
		this.itemKey = itemKey;
		this.itemValue = itemValue;
	}

	public CodeDto(String itemType, String name) {
		this.itemType = itemType;
		this.name = name;
	}
  	
  	
}
