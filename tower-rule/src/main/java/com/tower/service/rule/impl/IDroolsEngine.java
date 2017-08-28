package com.tower.service.rule.impl;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSessionConfiguration;

import com.tower.service.rule.IEngine;

public interface IDroolsEngine extends IEngine {

	public void setKieService(KieServices kieService);

	public KieServices getKieService();

	public KieContainer getContainer();

	public void setkContainer(KieContainer kContainer);

	public TowerDroolsSession getSession(String sessionName);

	public TowerDroolsSession getSession(KieSessionConfiguration conf);

	public TowerDroolsSession getSession(Environment environment);

	public TowerDroolsSession getSession(Environment environment,
			KieSessionConfiguration conf);

	public TowerDroolsSession getSession(String kSessionName,
			Environment environment);

	public TowerDroolsSession getSession(String kSessionName,
			KieSessionConfiguration conf);

	public TowerDroolsSession getSession(String kSessionName,
			Environment environment, KieSessionConfiguration conf);

	public StatelessTowerDroolsSession getStatelessSession();

	public StatelessTowerDroolsSession getStatelessSession(
			KieSessionConfiguration conf);

	public StatelessTowerDroolsSession getStatelessSession(
			String kSessionName);

	public StatelessTowerDroolsSession getStatelessSession(
			String kSessionName, KieSessionConfiguration conf);

	public String getFileBasePath();

	public void setFileBasePath(String fileBasePath);

	public KieFileSystem getFileSystem();

	public void setFileSystem(KieFileSystem fileSystem);

	public void setKieBaseName(String kieBaseName);

	public String getKieBaseName();

	public void setPackages(String packages);

	public String getPackages();

	public void setSessionName(String sessionName);

	public String getSessionName();

	public void refresh();

    public String getGroupId();

    public void setGroupId(String groupId);

    public String getArtifactId();

    public void setArtifactId(String artifactId);

    public String getVersion();

    public void setVersion(String version);

    public long getPollingInterval();

    public void setPollingInterval(long pollingInterval);

}