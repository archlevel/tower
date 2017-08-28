package com.tower.service.job.impl;

import java.util.List;

import com.tower.service.job.DataProcessException;
import com.tower.service.job.GetPageException;
import com.tower.service.job.IPageableJob;
import com.tower.service.job.PageLoadException;
import com.tower.service.log.LogUtils;

/**
 * 所有分页处理job必须实现该类<br>
 * 该类有个线程负责提交监控数据<br>
 * 该线程会提交正常量、出错量、性能、及心跳数据到监控中心
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public abstract class AbsPageableJob<T> extends JobBase<T> implements IPageableJob<T> {

    public AbsPageableJob() {
        super();
    }
    
    public AbsPageableJob(String id) {
        super(id);
    }

    private int pageIdx=1;
    /**
     * 获取 页面序号，从1开始
     * @return
     */
    public int getPageIdx(){
        return pageIdx;
    }
    private int pageSize=50;
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
    
    public int getPageSize(){
        return pageSize;
    }
    
    final public void doProcess() {
        
        pageIdx = 1;
        this.failedReset();
        this.successedReset();
        
        long start = System.currentTimeMillis();
        long tmpStart = start;
        int pages = 0;
        try {
            pages = this.getPages();
            if (logger.isInfoEnabled()) {
                LogUtils.timeused(logger, "getPages()", tmpStart);
            }
            for (int i = 0; i < pages; i++) {
            	
            	pausedCheck("running");
            	
                pageProcess();
                pageIdx++;
            }
            this.onSuccessed();
        } catch (PageLoadException ex) {
            throw ex;
        } catch (DataProcessException ex) {
            throw ex;
        } catch (Exception ex) {
            LogUtils.error(logger, ex);
            this.onError(new GetPageException(ex));
        } finally {
            logger.info(
                    "start() - end totalPage={},pageProcessed={},totalProcessed={},successProcessed={},failedProcessed={} timeused={}",
                    pages, pageIdx-1, (this.getSuccessed() + this.getFailed()),
                    this.getSuccessed(), this.getFailed(),System.currentTimeMillis()-start);
        }
    }

    private void pageProcess() {
        List<T> pageDatas = null;
        long start = System.currentTimeMillis();
        long tmpStart = start;
        try {
            pageDatas = this.pageLoad();
            if (logger.isInfoEnabled()) {
                LogUtils.timeused(logger, "pageLoad", tmpStart);
            }
            
            this.pageDataProcess(pageDatas);
            
        } catch (DataProcessException ex) {
            throw ex;
        } catch (Exception ex) {
            LogUtils.error(logger, ex);
            this.onError(new PageLoadException(ex));
        } finally {
            if (logger.isInfoEnabled()) {
                LogUtils.timeused(logger, "pageProcess", tmpStart);
            }
        }
    }

    private void pageDataProcess(List<T> datas) {

        int total = datas == null ? 0 : datas.size();
        long start = System.currentTimeMillis();
        long tmpStart = start;
        try {
            for (int i = 0; i < total; i++) {
                T data = datas.get(i);
                try {
                    tmpStart = System.currentTimeMillis();
                    doProcess(data);
                    this.increaseSuccessNum();
                    if (logger.isInfoEnabled()) {
                        LogUtils.timeused(logger, "doProcess", tmpStart);
                    }
                } catch (Exception ex) {
                    LogUtils.error(logger, ex);
                    this.increaseErrorNum();
                    if (logger.isInfoEnabled()) {
                        LogUtils.timeused(logger, "doProcess", tmpStart);
                    }
                    this.logger.error("error process: "+data.toString());
                    this.onError(new DataProcessException(ex));
                }
            }
            batch();
        } catch (DataProcessException ex) {
            throw ex;
        } finally {
            if (logger.isInfoEnabled()) {
                LogUtils.timeused(logger, "pageDataProcess", start);
            }
        }
    }
    /**
     * 一页数据处理完成之后，后续业务扩展方法
     */
    public void batch(){
    }
}
