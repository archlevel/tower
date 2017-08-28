package com.tower.service.dao.generate.tool;


public class Type {

  private String sqlType;
  private String javaType;
  private static DataTypeMap typeMap = null;
  
  static {
    //mysql
    if (DBSetting.isMysql()){
      typeMap = new MySqlType();
    }
    else{
    //sql server
      typeMap = new SqlServerType();
    }
  }

  public Type(String sqlType, String javaType) {
    this.sqlType = sqlType.toLowerCase();
    this.javaType = javaType;
  }

  public String getSqlType() {
    return sqlType;
  }

  public void setSqlType(String sqlType) {
    this.sqlType = sqlType;
  }

  public String getJavaType() {
    return javaType;
  }

  public void setJavaType(String javaType) {
    this.javaType = javaType;
  }

  public static void add(Type type) {
    typeMap.put(type.getSqlType(), type);
  }

  public static Type get(String type) {
    Type result = typeMap.getType(type);
    if (result == null) {
      result = typeMap.getType("other");
    }
    return result;
  }

}
