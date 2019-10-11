package com.tower.service.kafka.exception;

/**
 * kafka限流异常
 *
 * @author yhzhu
 */
public class RateLimiterException extends RuntimeException {

	public RateLimiterException(String message) {
		super(message);
	}

}
