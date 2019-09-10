package com.tower.service.job.elastic.impl;

import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.tower.service.job.IJob;
import com.tower.service.job.JobException;

public abstract class AbsElastic extends AbstractSimpleElasticJob implements IJob{


    @Override
    public void onError(JobException ex) {

    }
}
