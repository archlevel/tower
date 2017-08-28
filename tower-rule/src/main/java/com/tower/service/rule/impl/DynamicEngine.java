/**
 * 
 */
package com.tower.service.rule.impl;

import com.tower.service.rule.IEngine;

/**
 * @author alexzhu
 * 
 */
public abstract class DynamicEngine extends TowerDroolsEngine implements IEngine {
	
	private String groupId;
	private String artifactId;
	private String version;
	
	public DynamicEngine(){
		super();
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
	
}
