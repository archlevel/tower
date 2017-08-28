### service规范

+ 需要动态加载配置必须继承实现com.tower.service.impl.AbsServiceImpl同时实现对应的服务接口
+ 规则服务必须继承实现com.tower.service.impl.AbsRuleServiceImpl同时实现对应的服务接口
+ 框架配置必须放在对应项目的resources/META-INF/config/spring/spring-service.xml中
+ 运行时配置必须放在service.xml
### 代码生成器
+ 运行项目#{artifactId}-service-impl/src/test/java/com/#{company}/service/#{artifactId}/ServiceGen.java
### 分库分表实现
+ 在相关的serviceImpl中实现分库分表逻辑；
+ 分库实现借口
	+ 设置数据源为spring-dao.xml中配置的数据源id；eg:cache_dbSrc
    + DataSourceRouter.switch2("cache_dbSrc");
+ 分表实现接口
	+ 设置dao接口中tabNameSuffix参数