package com.tower.service.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tower.service.cache.IModel;

/**
 * ibatis 基础数据访问接口
 * 
 * @author alexzhu
 *
 * @param 
 */
public interface IHelpperMapper<T> {

  public int countByHelpper(T helpper);

  public List<? extends IModel> queryByHelpper(T helpper);

  public int deleteByHelpper(T helpper);

  public int updateByHelpper(@Param("record") IModel record, @Param("helpper") T helpper);

  public int updateByHelpper(@Param("record") Map<String, Object> record,
      @Param("helpper") T helpper);
}
