package com.tower.service.reflection;

import com.tower.service.reflection.factory.DefaultObjectFactory;
import com.tower.service.reflection.factory.ObjectFactory;
import com.tower.service.reflection.wrapper.DefaultObjectWrapperFactory;
import com.tower.service.reflection.wrapper.ObjectWrapperFactory;

/**
 * @author Clinton Begin
 */
public class SystemMetaObject {

  public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
  public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
  public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

  private static class NullObject {
  }
  
  public static MetaObject forObject(Object object) {
    return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
  }
  
}