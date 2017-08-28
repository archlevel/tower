package com.tower.service.dao;

import java.util.List;
import java.util.Map;

/**
 * 批处理数据访问层接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface ILBatchDAO<T> extends IBatchDAO<T>{
	/**
	   * @deprecated replace by @see
	   * public Integer batchInsert(List<String> cols,List<Map<String,Object>> datas, String tabNameSuffix);
	   * 批量插入
	   * 
	   * @param datas
	   * @param tabNameSuffix
	   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
	   * @return
	   */
	  public Long[] batchInsert(List<Map<String, Object>> datas, String tabNameSuffix);
	  /**
	   * 
	   * @param cols model属性集合
	   * @param datas 数据集合
	   * @param tabNameSuffix 表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
	   * @return
	   */
	  public Long[] batchInsert(List<String> cols,List<Map<String,Object>> datas, String tabNameSuffix);
	  
	  /**
	   * 
	   * @param cols
	   * @param datas
	   * @param tabNameSuffix
	   * @return
	   */
	  public Long[] batchInsert(String[] cols,List<Map<String,Object>> datas, String tabNameSuffix);
  
}
