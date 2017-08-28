package com.tower.service.jms;

import com.tower.service.mq.IMessage;
import com.tower.service.mq.IReceiver;

public class JmsMQReceiver extends JmsMQClient implements IReceiver {

    public JmsMQReceiver(String id) {
        super(id);
    }

    @Override
    public IMessage receive() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IMessage receive(long timeout) {
        // TODO Auto-generated method stub
        return null;
    }

    

}
