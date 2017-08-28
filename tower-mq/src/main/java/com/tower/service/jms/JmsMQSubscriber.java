package com.tower.service.jms;

import com.tower.service.mq.IMessage;
import com.tower.service.mq.ISubscriber;

public class JmsMQSubscriber extends JmsMQClient implements ISubscriber {

    public JmsMQSubscriber(String id) {
        super(id);
    }

    @Override
    public void onMessage(IMessage message) {
        // TODO Auto-generated method stub
        
    }

}
