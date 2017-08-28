package com.tower.service.rpc.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tower.service.config.PrefixPriorityConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileDict;
import com.tower.service.config.dict.ConfigFileTypeDict;

@Component(ConfigComponent.RpcConfig)
public class RpcConfig extends PrefixPriorityConfig{
    public RpcConfig(){
    }
    @PostConstruct
    public void init(){
        this.setFileName(System.getProperty(ConfigFileDict.DEFAULT_RPC_CONFIG_FILE,
            ConfigFileDict.DEFAULT_RPC_CONFIG_NAME));
        this.setType(ConfigFileTypeDict.XML);
        super.init();
    }
}
