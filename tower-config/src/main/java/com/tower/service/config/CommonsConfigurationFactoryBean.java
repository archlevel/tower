package com.tower.service.config;

import java.net.URL;
import java.util.Properties;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class CommonsConfigurationFactoryBean implements InitializingBean, FactoryBean<Properties> {
    public CommonsConfigurationFactoryBean() {}

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Properties getObject() throws Exception {
        Properties props = (_configuration != null)
                ? ConfigurationConverter.getProperties(_configuration)
                : null;
        return props;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<?> getObjectType() {
        return java.util.Properties.class;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        if (_configuration == null && (_configurations == null || _configurations.length == 0)
                && (_locations == null || _locations.length == 0))
            throw new IllegalArgumentException("no configuration object or location specified");

        if (_configuration == null) _configuration = new CompositeConfiguration();

        _configuration.setThrowExceptionOnMissing(_throwExceptionOnMissing);

        if (_configurations != null) {
            for (int i = 0; i < _configurations.length; i++) {
                _configuration.addConfiguration(_configurations[i]);
            }
        }

        if (_locations != null) {
            for (int i = 0; i < _locations.length; i++) {
                try {
                    URL url = _locations[i].getURL();
                    Configuration props = new PropertiesConfiguration(url);
                    _configuration.addConfiguration(props);
                } catch (Exception e) {
                    if (_logger.isInfoEnabled()) {
                        _logger.info("Skip config '{}', {}", _locations[i], e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * @return Returns the configurations.
     */
    public Configuration[] getConfigurations() {
        return _configurations;
    }

    /**
     * Set the commons configurations objects which will be used as properties.
     *
     * @param configurations
     */
    public void setConfigurations(Configuration[] configurations) {
        this._configurations = configurations;
    }

    public Resource[] getLocations() {
        return _locations;
    }

    /**
     * Shortcut for loading configuration from Spring resources. It will internally create a
     * PropertiesConfiguration object based on the URL retrieved from the given Resources.
     *
     * @param locations
     */
    public void setLocations(Resource[] locations) {
        this._locations = locations;
    }

    public boolean isThrowExceptionOnMissing() {
        return _throwExceptionOnMissing;
    }

    /**
     * Set the underlying Commons CompositeConfiguration throwExceptionOnMissing flag.
     * 
     * @param throwExceptionOnMissing
     */
    public void setThrowExceptionOnMissing(boolean throwExceptionOnMissing) {
        this._throwExceptionOnMissing = throwExceptionOnMissing;
    }

    /**
     * Getter for the underlying CompositeConfiguration object.
     *
     * @return
     */
    public CompositeConfiguration getConfiguration() {
        return _configuration;
    }

    //

    private CompositeConfiguration _configuration;

    private Configuration[] _configurations;

    private Resource[] _locations;

    private boolean _throwExceptionOnMissing = true;
    
    private transient static Logger _logger = LoggerFactory.getLogger(CommonsConfigurationFactoryBean.class);
}
