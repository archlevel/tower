package com.tower.service.web.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tower.service.config.DynamicConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileDict;
import com.tower.service.config.dict.ConfigFileTypeDict;

@Component(ConfigComponent.WebAppConfig)
public class WebAppConfig extends DynamicConfig {
    
    public WebAppConfig(){
    }
    
    public WebAppConfig(boolean delimiterParsingDisabled){
    }
    @PostConstruct
    public void init(){
        this.setFileName(System.getProperty(ConfigFileDict.WEB_CONTROLLER_CONFIG_FILE,
            ConfigFileDict.DEFAULT_WEB_CONTROLLER_CONFIG_NAME));
        this.setType(ConfigFileTypeDict.XML);
        super.init();
    }
    
}
