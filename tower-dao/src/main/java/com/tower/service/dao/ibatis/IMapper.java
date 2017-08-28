package com.tower.service.dao.ibatis;

import java.util.List;
import java.util.Map;

import com.tower.service.dao.MapPage;
import com.tower.service.dao.Page;

/**
 * ibatis 基础数据访问接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IMapper<T> extends IBatchMapper<T> {

  public Integer deleteByMap(Map<String, Object> params);

  public Integer updateById(Map<String, Object> params);

  public Integer cmplxUpdate(Map<String, Object> params);

  public List<T> queryByMap(Map<String, Object> params);

  public List<String> queryIdsByMap(Map<String, Object> params);

  public List<T> pageQuery(MapPage<Map<String, Object>> params);

  public List<T> pageQuery(Page<T> cmd);

  public Integer countByMap(Map<String, Object> params);

}
