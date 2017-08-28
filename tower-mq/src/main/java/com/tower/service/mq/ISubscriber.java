package com.tower.service.mq;

public interface ISubscriber {
	
	public void onMessage(IMessage message);
	
}
