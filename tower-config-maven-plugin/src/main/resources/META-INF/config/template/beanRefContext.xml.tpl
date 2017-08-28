<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.springframework.org/schema/beans" 
	xmlns:context="http://www.springframework.org/schema/context" 
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.0.xsd"
	default-autowire="byName">
	<bean id="towerContext"
		class="com.tower.service.web.spring.TowerApplicationContextFactoryBean">
		<constructor-arg index="0">
		    <value>#{artifactId}-web</value>
		</constructor-arg>
		<constructor-arg index="1">
			<value>classpath*:META-INF/config/spring/spring-service.xml</value>
		</constructor-arg>
	</bean>
</beans>