package com.tower.service.kafka;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * kafka消息Bean
 *
 * @author fanglin
 * @version 1.0
 * @date [2017-09-27]
 */
public class MessageBean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * topic名称
	 */
	private String topic;
	/**
	 * partition
	 */
	private int partition;
	/**
	 * offset
	 */
	private long offset;
	/**
	 * 具体消息
	 */
	private String message;

	private String uuid;

	private String requestId;

	private long sendTime;

	private String sendIp;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendIp() {
		return sendIp;
	}

	public void setSendIp(String sendIp) {
		this.sendIp = sendIp;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getPartition() {
		return partition;
	}

	public void setPartition(int partition) {
		this.partition = partition;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
