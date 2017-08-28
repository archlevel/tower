package com.tower.service.gen;

import java.io.File;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;

public class TemplateFactoryBean implements FactoryBean {

  private Configuration configuration;

  private String path;

  @Override
  public Object getObject() throws Exception {
    configuration = new Configuration();
    Assert.hasLength(path);
    if (path.startsWith("file:")) {
      path = path.substring(5);
      configuration.setTemplateLoader(new FileTemplateLoader(new File(path)));
    } else if (path.startsWith("classpath:")) {
      path = path.substring(10);
      configuration.setTemplateLoader(new ClassTemplateLoader(TemplateFactoryBean.class, path));
    }
    return configuration;
  }

  @Override
  public Class getObjectType() {
    return Configuration.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  public void setPath(String path) {
    this.path = path;
  }

}
