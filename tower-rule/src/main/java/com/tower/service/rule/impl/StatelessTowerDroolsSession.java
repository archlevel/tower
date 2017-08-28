package com.tower.service.rule.impl;

import java.util.Collection;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.command.Command;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.StatelessKieSession;

import com.tower.service.rule.ISession;

public class StatelessTowerDroolsSession implements ISession,StatelessKieSession {
	
	private StatelessKieSession delegate;
	public StatelessTowerDroolsSession(StatelessKieSession delegate){
		if(delegate==null){
			throw new RuntimeException("KieSession 不能为空！");
		}
		this.delegate = delegate;
	}
	

	@Override
	public <T> T execute(Command<T> command) {
		return delegate.execute(command);
	}

	@Override
	public void setGlobal(String identifier, Object value) {
		delegate.setGlobal(identifier, value);
	}

	@Override
	public Globals getGlobals() {
		return delegate.getGlobals();
	}

	@Override
	public KieBase getKieBase() {
		return delegate.getKieBase();
	}

	@Override
	public void registerChannel(String name, Channel channel) {
		delegate.registerChannel(name, channel);
	}

	@Override
	public void unregisterChannel(String name) {
		delegate.unregisterChannel(name);
	}

	@Override
	public Map<String, Channel> getChannels() {
		return delegate.getChannels();
	}

	@Override
	public KieRuntimeLogger getLogger() {
		return delegate.getLogger();
	}

	@Override
	public void addEventListener(RuleRuntimeEventListener listener) {
		delegate.addEventListener(listener);
	}

	@Override
	public void removeEventListener(RuleRuntimeEventListener listener) {
		delegate.removeEventListener(listener);
	}

	@Override
	public Collection<RuleRuntimeEventListener> getRuleRuntimeEventListeners() {
		return delegate.getRuleRuntimeEventListeners();
	}

	@Override
	public void addEventListener(AgendaEventListener listener) {
		delegate.addEventListener(listener);
	}

	@Override
	public void removeEventListener(AgendaEventListener listener) {
		delegate.removeEventListener(listener);
	}

	@Override
	public Collection<AgendaEventListener> getAgendaEventListeners() {
		return delegate.getAgendaEventListeners();
	}

	@Override
	public void addEventListener(ProcessEventListener listener) {
		delegate.addEventListener(listener);
	}

	@Override
	public void removeEventListener(ProcessEventListener listener) {
		delegate.removeEventListener(listener);
	}

	@Override
	public Collection<ProcessEventListener> getProcessEventListeners() {
		return delegate.getProcessEventListeners();
	}

	@Override
	public void execute(Object object) {
		delegate.execute(object);
	}
	@Override
	public void execute(Iterable objects) {
		delegate.execute(objects);
	}

}
