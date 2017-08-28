package com.tower.service.monitor;


public interface IMonitorService {
	/**
	 * 注册服务
	 * @param id
	 */
	public void enroll(String id);
    /**
     * 发布心跳信息
     * @param monitors
     * @return
     */
    public void heartbeat(String id);
    
}
