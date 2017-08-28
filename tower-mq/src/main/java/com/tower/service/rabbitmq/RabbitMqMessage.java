package com.tower.service.rabbitmq;

import java.util.Properties;

import com.tower.service.mq.IMessage;

public class RabbitMqMessage implements IMessage{
    private byte[] body = null;
    public RabbitMqMessage(byte[] body){
        this.body = body;
    }
    @Override
    public byte[] getBody() {
        // TODO Auto-generated method stub
        return body;
    }

    @Override
    public String getContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Properties getProperty() {
        // TODO Auto-generated method stub
        return null;
    }

}
