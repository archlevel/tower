package com.tower.service.rabbitmq;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.configuration.Configuration;

import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;
import com.tower.service.mq.IMessage;
import com.tower.service.mq.IReceiver;

public class RabbitMQReceiver extends RabbitMQClient implements IReceiver {
    public RabbitMQReceiver(String id) {
        super(id);
    }

    private QueueingConsumer consumer = null;
    @Override
    public IMessage receive() {
        return receive(-1);
    }

    @Override
    public IMessage receive(long timeout) {
        if (!isConnected()) {
            throw new IllegalStateException("Connect before running");
        }

        try {
            Delivery delivery = timeout > 0 ? consumer.nextDelivery(timeout)
                    : consumer.nextDelivery();
            if (delivery != null) {
                channel.basicAck(
                        delivery.getEnvelope().getDeliveryTag(), false);
                byte[] aa = delivery.getBody();
            }
            return new RabbitMqMessage(delivery.getBody());
        } catch (ShutdownSignalException e) {
        } catch (InterruptedException e) {
        } catch (IOException e) {
        }
        return null;
    }
    
    @Override
    public void connect() {
        try {
            super.connect();
            prepareQueue();
            consumer = new QueueingConsumer(this.channel);
            channel.basicConsume(queueName, consumer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
    
    protected void prepareQueue() throws IOException {
        //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(queueName, durable, exclusive, autoDelete, new HashMap());
    }
    
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete=true;
    
    @Override
    public void disconn() {
        super.disconn();
        consumer = null;
    }
    
    @Override
    protected String configToString(Configuration config) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private String queueName;
    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    protected void build(Configuration config) {
        String prefix_ = this.getPrefix();
        String brokerAddressesString = config.getString(prefix_ + "brokerAddressesString");
        String queue = config.getString(prefix_ + "queue");
        String receiveRoutingKey = config.getString(prefix_ + "receiveRoutingKey");
//        MessageReceiver receive = new MessageReceiver();
//        ConnectionFactory fac = new ConnectionFactory(new ConnectionParameters());
//        Connector c = new Connector();
//        c.setConnectionFactory(fac);
//        receive.setConnector(c);
//
//        c.setBrokerAddressesString(brokerAddressesString);
//        receive.setDurable(durable);
//        receive.setAutoDelete(autoDelete);
//        receive.setQueueName(queueName);
//        receive.setRoutingPattern(routingPattern);
    }

}
