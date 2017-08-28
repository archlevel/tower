package com.tower.service.job.elastic.impl;

import java.util.List;

import com.dangdang.ddframe.job.api.JobExecutionSingleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.dataflow.AbstractIndividualSequenceDataFlowElasticJob;
import com.tower.service.job.IJob;
import com.tower.service.job.JobException;

public abstract class AbsElasticSequenceDataFlow<T> extends AbstractIndividualSequenceDataFlowElasticJob<T> implements IJob<T>{

	@Override
	public List<T> fetchData(JobExecutionSingleShardingContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean processData(JobExecutionSingleShardingContext arg0, T arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void before() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doProcess(T data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(JobException ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccessed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after() {
		// TODO Auto-generated method stub
		
	}


}
