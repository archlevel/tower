package com.tower.service.kafka.producer;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Map;
import java.util.Properties;

/**
 * 消息发送类
 *
 * @author qqmxl
 * @Date: 2018/9/7 15:36
 * @Description:
 * put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
 */
public class KafkaProducerBuilder {

	private Properties properties;

	private static final Map<String, Object> MOGO_DEFAULT_CONFIG = Maps.newHashMap();

	static {
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.ACKS_CONFIG, "all");
		// retry times
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.RETRIES_CONFIG, 15);
		// Batch Size
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		//此设置默认为50（即无延迟）
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		// Send in // memorys
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		//请求超时时间（客户端） +副本时间（服务端） 需要同时设置
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 15000);
		//设置过大第一次发送会比较慢
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		MOGO_DEFAULT_CONFIG.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	}

	private KafkaProducerBuilder(Properties properties) {
		this.properties = properties;
	}

	public static KafkaProducerBuilder builder() {
		return builder(null);
	}

	public static KafkaProducerBuilder builder(Properties props) {
		if (props == null) {
			return new KafkaProducerBuilder(new Properties());
		}
		return new KafkaProducerBuilder(props);
	}

	public KafkaProducerBuilder withServer(final String bootstrapServer) {
		properties.put("bootstrap.servers", bootstrapServer);
		return this;
	}

	public <K, V> KafkaMogoProducer<K, V> build() {
		for (Map.Entry<String, Object> line : MOGO_DEFAULT_CONFIG.entrySet()) {
			if (!properties.containsKey(line.getKey())) {
				properties.put(line.getKey(), line.getValue());
			}
		}
		return new KafkaMogoProducer<>(properties);
	}
}
