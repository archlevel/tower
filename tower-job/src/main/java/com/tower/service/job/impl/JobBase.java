package com.tower.service.job.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.InitializingBean;

import com.tower.service.TowerServiceContainer;
import com.tower.service.cache.CacheSwitcher;
import com.tower.service.config.IConfigChangeListener;
import com.tower.service.job.IJob;
import com.tower.service.job.IListener;
import com.tower.service.job.JobException;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.reflection.MetaObject;
import com.tower.service.reflection.factory.DefaultObjectFactory;
import com.tower.service.util.Request;

public abstract class JobBase<T> implements IJob<T>,IConfigChangeListener,InitializingBean{
    
	private static Thread monitor = null;
	private static AtomicInteger runningCnt = new AtomicInteger(0);
	private JobConfig config;
	
    /**
     * Logger for this class
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String id;

    private int successed;

    private int failed;
    
    public JobBase() {
        this.id = this.getClass().getSimpleName();
    }

    public JobBase(String id) {
        this.id = id;
    }
    
	@PostConstruct
    public void init(){
        if(monitor==null){
        	monitor = new Thread() {
        		Logger logger = LoggerFactory.getLogger("monitor");
				@Override
				public void run() {
					while(true){
						long time=1000;
						String status = getStatus();
						if(isPaused()){
							time=5000;
						}
						if(isStoped()){
							if(runningCnt.get()==0){
								logger.info(TowerServiceContainer.SERVICE_ID+" has exited...");
								System.exit(1);
							}
						}
						
						logger.info("status:{},runningCnt:{}",status,runningCnt.get());
						
						try {
							sleep(time);
						} catch (InterruptedException e) {
						}
					}
				}
			};
			monitor.start();
        }
    }
    
    protected void increaseRunning(){
    	runningCnt.incrementAndGet();
    }
    
    protected void decreaseRunning(){
    	runningCnt.decrementAndGet();
    }
    
    protected  boolean running(){
    	return runningCnt.get()>0;
    }
    
    protected  boolean isStarted(){
        return "start".equalsIgnoreCase(getStatus());
    }
    protected  boolean isPaused(){
        return "pause".equalsIgnoreCase(getStatus());
    }
    protected  boolean isStoped(){
        return "stop".equalsIgnoreCase(getStatus());
    }
    /**
     * status:start/pause/stop
     * @return
     */
    protected String getStatus(){
    	return config.getString(TowerServiceContainer.SERVICE_ID,"status",defStatus);//优先job
    }
    
    protected String defStatus = "start";
    
    private List<IJob> beforeList = new ArrayList<IJob>();
    @Override
    public final void before() {
    	int size = beforeList==null?0:beforeList.size();
    	for(int i=0;i<size;i++){
    		beforeList.get(i).start();
    	}
    }
    
    public List<IJob> getBeforeList() {
		return beforeList;
	}

	public void setBeforeList(List<IJob> beforeList) {
		this.beforeList = beforeList;
	}

	private boolean newStart = false;
    
    public boolean isNewStart() {
		return newStart;
	}

	public void setNewStart(boolean newStart) {
		this.newStart = newStart;
	}

	public final void start() {
        
		CacheSwitcher.set(Boolean.valueOf(config.getString(config.getPrefix()+"X-Cached", System.getProperty("X-Cached","true"))));
    	Request.setId(null);
    	this.setNewStart(true);
    	
    	pausedCheck("starting");
    	
    	this.increaseRunning();
        
        try {
        	before();
        	doProcess();
        	after();
        }
        catch(Exception ex){
        	logger.error(ex);
        }
        finally{
        	try{
        		notifyFinished();
        	}
        	catch(Exception ex){}
            decreaseRunning();
            CacheSwitcher.unset();
            logger.info("finshed");
        }
    }
    
	protected void pausedCheck(String status){
    	while("pause".equalsIgnoreCase(this.getStatus())){
    		try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
			}
    		logger.info("{} has paused",status);
    	}
    }
	
    abstract public void doProcess();
    
    @Override
    public String getId() {
        return id;
    }

    public void onError(JobException ex) {
    	logger.info("onError(Exception ex={}) - start", ex); //$NON-NLS-1$
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
            logger.info("onSuccessed()"); //$NON-NLS-1$
        }
    }

    final public int getSuccessed() {
        return successed;
    }

    private JobDetail jobDetail;

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    private CronTrigger trigger;

    public void setTrigger(CronTrigger trigger) {
        this.trigger = trigger;
    }

    private Scheduler scheduler;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public synchronized void configChanged() {
    	if(trigger!=null){
    		String cronExpression = trigger.getCronExpression();
            String currentCronExpression = config.getString("CronExpression");
            if (currentCronExpression != null && currentCronExpression.trim().length() > 0
                    && !currentCronExpression.equalsIgnoreCase(cronExpression)) {
                boolean settingNull = false;
                if(jobDetail == null){
                    logger.info("jobDetail is null");
                    settingNull = true;
                }
                if(trigger == null){
                    logger.info("trigger is null");
                    settingNull = true;
                }
                if(scheduler == null){
                    logger.info("scheduler is null");
                    settingNull = true;
                }
                if(settingNull){
                    logger.info("updateCronTriggerExp 被忽略");
                    return;
                }
                this.updateCronTriggerExp(currentCronExpression);
            }
    	}
    	else{
    		logger.info("job {} 没有遵从job开发框架,{}",this.getClass().getSimpleName(),"updateCronTriggerExp 被忽略");
    	}
    }

    protected void updateCronTriggerExp(String expression) {
        if (logger.isInfoEnabled()) {
            logger.info("updateCronTriggerExp(String expression={}) - start", expression); //$NON-NLS-1$
        }
        
        Field field = null;
        try {
            MetaObject metaObject = DefaultObjectFactory.getMetaObject(trigger);
            metaObject.setValue("cronEx",new CronExpression(expression));
            unregist();
            regist();
        } catch (Exception e) {
            logger.error("updateCronTriggerExp(String)", e); //$NON-NLS-1$
        }

        if (logger.isInfoEnabled()) {
            logger.info("updateCronTriggerExp(String expression={}) - end", expression); //$NON-NLS-1$
        }
    }

    private void regist() {
        if (logger.isInfoEnabled()) {
            logger.info("regist() - start"); //$NON-NLS-1$
        }

        try {
            // 触发器
            ((CronTriggerImpl) trigger).setCronExpression(config.getString("CronExpression"));// 触发器时间设定
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            logger.error("regist()", e); //$NON-NLS-1$

            throw new RuntimeException(e);
        }

        if (logger.isInfoEnabled()) {
            logger.info("regist() - end"); //$NON-NLS-1$
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * 
     * @param jobName
     */
    private void unregist() {
        if (logger.isInfoEnabled()) {
            logger.info("unregist() - start"); //$NON-NLS-1$
        }

        try {
            TriggerKey triggerKey = trigger.getKey();
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(jobDetail.getKey());// 删除任务
        } catch (Exception e) {
            logger.error("unregist()", e); //$NON-NLS-1$

            throw new RuntimeException(e);
        }

        if (logger.isInfoEnabled()) {
            logger.info("unregist() - end"); //$NON-NLS-1$
        }
    }

    /**
     * 创建监控线程，实现每分钟进行提交监控数据
     */
    private void createMonitor() {
        /**
         * 
         */
    }
    private List<IJob> afterList = new ArrayList<IJob>();
    public List<IJob> getAfterList() {
		return afterList;
	}

	public void setAfterList(List<IJob> afterList) {
		this.afterList = afterList;
	}

	/**
     * 整个job执行完成后，后续业务扩展
     */
    @Override
    public final void after(){
    	int size = afterList==null?0:afterList.size();
    	for(int i=0;i<size;i++){
    		afterList.get(i).start();
    	}
    }
    protected void notifyFinished(){
    	int size = listeners==null?0:listeners.size();
    	for(int i=0;i<size;i++){
    		listeners.get(i).finished();
    	}
    }
    private List<IListener> listeners = new ArrayList<IListener>();

	public void setListeners(List<IListener> listeners) {
		this.listeners = listeners;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(config!=null){
			config.addChangeListener(this);
		}
	}
	
	public JobConfig getConfig() {
		return config;
	}

	public void setConfig(JobConfig config) {
		this.config = config;
	}
	
}
