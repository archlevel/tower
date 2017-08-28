<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.springframework.org/schema/beans" 
	xmlns:context="http://www.springframework.org/schema/context" 
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.0.xsd"
	default-autowire="byName">
	<!--从这里开始这里只定义web app相关的配置-->
	<!-- 
	<import resource="classpath*:/META-INF/config/spring/spring-security.xml"/>	
	-->
	<!--下面是springSession配置-->
	<!--
	<context:annotation-config/> 
 	<bean id="session" class="com.tower.service.config.ConfigurationFactoryBean">
		<property name="name" value="session" />
        <property name="encoding" value="utf8" />
	</bean>
	
	<bean id="sessionPlaceholder"
		class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<property name="properties">
			<bean id="propertiesConfigurationFactoryBean"
				class="com.tower.service.config.CommonsConfigurationFactoryBean">
				<property name="configurations">  
			       	<list>  
			       		<ref bean="session" />
			        </list>  
			    </property>				
			</bean>
		</property>
	</bean>
	
	<bean class="org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration"/>  
	<bean id="redispoolConfig" class="redis.clients.jedis.JedisPoolConfig" />    
	<bean id="redisconnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
			<property name="hostName" value="${redis.host}" />
			<property name="port" value="${redis.port}" />
			<property name="poolConfig" ref="redispoolConfig"></property>
	</bean>
	 -->
</beans>
