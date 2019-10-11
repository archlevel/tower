package com.tower.service.kafka.producer;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.UUID;

/**
 * @author qqmxl
 * @Date: 2018/9/7 15:36
 * @Description:
 */
public class MessageBuilder<V> {

	private String topicName;
	private Integer partition;
	private V value;
	private List<V> vList;
	private String key;
	private String kafkaGroup;
	private String uuid;

	private MessageBuilder(String kafkaGroup, String topicKey, List<V> vList) {
		this.vList = vList;
		this.init(kafkaGroup, topicKey, null);
	}

	private MessageBuilder(String kafkaGroup, V value, String topicKey, String... uuid) {
		this.init(kafkaGroup, topicKey, value);
		if (uuid == null || uuid.length != 1 || uuid[0] == null) {
			this.uuid = UUID.randomUUID().toString().replace("-", "");
		} else {
			this.uuid = uuid[0];
		}
	}

	private MessageBuilder(String topicKey, V value) {
		int index = topicKey.indexOf('-');
		this.init(topicKey.substring(0, index < 0 ? 0 : index), topicKey, value);
	}

	private void init(String kafkaGroup, String topicKey, V value) {
		this.kafkaGroup = kafkaGroup;
		this.topicName = topicKey;
		this.value = value;
	}

	public static MessageBuilder withPayload(String topicKey, String value) {
		return new MessageBuilder(topicKey, value);
	}

	public static MessageBuilder withPayload(String kafkaGroup, String topicKey, String value, String... uuid) {
		return new MessageBuilder(kafkaGroup, value, topicKey, uuid);
	}

	public static <V> MessageBuilder withBatchPayload(String kafkaGroup, String topicKey, List<V> vList) {
		return new MessageBuilder(kafkaGroup, topicKey, vList);
	}

	public MessageBuilder key(String key) {
		this.key = key;
		return this;
	}

	public MessageBuilder partition(Integer partition) {
		this.partition = partition;
		return this;
	}

	public String getTopicName() {
		return topicName;
	}

	public List<V> getvList() {
		return vList;
	}

	public Integer getPartition() {
		return partition;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getKafkaGroup() {
		return kafkaGroup;
	}

	public void setKafkaGroup(String kafkaGroup) {
		this.kafkaGroup = kafkaGroup;
	}

	public String getUuid() {
		return uuid;
	}

	public void setvList(List<V> vList) {
		this.vList = vList;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
