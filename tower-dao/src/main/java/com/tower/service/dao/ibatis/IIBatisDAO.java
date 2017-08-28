package com.tower.service.dao.ibatis;

import com.tower.service.cache.IModel;
import com.tower.service.dao.IIDAO;

public interface IIBatisDAO<T extends IModel> extends IBatisDAO<T>, IIDAO<T> {

  public Class<? extends IIMapper<T>> getMapperClass();

}
