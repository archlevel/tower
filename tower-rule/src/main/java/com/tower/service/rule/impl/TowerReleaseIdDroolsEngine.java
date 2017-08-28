package com.tower.service.rule.impl;

/**
 * Created by lvli on 16/7/20.
 */
public class TowerReleaseIdDroolsEngine extends  TowerDroolsEngine{

    public TowerReleaseIdDroolsEngine(String groupId,String artifactId, String version){
        super();
        init(groupId,artifactId,version);
    }

    public void init(String groupId,String artifactId, String version){
        this.setGroupId(groupId);
        this.setArtifactId(artifactId);
        this.setVersion(version);
        this.setkContainer(getKieService().newKieContainer(getKieService().newReleaseId(groupId, artifactId, version)));
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
