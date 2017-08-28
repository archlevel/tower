### resource规范

+ 需要动态加载配置加载必须继承实现com.tower.service.web.impl.AbsResource同时实现其对应的服务接口
+ 所有web资源实现必须放在com.#{company}.service.#{artifactId}.resource
+ 服务框架配置必须放在对应项目的resources/META-INF/config/spring/spring-service.xml中
+ mvc框架配置必须放在对应项目的resources/META-INF/config/spring/spring-mvc.xml中
+ web框架配置必须放在对应项目的resources/META-INF/config/spring/spring-web.xml中
+ security框架配置必须放在对应项目的resources/META-INF/config/spring/spring-security.xml中
+ 运行时配置必须放在webapp.xml
	