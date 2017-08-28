package com.tower.service.config;

import org.apache.commons.configuration.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class ConfigurationFactoryBean implements InitializingBean, FactoryBean<Configuration>, ApplicationContextAware {

    @Override
    public Configuration getObject() throws Exception {
        return configuration;
    }

    @Override
    public Class<?> getObjectType() {
        return Configuration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private Configuration configuration;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        _context = applicationContext;
    }

    private ApplicationContext _context;

    @Override
    public void afterPropertiesSet() throws Exception {
        
        Assert.hasLength(_name);
        DynamicConfig config = new DynamicConfig();
        config.setFileName(_name);
        config.setType(type);
        config.setEncoding(encoding);
        config.setClasspath(classpath);
        config.init();
        configuration = config;
    }

    private String classpath = "META-INF/config/local/";

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public void setName(String value) {
        _name = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setThrowExceptionOnMissing(boolean value) {
        _throwExceptionOnMissing = value;
    }

    private String _name;
    private String encoding="utf-8";
    private String type="properties";
    
    private boolean _throwExceptionOnMissing = false;
    private transient static Logger _logger = LoggerFactory.getLogger(ConfigurationFactoryBean.class);
}
