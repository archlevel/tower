package com.tower.service.config;

import org.apache.commons.configuration.Configuration;

public interface IConfigListener{

	/**
	 * 这个方法用在配置变更时通知观察者更新相关的配置，比如重新初始化一些变量（值来源于配置信息） 当配置项发生变化是，系统自动执行 load();
	 */
	public void onUpdate(Configuration config);

}
