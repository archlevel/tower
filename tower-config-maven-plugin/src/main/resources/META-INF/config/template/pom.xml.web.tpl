<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
		<groupId>com.#{company}.service.#{artifactId}</groupId>
		<artifactId>#{artifactId}</artifactId>
		<version>1.0-SNAPSHOT</version>
		<relativePath>../pom.xml</relativePath>
	</parent>

	<artifactId>#{artifactId}-web</artifactId>
	<packaging>war</packaging>
	<name>#{artifactId}-web</name>
	<url>http://maven.apache.org</url>
	<properties>
		<jetty.port>#{startPort}</jetty.port>
		<!-- 异常生成定义 -->
		<exception.level>CONTROLLER</exception.level>
		<!--AUTO ?groupId + . + service+ . + CommunityServiceExceptionMessage -->
		<exception.enum.class>AUTO</exception.enum.class>
		<!-- 异常生成定义 END -->
		
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

	</properties>
	<dependencies>
        <dependency>
			<groupId>com.tower.soafw</groupId>
			<artifactId>tower-web</artifactId>
		</dependency>
		
		<dependency>
			<groupId>com.#{company}.service.#{artifactId}</groupId>
			<artifactId>#{artifactId}-service</artifactId>
		</dependency>
		
		<dependency>
			<groupId>net.sf.json-lib</groupId>
			<artifactId>json-lib</artifactId>
			<version>2.2.3</version>
			<classifier>jdk15</classifier>
		</dependency>

		<!-- spring相关jar包 -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context-support</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-web</artifactId>
		</dependency>

		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<version>3.1.0</version>
			<scope>compile</scope>
		</dependency>

		<dependency>
			<groupId>javax.servlet.jsp</groupId>
			<artifactId>jsp-api</artifactId>
			<version>2.2</version>
			<scope>compile</scope>
		</dependency>

		<dependency>
			<groupId>javax.servlet.jsp.jstl</groupId>
			<artifactId>javax.servlet.jsp.jstl-api</artifactId>
			<version>1.2.1</version>
			<scope>compile</scope>
		</dependency>
		
		<dependency>
			<groupId>jstl</groupId>  
			<artifactId>jstl</artifactId>
			<version>1.1.2</version>
		</dependency>
		  
		<dependency>
			<groupId>taglibs</groupId>
			<artifactId>standard</artifactId>
			<version>1.1.2</version>
		</dependency>
		
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>${jackson-databind.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.session</groupId>
			<artifactId>spring-session-data-redis</artifactId>
			<version>1.0.2.RELEASE</version>
		</dependency>
		<!-- dubbo -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>dubbo</artifactId>
			<exclusions>
				<exclusion>
					<groupId>org.springframework</groupId>
					<artifactId>spring</artifactId>
				</exclusion>
				<!-- 指定版本的netty -->
				<exclusion>
					<groupId>io.netty</groupId>
					<artifactId>netty</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.apache.httpcomponents</groupId>
					<artifactId>httpclient</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.apache.httpcomponents</groupId>
					<artifactId>httpcore</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		
		<!-- zookeeper -->
		<dependency>
			<groupId>org.apache.zookeeper</groupId>
			<artifactId>zookeeper</artifactId>
			<exclusions>
				<exclusion>
					<groupId>javax.mail</groupId>
					<artifactId>mail</artifactId>
				</exclusion>
				<exclusion>
					<groupId>log4j</groupId>
					<artifactId>log4j</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.slf4j</groupId>
					<artifactId>slf4j-log4j12</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

		<!-- zkClient -->
		<dependency>
			<groupId>com.101tec</groupId>
			<artifactId>zkclient</artifactId>
		</dependency>
		
	</dependencies>

	<build>
		<finalName>#{artifactId}-web</finalName>
		<plugins>
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>aspectj-maven-plugin</artifactId>
				<version>1.8</version>
				<configuration>
					<verbose>true</verbose>
					<privateScope>true</privateScope>
					<showWeaveInfo>false</showWeaveInfo>
					<source>1.7</source>
					<target>1.7</target>
					<complianceLevel>1.7</complianceLevel>
					<encoding>UTF-8</encoding>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>compile</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.mortbay.jetty</groupId>
				<artifactId>jetty-maven-plugin</artifactId>
				<version>8.1.6.v20120903</version>

				<configuration>
					<connectors>
						<connector implementation="org.eclipse.jetty.server.nio.SelectChannelConnector">
							<port>${jetty.port}</port>
						</connector>
					</connectors>
					<scanIntervalSeconds>10</scanIntervalSeconds>
					<stopKey>foo</stopKey>
					<stopPort>#{stopPort}</stopPort>
					<webAppConfig>
						<contextPath>/#{artifactId}-web</contextPath>
						<war>${project.build.directory}/${project.build.finalName}.war</war>
					</webAppConfig>
					<requestLog implementation="org.eclipse.jetty.server.NCSARequestLog">
						<!--框架设置：该filename信息请不要修改-->
						<filename>/data1/logs/service/#{artifactId}/request.log.yyyy_mm_dd</filename>
						<retainDays>7</retainDays>
						<append>true</append>
						<extended>false</extended>
						<logTimeZone>GMT</logTimeZone>
					</requestLog>
				</configuration>
				<executions>
					<execution>
						<id>start-jetty</id>
						<phase>deploy</phase>
						<goals>
							<goal>run</goal>
						</goals>
						<configuration>
							<scanIntervalSeconds>0</scanIntervalSeconds>
							<daemon>true</daemon>
						</configuration>
					</execution>
					<execution>
						<id>stop-jetty</id>
						<phase>deploy</phase>
						<goals>
							<goal>stop</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.1</version>
				<configuration>
					<source>1.7</source>
					<target>1.7</target>
				</configuration>
			</plugin>
		</plugins>
		<pluginManagement>
			<plugins>
				<!--This plugin's configuration is used to store Eclipse m2e settings 
					only. It has no influence on the Maven build itself. -->
				<plugin>
					<groupId>org.eclipse.m2e</groupId>
					<artifactId>lifecycle-mapping</artifactId>
					<version>1.0.0</version>
					<configuration>
						<lifecycleMappingMetadata>
							<pluginExecutions>
								<pluginExecution>
									<pluginExecutionFilter>
										<groupId>
											org.codehaus.mojo
										</groupId>
										<artifactId>
											aspectj-maven-plugin
										</artifactId>
										<versionRange>
											[1.6,)
										</versionRange>
										<goals>
											<goal>compile</goal>
										</goals>
									</pluginExecutionFilter>
									<action>
										<ignore></ignore>
									</action>
								</pluginExecution>
							</pluginExecutions>
						</lifecycleMappingMetadata>
					</configuration>
				</plugin>
			</plugins>
		</pluginManagement>
	</build>
</project>
