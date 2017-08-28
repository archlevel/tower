package com.tower.service.dao.ibatis;

import com.tower.service.cache.IModel;

/**
 * 主键为整型的 ibatis 数据访问接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IIMapper<T extends IModel> extends IMapper<T>,IBatchMapper<T> {

  public T queryById(Integer id);

  public Long insert(T model);

}
