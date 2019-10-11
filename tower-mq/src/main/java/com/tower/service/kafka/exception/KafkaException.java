package com.tower.service.kafka.exception;

/**
 * kafka 消息消費異常
 *
 * @author mxl
 */
public class KafkaException extends RuntimeException {
	public KafkaException(Throwable cause) {
		super(cause);
	}
}