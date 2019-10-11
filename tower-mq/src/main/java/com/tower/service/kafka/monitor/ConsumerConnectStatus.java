package com.tower.service.kafka.monitor;

/**
 * @author: qqmxl
 * @Date: 2018/11/16 17:36
 * @Description:
 */
public enum ConsumerConnectStatus {
	/**
	 * 关闭
	 */
	CLOSE,
	/**
	 * 重连
	 */
	RECONNECT,
	/**
	 * 停止
	 */
	STOP,
	/**
	 * 初始化
	 */
	INIT;
}
