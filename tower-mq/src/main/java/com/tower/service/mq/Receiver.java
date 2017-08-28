package com.tower.service.mq;


public abstract class Receiver extends MQClient implements IReceiver {

    public Receiver(String id) {
        super(id);
    }

}
