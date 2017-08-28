<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
		<groupId>com.#{company}.service.#{artifactId}</groupId>
		<artifactId>#{artifactId}</artifactId>
		<version>1.0-SNAPSHOT</version>
	</parent>

	<artifactId>#{artifactId}-config</artifactId>
	<name>#{artifactId}-config</name>
	<url>http://maven.apache.org</url>
	
	<dependencies>
		<dependency>
			<groupId>com.tower.soafw</groupId>
			<artifactId>tower-config</artifactId>
		</dependency>
		<dependency>
	        <groupId>com.tower.soafw</groupId>
	    	<artifactId>tower-concurrent</artifactId>
	    </dependency>
		<dependency>
			<groupId>com.#{company}.service.#{artifactId}</groupId>
			<artifactId>#{artifactId}-util</artifactId>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<scope>test</scope>
		</dependency>
		
	</dependencies>

</project>
