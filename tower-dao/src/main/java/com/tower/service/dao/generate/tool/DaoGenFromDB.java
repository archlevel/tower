package com.tower.service.dao.generate.tool;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tower.service.gen.TemplateFactoryBean;

import freemarker.template.Configuration;

public class DaoGenFromDB extends DaoGen {
  private String table;
  private String dbName;
  private DataSource[] datasource;

  public DaoGenFromDB(DataSource[] datasource, String dbName, String table) {
    this.datasource = datasource;
    this.dbName = dbName;
    this.table = table;
  }

  @Override
  protected DB load() throws Exception {
    return new DB(dbName, table, datasource);
  }
  
  public static void generateDAO(String dbName, String tableName, String springXml,
      String packageName, String targetJava, String resources) throws Exception {
    String[] tableNames = {tableName};
    generateDAO(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  public static void generateDAO(String dbName, String tableNames[], String springXml,
      String packageName, String targetJava, String resources) throws Exception {
    commonGen(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  public static void generateDAOWithHellper(String dbName, String tableName, String springXml,
      String packageName, String targetJava, String resources) throws Exception {
    String[] tableNames = {tableName};
    generateDAOWithHellper(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  public static void generateDAOWithHellper(String dbName, String tableNames[], String springXml,
      String packageName, String targetJava, String resources) throws Exception {
    DBSetting.setGenHelp();
    generateDAO(dbName, tableNames, springXml, packageName, targetJava, resources);
  }

  /**
   * sqlserver database 生成dao文件
   * 
   * @param masterDataSourceBean
   *          主数据源bean名称
   * @param slaveDataSourceBean
   *          从数据源bean名称
   * @param dbName
   *          db名称
   * @param tableName
   *          表名
   * @param springXml
   *          spring文件
   * @param packageName
   *          包名
   * @param targetJava
   *          存放产生java的目录
   * @param resources
   *          存放产生xml的目录
   * @throws Exception
   */
  public static void generateSQLSvrDAO(String dbName, String tableName, String springXml,
      String packageName, String targetJava, String resources) throws Exception {
      String[] tableNames = {tableName};
      generateSQLSvrDAO(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  public static void generateSQLSvrDAO(String dbName, String[] tableNames, String springXml,
      String packageName, String targetJava, String resources) throws Exception{
    DBSetting.setSetting("type",DBSetting.Type_SQLSvr);
    commonGen(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  public static void generateSQLSvrDAOWithHelpper(String dbName, String tableName,
      String springXml, String packageName, String targetJava, String resources) throws Exception {
    String[] tableNames = {tableName};
    generateSQLSvrDAOWithHelpper(dbName, tableNames, springXml, packageName, targetJava, resources);
  }

  public static void generateSQLSvrDAOWithHelpper(String dbName, String[] tableNames, String springXml,
      String packageName, String targetJava, String resources) throws Exception{
    DBSetting.setGenHelp();
    generateSQLSvrDAO(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  /**
   * Oracle database 生成dao文件
   * 
   * @param masterDataSourceBean
   *          主数据源bean名称
   * @param slaveDataSourceBean
   *          从数据源bean名称
   * @param dbName
   *          db名称
   * @param tableName
   *          表名
   * @param springXml
   *          spring文件
   * @param packageName
   *          包名
   * @param targetJava
   *          存放产生java的目录
   * @param resources
   *          存放产生xml的目录
   * @throws Exception
   */
  public static void generateOracleDAO(String dbName, String tableName, String springXml,
      String packageName, String targetJava, String resources) throws Exception {
      String[] tableNames = {tableName};
      generateSQLSvrDAO(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  public static void generateOracleDAO(String dbName, String[] tableNames, String springXml,
      String packageName, String targetJava, String resources) throws Exception{
    DBSetting.setSetting("type",DBSetting.Type_Oracle);
    commonGen(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  public static void generateOracleDAOWithHelpper(String dbName, String tableName,
      String springXml, String packageName, String targetJava, String resources) throws Exception {
    String[] tableNames = {tableName};
    generateSQLSvrDAOWithHelpper(dbName, tableNames, springXml, packageName, targetJava, resources);
  }

  public static void generateOracleDAOWithHelpper(String dbName, String[] tableNames, String springXml,
      String packageName, String targetJava, String resources) throws Exception{
    DBSetting.setGenHelp();
    generateSQLSvrDAO(dbName, tableNames, springXml, packageName, targetJava, resources);
  }
  
  private static void commonGen(String dbName, String[] tableName, String springXml,
      String packageName, String targetJava, String resources) throws Exception{
	DBSetting.setName(dbName);
    int size = tableName==null?0:tableName.length;
    TemplateFactoryBean templateFactoryBean = new TemplateFactoryBean();
    templateFactoryBean.setPath("classpath:/META-INF/generate/templates/dal");

    ClassPathXmlApplicationContext cac = new ClassPathXmlApplicationContext(
        springXml != null ? springXml : "generateDAO.xml");
    String masterDataSourceBean = dbName;
    String slaveDataSourceBean = dbName + "_slave";
    String mapQueryDataSourceBean = dbName + "_map_query";
    DataSource masterDataSource = (DataSource) cac.getBean(masterDataSourceBean);
    String url = masterDataSource.getConnection().getMetaData().getURL();
    DataSource[] datasoures = { masterDataSource };
    for(int i=0;i<size;i++){
      DaoGenFromDB hf = new DaoGenFromDB(datasoures, DBSetting.getDatabaseName(url), tableName[i]);
      hf.setConfiguration((Configuration) templateFactoryBean.getObject());
      hf.process(masterDataSourceBean, slaveDataSourceBean, mapQueryDataSourceBean, packageName,
          targetJava, resources);
    }
  }

}
