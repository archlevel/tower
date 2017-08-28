package com.tower.service.aop;

import com.tower.service.annotation.Tx;

public class Hello {
	
	@Tx
	public String getName() {
		return "alex.zhu";
	}

	@Tx
	public void say() {
		System.out.println("hello," + getName());
	}

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.say();
	}
}
