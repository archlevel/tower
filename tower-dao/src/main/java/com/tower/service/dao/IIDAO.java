package com.tower.service.dao;

import java.util.Map;

import com.tower.service.cache.ICacheable;

/**
 * 
 * 主键为整型操作接口
 * 
 * 主键缓存(pk)<br>
 * 应用场景:根据主键查询时使用。<br>
 * 查询执行步骤:<br>
 * 1. select * from loupan_basic where loupan_id=?;<br>
 * 2. 是否存在缓存，mc缓存key拼接规则{表名}+{recVersion}+{主键ID},
 * 如果存在直接返回结果，否则查询数据库，设置缓存并返回结果。例如：loupan_basic表主键mc缓存key:loupan_basic##2##1<br>
 * 更新执行步骤:<br>
 * 1. update loupan_basic set loupan_name=? where loupan_id=?;<br>
 * 2. 删除外键缓存。(如何删除外键缓存，下文有说明)<br>
 * 3. 删除对应的主键缓存<br>
 * 4. 更新updated版本号，删除cache_tags表缓存<br>
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IIDAO<T> extends ICacheable<T>,IIBatchDAO<T> {
  /**
   * 持久化数据对象，返回当前对象的id
   * 
   * @param model 数据库bean对象
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer insert(T model, String tabNameSuffix);

  /**
   * 通过主键删除业务对象
   * 
   * @param id 主键值
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer deleteById(Integer id, String tabNameSuffix);

  /**
   * 通过id，更新业务对象
   * 
   * @param id 主键值
   * @param newValue
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public Integer updateById(Integer id, Map<String, Object> newValue, String tabNameSuffix);

  /**
   * 通过id查询对象，默认从slave中查询
   * 
   * @param id 主键值
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public T queryById(Integer id, String tabNameSuffix);

  /**
   * 通过id查询对象
   * 
   * @param id 主键值
   *         
   * @param master
   *          是否从master中查询,master＝true时从master库中查询,同时重新刷新缓存
   * @param tabNameSuffix
   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
   * @return
   */
  public T queryById(Integer id, Boolean master, String tabNameSuffix);

}
