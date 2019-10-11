package com.tower.service.kafka.consumer;

import com.google.common.collect.Maps;
import com.mgzf.sdk.mq.kafka.ConsumerConfigConst;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Map;
import java.util.Properties;

/**
 * kafka 消费者Consumer创建类
 *
 * @author mxl
 */
public class KafkaConsumerBuilder {
	private Properties properties;

	private static final Map<String, Object> MOGO_DEFAULT_CONFIG = Maps.newHashMap();

	static {
		MOGO_DEFAULT_CONFIG.put(ConsumerConfigConst.EXCLUDE_INTERNAL_TOPICS, "false");
	}

	private KafkaConsumerBuilder(Properties properties) {
		this.properties = properties;
	}

	public static KafkaConsumerBuilder builder(Properties props) {
		if (props == null) {
			return new KafkaConsumerBuilder(new Properties());
		}
		return new KafkaConsumerBuilder(props);
	}

	public KafkaConsumerBuilder withZkServer(final String zkServer) {
		properties.put(ConsumerConfigConst.ZOOKEEPER_CONNECT, zkServer);
		return this;
	}

	public KafkaConsumerBuilder withBorkerServer(final String kafkaServer) {
		properties.put(ConsumerConfigConst.KAFKA_BROKER_CONNECT, kafkaServer);
		return this;
	}

	public KafkaConsumerBuilder withGroupId(final String groupId) {
		properties.put(ConsumerConfigConst.GROUP_ID, groupId);
		return this;
	}

	public KafkaConsumer<String, String> build() {
		modifyConf();
		return new KafkaConsumer<>(properties);
	}

	public ConsumerConnector buildConsumer() {
		modifyConf();
		return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
	}

	private void modifyConf() {
		for (Map.Entry<String, Object> line : MOGO_DEFAULT_CONFIG.entrySet()) {
			if (!properties.containsKey(line.getKey())) {
				properties.put(line.getKey(), line.getValue());
			}
		}
	}


}
