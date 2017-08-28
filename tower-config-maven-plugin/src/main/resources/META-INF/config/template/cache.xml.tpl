<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
	<comment>cache开关等动态配置项配置文件</comment>
	<!--缓存总开关-->
	<entry key="#{artifactId}.X-Cached">false</entry>
	<!--主键缓存开关-->
	<entry key="#{artifactId}.X-Cached.pk">false</entry>
	<!--外键缓存开关-->
	<entry key="#{artifactId}.X-Cached.fk">false</entry>
	<!--表级缓存开关-->
	<entry key="#{artifactId}.X-Cached.tb">false</entry>
	<!--缓存同步时，影响的主键缓存数量超过当前值时，系统自动升级表级缓存、记录缓存的版本号-->
	<entry key="#{artifactId}.threshold_for_delete_pk_by_where">100</entry>
	<!--#{artifactId}表级缓存，改变下列值可以使当前#{artifactId}的所有表级缓存失效-->
	<entry key="#{artifactId}.tab.cache.tag">0</entry>
	<!--#{artifactId}记录级缓存，改变下列值可以使当前#{artifactId}的所有记录级缓存失效-->
	<entry key="#{artifactId}.rec.cache.tag">0</entry>
</properties>
