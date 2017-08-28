package com.tower.service.dao.generate.tool;

import java.util.HashMap;

public abstract class DataTypeMap extends HashMap {
  public DataTypeMap(){
    init();
  }
  
  protected abstract void init();
  
  protected void add(Type type) {
    put(type.getSqlType(), type);
  }

  public Type getType(String type) {
    Type result = null;
    result = (Type) get(type);
    if (result == null) {
      result = (Type) get("other");
    }
    return result;
  }
}
