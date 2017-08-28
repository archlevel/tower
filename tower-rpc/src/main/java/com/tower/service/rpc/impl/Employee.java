package com.tower.service.rpc.impl;

import com.tower.service.domain.IDTO;

public class Employee implements IDTO {

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String[] getInterests() {
		return interests;
	}

	public void setInterests(String[] interests) {
		this.interests = interests;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8424634939835743709L;

	private String first_name;
	private String last_name;
	private Integer age;
	private String about;
	private String[] interests;

}
