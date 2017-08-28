package com.tower.service.domain.auth;

import com.tower.service.domain.AbsDTO;
import com.tower.service.domain.IResult;

public class AuthRoleDto extends AbsDTO implements IResult {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2338118587684241322L;
	/**
	 * 主键
	 */
 	protected java.math.BigInteger id;
 	
  	public void setId(java.math.BigInteger id){
  		this.id=id;
  	}
  	
  	public java.math.BigInteger getId(){
  		return this.id;
  	}
	/**
	 * 角色名称
	 */
  	private String name;
  	
  	public void setName(String name){
  		this.name=name;
  	}
  	
  	public String getName(){
  		return this.name;
  	}
	/**
	 * 描述
	 */
  	private String description;
  	
  	public void setDescription(String description){
  		this.description=description;
  	}
  	
  	public String getDescription(){
  		return this.description;
  	}
	/**
	 * 创建人
	 */
  	private java.math.BigInteger creatorUserId;
  	
  	public void setCreatorUserId(java.math.BigInteger creatorUserId){
  		this.creatorUserId=creatorUserId;
  	}
  	
  	public java.math.BigInteger getCreatorUserId(){
  		return this.creatorUserId;
  	}
	/**
	 * 创建时间
	 */
  	private java.sql.Timestamp creationTime;
  	
  	public void setCreationTime(java.sql.Timestamp creationTime){
  		this.creationTime=creationTime;
  	}
  	
  	public java.sql.Timestamp getCreationTime(){
  		return this.creationTime;
  	}
	/**
	 * 更新时间
	 */
  	private java.sql.Timestamp updateTime;
  	
  	public void setUpdateTime(java.sql.Timestamp updateTime){
  		this.updateTime=updateTime;
  	}
  	
  	public java.sql.Timestamp getUpdateTime(){
  		return this.updateTime;
  	}
	/**
	 * 备用字段1
	 */
  	private String rsv1;
  	
  	public void setRsv1(String rsv1){
  		this.rsv1=rsv1;
  	}
  	
  	public String getRsv1(){
  		return this.rsv1;
  	}
	/**
	 * 备用字段1
	 */
  	private String rsv2;
  	
  	public void setRsv2(String rsv2){
  		this.rsv2=rsv2;
  	}
  	
  	public String getRsv2(){
  		return this.rsv2;
  	}
	/**
	 * 备用字段1
	 */
  	private String rsv3;
  	
  	public void setRsv3(String rsv3){
  		this.rsv3=rsv3;
  	}
  	
  	public String getRsv3(){
  		return this.rsv3;
  	}
 
}
