package com.tower.service.dao.generate.tool;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class DB {
  List<Tab> tabs = new ArrayList<Tab>();
  private String name;

  public DB(String dbName, String name, DataSource[] ds) throws Exception {
    int size = ds == null ? 0 : ds.length;
    if (size == 0) {
      throw new RuntimeException("数据源必须有一个");
    }
    TabReader reader = null;
    
    if (this.isMysql()) {
      reader = new TabReader(ds[0], dbName, name);
    } else {
      reader = new TabReader_SqlSvr(ds[0], dbName, name);
    }

    List<List<Object>> heads = new ArrayList<List<Object>>();
    List<Object> first = new ArrayList<Object>();

    first.add(null);
    /**
     * 第一行第二列为表名
     */
    first.add(name);

    first.add(null);
    first.add(null);
    /**
     * 第一行第五列为默认数据源名
     */
    first.add(ds[0].toString());
    heads.add(first);

    /**
     * 第二行为表操作的数据源名称 第二列：查询数据源 第二列： 第二列： 第二列：
     */
    List<Object> second = new ArrayList<Object>();
    second.add(null);
    if (size > 1) {
      second.add(ds[1].toString());
    } else {
      second.add(null);
    }

    second.add(null);
    if (size > 2) {
      second.add(ds[2].toString());
    } else {
      second.add(null);
    }

    second.add(null);
    if (size > 3) {
      second.add(ds[3].toString());
    } else {
      second.add(null);
    }

    second.add(null);
    if (size > 4) {
      second.add(ds[4].toString());
    } else {
      second.add(null);
    }

    heads.add(second);

    List<Object> third = new ArrayList<Object>();

    heads.add(third);

    Tab tab = reader.createDef(heads);
    tabs.add(tab);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Tab> getTabs() {
    return tabs;
  }

  public void setTabs(List<Tab> tabs) {
    this.tabs = tabs;
  }
  
  public String getType() {
    return DBSetting.getType();
  }

  public boolean isMysql(){
    return DBSetting.isMysql();
  }
  
  public boolean isGenHelp() {
    return DBSetting.isGenHelp();
  }
  
}
