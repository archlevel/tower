package com.tower.service.dao.ibatis;

import com.tower.service.cache.IModel;
import com.tower.service.dao.ISDAO;

/**
 * id 为long型的数据访问接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface ISBatisDAO<T extends IModel> extends IBatisDAO<T>, ISDAO<T> {

  public Class<? extends ISMapper<T>> getMapperClass();

}
