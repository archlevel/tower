package com.tower.service.dao.generate.tool;

import java.util.HashMap;
import java.util.Map;

public class DBSetting {
	
	public static String Type_MySQL = "mysql";
	public static String Type_SQLSvr = "sqlserver";
	public static String Type_Oracle = "oracle";
	
	private static final ThreadLocal<String> dbname = new ThreadLocal<String>();
	private static Map<String, String> setting = new HashMap<String, String>();

	public static String getSetting(String key) {
		return setting.get(key);
	}

	public static void setSQLSvr() {
		setting.put("type", Type_SQLSvr);
	}

	public static String getType() {
		return setting.get("type") == null ? Type_MySQL : setting.get("type");
	}

	public static void setSetting(String key, String value) {
		setting.put(key, value);
	}

	public static boolean isGenHelp() {
		return DBSetting.getSetting("genHelper") == null ? false : Boolean
				.valueOf(DBSetting.getSetting("genHelper"));
	}

	public static boolean isMysql() {
		return getSetting("type") == null
				|| Type_MySQL.equalsIgnoreCase(getSetting("type"));
	}

	public static void setGenHelp() {
		DBSetting.setSetting("genHelp", "true");
	}

	public static String getDatabaseName(String url) {
		String database = null;
		if (url.indexOf(Type_MySQL) != -1) {
			// jdbc:mysql://db.corp.tower.com:3306/soafw_db?${db.conn.str}
			String tmp = url.substring(0, url.indexOf("?")).substring(
					url.lastIndexOf("/") + 1);
			database = tmp;
		} else if (url.indexOf("DatabaseName=") != -1) {
			// jdbc:sqlserver://10.10.2.16:1433; DatabaseName=dropship
			String tmp = url.substring(url.indexOf("DatabaseName") + 13);
			int idx = tmp.indexOf(";");
			if (idx != -1) {
				tmp = tmp.substring(0, idx).trim();
			}
			database = tmp;
		}
		else{
			int lst_idx0 = url.indexOf(":");
			// jdbc:oracle:thin@10.10.2.16:1521:orcl
			if(lst_idx0!=-1 ){//SID 格式
				String tmp = url.substring(lst_idx0+1);
				database = tmp;
			}
			throw new RuntimeException("Oracle Url 格式不支持，请修改成'jdbc:oracle:thin@ip:1521:SID'");
		}
		return database;
	}
	public static void setName(String name){
		dbname.set(name);
	}
	public static String getName(){
		return dbname.get();
	}
	
	public static void main(String[] args) {
		// System.out.println(getDatabaseName("jdbc:mysql://db.corp.tower.com:3306/soafw_db?${db.conn.str}"));

		System.out
				.println(getDatabaseName("jdbc:sqlserver://db.corp.tower.com:1433; DatabaseName=dropship;"));
	}
}
