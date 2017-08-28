## Usage:

+ 配置settings.xml

```
	<pluginGroups>
    	<pluginGroup>com.tower.soafw</pluginGroup>
    </pluginGroups>
        
```

+ 配置pom.xml

```
	<dependency>
		<groupId>com.tower.soafw</groupId>
		<artifactId>tower-annotation</artifactId>
		<version>2.2.3-SNAPSHOT</version>
	</dependency>
		
	<plugin>
		<groupId>com.tower.soafw</groupId>
			<artifactId>tower-test-maven-plugin</artifactId>
			<version>2.2.3-SNAPSHOT</version>
			<configuration>
				<!--是否启用-->
				<!--
				<config>false</config>
				-->
				<!--当config为true时，configFileLocations配置项启用-->
				<!--
				<configFileLocations></configFileLocations>
				-->
				<test.gen.skip>false</test.gen.skip>
			</configuration>
			<executions>
				<execution>
					<id>tower-test</id>
					<goals>
						<goal>gen</goal>
					</goals>
					<phase>generate-sources</phase>
				</execution>
			</executions>
		</plugin>
```

+ 通过设置java 属性参数test.gen.skip=false的方式开启单元测试框架代码生成，默认是关闭的
+ eg: mvn -Dtest.gen.skip=false package