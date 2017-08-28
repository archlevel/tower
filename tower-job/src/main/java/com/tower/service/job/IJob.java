package com.tower.service.job;


/**
 * 
 * @author alexzhu
 *
 * @param <T>
 * 
 */
public interface IJob<T> {
	/**
	 * job执行前调用
	 */
	public void before();

    /**
     * 获取job的名称，必须唯一
     * 
     * @return
     */
    public String getId();
    /**
     * job程序运行入口
     */
    public void start();

    /**
     * 单数据处理实现
     * 
     * @param data
     */
    public void doProcess(T data);
    
    /**
     * 当发生exception时，调用
     * @param ex
     */
    public void onError(JobException ex);
    
    /**
     * 当成功处理完时，调用
     */
    public void onSuccessed();
    
    /**
     * job执行后调用
     */
    public void after();

}
