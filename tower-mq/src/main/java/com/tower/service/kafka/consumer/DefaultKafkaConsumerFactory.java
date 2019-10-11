package com.tower.service.kafka.consumer;

import com.mgzf.sdk.mq.kafka.config.ConsumerType;
import org.springframework.util.Assert;

/**
 * @author : qqmxl
 * @Date: 2018/11/19 19:07
 * @Description:
 */
public class DefaultKafkaConsumerFactory {

	public static AbstractMessageListenerContainer createConsumer(ContainerProperties containerProperties, ConsumerType consumerType) {
		if (ConsumerType.ZK.equals(consumerType)) {
			return new ZkKafkaMessageListenerContainer(containerProperties);
		} else if (ConsumerType.PARTITION.equals(consumerType)) {
			return new PartitionKafkaMessageListenerContainer(containerProperties);
		}
		Assert.notNull(null, "暂不支持此种连接方式进行消息消费");
		return null;
	}
}
