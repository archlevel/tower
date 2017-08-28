package com.tower.service.dao.generate.tool;

import java.util.HashMap;
import java.util.Map;

public class MySqlType extends DataTypeMap {
  
  protected void init() {
    add(new Type("VARCHAR", "String"));
    add(new Type("CHAR", "String"));
    add(new Type("BLOB", "byte[]"));
    add(new Type("TEXT", "String"));

    add(new Type("INT", "Integer"));
    add(new Type("INT UNSIGNED", "Integer"));

    add(new Type("INTEGER", "Long"));
    add(new Type("INTEGER UNSIGNED", "Long"));

    add(new Type("TINYINT", "Integer"));
    add(new Type("TINYINT UNSIGNED", "Integer"));

    add(new Type("SMALLINT", "Integer"));
    add(new Type("SMALLINT UNSIGNED", "Integer"));

    add(new Type("MEDIUMINT", "Integer"));
    add(new Type("MEDIUMINT UNSIGNED", "Integer"));

    add(new Type("FLOAT", "Float"));
    add(new Type("FLOAT UNSIGNED", "Float"));

    add(new Type("DOUBLE", "Double"));

    add(new Type("BIT", "Boolean"));
    add(new Type("BIGINT", "java.math.BigInteger"));
    add(new Type("DECIMAL", "java.math.BigDecimal"));
    add(new Type("BOOLEAN", "Long"));
    add(new Type("DATE", "java.sql.Date"));
    add(new Type("TIME", "java.sql.Time"));
    add(new Type("DATETIME", "java.sql.Timestamp"));
    add(new Type("TIMESTAMP", "java.sql.Timestamp"));
    add(new Type("YEAR", "java.sql.Date"));
    add(new Type("OTHER", "String"));
  }

}
