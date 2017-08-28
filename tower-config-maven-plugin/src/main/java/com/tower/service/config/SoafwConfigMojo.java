package com.tower.service.config;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;

import com.tower.service.config.utils.TowerConfig;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal config
 * 
 * @phase process-sources
 */
public class SoafwConfigMojo extends AbstractMojo {

	private String serviceId = "1";
	private Integer startPort = 8080;
	private Integer stopPort = 9080;
	private Integer servicePort = 20880;
	private String groupId = "";
	private String artifactId = "";
	private String destDir = ".";
	private String template = "";
	private String sufix = "xml";
	private String module;
	private String moduleSuffix = "";
	private String company = "tower";
	private PluginDescriptor desc = null;
	private String dburl;
	private String dbuser;
	private String dbpwd;

	public void execute() throws MojoExecutionException {
		
		dburl = TowerConfig.getConfig("db.url");
		dbuser = TowerConfig.getConfig("db.user");
		dbpwd = TowerConfig.getConfig("db.pwd");
		
		groupId = System.getProperty("groupId");
		artifactId = System.getProperty("artifactId");
		destDir = System.getProperty("destDir", destDir);
		template = System.getProperty("template", template);
		sufix = System.getProperty("sufix", sufix);
		String model = System.getProperty("model", "split");
		module = System.getProperty("genModule", "all");
		moduleSuffix = System.getProperty("moduleSuffix", "").trim();
		company = System.getProperty("company", "tower");

		desc = (PluginDescriptor) this.getPluginContext().get(
				"pluginDescriptor");

		getServiceInfo();

		this.getLog().info(
				format(model, groupId, artifactId, String.valueOf(startPort),
						String.valueOf(stopPort),
						new File(destDir).getAbsolutePath(), template));

		if ("split".equalsIgnoreCase(model)) {
			/**
			 * read template
			 */
			String tpl = getTemplate(template + ".tpl");
			tpl = doFillIn(tpl);
			this.getLog().info(tpl);
			/**
			 * write to dest
			 */
			write(destDir, template, tpl, sufix);
		} else {

			this.getLog().info("genModule: " + module);
			this.getLog().info("moduleSuffix: " + moduleSuffix);
			doConfig(destDir, artifactId);
		}

	}

	private String dbDriver = "com.mysql.jdbc.Driver";

	public Connection getConn() {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			this.getLog().error(e);
		}
		;
		try {
			this.getLog().info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			this.getLog().info(">>dburl: " + dburl);
			this.getLog().info(">>dbuser: " + dbuser);
			this.getLog().info(">>dbpwd: " + dbpwd);
			Connection connection = DriverManager.getConnection(dburl, dbuser,
					dbpwd);
			return connection;
		} catch (SQLException e) {
			this.getLog().error(e);
		}
		return null;
	}

	private void getServiceInfo() {
		Connection conn = getConn();
		String checkSql = "select * from soa_sp where sp_name='" + artifactId
				+ "'";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			int id = 0;
			if (rs.next()) {

				id = rs.getInt("id");
				serviceId = String.valueOf(id);
				startPort = rs.getInt("start_port");
				stopPort = rs.getInt("stop_port");
				servicePort = rs.getInt("service_port");
				this.getLog().info(
						artifactId + "服务已经存在,startPort=" + startPort
								+ ",stopPort=" + stopPort + ",servicePort="
								+ servicePort);
			} else {
				String sql = "select max(id) as id,max(start_port) as start_port,max(`stop_port`) as stop_port,max(`service_port`) as service_port from soa_sp";
				rs = st.executeQuery(sql);
				if (rs.next()) {
					id = rs.getInt("id") + 1;
					serviceId = String.valueOf(id);
					int tmp = rs.getInt("start_port") + 1;
					if (tmp!=1){
						startPort = tmp;
					}
					tmp = rs.getInt("stop_port") + 1;
					if (tmp!=1){
						stopPort = tmp;
					}
					tmp = rs.getInt("service_port") + 1;
					if (tmp!=1){
						servicePort = tmp;
					}
				}
				sql = "insert into `soa_sp` (id,sp_name,start_port,stop_port,service_port,sp_description) values ("
						+ id
						+ ",'"
						+ artifactId
						+ "',"
						+ startPort
						+ ","
						+ stopPort
						+ ","
						+ servicePort
						+ ",'"
						+ artifactId
						+ "')";
				st.execute(sql);
				this.getLog().info(
						artifactId + "服务不存在,startPort=" + startPort
								+ ",stopPort=" + stopPort + ",servicePort="
								+ servicePort);
			}
		} catch (SQLException e) {
			this.getLog().error(e);
		}
	}

	private String format(String... args) {
		return MessageFormat
				.format("config: flag={0},groupId={1},artifactId={2},startPort={3},stopPort={4},toDest={5},template={6}",
						args);
	}

	private String doFillIn(String tpl) {
		tpl = format(tpl, "company", company);
		tpl = format(tpl, "groupId", groupId);
		tpl = format(tpl, "artifactId", artifactId);
		tpl = format(tpl, "moduleSuffix", moduleSuffix);
		tpl = format(tpl, "startPort", String.valueOf(startPort));
		tpl = format(tpl, "stopPort", String.valueOf(stopPort));
		tpl = format(tpl, "servicePort", String.valueOf(servicePort));
		tpl = format(tpl, "serviceId", serviceId);
		tpl = format(tpl, "db.username", dbuser);
		tpl = format(tpl, "db.password", dbpwd);
		tpl = format(tpl, "tower.version", desc.getVersion());
		return tpl;
	}

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	private String getTemplate(String template) throws MojoExecutionException {
		InputStream is = null;
		BufferedReader br = null;
		this.getLog().info("template: " + template);

		StringBuffer stringBuffer = new StringBuffer();
		try {
			is = this
					.getClass()
					.getClassLoader()
					.getResourceAsStream("META-INF/config/template/" + template);
			br = new BufferedReader(new InputStreamReader(is));
			String s = null;
			while ((s = br.readLine()) != null) {
				stringBuffer.append(s + LINE_SEPARATOR);
			}
		} catch (IOException e) {
			this.getLog().info(
					"load template: " + template + "failed with "
							+ e.getMessage());
			throw new MojoExecutionException("获取模版文件失败");
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception ex) {
			}
		}
		return stringBuffer.toString();
	}

	private String format(String tpl, String pattern, String content) {
		if (content == null) {
			return tpl;
		}
		tpl = tpl.replace("#{" + pattern + "}", content);
		return tpl;
	}

	private void write(String dest, String template, String tpl, String sufix)
			throws MojoExecutionException {
		FileWriter fw = null;
		try {
			new File(dest).mkdirs();
			if (sufix.isEmpty()) {
				sufix = ".tpl";
				template = template.substring(0, template.indexOf(sufix));
			} else {
				template = template.substring(0, template.indexOf(sufix))
						+ sufix;
			}

			fw = new FileWriter(dest + File.separator + template);
			fw.write(tpl);
		} catch (IOException e) {
			throw new MojoExecutionException("", e);
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private void doConfig(String baseDir, String artifactId)
			throws MojoExecutionException {

		if (!moduleSuffix.isEmpty() && !moduleSuffix.startsWith("-")) {
			moduleSuffix = "-" + moduleSuffix;
		}
		PropertiesConfiguration configSetting = load();
		String modules = configSetting.getString("modules");
		String[] moduleArray = modules.split(";");
		int len = moduleArray == null ? 0 : moduleArray.length;
		for (int m = 0; m < len; m++) {
			String currentModule = moduleArray[m];

			if (!"all".equals(module)
					&& (("job".equalsIgnoreCase(module)
							&& !module.equals(currentModule) && !"root"
								.equalsIgnoreCase(currentModule))
							|| ("web".equalsIgnoreCase(module)
									&& !module.equals(currentModule) && !"root"
										.equalsIgnoreCase(currentModule)) || (("service"
							.equalsIgnoreCase(module)
							&& ("job".equalsIgnoreCase(currentModule) || "web"
									.equalsIgnoreCase(currentModule)) && !"root"
								.equalsIgnoreCase(currentModule))))) {
				continue;
			}
			this.getLog().info("start config module: " + currentModule);// service-impl=1;2;3;4;5;6;7
			String moduleIdxs = configSetting.getString(currentModule);
			String[] moduleIdxArray = moduleIdxs.split(";");
			int iLen = moduleIdxArray == null ? 0 : moduleIdxArray.length;
			for (int i = 0; i < iLen; i++) {
				String idx = moduleIdxArray[i];
				String config = currentModule + "." + idx;
				String configs = configSetting.getString(config);

				// sufix;template;destDir
				String[] itemPattern = configs.split(";");
				String sufix = itemPattern[0];
				String templateFile = itemPattern[1] + ".tpl";
				String storeDir = itemPattern[2];

				this.getLog().info("start template: " + templateFile);

				String tpl = this.getTemplate(templateFile);
				if ("SPID.tpl".equals(templateFile)) {
					this.getLog().info("start template: " + templateFile);
				}
				tpl = doFillIn(tpl);
				this.getLog().info(tpl);

				storeDir = format(storeDir, "artifactId", artifactId);

				storeDir = format(storeDir, "company", company);

				storeDir = format(storeDir, "moduleSuffix", moduleSuffix);

				String configToDir = baseDir + File.separator + storeDir;

				this.getLog().info(configToDir);

				templateFile = format(templateFile, "artifactId", artifactId);

				templateFile = format(
						templateFile,
						"moduleSuffix",
						((moduleSuffix == null || moduleSuffix.trim().length() == 0) ? ""
								: moduleSuffix.substring(1)));

				write(configToDir, templateFile, tpl, sufix);
			}
		}
	}

	private PropertiesConfiguration load() throws MojoExecutionException {
		PropertiesConfiguration config = new PropertiesConfiguration();
		InputStream is = SoafwConfigMojo.class.getClassLoader()
				.getResourceAsStream(
						"META-INF/config/template/template.properties");
		try {
			config.load(is);
		} catch (ConfigurationException e) {
			throw new MojoExecutionException("获取'template.properties'文件失败");
		}
		return config;
	}

	public static void main(String[] args) {

		URL resource = SoafwConfigMojo.class.getClassLoader().getResource(
				"META-INF/config/template/template.properties");
		SoafwConfigMojo mojo = new SoafwConfigMojo();
		try {
			mojo.module = "util";
			// mojo.moduleSuffix = "aa";
			mojo.artifactId = "s2s";
			mojo.company = "oimboi";
			mojo.getServiceInfo();
			mojo.doConfig("/Users/alexzhu/soa/projects", "s2s");
		} catch (MojoExecutionException e) {
			throw new RuntimeException(e);
		}
		System.out.println(resource);
	}
}
