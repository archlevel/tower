package com.tower.service.util;

public class SystemUtils {

	/**
	 * 获取当前应用的进程id号
	 * 
	 * @return
	 */
	public static String getProcessId() {
		return java.lang.management.ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	}
	
	public static ClassLoader print(ClassLoader cls){
		if(cls!=null&&cls.getParent()!=null){
			return print(cls.getParent());
		}
		else if(cls!=null){
			System.out.println(cls);
		}
		return null;
	}
	
	public static void main(String[] args){
		System.out.println(print(ClassLoader.getSystemClassLoader()));
	}
}
