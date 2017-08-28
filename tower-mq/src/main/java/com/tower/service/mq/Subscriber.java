package com.tower.service.mq;


public abstract class Subscriber extends MQClient implements ISubscriber {

    public Subscriber(String id) {
        super(id);
    }

}
