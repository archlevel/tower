package com.tower.service.rule.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatefulRuleSession;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.drools.jsr94.rules.RuleServiceProviderImpl;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rule.IEngine;

public class TowerJSR94Engine implements IEngine, IJSR94Engine {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String uri = RuleServiceProviderImpl.RULE_SERVICE_PROVIDER;
	private String ruleProvider = RuleServiceProviderImpl.class.getName();
	private RuleServiceProvider provider;
	
	private String basePath = "com/leya/service/apmt/rule/jsr94/";
	private LocalRuleExecutionSetProvider ruleExecutionSetProvider;
	private RuleRuntime runtime;
	private boolean inited = false;

	public TowerJSR94Engine() {
	}

	@PostConstruct
	public void init() {
		if(!inited){
			try {
				// 1.注册ruleProvider,并且从RuleServiceProviderManager获取ruleProvider
				RuleServiceProviderManager.registerRuleServiceProvider(uri,
						Class.forName(ruleProvider));
				provider = RuleServiceProviderManager.getRuleServiceProvider(uri);
				// 2.获取RuleAdministrator实例,获取RuleExectuionSetProvider
				admin = provider.getRuleAdministrator();
				ruleExecutionSetProvider = admin
						.getLocalRuleExecutionSetProvider(properties);
				// 5.获取RuleRuntime, 创建会话
				runtime = provider.getRuleRuntime();
				inited = true;
			} catch (Exception e) {
				logger.error("init 失败", e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getRuleExecutionSetProvider()
	 */
	@Override
	public LocalRuleExecutionSetProvider getRuleExecutionSetProvider() {
		if(!inited){
			init();
		}
		return ruleExecutionSetProvider;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getProvider()
	 */
	@Override
	public RuleServiceProvider getProvider() {
		if(!inited){
			init();
		}
		return provider;
	}

	private HashMap<String, String> properties = new HashMap<String, String>();
	private RuleAdministrator admin = null;

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getAdmin()
	 */
	@Override
	public RuleAdministrator getAdmin() {
		if(!inited){
			init();
		}
		return admin;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getProperties()
	 */
	@Override
	public HashMap<String, String> getProperties() {
		return properties;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#setProperties(java.util.HashMap)
	 */
	@Override
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getUri()
	 */
	@Override
	public String getUri() {
		return uri;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#setUri(java.lang.String)
	 */
	@Override
	public void setUri(String uri) {
		this.uri = uri;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getRuleProvider()
	 */
	@Override
	public String getRuleProvider() {
		return ruleProvider;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#setRuleProvider(java.lang.String)
	 */
	@Override
	public void setRuleProvider(String ruleProvider) {
		this.ruleProvider = ruleProvider;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getBasePath()
	 */
	@Override
	public String getBasePath() {
		return basePath;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#setBasePath(java.lang.String)
	 */
	@Override
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#addRule(java.lang.String, java.lang.String)
	 */
	@Override
	public void addRule(String bindUrl, String ruleFileName) {
		// 3.创建RuleExecutionSet
		if(!inited){
			init();
		}
		try {
			Reader reader = new InputStreamReader(getClass().getClassLoader()
					.getResourceAsStream(this.getBasePath() + ruleFileName));// "addpoint.drl"));
			RuleExecutionSet reSet = ruleExecutionSetProvider
					.createRuleExecutionSet(reader, properties);
			// 4.注册RuleExecutionSet
			admin.registerRuleExecutionSet(bindUrl, reSet, properties);
		} catch (Exception ex) {
			logger.error("addRule失败", ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getStatelessSession(java.lang.String)
	 */
	@Override
	public StatelessTowerJSR94Session getStatelessSession(String bindUrl) {
		try {
			return new StatelessTowerJSR94Session(
					(StatelessRuleSession) runtime.createRuleSession(bindUrl, null,
							RuleRuntime.STATELESS_SESSION_TYPE));
		} catch (Exception e) {
			logger.error("创建"+bindUrl+" session失败", e);
			throw new RuntimeException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#getSession(java.lang.String)
	 */
	@Override
	public TowerJSR94Session getSession(String bindUrl) {
		try {
			return new TowerJSR94Session(
					(StatefulRuleSession) runtime.createRuleSession(bindUrl, null,
							RuleRuntime.STATEFUL_SESSION_TYPE));
		} catch (Exception e) {
			logger.error("创建"+bindUrl+" session失败", e);
			throw new RuntimeException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IJSR94Engine#refresh()
	 */
	@Override
	public void refresh() {
		
	}
}
