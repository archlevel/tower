package com.tower.service.dao.ibatis;

import java.math.BigInteger;

import com.tower.service.cache.IModel;

/**
 * 主键为长整型的 ibatis 数据访问接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IBigIMapper<T extends IModel> extends IMapper<T>,IBatchMapper<T> {

  public T queryById(BigInteger id);

  public Long insert(IModel params);

}
