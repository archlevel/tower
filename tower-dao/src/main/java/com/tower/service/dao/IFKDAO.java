package com.tower.service.dao;

import java.util.List;
import java.util.Map;

import com.tower.service.cache.ICacheable;

/**
 * 以外健操作的数据访问层接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IFKDAO<T> extends ICacheable<T> {

  /**
   * 该属性名是否是外键属性
   * 
   * @param property model bean属性名
   * @return
   */
  public boolean isFk(String property);

  /**
   * 通过外键property及其fkValue删除对象
   * 
   * @param property
   *          model bean属性名
   * @param fkValue
   *          对象属性值
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer deleteByFK(String property, Object fkValue, String tabNameSuffix);

  /**
   * 
   * @param property
   *          model bean属性名
   * @param fkValue
   *          外键值
   * @param attchParams
   *          附加参数
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer deleteByFK(String property, Object fkValue, Map<String, Object> attchParams,
      String tabNameSuffix);

  /**
   * 通过外键property及其fkValue更新对象
   * 
   * @param property
   *          model bean属性名
   * @param fkValue
   *          外键值
   * @param newValue
   *          新属性值
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer updateByFK(String property, Object fkValue, Map<String, Object> newValue,
      String tabNameSuffix);

  /**
   * 
   * 通过外键property及其fkValue更新对象
   * 
   * @param property
   *          model bean属性名
   * @param fkValue
   *          外键值
   * @param attchParams
   *          附加参数
   * @param newValue
   *          新属性值
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer updateByFK(String property, Object fkValue, Map<String, Object> attchParams,
      Map<String, Object> newValue, String tabNameSuffix);

  /**
   * 通过外键property及其fkValue查询对象，默认从slave中查询
   * 
   * @param property
   *          model bean属性名
   * @param fkValue
   *          外键值
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> queryByFK(String property, Object fkValue, String tabNameSuffix);

  /**
   * 
   * 通过外键property及其fkValue查询对象，默认从slave中查询
   * 
   * @param property
   *          model bean属性名
   * @param fkValue
   *          外键值
   * @param attchParams
   *          附加参数
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> queryByFK(String property, Object fkValue, Map<String, Object> attchParams,
      String tabNameSuffix);

  /**
   * 通过外键property及其fkValue查询对象
   * 
   * @param property
   *          model bean属性名
   * @param fkValue
   *          外键值
   * @param master
   *          是否从master中查询,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> queryByFK(String property, Object fkValue, Boolean master, String tabNameSuffix);

  /**
   * 通过外键property及其fkValue查询对象
   * 
   * @param property
   *          model bean属性名
   * @param fkValue
   *          外键值
   * @param attchParams
   *          附加参数
   * @param master
   *          是否从master中查询,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> queryByFK(String property, Object fkValue, Map<String, Object> attchParams,
      Boolean master, String tabNameSuffix);
}
