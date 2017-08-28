package com.tower.service.rule.impl;

import java.util.HashMap;

import javax.rules.RuleServiceProvider;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;

import com.tower.service.rule.IEngine;

public interface IJSR94Engine extends IEngine{

	public abstract LocalRuleExecutionSetProvider getRuleExecutionSetProvider();

	public abstract RuleServiceProvider getProvider();

	public abstract RuleAdministrator getAdmin();

	public abstract HashMap<String, String> getProperties();

	public abstract void setProperties(HashMap<String, String> properties);

	public abstract String getUri();

	public abstract void setUri(String uri);

	public abstract String getRuleProvider();

	public abstract void setRuleProvider(String ruleProvider);

	public abstract String getBasePath();

	public abstract void setBasePath(String basePath);

	public abstract void addRule(String bindUrl, String ruleFileName);

	public abstract StatelessTowerJSR94Session getStatelessSession(
			String bindUrl);

	public abstract TowerJSR94Session getSession(String bindUrl);

	public abstract void refresh();

}