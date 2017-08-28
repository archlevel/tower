package com.tower.service.dao.ibatis;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tower.service.config.PrefixPriorityConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileDict;
import com.tower.service.config.dict.ConfigFileTypeDict;

@Component(ConfigComponent.AccConfig)
public class AccConfig extends PrefixPriorityConfig {

  public AccConfig() {
  }

  @PostConstruct
  public void init(){
      setFileName(System.getProperty(ConfigFileDict.ACCESS_CONTROL_CONFIG_FILE,
          ConfigFileDict.DEFAULT_ACCESS_CONTROL_CONFIG_NAME));
      this.setType(ConfigFileTypeDict.XML);
      super.init();
  }

}
