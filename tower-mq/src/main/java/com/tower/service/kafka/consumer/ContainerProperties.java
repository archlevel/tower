package com.tower.service.kafka.consumer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mgzf.sdk.mq.kafka.IMessageBatchProcessor;
import com.mgzf.sdk.mq.kafka.IMessageProcessor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * @author : qqmxl
 * @Date: 2018/11/18 09:03
 * @Description:
 */
@Getter
@Setter
public class ContainerProperties {
	private String kafkaGroup;
	private String topic;
	private String group;
	private int threadNum;

	private String newKafkaGroup;
	private String newTopic;

	private String topicKey;
	private String groupKey;

	private int batchSize = 1;
	private int partitions = -1;

	private boolean unpack = false;

	@JsonIgnore
	private Method method;

	@JsonIgnore
	private IMessageProcessor iMessageProcessor;

	@JsonIgnore
	private IMessageBatchProcessor iMessageBatchProcessor;

	@JsonIgnore
	private Object bean;

	private String beanName;

	@JsonIgnore
	private MessageCounter mesCount;

	public String toString() {
		return new StringBuilder().append("初始化参数:").append(beanName).append("#").append(kafkaGroup).append("#").append(topic).append("#").append(group).append("#").append(threadNum).append("#").append(unpack).append("#").append(partitions).append("#").append(batchSize).toString();
	}

}
