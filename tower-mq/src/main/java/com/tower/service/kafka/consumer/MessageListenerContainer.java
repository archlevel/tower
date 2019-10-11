package com.tower.service.kafka.consumer;

/**
 * @author : qqmxl
 * @Date: 2018/11/19 15:04
 * @Description:
 */
public interface MessageListenerContainer {

	/**
	 * 启动
	 */
	void start();

	/**
	 * 暂停
	 * @param error
	 */
	void stop(String error);

	/**
	 * 重连
	 * @param error
	 */
	void reconnect(String error);

	/**
	 * 关闭
	 */
	void close();
}
