package com.tower.service.mq.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tower.service.config.PrefixPriorityConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileTypeDict;

@Component(ConfigComponent.MQConfig)
public class MQConfig extends PrefixPriorityConfig {
    public MQConfig() {}

    @PostConstruct
    public void init() {
        this.setFileName(System.getProperty(DEFAULT_MQ_CONFIG_FILE, DEFAULT_MQ_CONFIG_NAME));
        this.setType(ConfigFileTypeDict.PROPERTIES);
        super.init();
    }
}
