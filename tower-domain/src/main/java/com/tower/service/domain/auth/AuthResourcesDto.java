package com.tower.service.domain.auth;

import java.util.List;

import com.tower.service.domain.AbsDTO;
import com.tower.service.domain.IResult;

public class AuthResourcesDto extends AbsDTO implements IResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6555650255760890962L;

	/**
	 * 主键
	 */
	protected java.math.BigInteger id;

	public void setId(java.math.BigInteger id) {
		this.id = id;
	}

	public java.math.BigInteger getId() {
		return this.id;
	}

	/**
	 * 资源名称
	 */
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 模块URL地址
	 */
	private String linkUrl;

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkUrl() {
		return this.linkUrl;
	}

	/**
	 * 描述
	 */
	private String description;

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * 父模块ID，如果是顶级模块，为0
	 */
	private java.math.BigInteger parentId;

	public void setParentId(java.math.BigInteger parentId) {
		this.parentId = parentId;
	}

	public java.math.BigInteger getParentId() {
		return this.parentId;
	}

	/**
	 * 排序号
	 */
	private Integer sequence;

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	/**
	 * 模块code，权限控制
	 */
	private String code;

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	/**
	 * 资源类型(模块跟方法)
	 */
	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	/**
	 * 资源类型(模块跟方法) 文本显示
	 */
	private String typeText;
	
	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	/**
	 * 创建人
	 */
	private java.math.BigInteger creatorUserId;

	public void setCreatorUserId(java.math.BigInteger creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public java.math.BigInteger getCreatorUserId() {
		return this.creatorUserId;
	}

	/**
	 * 创建时间
	 */
	private java.sql.Timestamp creationTime;

	public void setCreationTime(java.sql.Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public java.sql.Timestamp getCreationTime() {
		return this.creationTime;
	}

	/**
	 * 更新时间
	 */
	private java.sql.Timestamp updateTime;

	public void setUpdateTime(java.sql.Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public java.sql.Timestamp getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 备用字段1
	 */
	private String rsv1;

	public void setRsv1(String rsv1) {
		this.rsv1 = rsv1;
	}

	public String getRsv1() {
		return this.rsv1;
	}

	/**
	 * 备用字段1
	 */
	private String rsv2;

	public void setRsv2(String rsv2) {
		this.rsv2 = rsv2;
	}

	public String getRsv2() {
		return this.rsv2;
	}

	/**
	 * 备用字段1
	 */
	private String rsv3;

	public void setRsv3(String rsv3) {
		this.rsv3 = rsv3;
	}

	public String getRsv3() {
		return this.rsv3;
	}

	private Integer childCount;

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	private List<AuthResourcesDto> children;

  public List<AuthResourcesDto> getChildren() {
    return children;
  }

  public void setChildren(List<AuthResourcesDto> children) {
    this.children = children;
  }
	
}
