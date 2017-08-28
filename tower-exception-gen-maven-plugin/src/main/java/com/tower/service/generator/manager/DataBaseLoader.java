package com.tower.service.generator.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;

import com.google.common.collect.Lists;
import com.tower.service.generator.ExceptionMessage;

/**
 * 数据库方式获取配置信息.
 * <p>
 *     sql:select code,message,type from tableName
 * </p>
 */
public class DataBaseLoader extends AbstractLoader {
    private String dburl;
    private String dbuser;
    private String dbpwd;
    private String sql;
    private String dbDriver="com.mysql.jdbc.Driver";


    public DataBaseLoader(String dburl, String dbuser, String dbpwd, String sql) {
        this.dbpwd = dbpwd;
        this.dburl = dburl;
        this.dbuser = dbuser;
        this.sql = sql;
    }

    @Override
    public List<ExceptionMessage> getExceptionMessages() throws MojoExecutionException {

        List<ExceptionMessage> exceptionMessages=Lists.newArrayList();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(sql);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Statement statement= null;
        Connection connection=null;
        try {

            System.out.println(sql);
            connection=this.getConnection();
            statement = connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            while(rs.next()){
                exceptionMessages.add(new ExceptionMessage(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw  new MojoExecutionException(e.getMessage());
        }

        return exceptionMessages;
    }



    private Connection getConnection(){
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
        }
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(">>dburl: "+dburl);
            System.out.println(">>dbuser: "+dbuser);
            System.out.println(">>dbpwd: "+dbpwd);
            Connection connection= DriverManager.getConnection(this.dburl,this.dbuser,this.dbpwd);
            return  connection;
        } catch (SQLException e) {
        }
        return null;
    }
}
