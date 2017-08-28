package com.tower.service.dao.ibatis;

import com.tower.service.cache.IModel;
import com.tower.service.dao.IBIDAO;

/**
 * id 为long型的数据访问接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IBigIBatisDAO<T extends IModel> extends IBatisDAO<T>, IBIDAO<T> {

  public Class<? extends IBigIMapper<T>> getMapperClass();

}
