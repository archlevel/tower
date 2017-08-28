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

	<artifactId>#{artifactId}-service-impl</artifactId>
	<name>#{artifactId}-service-impl</name>
	<url>http://maven.apache.org</url>
	
	<dependencies>
		<dependency>
			<groupId>com.tower.soafw</groupId>
			<artifactId>tower-service-impl</artifactId>
		</dependency>
		
		<dependency>
			<groupId>com.#{company}.service.#{artifactId}</groupId>
			<artifactId>#{artifactId}-service</artifactId>
		</dependency>
		
		<dependency>
			<groupId>com.#{company}.service.#{artifactId}</groupId>
			<artifactId>#{artifactId}-dao</artifactId>
		</dependency>
		
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<scope>test</scope>
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
		
		<dependency>
			<groupId>org.kie</groupId>
			<artifactId>kie-ci</artifactId>
			<version>6.4.0.Final</version>
			<exclusions>
				<exclusion>
					<artifactId>httpclient</artifactId>
					<groupId>org.apache.httpcomponents</groupId>
				</exclusion>
				<exclusion>
					<artifactId>httpcore</artifactId>
					<groupId>org.apache.httpcomponents</groupId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>
	<build>
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
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<version>2.6</version>
				<configuration>
					<archive>
						<manifest>
							<addClasspath>true</addClasspath>
							<mainClass>com.#{company}.service.#{artifactId}.Startup</mainClass>
						</manifest>
					</archive>
				</configuration>
			</plugin>
			<plugin>
				<artifactId>maven-assembly-plugin</artifactId>
				<version>2.5.4</version>
				<configuration>
					<descriptors>
						<descriptor>src/main/assemble/dist.xml</descriptor>
					</descriptors>
				</configuration>
				<executions>
					<execution>
						<id>make-assembly</id>
						<phase>dist</phase>
						<goals>
							<goal>directory</goal>
						</goals>
					</execution>
				</executions>
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
