package com.tower.service.jms;

import com.tower.service.mq.IMessage;
import com.tower.service.mq.IPublisher;
import com.tower.service.mq.ISender;

public class JmsMQProducer extends JmsMQClient implements ISender, IPublisher {

    public JmsMQProducer(String id) {
        super(id);
    }

    @Override
    public void send(IMessage message) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void publish(IMessage message) {
        // TODO Auto-generated method stub
        
    }

}
