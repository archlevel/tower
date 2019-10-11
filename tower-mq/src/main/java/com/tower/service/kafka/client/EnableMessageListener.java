package com.tower.service.kafka.client;

import java.lang.annotation.*;

/**
 * @auther: qqmxl
 * @Date: 2018/11/19 18:57
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableMessageListener {
}
