package com.tower.service.reflection.wrapper;

import com.tower.service.reflection.MetaObject;

/**
 * @author Clinton Begin
 */
public interface ObjectWrapperFactory {

  boolean hasWrapperFor(Object object);
  
  ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
  
}
