### 缓存包

+ 配置文件目录默认为： /config，可以通过java属性机制进行改变-Dconfig.file.dir=iiii进行设置
+ 配置支持动态加载

+ 支持mem cache机制
	+ com.tower.service.cache.mem.impl.DynamicMemCache
	+ 对应配置文件cache-mem.properties
	+ 改变其配置信息［ip。。。］
+ 支持RedisCache
	+ com.tower.service.cache.redis.impl.DynamicRedisCache
	+ 对应配置文件cache-redis.properties
	+ 改变其配置信息［ip。。。］

+ 开关配置文件默认为 cache.xml,可以通过java 属性机制进行改变-Dcache.config=xxx机制进行改变
	+ 默认情况数据访问层集成了缓存；
	+ 缓存支持多种级别：主键、外键、表级且都支持动态开关；
	+ 项目编号.X-Cached默认为false
	+ 项目编号.X-Cached.pk默认为false
	+ 项目编号.X-Cached.fk默认为false
	+ 项目编号.X-Cached.tb默认为false
	+ 当缓存启用时可以改变下面设置进行清除相关缓存［不常用］
		+ 项目编号.tab.cache.tag=0设定[项目编号]组缓存的当前表级版本号，通过改变这个值可以使得[项目编号]组的表级缓存立马失效
		+ 项目编号.rec.cache.tag=0设定[项目编号]组缓存的当前记录级版本号，通过改变这个值可以使得[项目编号]组的记录级缓存立马失效
	+ 调用方是否启用缓存,默认情况都是启用缓存，可以通过配置文件［job.xml、cache.xml等配置文件设置xxx.X-Cached=false］-DX-Cached=false机制进行查询cache的关闭，更新时缓存会同步，比如实时性要求较高的job(调用方)，其调用的所有service都不需要启用缓存；
	+ 当有分布式事务是，查询缓存不启用，如果缓存开启则会更新缓存；