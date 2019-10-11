package com.tower.service.kafka.producer;

import com.mgzf.sdk.log.MgzfLogType;
import com.mgzf.sdk.log.MgzfMonitorJsonLogger;
import com.mgzf.sdk.mq.kafka.SdkContext;

import java.util.List;

/**
 * 日志写本地磁盘--通过配置log4j做控制，一天一个文件 一个文件控制在200M
 * 内部区分是发到哪个集群的消息
 *
 * @author qqmxl
 * @Date: 2018/9/7 15:36
 * @Description:
 */
public class FailMsgWriteFile {

	public static void writer(String kafkaGroup, String topic, String message) {
		//集群-topic-消息（加上uuid+时间戳 前缀）
		MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "producer-fail", SdkContext.getAllIp(), kafkaGroup + "#" + topic, 0, message);
	}

	public static void writer(String kafkaGroup, String topic, List<?> message) {
		//集群-topic-消息（加上uuid+时间戳 前缀）
		for (Object msg : message) {
			MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "producer-fail", SdkContext.getAllIp(), kafkaGroup + "#" + topic, 0, msg.toString());
		}
	}

}
