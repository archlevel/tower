package com.tower.service.dao.ibatis;

import org.apache.ibatis.session.SqlSessionFactory;

import com.tower.service.cache.IModel;
import com.tower.service.dao.IBatchDAO;
import com.tower.service.dao.IDAO;

/**
 * ibatis 操作接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IBatisDAO<T> extends IDAO<T>,IBatchDAO<T> {

  public Class<? extends IMapper<T>> getMapperClass();

  public Class<? extends IModel> getModelClass();

  public SqlSessionFactory getMasterSessionFactory();

  public SqlSessionFactory getSlaveSessionFactory();

  public SqlSessionFactory getMapQuerySessionFactory();
  
  public void validate(IModel model);
  /**
   * 该属性名是否是外键属性
   * 
   * @param property
   * @return
   */
  public boolean isFk(String property);
  
  public int getVersion();
}
