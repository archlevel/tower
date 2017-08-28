package com.tower.service.dao.ibatis;

import org.apache.ibatis.session.SqlSessionFactory;

import com.tower.service.dao.IHelpperDAO;

/**
 * ibatis 操作接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IBatisHelpperDAO<T> extends IHelpperDAO<T> {

  public Class<? extends IHelpperMapper<T>> getMapperClass();

  public SqlSessionFactory getMasterSessionFactory();

  public SqlSessionFactory getMapQuerySessionFactory();

  public void validate(T model);

}
