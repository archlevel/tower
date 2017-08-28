package com.tower.service.web.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tower.service.TowerServiceContainer;

public class TowerApplicationContextFactoryBean implements InitializingBean,
		FactoryBean<ClassPathXmlApplicationContext>, ApplicationContextAware {

	private TowerServiceContainer container = null;

	public TowerApplicationContextFactoryBean(String id,String location) {
		container = new TowerServiceContainer(id,location);
		container.start();
		classPathXmlApplicationContext = container.getContext();
	}
	
	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	private ClassPathXmlApplicationContext classPathXmlApplicationContext;
	@Override
	public ClassPathXmlApplicationContext getObject() throws Exception {
		return classPathXmlApplicationContext;
	}

	@Override
	public Class<?> getObjectType() {
		return ClassPathXmlApplicationContext.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

}
