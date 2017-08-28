package com.tower.service.config.utils;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ConfigReloadObserver implements Observer {

	String identify;
	Sender sender;

	public void update(Observable o, Object object) {
		Map msg = (Map) object;
		sender.send(this.identify, msg);
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public ConfigReloadObserver(String identify) {
		this.identify = identify;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

}
