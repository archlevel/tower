### job规范

+ 分页处理job必须继承实现com.tower.service.job.impl.AbsPageableJob,同时必须实现IPageableJob接口
+ 循环处理（处理结果影响待处理数据范围）job必须继承实现com.tower.service.job.impl.AbsJob,同时必须实现INormalJob接口
+ 应用配置必须放在对应项目的resources/META-INF/config/spring/spring-job.xml中
+ 运行时配置必须放在job.xml
+ 可以通过-DX-Cached=false机制进行查询cache的关闭，或者通过在job.xml中进行配置进行cache关闭,更新时缓存会同步，比如实时性要求较高的job(调用方)，其调用的所有service都不需要启用缓存,默认情况都是启用缓存；
	