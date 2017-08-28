package com.tower.service.job.impl;

import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tower.service.job.DataProcessException;
import com.tower.service.job.IJob;
import com.tower.service.job.JobException;
import com.tower.service.job.JobExecuteException;
import com.tower.service.log.LogUtils;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.Request;

public abstract class AbsClusterJob<T> extends QuartzJobBean implements IJob<T> {
	protected JobDataMap dataMap = null;
	private String id;
	private int successed;
	private int failed;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AbsClusterJob(String id) {
		this.id = id;
	}

	@Override
	public void before() {

	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		dataMap = context.getJobDetail().getJobDataMap();
		start();
	}

	final public void start() {
		if (logger.isInfoEnabled()) {
			logger.info("start() - start"); //$NON-NLS-1$
		}

		List<T> datas = null;
		long start = System.currentTimeMillis();
		long tmpStart = start;
		int total = 0;
		try {
			Request.setId(null);
			before();
			datas = execute();
			if (logger.isInfoEnabled()) {
				LogUtils.timeused(logger, "execute", tmpStart);
			}
			total = datas == null ? 0 : datas.size();
			for (int i = 0; i < total; i++) {
				T data = datas.get(i);
				try {
					tmpStart = System.currentTimeMillis();
					this.doProcess(data);
					if (logger.isInfoEnabled()) {
						LogUtils.timeused(logger, "doProcess", tmpStart);
					}
					this.increaseSuccessNum();
				} catch (Exception ex) {
					logger.error("start()", ex); //$NON-NLS-1$
					this.increaseErrorNum();
					this.logger.error("error process: " + data.toString());
					this.onError(new DataProcessException(ex));
				}
			}
			this.onSuccessed();
			after();
		} catch (DataProcessException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("start()", ex); //$NON-NLS-1$

			this.onError(new JobExecuteException(ex));
		} finally {
			if (logger.isInfoEnabled()) {
				LogUtils.timeused(logger, "doProcess", start);
			}
			logger.info("start() - end total={},success={},failed={}", total,
					this.getSuccessed(), this.getFailed());
			if (logger.isInfoEnabled()) {
				logger.info("start() - end"); //$NON-NLS-1$
			}
		}
	}

	public void onError(JobException ex) {
		if (logger.isInfoEnabled()) {
			logger.info("onError(Exception ex={}) - start", ex); //$NON-NLS-1$
		}
		throw ex;
	}

	protected void increaseErrorNum() {
		failed++;
	}

	protected void failedReset() {
		failed = 0;
	}

	protected void successedReset() {
		successed = 0;
	}

	protected void increaseSuccessNum() {
		successed++;
	}

	final public int getFailed() {
		return failed;
	}

	public void onSuccessed() {
		if (logger.isInfoEnabled()) {
			logger.info("onSuccessed()");
		}
	}

	final public int getSuccessed() {
		return successed;
	}

	@Override
	public void after() {

	}

	public abstract List<T> execute();
}
