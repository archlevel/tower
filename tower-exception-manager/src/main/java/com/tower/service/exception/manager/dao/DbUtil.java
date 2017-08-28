package com.tower.service.exception.manager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.tower.service.config.utils.TowerConfig;

/**
 * Created by kevin on 15/1/6.
 */
public class DbUtil {

    private static String getConfig(String key){
        return TowerConfig.getConfig(key);
    }

    public static void closeConnection(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

    public static Connection getConnection(){
        try {
            Class.forName(getConfig("db.driver"));
        } catch (ClassNotFoundException e) {
        }
        try {
            Connection connection= DriverManager.getConnection(
                    getConfig("db.driver"), getConfig("db.user"), getConfig("db.pwd"));
            return  connection;
        } catch (SQLException e) {
        }
        return null;
    }
}
