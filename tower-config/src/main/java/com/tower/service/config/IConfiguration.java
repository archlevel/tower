package com.tower.service.config;

public interface IConfiguration {
    
    String getString(String key);

    Object getProperty(String key);
}
