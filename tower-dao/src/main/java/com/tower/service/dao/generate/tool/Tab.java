package com.tower.service.dao.generate.tool;

import java.util.ArrayList;
import java.util.List;

import com.tower.service.util.StringUtil;

public class Tab {
  private String dbNameStr;
  private String dbName;
  private String queryDB;
  private String insertDB;
  private String deleteDB;

  private String updateDB;

  private String sheetName;
  private String name;
  List<Col> cols = new ArrayList<Col>();
  List<Col> colMaps = new ArrayList<Col>();
  List<String> dbs = new ArrayList<String>();
  private Integer pkFieldNum;

  private Type pkFieldType;

  public Type getPkFieldType() {
    return pkFieldType;
  }

  public void setPkFieldType(Type pkFieldType) {
    this.pkFieldType = pkFieldType;
  }

  public List<Col> getColMaps() {
    return colMaps;
  }

  public void setColMaps(List<Col> colMaps) {
    this.colMaps = colMaps;
  }

  public Integer getPkFieldNum() {
    return pkFieldNum;
  }

  public void setPkFieldNum(Integer pkFieldNum) {
    this.pkFieldNum = pkFieldNum;
  }

  public Tab(String dbNameStr, String sheetName, List<List<Object>> defs) {
    this.sheetName = sheetName;
    this.dbNameStr = dbNameStr;
    int size = defs.get(0).size();
    if (size < 2) {
      throw new RuntimeException("sheet'" + sheetName + "'没有定义表名");
    }
    /**
     * 第一行第二列为表名
     */
    name = (String) defs.get(0).get(1);
    
    /**
     * 第一行第五列为默认数据源名
     */
    dbName = (String) defs.get(0).get(4);
    if (!StringUtil.isEmpty(dbName)) {
      dbName = dbName.trim().toUpperCase();
    } else {
      dbName = null;
    }
    /**
     * 第二行为表操作的数据源名称
     */
    if (size > 2) {
      int size_ = defs.get(1).size();
      if (size_ >= 2) {
        queryDB = (String) defs.get(1).get(1);
        if (!StringUtil.isEmpty(queryDB)) {
          queryDB = queryDB.trim().toUpperCase();
        }
      }

      if (size_ >= 4) {
        insertDB = (String) defs.get(1).get(3);
        if (!StringUtil.isEmpty(insertDB)) {
          insertDB = insertDB.trim().toUpperCase();
        }
      }
      if (size_ >= 6) {
        deleteDB = (String) defs.get(1).get(5);
        if (!StringUtil.isEmpty(deleteDB)) {
          deleteDB = deleteDB.trim().toUpperCase();
        }
      }
      if (size_ >= 8) {
        updateDB = (String) defs.get(1).get(7);
        if (!StringUtil.isEmpty(updateDB)) {
          updateDB = updateDB.trim().toUpperCase();
        }
      }
    }

    if (!StringUtil.isEmpty(dbName) && !dbs.contains(dbName)) {
      dbs.add(dbName);
    }
    if (!StringUtil.isEmpty(queryDB) && !dbs.contains(queryDB)) {
      dbs.add(queryDB);
    }
    if (!StringUtil.isEmpty(insertDB) && !dbs.contains(insertDB)) {
      dbs.add(insertDB);
    }
    if (!StringUtil.isEmpty(deleteDB) && !dbs.contains(deleteDB)) {
      dbs.add(deleteDB);
    }
    if (!StringUtil.isEmpty(updateDB) && !dbs.contains(updateDB)) {
      dbs.add(updateDB);
    }
    defs.remove(0);
    defs.remove(0);
    defs.remove(0);
    int cnum = defs.size();
    for (int i = 0; i < cnum; i++) {
      String temp = (String) defs.get(i).get(1);
      if (temp != null && temp.trim().length() > 0) {
        cols.add(new Col(name, i, defs.get(i), false));
        colMaps.add(new Col(name, i, defs.get(i), true));
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getDbNameStr() {
    return dbNameStr;
  }

  public void setDbNameStr(String dbNameStr) {
    this.dbNameStr = dbNameStr;
  }

  public String getQueryDB() {
    return queryDB;
  }

  public void setQueryDB(String queryDB) {
    this.queryDB = queryDB;
  }

  public String getInsertDB() {
    return insertDB;
  }

  public void setInsertDB(String insertDB) {
    this.insertDB = insertDB;
  }

  public String getDeleteDB() {
    return deleteDB;
  }

  public void setDeleteDB(String deleteDB) {
    this.deleteDB = deleteDB;
  }

  public String getUpdateDB() {
    return updateDB;
  }

  public void setUpdateDB(String updateDB) {
    this.updateDB = updateDB;
  }

  public String getSheetName() {
    return sheetName;
  }

  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  public List<String> getDbs() {
    return dbs;
  }

  public void setDbs(List<String> dbs) {
    this.dbs = dbs;
  }

  public List<Col> getCols() {
    return cols;
  }

  public void setCols(List<Col> cols) {
    this.cols = cols;
  }

}
