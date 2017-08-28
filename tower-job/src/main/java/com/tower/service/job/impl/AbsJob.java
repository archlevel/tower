package com.tower.service.job.impl;

import java.util.List;

import com.tower.service.job.DataProcessException;
import com.tower.service.job.INormalJob;
import com.tower.service.job.JobExecuteException;
import com.tower.service.log.LogUtils;

/**
 * 所有除分页job之外的job必须实现该类<br>
 * 该类有个线程负责提交监控数据<br>
 * 该线程会提交正常量、出错量、性能、及心跳数据到监控中心
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public abstract class AbsJob<T> extends JobBase<T> implements INormalJob<T> {

    public AbsJob() {
        super();

    }

    public AbsJob(String id) {
        super(id);
    }
    
    final public void doProcess() {

    	int processed = 0;
		long start = System.currentTimeMillis();
		try {
			while (true) {
				
				pausedCheck("running");
				
				List<T> datas = execute();
				int size = datas == null ? 0 : datas.size();
				if (size == 0) {
					break;
				}
				for (int i = 0; i < size; i++) {
					T data = datas.get(i);
					try {
						long tmpStart = System.currentTimeMillis();
						this.doProcess(data);
						LogUtils.timeused(logger, "doProcess", tmpStart);
						this.increaseSuccessNum();
					} catch (Exception ex) {
						logger.error("doProcess()", ex); //$NON-NLS-1$
						this.increaseErrorNum();
						this.logger.error("error process: " + data.toString());
						this.onError(new DataProcessException(ex));
					}
				}
				batch();
			}
		} catch (DataProcessException ex) {
			throw ex;
		} catch (Exception ex) {
			this.onError(new JobExecuteException(ex));
		} finally {
			logger.info("start() - end total={},success={},failed={} timeused={}",
					processed, processed, this.getFailed(),System.currentTimeMillis()-start);
		}
    }
    
    public void batch(){
    	
    }
}
