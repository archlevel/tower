<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.springframework.org/schema/beans" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	xmlns:cache="http://www.springframework.org/schema/cache"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.0.xsd
    http://code.alibabatech.com/schema/dubbo
    http://code.alibabatech.com/schema/dubbo/dubbo.xsd"
	default-autowire="byName">
	
	<context:component-scan base-package="com.#{company}.service.#{artifactId}.impl" />
	
	<!-- 激活组件扫描功能,在包com.oimboi.service.s2s.aop及其子包下面自动扫描通过注解配置的组件 -->
	<context:component-scan base-package="com.#{company}.service.#{artifactId}.aop" />
	<!-- 激活自动代理功能 -->
	<!-- 启用@AspectJ 支持 -->
	<aop:aspectj-autoproxy />
	
	<!--框架配置：该import设置请不要轻易改变-->
	<import resource="classpath*:/META-INF/config/spring/spring-dao.xml"/>
	<import resource="classpath*:/META-INF/config/spring/spring-rpc.xml"/>
	<import resource="classpath*:/META-INF/config/spring/spring-mq.xml"/>
	
	<!--框架配置：该设置请不要轻易改变-->
	<bean id="dubbo" class="com.tower.service.config.ConfigurationFactoryBean">
		<property name="name" value="dubbo" />
        <property name="encoding" value="utf8" />
	</bean>
	
	<bean id="dubboPlaceholder"
		class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<property name="properties">
			<bean id="propertiesConfigurationFactoryBean"
				class="com.tower.service.config.CommonsConfigurationFactoryBean">
				<property name="configurations">  
			       	<list>  
			       		<ref bean="dubbo" />
			        </list>  
			    </property>				
			</bean>
		</property>
	</bean>
	
	<!--dubbo服务发布配置-->
	
	<!-- 提供方应用信息，用于计算依赖关系 -->
	<dubbo:application name="#{company}-#{artifactId}-service"/>

    <!-- 用dubbo协议在20880端口暴露服务 -->
    <dubbo:protocol name="dubbo" port="${#{artifactId}.service.protocol.dubbo.port}" />
    
    <!-- 使用multicast广播注册中心暴露服务地址 -->
    <!-- 多注册中心配置，竖号分隔表示同时连接多个不同注册中心，同一注册中心的多个集群地址用逗号分隔 -->
    <dubbo:registry protocol="zookeeper" address="${#{artifactId}.service.registry.address}"/>
    
    <!-- 使用监控中心 -->
    <dubbo:monitor protocol="registry"/>
    <!--取消下列备注，开启elastic search 服务-->
   	<!--
    <bean id="rpcConfig" class="com.tower.service.rpc.impl.RpcConfig">
    	<property name="prefix" value="#{artifactId}" />
    </bean>
    <bean id="esAdvancedService" class="com.tower.service.rpc.impl.EsAdvancedService">
    	<property name="config" ref="rpcConfig" />
    </bean>
    -->
    <!--取消下列备注，开启 rule 服务-->
    <!--
    <bean id="serviceConfig" class="com.tower.service.impl.ServiceConfig">
    	<property name="prefix" value="#{artifactId}" />
    </bean>
    <bean id="ruleService" class="com.tower.service.impl.RuleServiceImpl">
    	<property name="config" ref="serviceConfig" />
    </bean>
    -->
    <!--服务注册信息请在该备注以下添加-->
    
    <!-- 声明需要暴露的服务接口 -->
    <!--
    <dubbo:service interface="com.#{company}.service.#{artifactId}.IHelloService" ref="helloService"/>
    -->
    <!-- 和本地bean一样实现服务 -->
    <!--
    <bean id="helloService" class="com.#{company}.service.#{artifactId}.impl.HelloServiceImpl"/>
    -->
    
</beans>