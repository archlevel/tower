package com.tower.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.container.spring.SpringContainer;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.monitor.IMonitorService;

public class TowerServiceContainer {
	private SpringContainer container = null;
	public static String SERVICE_ID;
	static ClassPathXmlApplicationContext context;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public TowerServiceContainer(String id,String location) {
		SERVICE_ID = id;
		String tmpLocation = (location == null ? "classpath*:META-INF/config/spring/spring-service.xml": location);
		System.setProperty("dubbo.spring.config",tmpLocation);
		container = new SpringContainer();
	}

	public void start() {
		try {
			container.start();
			context = SpringContainer.getContext();
			initMonitor();
		} catch (Exception ex) {
			logger.error("初始化出错", ex);
		}
	}

	private void initMonitor() {
		try {
			final IMonitorService monitorService = (IMonitorService) context
					.getBean("monitorService");
			if (monitorService != null) {
				monitorService.enroll(SERVICE_ID);
				new Thread() {
					public void run() {
						while (true) {
							try {
								monitorService.heartbeat(SERVICE_ID);
								sleep(1000 * 60);
							} catch (InterruptedException e) {
							}
						}
					};
				}.start();
			}
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
	}

	public static <T> T getBean(Class<T> requiredType) {
		T bean = null;
		try{
			bean = context.getBean(requiredType);
		}
		catch(Exception ex){
		}
		return bean;
	}

	public ClassPathXmlApplicationContext getContext() {
		return context;
	}
}
