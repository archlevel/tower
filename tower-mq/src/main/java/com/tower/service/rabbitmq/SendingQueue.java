package com.tower.service.rabbitmq;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.tower.service.mq.IMessage;

public class SendingQueue {
    public SendingQueue() {
        queue = new ConcurrentLinkedQueue<IMessage>();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean add(IMessage value) {
        return queue.add(value);
    }

    public IMessage poll() {
        return queue.poll();
    }

    private Queue<IMessage> queue;
}
