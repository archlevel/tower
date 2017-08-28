package com.tower.service.rabbitmq;

import org.apache.commons.configuration.Configuration;

import com.tower.service.mq.IMessage;
import com.tower.service.mq.ISubscriber;

public class RabbitMQSubscriber extends RabbitMQClient implements ISubscriber {

    public RabbitMQSubscriber(String id) {
        super(id);
    }

    @Override
    public void onMessage(IMessage message) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String configToString(Configuration config) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void build(Configuration config) {
        String prefix_ = this.getPrefix();
        String brokerAddressesString = config.getString(prefix_ + "brokerAddressesString");

    }
}
