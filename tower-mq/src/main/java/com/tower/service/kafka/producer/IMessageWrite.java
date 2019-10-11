package com.tower.service.kafka.producer;


/**
 * 日志写本地磁盘--通过配置log4j做控制，一天一个文件 一个文件控制在200M
 * 内部区分是发到哪个集群的消息
 * @author  qqmxl
 * @Date: 2018/9/7 15:36
 * @Description:
 */
public interface IMessageWrite {

	/**
	 * 依托log4j写日志
	 * @param kafkaGroup
	 * @param topic
	 * @param message
	 */
	void writer(String kafkaGroup, String topic, String message);
}
