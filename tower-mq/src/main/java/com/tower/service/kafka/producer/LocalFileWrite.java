package com.tower.service.kafka.producer;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 日志写本地磁盘--通过配置log4j做控制，一天一个文件 一个文件控制在200M
 * 内部区分是发到哪个集群的消息
 * @author  qqmxl
 * @Date: 2018/9/7 15:36
 * @Description:
 */
public class LocalFileWrite {

	private static Logger logger = LoggerFactory.getLogger(LocalFileWrite.class);

	public static void writer(String kafkaGroup,String topic,String message) {
		//集群-topic-消息（加上uuid+时间戳 前缀）
		logger.info("{}|{}|{}|{}",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"),kafkaGroup,topic,message);
	}

	public static void writer(String kafkaGroup,String topic,List<?> messageList) {
		//集群-topic-消息（加上uuid+时间戳 前缀）
		for (Object message:messageList) {
			logger.info("{}|{}|{}|{}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), kafkaGroup, topic, message);
		}
	}
}
