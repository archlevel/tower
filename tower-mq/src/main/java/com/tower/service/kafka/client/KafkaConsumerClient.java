package com.tower.service.kafka.client;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.mq.kafka.IMessageBatchProcessor;
import com.mgzf.sdk.mq.kafka.IMessageProcessor;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.config.ApolloUtils;
import com.mgzf.sdk.mq.kafka.config.ConsumerType;
import com.mgzf.sdk.mq.kafka.config.ResetAttribute;
import com.mgzf.sdk.mq.kafka.consumer.AbstractMessageListenerContainer;
import com.mgzf.sdk.mq.kafka.consumer.ContainerProperties;
import com.mgzf.sdk.mq.kafka.consumer.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * kafka 消费者监听和收消息
 *
 * @author mxl
 */
@Component
public class KafkaConsumerClient implements AutoCloseable {

	private Logger logger = LoggerFactory.getLogger(KafkaConsumerClient.class);
	private static KafkaConsumerClient kafkaConsumerClient = null;

	/**
	 * 实例化永远 给 kafkaProducerClient
	 */
	public KafkaConsumerClient() {
		kafkaConsumerClient = this;
	}

	public static KafkaConsumerClient getInstance() {
		if (kafkaConsumerClient == null) {
			kafkaConsumerClient = new KafkaConsumerClient();
		}
		return kafkaConsumerClient;
	}

	/**
	 * 连接zk进行消息消费
	 *
	 * @param kafkaGroup
	 * @param topicKey
	 * @param consumerNum
	 * @param groupKey
	 * @param iMessageProcessor
	 */
	public void receiveMessages(String kafkaGroup, String topicKey, int consumerNum, String groupKey, final IMessageProcessor iMessageProcessor) {
		ContainerProperties containerProperties = convert(kafkaGroup, topicKey, groupKey);
		containerProperties.setBeanName("static-zk");
		containerProperties.setIMessageProcessor(iMessageProcessor);
		containerProperties.setThreadNum(consumerNum);
		AbstractMessageListenerContainer messageListenerContainer = DefaultKafkaConsumerFactory.createConsumer(containerProperties, ConsumerType.ZK);
		SdkContext.CONSUMER_LISTENER_MAP.put(SdkContext.warpConsumerKey(kafkaGroup, containerProperties.getGroup(), containerProperties.getTopic()), messageListenerContainer);
		messageListenerContainer.run();
	}


	/**
	 * 每一个分区一个consumer批量消费，注意分区的参数必须等于topic设置的分区数. 特定场景使用.
	 * 使用后 是不支持消费自平衡，也不支持动态扩容分区等
	 *
	 * @param kafkaGroup
	 * @param topicKey
	 * @param groupKey
	 * @param iMessageProcessor
	 * @param partitions        有多少个分区数就填写多少，少于分区数的，后面的分区消息无法消费
	 * @param batchSize
	 */
	public void receiveBatchMessages(String kafkaGroup, String topicKey, String groupKey, final IMessageBatchProcessor iMessageProcessor, int partitions, int batchSize) {
		ContainerProperties containerProperties = convert(kafkaGroup, topicKey, groupKey);
		containerProperties.setBeanName("static-batch-broker");
		containerProperties.setIMessageBatchProcessor(iMessageProcessor);
		containerProperties.setPartitions(partitions);
		containerProperties.setThreadNum(partitions);
		containerProperties.setBatchSize(batchSize);
		AbstractMessageListenerContainer messageListenerContainer = DefaultKafkaConsumerFactory.createConsumer(containerProperties, ConsumerType.PARTITION);
		SdkContext.CONSUMER_LISTENER_MAP.put(SdkContext.warpConsumerKey(kafkaGroup, containerProperties.getGroup(), containerProperties.getTopic()), messageListenerContainer);
		messageListenerContainer.run();
	}


	private ContainerProperties convert(String kafkaGroup, String topicKey, String groupKey) {
		ApolloUtils.checkboxGroup(kafkaGroup);
		String topic = SdkContext.KAFKA_TOPIC.get(topicKey);
		String groupId = SdkContext.KAFKA_GROUP.get(groupKey);
		Assert.notNull(groupId, ApolloUtils.getCunEnv() + "，配置中心mogo.sdk.kafka.config.consumer.group." + kafkaGroup + "未配置group消费组" + groupKey + ":映射.");
		ContainerProperties containerProperties = new ContainerProperties();
		containerProperties.setGroupKey(groupKey);
		containerProperties.setTopicKey(topicKey);
		containerProperties.setKafkaGroup(kafkaGroup);
		containerProperties.setGroup(groupId);
		containerProperties.setTopic(topic);
		ResetAttribute.consumerReset(containerProperties);
		Assert.notNull(containerProperties.getTopic(), ApolloUtils.getCunEnv() + "，配置中心mogo.sdk.kafka.config.topic." + kafkaGroup + "未配置Topic队列" + topicKey + ":映射.");
		String consumerKey = SdkContext.warpConsumerKey(containerProperties.getKafkaGroup(), groupId, containerProperties.getTopic());
		Assert.isNull(SdkContext.CONSUMER_LISTENER_MAP.get(consumerKey), ApolloUtils.getCunEnv() + " 消费者【" + consumerKey + "】已创建，无需重复创建.");
		return containerProperties;
	}

	@PreDestroy
	public void destroy() {
		logger.info("================>>close KafkaConsumerClient......");
		for (Map.Entry<String, AbstractMessageListenerContainer> containerEntry : SdkContext.CONSUMER_LISTENER_MAP.entrySet()) {
			AbstractMessageListenerContainer container = SdkContext.CONSUMER_LISTENER_MAP.remove(containerEntry.getKey());
			if (container != null) {
				logger.info("=====Consumer Close ......consumer={}", container.getContainerProperties());
				container.close();
			}
		}
	}

	@Override
	public void close() {
		logger.debug("================>>此方式和sonar规则使用冲突，关闭采用spring方式管理销毁......");
	}

}
