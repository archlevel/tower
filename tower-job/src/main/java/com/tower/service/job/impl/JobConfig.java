package com.tower.service.job.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tower.service.config.PrefixPriorityConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileTypeDict;

@Component(ConfigComponent.JobConfig)
public class JobConfig extends PrefixPriorityConfig{
    
    public JobConfig(){
    }
    
    @PostConstruct
    public void init(){
        this.setFileName(System.getProperty(JOB_CONFIG_FILE, DEFAULT_JOB_CONFIG_NAME));
        this.setType(ConfigFileTypeDict.XML);
        super.init();
    }
}
