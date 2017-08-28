package com.tower.service.config.utils;

import java.util.Map;
import java.util.Observable;

public class ConfigReloadEvent extends Observable {

	Map msg;

	public ConfigReloadEvent() {
		super();
	}

	public ConfigReloadEvent(Map msg) {
		this.msg = msg;
	}

	public void configReload(Map configReloadMsg) {
		this.msg = configReloadMsg;
	}

	// 发布事件通知观察者
	public void fireEvent() {
		this.setChanged();
		this.notifyObservers(msg);
	}

	public Map getMsg() {
		return msg;
	}

	public void setMsg(Map msg) {
		this.msg = msg;
	}

}
