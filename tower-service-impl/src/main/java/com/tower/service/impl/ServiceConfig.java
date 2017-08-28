package com.tower.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tower.service.config.PrefixPriorityConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileDict;
import com.tower.service.config.dict.ConfigFileTypeDict;

@Component(ConfigComponent.ServiceConfig)
public class ServiceConfig extends PrefixPriorityConfig {
    
    public ServiceConfig() {}

    @PostConstruct
    public void init() {
        this.setFileName(System.getProperty(ConfigFileDict.SERVICE_CONFIG_FILE,
                ConfigFileDict.DEFAULT_SERVICE_CONFIG_NAME));
        this.setType(ConfigFileTypeDict.XML);
        super.init();
    }
}
