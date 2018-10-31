package com.tower.service.config.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class TowerConfig {
	private static Properties towerConfig=null;
	static{
		towerConfig = loadProperties();
	}
	public synchronized static String getConfig(String key){
        return towerConfig.getProperty(key);
    }
	public synchronized static String getConfig(String key,String defaultValue){
        return towerConfig.getProperty(key)==null?defaultValue:towerConfig.getProperty(key);
    }
    private static Properties loadProperties(){
    	towerConfig=new Properties();
        InputStream is = null;
        try {
            is = new URL("file:///config/tower.properties").openConnection().getInputStream();
            System.out.println("tower.properties loaded from 'file:///config/tower.properties'");
        } catch (IOException e) {
            is = TowerConfig.class.getResourceAsStream("/META-INF/tower.properties");
            System.out.println("tower.properties loaded from '/META-INF/tower.properties'");
        }
        finally {
            try {
                towerConfig.load(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return towerConfig;
    }
    
    public static void main(String[] args){
    	System.out.println(TowerConfig.getConfig("db.user"));
    }
}
