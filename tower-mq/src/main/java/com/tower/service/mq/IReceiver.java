package com.tower.service.mq;

public interface IReceiver {

	public IMessage receive();

	public IMessage receive(long timeout);
}
