package com.tower.service.dao;

import java.util.List;
import java.util.Map;

import com.tower.service.cache.ICacheable;

/**
 * 数据访问层基础接口
 * 
 * @todo 建议添加一个nocache访问接口［for关键性业务］
 * @author alexzhu
 *
 * @param <T>
 */
public interface IDAO<T> extends ICacheable<T> {

  /**
   * 根据map条件删除业务对象，返回影响记录数
   * 
   * @param params
   *          删除条件
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer deleteByMap(Map<String, Object> params, String tabNameSuffix);

  /**
   * 根据条件更新业务对象，返回影响纪录数
   * 
   * @param new_
   *          更新后值
   * @param cond
   *          更新条件
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer updateByMap(Map<String, Object> new_, Map<String, Object> cond,
      String tabNameSuffix);

  /**
   * 根据条件从slave中查询纪录列表
   * 
   * @param params
   *          查询条件
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> queryByMap(Map<String, Object> params, String tabNameSuffix);

  /**
   * 
   * @param params
   *          查询条件
   * @param orders
   *          有对象格式的字符串eg：name asc,age desc
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> queryByMap(Map<String, Object> params, String orders, String tabNameSuffix);

  /**
   * 根据条件查询纪录id列表
   * 
   * @param params
   *          查询条件
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List queryIdsByMap(Map<String, Object> params, String tabNameSuffix);

  /**
   * 
   * @param params
   *          查询条件
   * @param orders
   *          有对象格式的字符串eg：name asc,age desc
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List queryIdsByMap(Map<String, Object> params, String orders, String tabNameSuffix);

  /**
   * 根据条件查询纪录列表
   * 
   * @param params
   *          查询条件
   * @param master
   *          是否从master查询,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> queryByMap(Map<String, Object> params, Boolean master, String tabNameSuffix);

  /**
   * 
   * @param params
   *          查询条件
   * @param orders
   *          有对象格式的字符串eg：name asc,age desc
   * @param mastermaster
   *          是否从master查询,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> queryByMap(Map<String, Object> params, String orders, Boolean master,
      String tabNameSuffix);

  /**
   * 根据条件查询纪录id列表
   * 
   * @param params
   *          查询条件
   * @param master
   *          是否在master中查询,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List queryIdsByMap(Map<String, Object> params, Boolean master, String tabNameSuffix);

  /**
   * 
   * @param params
   *          查询条件
   * @param orders
   *          有对象格式的字符串eg：name asc,age desc
   * @param master
   *          是否在master中查询,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List queryIdsByMap(Map<String, Object> params, String orders, Boolean master,
      String tabNameSuffix);

  /**
   * 从slave中通过条件进行统计记录数
   * 
   * @param params
   *          查询条件
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer countByMap(Map<String, Object> params, String tabNameSuffix);

  /**
   * 通过条件进行统计记录数
   * 
   * @param params
   *          查询条件
   * @param master
   *          是否在master中查询,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer countByMap(Map<String, Object> params, Boolean master, String tabNameSuffix);

  /**
   * 根据条件从slave中分页查询纪录列表
   * 
   * @param params
   *          查询条件
   * @param page
   *          第几页 从1开始，即必须大于等于1
   * @param size
   *          页面纪录最大数量
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> pageQuery(Map<String, Object> params, int page, int size, String tabNameSuffix);

  /**
   * 
   * @param params
   *          查询条件
   * @param page
   *          第几页 从1开始，即必须大于等于1
   * @param size
   *          页最大记录数
   * @param master
   *          是否在master中查询 ,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> pageQuery(Map<String, Object> params, int page, int size, Boolean master,
      String tabNameSuffix);

  /**
   * 
   * 必须在子类中实现
   * 
   * @param params
   *          查询条件
   * @param page
   *          第几页 从1开始，即必须大于等于1
   * @param size
   *          页最大记录数
   * @param orders
   *          有对象格式的字符串eg：name asc,age desc
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> pageQuery(Map<String, Object> params, int page, int size, String orders,
      String tabNameSuffix);

  /**
   * 必须在子类中实现
   * 
   * @param params
   *          查询条件
   * @param page
   *          第几页 从1开始，即必须大于等于1
   * @param size
   *          页最大记录数
   * @param orders
   *          有对象格式的字符串eg：name asc,age desc
   * @param master
   *          是否在master中查询 ,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public List<T> pageQuery(Map<String, Object> params, int page, int size, String orders,
      Boolean master, String tabNameSuffix);

  /**
   * 获取实践操作的表对象名
   * 
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public String get$TowerTabName(String tabNameSuffix);

  /**
   * 获取表名
   * 
   * @return
   */
  public String getTableName();
}
