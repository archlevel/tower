/**
 * 
 */
package com.tower.service.rule.impl;

import java.io.File;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSessionConfiguration;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

/**
 * @author alexzhu
 * 
 */
public class TowerDroolsEngine implements IDroolsEngine {

	private String kieBaseName = "FileSystemBase";
	private String packages = "rules";
	private String sessionName = "FileSystemKSession";
	private String fileBasePath=Thread.currentThread().getContextClassLoader()
			.getResource("").getPath();
	public static KieServices defaultKieService = KieServices.Factory.get();
	public KieServices kieService;
	protected KieContainer defaultContainer = defaultKieService.getKieClasspathContainer();

	private KieResources resources;
	private KieFileSystem fileSystem;
    protected KieContainer kContainer;

    private String groupId;
    private String artifactId;
    private String version;

    private long pollingInterval=10000L;

	protected Logger logger = LoggerFactory.getLogger(getClass());


	public TowerDroolsEngine() {
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
	}
	
	@Override
	public void setKieService(KieServices kieService) {
		this.kieService = kieService;
	}
	

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getContainer()
	 */
	@Override
	public KieContainer getContainer() {
		return kContainer==null?defaultContainer:kContainer;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#setkContainer(org.kie.api.runtime.KieContainer)
	 */
	@Override
	public void setkContainer(KieContainer kContainer) {
		this.kContainer = kContainer;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getSession(java.lang.String)
	 */
	@Override
	public TowerDroolsSession getSession(String sessionName) {
		return new TowerDroolsSession(getContainer().newKieSession(sessionName));
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getSession(org.kie.api.runtime.KieSessionConfiguration)
	 */
	@Override
	public TowerDroolsSession getSession(KieSessionConfiguration conf){
		return new TowerDroolsSession(getContainer().newKieSession(conf));
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getSession(org.kie.api.runtime.Environment)
	 */
	@Override
	public TowerDroolsSession getSession(Environment environment){
		return new TowerDroolsSession(getContainer().newKieSession(environment));
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getSession(org.kie.api.runtime.Environment, org.kie.api.runtime.KieSessionConfiguration)
	 */
	@Override
	public TowerDroolsSession getSession(Environment environment, KieSessionConfiguration conf){
		return new TowerDroolsSession(getContainer().newKieSession(environment,conf));
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getSession(java.lang.String, org.kie.api.runtime.Environment)
	 */
	@Override
	public TowerDroolsSession getSession(String kSessionName, Environment environment){
		return new TowerDroolsSession(getContainer().newKieSession(kSessionName,environment));
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getSession(java.lang.String, org.kie.api.runtime.KieSessionConfiguration)
	 */
	@Override
	public TowerDroolsSession getSession(String kSessionName, KieSessionConfiguration conf){
		return new TowerDroolsSession(getContainer().newKieSession(kSessionName,conf));
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getSession(java.lang.String, org.kie.api.runtime.Environment, org.kie.api.runtime.KieSessionConfiguration)
	 */
	@Override
	public TowerDroolsSession getSession(String kSessionName, Environment environment, KieSessionConfiguration conf){
		return new TowerDroolsSession(getContainer().newKieSession(kSessionName,environment,conf));
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getStatelessSession()
	 */
	@Override
	public StatelessTowerDroolsSession getStatelessSession(){
		return new StatelessTowerDroolsSession(getContainer().newStatelessKieSession());
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getStatelessSession(org.kie.api.runtime.KieSessionConfiguration)
	 */
	@Override
	public StatelessTowerDroolsSession getStatelessSession(KieSessionConfiguration conf){
		return new StatelessTowerDroolsSession(getContainer().newStatelessKieSession(conf));
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getStatelessSession(java.lang.String)
	 */
	@Override
	public StatelessTowerDroolsSession getStatelessSession(String kSessionName){
		return new StatelessTowerDroolsSession(getContainer().newStatelessKieSession(kSessionName));
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getStatelessSession(java.lang.String, org.kie.api.runtime.KieSessionConfiguration)
	 */
	@Override
	public StatelessTowerDroolsSession getStatelessSession(String kSessionName, KieSessionConfiguration conf){
		return new StatelessTowerDroolsSession(getContainer().newStatelessKieSession(kSessionName,conf));
	}
	
	public KieServices getKieService() {
		return kieService==null?defaultKieService:kieService;
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getFileBasePath()
	 */
	@Override
	public String getFileBasePath() {
		return fileBasePath;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#setFileBasePath(java.lang.String)
	 */
	@Override
	public void setFileBasePath(String fileBasePath) {
		this.fileBasePath = fileBasePath;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getFileSystem()
	 */
	@Override
	public KieFileSystem getFileSystem() {
		return fileSystem;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#setFileSystem(org.kie.api.builder.KieFileSystem)
	 */
	@Override
	public void setFileSystem(KieFileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#setKieBaseName(java.lang.String)
	 */
	@Override
	public void setKieBaseName(String kieBaseName) {
		this.kieBaseName = kieBaseName;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getKieBaseName()
	 */
	@Override
	public String getKieBaseName(){
		return kieBaseName;
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#setPackages(java.lang.String)
	 */
	@Override
	public void setPackages(String packages) {
		this.packages = packages;
	}
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getPackages()
	 */
	@Override
	public String getPackages(){
		return packages;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#setSessionName(java.lang.String)
	 */
	@Override
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#getSessionName()
	 */
	@Override
	public String getSessionName(){
		return sessionName;
	}



    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(long pollingInterval) {
        this.pollingInterval = pollingInterval;
    }
	
	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#refresh()
	 */
	@Override
	public void refresh() {
		
		KieResources resources = getKieService().getResources();
		
		KieModuleModel kieModuleModel = getKieService().newKieModuleModel();// 1

		KieBaseModel baseModel = kieModuleModel.newKieBaseModel(
				this.getKieBaseName()).addPackage(getPackages());// 2
		
		baseModel.newKieSessionModel(getSessionName());// 3

		fileSystem = getKieService().newKieFileSystem();

		String xml = kieModuleModel.toXML();
		
		logger.info("KieModuleXml: "+xml);
		
		fileSystem.writeKModuleXML(xml);// 5
		
		logger.info("FileBathPath: "+fileBasePath);
		fileBasePath = fileBasePath.substring(0, fileBasePath.length());

		List<String> fileList=null;
		try {
			fileList = null;//FileUtils.getDirectoryNames(new File(fileBasePath), ".drl", null, false);
			for (String sfile : fileList) {
				fileSystem.write("/config/rules/Rule.drl",
						resources.newFileSystemResource(new File(sfile)));// 6
			}
			KieBuilder kb = getKieService().newKieBuilder(fileSystem);
			kb.buildAll();// 7
			if (kb.getResults().hasMessages(Level.ERROR)) {
				logger.error("Build Errors:\n"+kb.getResults().toString());
			}
			KieContainer kContainer = getKieService().newKieContainer(
					getKieService().getRepository().getDefaultReleaseId());
			this.setkContainer(kContainer);
		} catch (Exception e) {
			logger.error("Build Errors",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.tower.service.rule.impl.IDroolsEngine#refreshRule(java.lang.String)
	 */
//	@Override
//	public void refreshRule(String ruleFile){
//		fileSystem.write(ruleFile,
//				resources.newFileSystemResource(this.getFileBasePath()));// 6
//		KieBuilder kb = getKieService().newKieBuilder(fileSystem);
//		kb.buildAll();// 7
//		if (kb.getResults().hasMessages(Level.ERROR)) {
//			logger.error("Build Errors:\n"+kb.getResults().toString());
//		}
//		KieContainer kContainer = getKieService().newKieContainer(
//				getKieService().getRepository().getDefaultReleaseId());
//		this.setkContainer(kContainer);
//	}
}
