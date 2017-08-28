/**
 * 
 */
package com.tower.service.rule.impl;

import com.tower.service.rule.IEngine;

/**
 * @author alexzhu
 * 
 */
public abstract class ManualDynamicEngine<T extends TowerDroolsSession> extends
		DynamicEngine implements IEngine {

	public ManualDynamicEngine() {
		super();
		init();
	}

	protected void init() {
		try {
			this.setkContainer(getKieService().newKieContainer(
					getKieService().newReleaseId(this.getGroupId(),
							this.getArtifactId(), this.getVersion())));
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(
					"fail to get maven rules kmodule from groupId: [");
			sb.append(getGroupId()).append("] artifactId: [")
					.append(getArtifactId()).append("] version: [")
					.append(getVersion()).append("]");
			this.logger.error(sb.toString());
			// throw new NotExistsException(sb.toString());}
		}
	}

	public void refresh() {
		try {
			this.getContainer().updateToVersion(
					getKieService().newReleaseId(this.getGroupId(),
							this.getArtifactId(), this.getVersion()));
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(
					"fail to get maven rules kmodule from groupId: [");
			sb.append(getGroupId()).append("] artifactId: [")
					.append(getArtifactId()).append("] version: [")
					.append(getVersion()).append("]");
			this.logger.error(sb.toString());
			// throw new NotExistsException(sb.toString());  
		}

	}
}
