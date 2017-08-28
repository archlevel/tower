### 设置

+ 定位到maven的settings.xml 文件，新增下列配置，注册插件

	```
	<pluginGroups>
        <pluginGroup>com.tower.soafw</pluginGroup>
    </pluginGroups>
    ```
+ 运行 mvn tower-config:config -Dxxx=yyy ...

	+ xxx列表
		+ groupId
		+ artifactId
		+ startPort
		+ stopPort
		+ destDir
		+ template
		
+ 例子
	
	mvn tower-config:config -Dtemplate=application-config.xml -DartifactId=test -DdestDir='../../test/test-web/src/main/resource/META-INF/config/spring'