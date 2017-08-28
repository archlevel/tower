package com.tower.service.dao.generate.tool;

public class OracleServerType extends DataTypeMap {
  
  protected void init() {
    add(new Type("CHAR", "String"));
    add(new Type("VARCHAR2", "String"));
    add(new Type("LONG", "String"));
    add(new Type("NUMBER", "java.math.BigDecimal"));
    add(new Type("NUMBER(19,0)", "Long"));
    add(new Type("RAW", "byte[]"));
    add(new Type("BLOB RAW", "byte[]"));
    add(new Type("CLOB RAW", "String"));
    add(new Type("DATE", "java.sql.Timestamp"));
    add(new Type("FLOAT(24)", "java.math.BigDecimal"));
    add(new Type("FLOAT", "Float"));
    add(new Type("NUMBER(10,0)", "Integer"));
    add(new Type("NUMBER(7,0)", "Integer"));
    add(new Type("NUMBER(5,0)", "Integer"));
    add(new Type("VARCHAR2 CLOB", "String"));
    add(new Type("NUMBER(3,0)", "Boolean"));
    add(new Type("OTHER", "String"));
  }

}
