package com.tower.service.config.dict;

public interface ConfigComponent {
    /**
     * cache.xml 配置文件读取
     */
    public static final String CacheConfig = "CacheConfig";
    
    //public static final String CacheConfigDisableDelimiter = "CacheConfigDisableDelimiter";
    /**
     * 数据访问层配置 acc.xml
     */
    public static final String AccConfig = "AccConfig";
    
    //public static final String AccConfigDisableDelimiter = "AccConfigDisableDelimiter";
    
    /**
     * job配置 job.xml
     */
    public static final String JobConfig = "JobConfig";
    
    //public static final String JobConfigDisableDelimiter = "JobConfigDisableDelimiter";
    /**
     * mq相关配置 mq.xml
     */
    public static final String MQConfig = "MQConfig";
    
    //public static final String MQConfigDisableDelimiter = "MQConfigDisableDelimiter";
    /**
     * rpc相关配置 rpc.xml
     */
    public static final String RpcConfig = "RpcConfig";
    
    //public static final String RpcConfigDisableDelimiter = "RpcConfigDisableDelimiter";
    /**
     * service相关配置 service.xml
     */
    public static final String ServiceConfig = "ServiceConfig";
    
    //public static final String ServiceConfigDisableDelimiter = "ServiceConfigDisableDelimiter";
    /**
     * web层相关配置 webapp.xml
     */
    public static final String WebAppConfig = "WebAppConfig";
    
    //public static final String WebAppConfigDisableDelimiter = "WebAppConfigDisableDelimiter";
}
