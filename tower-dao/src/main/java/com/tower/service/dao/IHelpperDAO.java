package com.tower.service.dao;

import java.util.List;
import java.util.Map;

import com.tower.service.cache.IModel;

public interface IHelpperDAO<T> {
  
  void validate(T params);
  /**
   * 获取实践操作的表对象名
   * 
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public String get$TowerTabName(String tabNameSuffix);
  
  /**
   * 复杂count支持表达式
   * @param helpper 条件
   * @param tabNameSuffix 表名后缀
   * @return
   */
  public int countByHelpper(T helpper, String tabNameSuffix);
  
  /**
   * 复杂query支持表达式
   * @param helpper 条件
   * @param tabNameSuffix 表名后缀
   * @return
   */
  public List<? extends IModel> queryByHelpper(T helpper, String tabNameSuffix);

  /**
   * 复杂delete支持表达式
   * @param helpper 条件
   * @param tabNameSuffix 表名后缀
   * @return
   */
  public int deleteByHelpper(T helpper, String tabNameSuffix);

  /**
   * 复杂update支持表达式
   * @param record model结构
   * @param helpper 条件
   * @param tabNameSuffix 表名后缀
   * @return
   */
  public int updateByHelpper(IModel record, T helpper, String tabNameSuffix);
  /**
   * 复杂update支持表达式
   * @param record map结构
   * @param helpper 条件
   * @param tabNameSuffix 表名后缀
   * @return
   */
  public int updateByHelpper(Map<String, Object> record,T helpper, String tabNameSuffix);
}
