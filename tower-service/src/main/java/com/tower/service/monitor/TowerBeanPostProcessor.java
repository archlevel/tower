package com.tower.service.monitor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

@Component("towerBeanPostProcessor")
public class TowerBeanPostProcessor implements BeanPostProcessor{

	private Logger logger = LoggerFactory.getLogger(TowerBeanPostProcessor.class);
	private long start = 0L;
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		start = System.currentTimeMillis();
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		logger.info(beanName+" init timeused: "+(System.currentTimeMillis()-start));
		return bean;
	}

}
