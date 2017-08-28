package com.tower.service.cache;

import java.io.Serializable;

/**
 * 所有数据库持久化对象的父类，其子类的每个属性必须含有
 * 
 * @author alexzhu
 *
 */
public interface IModel extends Serializable {

  /**
   * 验证接口
   * 
   * @return
   */
  public boolean validate();
  
  public void setTowerTabName(String TowerTabName);
}
