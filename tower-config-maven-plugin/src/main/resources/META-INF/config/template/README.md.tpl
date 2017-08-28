### [框架使用向导](https://github.com/archlevel/tower/blob/master/README.MD)

### 部署规范


+ 部署用户

	+ deployer
	
+ 部署应用程序目录结构

	+ ~/apps
	
			|
			start.sh service version
		 	#{artifactId}
		 	  |
		 	  current
		 	  v1
		 	  v2
		 	  v3
		 	  ...
		 	  vn
		 	  	|
		 	  	pom.xml
		 	  	#{artifactId}-cache
		 	  	#{artifactId}-common
		 	  	#{artifactId}-dao
		 	  	#{artifactId}-domain
		 	  	#{artifactId}-job
		 	  		|
		 	  		start.sh
		 	  	#{artifactId}-mq
		 	  	#{artifactId}-rpc
		 	  	#{artifactId}-service
		 	  	#{artifactId}-service-impl
		 	  		|
		 	  		start.sh
		 	  	#{artifactId}-web
		 	  		|
		 	  		start.sh
		 	  		pom.xml
		 	  		target
		 	  			|
		 	  			#{artifactId}-web.war
		 	  			或者
		 	  			#{artifactId}-web
	+ 日志目录结构
		+ /data1/logs/service/#{artifactId}/
		
								|
								dlog.log
								elog.log
								request.log
### 配置规范

+ 配置
	+ 应用配置
		+ 位置
		
			统一放置在对应模块中src/main/resources/META-INF/config/spring/
			
		+ 配置文件列表
			+ spring-cache.xml
			+ spring-dao.xml
			+ spring-rpc.xml
			+ spring-mq.xml
			+ spring-service.xml
			+ spring-job.xml
			+ spring-service.xml
		
	+ 资源&程序开关配置 支持动态加载机制
		+ 位置
			+ 支持3级配置机制
				+ 1，系统及配置优先级最高，系统级配置目录默认为/config/，可以通过-Dsystem.config.dir=/config的方式进行调整
				+ 2，应用配置优先级次之，通过-Dapp.home.dir=xxx的方式进行设置
        			/home/apps/tsl/current/config/ 优先级次之
				+ 3，优先级最低的配置［对应模块配置］，在对应模块的META-INF/config/local/目录下
				+ 4,支持profile机制，通过-Dprofile=xxx方式支持profile机制，当启用了profile机制后，其相关配置文件都必须采用统一采用profile的命名规范；即即：[profile.][configFile];eg:dev.service.xml;prd.service.xml
			
		+ 文件列表
			+ 资源类型
				+ cache-mem.properties
				+ cache-redis.properties
				+ database.properties
				+ dubbo.properties
				
			+ 开关类型
				+ cache.xml 数据访问层
					+ key命名规范: 服务名.dao.配置项名; eg; #{artifactId}.aaa
				+ acc.xml 数据访问层
					+ key命名规范: 服务名.dao.配置项名; eg; #{artifactId}.aaa
				+ service.xml 服务层
					+ key命名规范: 服务名.svc.配置项名; eg; #{artifactId}.bbb
				+ rpc.xml 第三方服务远程调用相关配置
					+ key命名规范: 服务名.rpc.配置项名; eg; #{artifactId}.rpc.ccc
				+ mq.xml  mq服务
					+ key命名规范: 服务名.mq.配置项名; eg; #{artifactId}.mq.ddd
				+ job.xml job层
					+ key命名规范: 服务名.job名称.配置项名; eg; #{artifactId}.[jobId.]eee
				+ webapp.xml web层
					+ key命名规范: 服务名.web.配置项名; eg; #{artifactId}.web.fff
		+ 扩展实现
			当你有新文件需要做到动态加载时，可以通过
			
			```
			<bean id="xxx" class="com.tower.service.config.ConfigurationFactoryBean">
				<property name="name" value="xxx" />
		        <property name="encoding" value="utf8" />
		        <property name="type" value="xml" />
			</bean>
			```