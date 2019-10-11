package com.tower.service.kafka.producer;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * 限流信息写入
 * @author yhzhu
 */
public class RateLimiterWriter {
    private static Logger logger = LoggerFactory.getLogger(RateLimiterWriter.class);

    public static void writer(String kafkaGroup, String topic, List<?> messageList) {
        /**
         * 集群-topic-消息（加上uuid+时间戳 前缀）
         * TODO: 是否应该异步写入
         */
        for (Object message:messageList) {
            logger.info("{}|{}|{}|{}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), kafkaGroup, topic, message);
        }
    }
}
