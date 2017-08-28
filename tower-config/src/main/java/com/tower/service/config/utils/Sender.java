package com.tower.service.config.utils;

import java.util.Map;

public interface Sender {
	public void send(Object receiver, Map msg);
}
