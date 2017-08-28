/**
 * 
 */
package com.tower.service.rule.impl;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

import com.tower.service.rule.IEngine;

/**
 * @author alexzhu
 * 
 */
public abstract class TimeDynamicEngine extends DynamicEngine implements IEngine {
	
	public TimeDynamicEngine(){
		super();
	}
	
	private long pollingInterval=10000L;
	
	public long getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(long pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public void refresh(){
		KieServices kieServices = getKieService();
		ReleaseId releaseId = kieServices.newReleaseId( this.getGroupId(), this.getArtifactId(), this.getVersion() );
		KieContainer kContainer = kieServices.newKieContainer( releaseId );
		KieScanner kScanner = kieServices.newKieScanner( kContainer );
		// Start the KieScanner polling the Maven repository every 10 seconds
		kScanner.start( this.getPollingInterval() );
	}
}
