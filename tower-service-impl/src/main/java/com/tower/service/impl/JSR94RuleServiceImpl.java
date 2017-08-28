package com.tower.service.impl;

import java.util.HashMap;

import javax.rules.RuleServiceProvider;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tower.service.IService;
import com.tower.service.config.IConfigChangeListener;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rule.impl.IJSR94Engine;
import com.tower.service.rule.impl.StatelessTowerJSR94Session;
import com.tower.service.rule.impl.TowerJSR94Engine;
import com.tower.service.rule.impl.TowerJSR94Session;

public class JSR94RuleServiceImpl implements IService,IJSR94Engine,IConfigChangeListener, InitializingBean {
    
    protected static ServiceConfig config;
    private IJSR94Engine delegate = new TowerJSR94Engine();
	/**
	 * Logger for this class
	 */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public JSR94RuleServiceImpl(){
	}
	
	protected static RpcContext context = RpcContext.getContext();
    @Override
    public String getRemoteHost() {
        return context.getRemoteHost();
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

	@Override
	public LocalRuleExecutionSetProvider getRuleExecutionSetProvider() {
		return delegate.getRuleExecutionSetProvider();
	}

	@Override
	public RuleServiceProvider getProvider() {
		return delegate.getProvider();
	}

	@Override
	public RuleAdministrator getAdmin() {
		return delegate.getAdmin();
	}

	@Override
	public HashMap<String, String> getProperties() {
		return delegate.getProperties();
	}

	@Override
	public void setProperties(HashMap<String, String> properties) {
		delegate.setProperties(properties);
	}

	@Override
	public String getUri() {
		return delegate.getUri();
	}

	@Override
	public void setUri(String uri) {
		delegate.setUri(uri);
	}

	@Override
	public String getRuleProvider() {
		return delegate.getRuleProvider();
	}

	@Override
	public void setRuleProvider(String ruleProvider) {
		delegate.setRuleProvider(ruleProvider);
	}

	@Override
	public String getBasePath() {
		return delegate.getBasePath();
	}

	@Override
	public void setBasePath(String basePath) {
		delegate.setBasePath(basePath);
	}

	@Override
	public void addRule(String bindUrl, String ruleFileName) {
		delegate.addRule(bindUrl, ruleFileName);
	}

	@Override
	public StatelessTowerJSR94Session getStatelessSession(String bindUrl) {
		return delegate.getStatelessSession(bindUrl);
	}

	@Override
	public TowerJSR94Session getSession(String bindUrl) {
		return delegate.getSession(bindUrl);
	}

	@Override
	public void refresh() {
		delegate.refresh();
	}
}
