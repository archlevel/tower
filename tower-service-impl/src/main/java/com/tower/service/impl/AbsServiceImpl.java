package com.tower.service.impl;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tower.service.IService;
import com.tower.service.config.IConfigChangeListener;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public abstract class AbsServiceImpl implements IService,IConfigChangeListener, InitializingBean {
    
    protected ServiceConfig config;
    
	/**
	 * Logger for this class
	 */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public AbsServiceImpl(){
	    
	}
	
	protected static RpcContext context = RpcContext.getContext();
    @Override
    public String getRemoteHost() {
        return context.getRemoteHost();
    }

	public String hello(String name){
		if (logger.isInfoEnabled()) {
			logger.info("hello(String name={}) - start", name); //$NON-NLS-1$
		}

		String returnString = "hello," + name;

		if (logger.isInfoEnabled()) {
			logger.info("hello(String name={}) - end - return value={}", name, returnString); //$NON-NLS-1$
		}
		return returnString;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(config!=null){
			config.addChangeListener(this);
		}
	}
	
	public ServiceConfig getConfig() {
		return config;
	}

	public void setConfig(ServiceConfig config) {
		this.config = config;
	}

	@Override
	public void configChanged() {
		
	}

}
