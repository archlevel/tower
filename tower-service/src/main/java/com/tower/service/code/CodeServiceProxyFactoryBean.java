package com.tower.service.code;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * code 码表工具类
 * 
 * @author Administrator
 *
 */
public class CodeServiceProxyFactoryBean
        implements
            InitializingBean,
            FactoryBean<CodeServiceProxy>,
            ApplicationContextAware {

    private CodeServiceProxy proxy = null;

    @Override
    public CodeServiceProxy getObject() throws Exception {
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return CodeServiceProxy.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private CodeService codeServiceClient;

    public void setCodeServiceClient(CodeService codeService) {
        codeServiceClient = codeService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.proxy = new CodeServiceProxy();
        this.proxy.setCodeServiceClient(codeServiceClient);
    }
}
