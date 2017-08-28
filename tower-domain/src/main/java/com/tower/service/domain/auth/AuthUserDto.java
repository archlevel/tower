package com.tower.service.domain.auth;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tower.service.domain.AbsDTO;
import com.tower.service.domain.IResult;

public class AuthUserDto extends AbsDTO implements IResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2118168834640584102L;

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
	 * 用户名
	 */
	private String userName;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	/**
	 * 密码
	 */
	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	/**
	 * 国家
	 */
	private Integer country;

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getCountry() {
		return this.country;
	}

	/**
	 * 过期日期
	 */
	private java.sql.Timestamp expiredDate;

	public void setExpiredDate(java.sql.Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

	public java.sql.Timestamp getExpiredDate() {
		return this.expiredDate;
	}

	/**
	 * 过期的凭据
	 */
	private String credentialsExpired;

	public void setCredentialsExpired(String credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public String getCredentialsExpired() {
		return this.credentialsExpired;
	}

	/**
	 * 用户全名
	 */
	private String fullName;

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
	}

	/**
	 * 用户所在机构
	 */
	private Integer org;

	public void setOrg(Integer org) {
		this.org = org;
	}

	public Integer getOrg() {
		return this.org;
	}

	/**
	 * 性别(10代表男性，20代表女性)
	 */
	private String gender;

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return this.gender;
	}

	/**
	 * 年龄
	 */
	private Integer age;

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return this.age;
	}

	/**
	 * 地址
	 */
	private String address;

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	/**
	 * 电话
	 */
	private String phone;

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	/**
	 * 移动电话
	 */
	private String mobile;

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 社会安全卡
	 */
	private String ssn;

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSsn() {
		return this.ssn;
	}

	/**
	 * 邮箱地址
	 */
	private String mail;

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return this.mail;
	}

	/**
	 * 用户类型 1普通用户 2超级管理员
	 */
	private String userType;

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return this.userType;
	}

	/**
	 * 通知模式 1邮箱通知 2电话通知 3短信通知
	 */
	private String notifyMode;

	public void setNotifyMode(String notifyMode) {
		this.notifyMode = notifyMode;
	}

	public String getNotifyMode() {
		return this.notifyMode;
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
	 * 创造者
	 */
	private java.math.BigInteger creatorUserId;

	public void setCreatorUserId(java.math.BigInteger creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public java.math.BigInteger getCreatorUserId() {
		return this.creatorUserId;
	}

	/**
	 * 用户状态
	 */
	private String status;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	/**
	 * 用户状态 文本显示
	 */
	private String statusText;

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
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

	private List<String> auths;

	private String ticket;
	
	private Map<String,Integer> authsMap = new ConcurrentHashMap<String,Integer>();
	public Map<String,Integer> getAuths() {
	    if(authsMap.isEmpty()){
	        if (auths != null && !auths.isEmpty()) {
                for (String string : auths) {
                    authsMap.put(string, 0);
                }
            }
	    }
		return authsMap;
	}

	public void setAuths(List<String> auths) {
		this.auths = auths;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
