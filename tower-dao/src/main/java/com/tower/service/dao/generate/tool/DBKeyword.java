package com.tower.service.dao.generate.tool;

import java.util.HashMap;
import java.util.Map;

public class DBKeyword {

  private String keyword;
  private static Map<String, DBKeyword> map = new HashMap<String, DBKeyword>();

  static {

    add(new DBKeyword("ADD"));
    add(new DBKeyword("ALL"));
    add(new DBKeyword("ALTER"));
    add(new DBKeyword("ANALYZE"));
    add(new DBKeyword("AND"));
    add(new DBKeyword("AS"));
    add(new DBKeyword("ASC"));
    add(new DBKeyword("ASENSITIVE"));
    add(new DBKeyword("BEFORE"));
    add(new DBKeyword("BETWEEN"));
    add(new DBKeyword("BIGINT"));
    add(new DBKeyword("BINARY"));
    add(new DBKeyword("BLOB"));
    add(new DBKeyword("BOTH"));
    add(new DBKeyword("BY"));
    add(new DBKeyword("CALL"));
    add(new DBKeyword("CASCADE"));
    add(new DBKeyword("CASE"));
    add(new DBKeyword("CHANGE"));
    add(new DBKeyword("CHAR"));
    add(new DBKeyword("CHARACTER"));
    add(new DBKeyword("CHECK"));
    add(new DBKeyword("COLLATE"));
    add(new DBKeyword("COLUMN"));
    add(new DBKeyword("CONDITION"));
    add(new DBKeyword("CONNECTION"));
    add(new DBKeyword("CONSTRAINT"));
    add(new DBKeyword("CONTINUE"));
    add(new DBKeyword("CONVERT"));
    add(new DBKeyword("CREATE"));
    add(new DBKeyword("CROSS"));
    add(new DBKeyword("CURRENT_DATE"));
    add(new DBKeyword("CURRENT_TIME"));
    add(new DBKeyword("CURRENT_TIMESTAMP"));
    add(new DBKeyword("CURRENT_USER"));
    add(new DBKeyword("CURSOR"));
    add(new DBKeyword("DATABASE"));
    add(new DBKeyword("DATABASES"));
    add(new DBKeyword("DAY_HOUR"));
    add(new DBKeyword("DAY_MICROSECOND"));
    add(new DBKeyword("DAY_MINUTE"));
    add(new DBKeyword("DAY_SECOND"));
    add(new DBKeyword("DEC"));
    add(new DBKeyword("DECIMAL"));
    add(new DBKeyword("DECLARE"));
    add(new DBKeyword("DEFAULT"));
    add(new DBKeyword("DELAYED"));
    add(new DBKeyword("DELETE"));
    add(new DBKeyword("DESC"));
    add(new DBKeyword("DESCRIBE"));
    add(new DBKeyword("DETERMINISTIC"));
    add(new DBKeyword("DISTINCT"));
    add(new DBKeyword("DISTINCTROW"));
    add(new DBKeyword("DIV"));
    add(new DBKeyword("DOUBLE"));
    add(new DBKeyword("DROP"));
    add(new DBKeyword("DUAL"));
    add(new DBKeyword("EACH"));
    add(new DBKeyword("ELSE"));
    add(new DBKeyword("ELSEIF"));
    add(new DBKeyword("ENCLOSED"));
    add(new DBKeyword("ESCAPED"));
    add(new DBKeyword("EXISTS"));
    add(new DBKeyword("EXIT"));
    add(new DBKeyword("EXPLAIN"));
    add(new DBKeyword("FALSE"));
    add(new DBKeyword("FETCH"));
    add(new DBKeyword("FLOAT"));
    add(new DBKeyword("FLOAT4"));
    add(new DBKeyword("FLOAT8"));
    add(new DBKeyword("FOR"));
    add(new DBKeyword("FORCE"));
    add(new DBKeyword("FOREIGN"));
    add(new DBKeyword("FROM"));
    add(new DBKeyword("FULLTEXT"));
    add(new DBKeyword("GOTO"));
    add(new DBKeyword("GRANT"));
    add(new DBKeyword("GROUP"));
    add(new DBKeyword("HAVING"));
    add(new DBKeyword("HIGH_PRIORITY"));
    add(new DBKeyword("HOUR_MICROSECOND"));
    add(new DBKeyword("HOUR_MINUTE"));
    add(new DBKeyword("HOUR_SECOND"));
    add(new DBKeyword("IF"));
    add(new DBKeyword("IGNORE"));
    add(new DBKeyword("IN"));
    add(new DBKeyword("INDEX"));
    add(new DBKeyword("INFILE"));
    add(new DBKeyword("INNER"));
    add(new DBKeyword("INOUT"));
    add(new DBKeyword("INSENSITIVE"));
    add(new DBKeyword("INSERT"));
    add(new DBKeyword("INT"));
    add(new DBKeyword("INT1"));
    add(new DBKeyword("INT2"));
    add(new DBKeyword("INT3"));
    add(new DBKeyword("INT4"));
    add(new DBKeyword("INT8"));
    add(new DBKeyword("INTEGER"));
    add(new DBKeyword("INTERVAL"));
    add(new DBKeyword("INTO"));
    add(new DBKeyword("IS"));
    add(new DBKeyword("ITERATE"));
    add(new DBKeyword("JOIN"));
    add(new DBKeyword("KEY"));
    add(new DBKeyword("KEYS"));
    add(new DBKeyword("KILL"));
    add(new DBKeyword("LABEL"));
    add(new DBKeyword("LEADING"));
    add(new DBKeyword("LEAVE"));
    add(new DBKeyword("LEFT"));
    add(new DBKeyword("LIKE"));
    add(new DBKeyword("LIMIT"));
    add(new DBKeyword("LINEAR"));
    add(new DBKeyword("LINES"));
    add(new DBKeyword("LOAD"));
    add(new DBKeyword("LOCALTIME"));
    add(new DBKeyword("LOCALTIMESTAMP"));
    add(new DBKeyword("LOCK"));
    add(new DBKeyword("LONG"));
    add(new DBKeyword("LONGBLOB"));
    add(new DBKeyword("LONGTEXT"));
    add(new DBKeyword("LOOP"));
    add(new DBKeyword("LOW_PRIORITY"));
    add(new DBKeyword("MATCH"));
    add(new DBKeyword("MEDIUMBLOB"));
    add(new DBKeyword("MEDIUMINT"));
    add(new DBKeyword("MEDIUMTEXT"));
    add(new DBKeyword("MIDDLEINT"));
    add(new DBKeyword("MINUTE_MICROSECOND"));
    add(new DBKeyword("MINUTE_SECOND"));
    add(new DBKeyword("MOD"));
    add(new DBKeyword("MODIFIES"));
    add(new DBKeyword("NATURAL"));
    add(new DBKeyword("NOT"));
    add(new DBKeyword("NO_WRITE_TO_BINLOG"));
    add(new DBKeyword("NULL"));
    add(new DBKeyword("NUMERIC"));
    add(new DBKeyword("ON"));
    add(new DBKeyword("OPTIMIZE"));
    add(new DBKeyword("OPTION"));
    add(new DBKeyword("OPTIONALLY"));
    add(new DBKeyword("OR"));
    add(new DBKeyword("ORDER"));
    add(new DBKeyword("OUT"));
    add(new DBKeyword("OUTER"));
    add(new DBKeyword("OUTFILE"));
    add(new DBKeyword("PRECISION"));
    add(new DBKeyword("PRIMARY"));
    add(new DBKeyword("PROCEDURE"));
    add(new DBKeyword("PURGE"));
    add(new DBKeyword("RAID0"));
    add(new DBKeyword("RANGE"));
    add(new DBKeyword("READ"));
    add(new DBKeyword("READS"));
    add(new DBKeyword("REAL"));
    add(new DBKeyword("REFERENCES"));
    add(new DBKeyword("REGEXP"));
    add(new DBKeyword("RELEASE"));
    add(new DBKeyword("RENAME"));
    add(new DBKeyword("REPEAT"));
    add(new DBKeyword("REPLACE"));
    add(new DBKeyword("REQUIRE"));
    add(new DBKeyword("RESTRICT"));
    add(new DBKeyword("RETURN"));
    add(new DBKeyword("REVOKE"));
    add(new DBKeyword("RIGHT"));
    add(new DBKeyword("RLIKE"));
    add(new DBKeyword("SCHEMA"));
    add(new DBKeyword("SCHEMAS"));
    add(new DBKeyword("SECOND_MICROSECOND"));
    add(new DBKeyword("SELECT"));
    add(new DBKeyword("SENSITIVE"));
    add(new DBKeyword("SEPARATOR"));
    add(new DBKeyword("SET"));
    add(new DBKeyword("SHOW"));
    add(new DBKeyword("SMALLINT"));
    add(new DBKeyword("SPATIAL"));
    add(new DBKeyword("SPECIFIC"));
    add(new DBKeyword("SQL"));
    add(new DBKeyword("SQLEXCEPTION"));
    add(new DBKeyword("SQLSTATE"));
    add(new DBKeyword("SQLWARNING"));
    add(new DBKeyword("SQL_BIG_RESULT"));
    add(new DBKeyword("SQL_CALC_FOUND_ROWS"));
    add(new DBKeyword("SQL_SMALL_RESULT"));
    add(new DBKeyword("SSL"));
    add(new DBKeyword("STARTING"));
    add(new DBKeyword("STRAIGHT_JOIN"));
    add(new DBKeyword("TABLE"));
    add(new DBKeyword("TERMINATED"));
    add(new DBKeyword("THEN"));
    add(new DBKeyword("TINYBLOB"));
    add(new DBKeyword("TINYINT"));
    add(new DBKeyword("TINYTEXT"));
    add(new DBKeyword("TO"));
    add(new DBKeyword("TRAILING"));
    add(new DBKeyword("TRIGGER"));
    add(new DBKeyword("TRUE"));
    add(new DBKeyword("UNDO"));
    add(new DBKeyword("UNION"));
    add(new DBKeyword("UNIQUE"));
    add(new DBKeyword("UNLOCK"));
    add(new DBKeyword("UNSIGNED"));
    add(new DBKeyword("UPDATE"));
    add(new DBKeyword("USAGE"));
    add(new DBKeyword("USE"));
    add(new DBKeyword("USING"));
    add(new DBKeyword("UTC_DATE"));
    add(new DBKeyword("UTC_TIME"));
    add(new DBKeyword("UTC_TIMESTAMP"));
    add(new DBKeyword("VALUES"));
    add(new DBKeyword("VARBINARY"));
    add(new DBKeyword("VARCHAR"));
    add(new DBKeyword("VARCHARACTER"));
    add(new DBKeyword("VARYING"));
    add(new DBKeyword("WHEN"));
    add(new DBKeyword("WHERE"));
    add(new DBKeyword("WHILE"));
    add(new DBKeyword("WITH"));
    add(new DBKeyword("WRITE"));
    add(new DBKeyword("X509"));
    add(new DBKeyword("XOR"));
    add(new DBKeyword("YEAR_MONTH"));
    add(new DBKeyword("ZEROFILL"));

  }

  public DBKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public static void add(DBKeyword keyword) {
    map.put(keyword.getKeyword(), keyword);
  }

  public static DBKeyword get(String keyword) {
    return map.get(keyword);
  }

}
