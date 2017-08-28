package com.tower.service.mq;

import java.util.Properties;

public interface IMessage {
	
	public byte[] getBody();

	public String getContentType();

	public Properties getProperty();

}
