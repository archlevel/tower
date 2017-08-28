package com.tower.service.rule.impl;

import java.util.List;

import javax.rules.ObjectFilter;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.StatelessRuleSession;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rule.ISession;

public class StatelessTowerJSR94Session implements ISession,
		StatelessRuleSession {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private StatelessRuleSession delegate;

	public StatelessTowerJSR94Session(StatelessRuleSession delegate) {
		if (delegate == null) {
			throw new RuntimeException("StatelessRuleSession 不能为空！");
		}
		this.delegate = delegate;
	}

	@Override
	public RuleExecutionSetMetadata getRuleExecutionSetMetadata() {
		try {
			return delegate.getRuleExecutionSetMetadata();
		} catch (Exception e) {
			logger.error("getRuleExecutionSetMetadata失败", e);
			throw new RuntimeException(e);
		} 
	}

	@Override
	public int getType() {
		try {
			return delegate.getType();
		} catch (Exception e) {
			logger.error("获取类型失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void release() {
		try {
			delegate.release();
		} catch (Exception e) {
			logger.error("release失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List executeRules(List arg0) {
		try {
			return delegate.executeRules(arg0);
		} catch (Exception e) {
			logger.error("执行" + arg0 + " rules失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List executeRules(List arg0, ObjectFilter arg1) {
		try {
			return delegate.executeRules(arg0, arg1);
		} catch (Exception e) {
			logger.error("执行" + arg0 + " rules失败", e);
			throw new RuntimeException(e);
		}
	}

}
