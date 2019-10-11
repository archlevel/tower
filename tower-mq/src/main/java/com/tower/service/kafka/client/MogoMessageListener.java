package com.tower.service.kafka.client;

import com.mgzf.sdk.mq.kafka.config.ConsumerType;

import java.lang.annotation.*;

/**
 * @auther: qqmxl
 * @Date: 2018/11/15 19:13
 * @Description:
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MogoMessageListener {

	String topicKey();

	String kafkaGroup() default "";

	String groupKey();

	ConsumerType consumerType() default ConsumerType.ZK;

	int threadNum() default 1;

}
