package com.tower.service.web.impl;

import javax.annotation.Resource;

import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.resource.IResource;


public abstract class AbsResource implements IResource {
    
    @Resource(name=ConfigComponent.WebAppConfig)
    protected WebAppConfig config;
}
