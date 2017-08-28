package com.tower.service.dao.generate.tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tower.service.gen.AbsGen;

import freemarker.template.TemplateException;

public abstract class DaoGen extends AbsGen {
  
  protected abstract DB load() throws Exception;

  public void process(String masterDataSourceBean, String slaveDataSourceBean,
      String mapQueryDataSourceBean, String packageName, String targetJava, String targetResources)
      throws Exception {

    DB def = load();
    process(masterDataSourceBean, slaveDataSourceBean, mapQueryDataSourceBean, def, packageName,
        targetJava, targetResources);
  }

  protected void process(String masterDataSourceBean, String slaveDataSourceBean,
      String mapQueryDataSourceBean, DB def, String packageName, String targetJava,
      String targetResources) throws IOException, TemplateException {

    Map root = new HashMap();
    
    root.putAll(System.getProperties());

    root.put("db", def);
    
    List<Tab> tabs = def.getTabs();

    int size = tabs == null ? 0 : tabs.size();

    String package_ = packageName;

    String java = targetJava;

    String resource = targetResources;

    root.put("package", package_);

    package_ = package_.replaceAll("\\.", "/");

    List<String> modelNames = new ArrayList<String>();

    for (int i = 0; i < size; i++) {

      String name = format(tabs.get(i).getName());
      String subName = name.substring(0, 1).toLowerCase() + name.substring(1);
      modelNames.add(name);
      String dbName = DBSetting.getName();
      int index = dbName.indexOf("_");
      String app = dbName.substring(0,index);
      
      root.put("masterSessionFactory", app+"SessionFactory");
      root.put("slaveSessionFactory", app+"SlaveSessionFactory");
      root.put("mapQuerySessionFactory", app+"MapQuerySessionFactory");
      
      root.put("masterDataSource", masterDataSourceBean);
      
      root.put("slaveDataSource", slaveDataSourceBean);
      root.put("mapQueryDataSource", mapQueryDataSourceBean);
      
      
      
      root.put("upperDataSource", masterDataSourceBean);
      root.put("name", name);
      root.put("subName", subName);
      root.put("tab", tabs.get(i));
      root.put("dbs", tabs.get(i).getDbs());
      root.put("cols", tabs.get(i).getCols());
      root.put("colMaps", tabs.get(i).getColMaps());

      create("model.ftl", root, java + package_ + "/dao/model/" + name + ".java");
      create("idao.ftl", root, java + package_ + "/dao/" + "I" + name + "DAO.java");
      create("daoimpl.ftl", root, java + package_ + "/dao/ibatis/" + name + "IbatisDAOImpl.java");
      create("mapper.ftl", root, java + package_ + "/dao/ibatis/mapper/" + name + "Mapper.java");
      create("mapper.xml.ftl", root, resource + package_ + "/dao/ibatis/mapper/" + name
          + "Mapper.xml");
      boolean genHelp = DBSetting.isGenHelp();
      if(genHelp){
        create("helpper.ftl", root, java + package_ + "/dao/model/" + name + "Helpper.java");
        create("ihelpperdao.ftl", root, java + package_ + "/dao/" + "I" + name + "HelpperDAO.java");
        create("helpperdaoimpl.ftl", root, java + package_ + "/dao/ibatis/" + name + "HelpperIbatisDAOImpl.java");
        create("helppermapper.ftl", root, java + package_ + "/dao/ibatis/mapper/" + name + "HelpperMapper.java");
        create("helppermapper.xml.ftl", root, resource + package_ + "/dao/ibatis/mapper/" + name
            + "HelpperMapper.xml");
      }
      //create("spring.xml.ftl", root, resource + "/META-INF/config/local/" + "spring.xml");
    }
    // root.put("modelNames", modelNames);

    // create("spring.xml.ftl", root, resource + package_ + "/spring-dao.xml");
  }
}
