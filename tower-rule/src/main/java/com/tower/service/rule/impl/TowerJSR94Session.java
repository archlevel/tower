package com.tower.service.rule.impl;

import java.util.List;

import javax.rules.Handle;
import javax.rules.ObjectFilter;
import javax.rules.RuleExecutionSetMetadata;
import javax.rules.StatefulRuleSession;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rule.ISession;

public class TowerJSR94Session implements ISession, StatefulRuleSession {

	/**
	 * 
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1622962438476239211L;
	private StatefulRuleSession delegate;

	public TowerJSR94Session(StatefulRuleSession delegate) {
		if (delegate == null) {
			throw new RuntimeException("StatefulRuleSession 不能为空！");
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
	public Handle addObject(Object arg0) {
		try {
			return delegate.addObject(arg0);
		} catch (Exception e) {
			logger.error("addObject失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List addObjects(List arg0) {
		try {
			return delegate.addObjects(arg0);
		} catch (Exception e) {
			logger.error("addObject失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean containsObject(Handle arg0) {
		try {
			return delegate.containsObject(arg0);
		} catch (Exception e) {
			logger.error("containsObject失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void executeRules() {
		try {
			delegate.executeRules();
		} catch (Exception e) {
			logger.error("executeRules失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List getHandles() {
		try {
			return delegate.getHandles();
		} catch (Exception e) {
			logger.error("getHandles失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object getObject(Handle arg0) {
		try {
			return delegate.getObject(arg0);
		} catch (Exception e) {
			logger.error("getObject失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List getObjects() {
		try {
			return delegate.getObjects();
		} catch (Exception e) {
			logger.error("getObjects失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List getObjects(ObjectFilter arg0) {
		try {
			return delegate.getObjects(arg0);
		} catch (Exception e) {
			logger.error("getObjects失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeObject(Handle arg0) {
		try {
			delegate.removeObject(arg0);
		} catch (Exception e) {
			logger.error("removeObject失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void reset() {
		try {
			delegate.reset();
		} catch (Exception e) {
			logger.error("reset失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateObject(Handle arg0, Object arg1) {
		try {
			delegate.updateObject(arg0, arg1);
		} catch (Exception e) {
			logger.error("updateObject失败", e);
			throw new RuntimeException(e);
		}
	}

}
