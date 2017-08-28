<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.springframework.org/schema/beans" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:cache="http://www.springframework.org/schema/cache"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.0.xsd
    http://www.springframework.org/schema/cache
    http://www.springframework.org/schema/cache/spring-cache-4.0.xsd">

	<!--框架配置：该文件配置请不要轻易修改-->

	<context:component-scan base-package="com.tower.service.cache.impl" />
	
	<context:component-scan base-package="com.#{company}.service.#{artifactId}.*" />
	
	<cache:annotation-driven cache-manager="cacheManager" />

	<bean name="cacheManager"
		class="com.tower.service.cache.spring.DynamicMemcacheManager">
		<property name="caches">
			<list>
				<ref bean="defaultCache" />
				<ref bean="defaultCache-redis" />
			</list>
		</property>
	</bean>

	<bean name="defaultCache"
		class="com.tower.service.cache.mem.impl.DynamicMemCache"
		init-method="init">
		<property name="prefix" value="#{artifactId}" />
	</bean>
	
	<bean name="defaultCache-redis"
		class="com.tower.service.cache.redis.impl.DynamicRedisCache"
		init-method="init">
		<property name="prefix" value="#{artifactId}" />
	</bean>

</beans>