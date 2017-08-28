package com.tower.service.mq;


public interface IConnector {
	
    public boolean isConnected();
    
	public void connect();

	public void disconn();
}
