package com.tower.service.mq;


public abstract class Producer extends MQClient implements ISender, IPublisher {

    public Producer(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }
	
}
