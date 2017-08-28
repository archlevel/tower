package com.tower.service.mq;

import javax.annotation.PostConstruct;

import com.tower.service.mq.impl.MQConfig;

public abstract class MQClient extends MQConfig implements IConnector{
    private String id;
    public MQClient(String id){
        this.id = id;
    }
    
    @PostConstruct
    public void init(){
        this.setPrefix(id);
        super.init();
    }
    
}
