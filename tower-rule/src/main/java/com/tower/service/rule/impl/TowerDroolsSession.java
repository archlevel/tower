package com.tower.service.rule.impl;

import java.util.Collection;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.command.Command;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.Calendars;
import org.kie.api.runtime.Channel;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.rule.Agenda;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.FactHandle.State;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.ViewChangedEventListener;
import org.kie.api.time.SessionClock;

import com.tower.service.rule.ISession;

public class TowerDroolsSession implements ISession,KieSession {
	
	private KieSession delegate;
	public TowerDroolsSession(KieSession delegate){
		if(delegate==null){
			throw new RuntimeException("KieSession 不能为空！");
		}
		this.delegate = delegate;
	}
	@Override
	public int fireAllRules() {
		return delegate.fireAllRules();
	}

	@Override
	public int fireAllRules(int max) {
		return delegate.fireAllRules(max);
	}

	@Override
	public int fireAllRules(AgendaFilter agendaFilter) {
		return delegate.fireAllRules(agendaFilter);
	}

	@Override
	public int fireAllRules(AgendaFilter agendaFilter, int max) {
		return delegate.fireAllRules(agendaFilter, max);
	}

	@Override
	public void fireUntilHalt() {
		delegate.fireUntilHalt();
	}

	@Override
	public void fireUntilHalt(AgendaFilter agendaFilter) {
		delegate.fireUntilHalt(agendaFilter);
	}

	@Override
	public <T> T execute(Command<T> command) {
		return delegate.execute(command);
	}

	@Override
	public <T extends SessionClock> T getSessionClock() {
		return delegate.getSessionClock();
	}

	@Override
	public void setGlobal(String identifier, Object value) {
		delegate.setGlobal(identifier, value);
	}

	@Override
	public Object getGlobal(String identifier) {
		return delegate.getGlobal(identifier);
	}

	@Override
	public Globals getGlobals() {
		return delegate.getGlobals();
	}

	@Override
	public Calendars getCalendars() {
		return delegate.getCalendars();
	}

	@Override
	public Environment getEnvironment() {
		return delegate.getEnvironment();
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
	public KieSessionConfiguration getSessionConfiguration() {
		return delegate.getSessionConfiguration();
	}

	@Override
	public void halt() {
		delegate.halt();
	}

	@Override
	public Agenda getAgenda() {
		return delegate.getAgenda();
	}

	@Override
	public EntryPoint getEntryPoint(String name) {
		return delegate.getEntryPoint(name);
	}

	@Override
	public Collection<? extends EntryPoint> getEntryPoints() {
		return delegate.getEntryPoints();
	}

	@Override
	public QueryResults getQueryResults(String query, Object... arguments) {
		return delegate.getQueryResults(query, arguments);
	}

	@Override
	public LiveQuery openLiveQuery(String query, Object[] arguments,
			ViewChangedEventListener listener) {
		return delegate.openLiveQuery(query, arguments, listener);
	}

	@Override
	public String getEntryPointId() {
		return delegate.getEntryPointId();
	}

	@Override
	public FactHandle insert(Object object) {
		return delegate.insert(object);
	}

	@Override
	public void retract(FactHandle handle) {
		delegate.retract(handle);
	}

	@Override
	public void delete(FactHandle handle) {
		delegate.delete(handle);
	}

	@Override
	public void delete(FactHandle handle, State fhState) {
		delegate.delete(handle, fhState);
	}

	@Override
	public void update(FactHandle handle, Object object) {
		delegate.update(handle, object);
	}

	@Override
	public FactHandle getFactHandle(Object object) {
		return delegate.getFactHandle(object);
	}

	@Override
	public Object getObject(FactHandle factHandle) {
		return delegate.getObject(factHandle);
	}

	@Override
	public Collection<? extends Object> getObjects() {
		return delegate.getObjects();
	}

	@Override
	public Collection<? extends Object> getObjects(ObjectFilter filter) {
		return delegate.getObjects(filter);
	}

	@Override
	public <T extends FactHandle> Collection<T> getFactHandles() {
		return delegate.getFactHandles();
	}

	@Override
	public <T extends FactHandle> Collection<T> getFactHandles(
			ObjectFilter filter) {
		return delegate.getFactHandles(filter);
	}

	@Override
	public long getFactCount() {
		return delegate.getFactCount();
	}

	@Override
	public ProcessInstance startProcess(String processId) {
		return delegate.startProcess(processId);
	}

	@Override
	public ProcessInstance startProcess(String processId,
			Map<String, Object> parameters) {
		return delegate.startProcess(processId, parameters);
	}

	@Override
	public ProcessInstance createProcessInstance(String processId,
			Map<String, Object> parameters) {
		return delegate.createProcessInstance(processId, parameters);
	}

	@Override
	public ProcessInstance startProcessInstance(long processInstanceId) {
		return delegate.startProcessInstance(processInstanceId);
	}

	@Override
	public void signalEvent(String type, Object event) {
		delegate.signalEvent(type, event);
	}

	@Override
	public void signalEvent(String type, Object event, long processInstanceId) {
		delegate.signalEvent(type, event, processInstanceId);
	}

	@Override
	public Collection<ProcessInstance> getProcessInstances() {
		return delegate.getProcessInstances();
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId) {
		return delegate.getProcessInstance(processInstanceId);
	}

	@Override
	public ProcessInstance getProcessInstance(long processInstanceId,
			boolean readonly) {
		return delegate.getProcessInstance(processInstanceId, readonly);
	}

	@Override
	public void abortProcessInstance(long processInstanceId) {
		delegate.abortProcessInstance(processInstanceId);
	}

	@Override
	public WorkItemManager getWorkItemManager() {
		return delegate.getWorkItemManager();
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
	public int getId() {
		return delegate.getId();
	}

	@Override
	public long getIdentifier() {
		return delegate.getIdentifier();
	}

	@Override
	public void dispose() {
		delegate.dispose();
	}

	@Override
	public void destroy() {
		delegate.destroy();
	}
}
