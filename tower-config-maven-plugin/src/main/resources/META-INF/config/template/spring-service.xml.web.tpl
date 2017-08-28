<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.springframework.org/schema/beans" 
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context" 
	xmlns:mvc="http://www.springframework.org/schema/mvc" 
	xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.0.xsd
    http://code.alibabatech.com/schema/dubbo
    http://code.alibabatech.com/schema/dubbo/dubbo.xsd"
	default-autowire="byName">
	
	<!--框架配置：该设置请不要轻易改变-->
	<context:component-scan base-package="com.tower.service.web.impl,com.#{company}.service.#{artifactId}" />
	
	<!-- 激活组件扫描功能,在包com.oimboi.service.s2s.aop及其子包下面自动扫描通过注解配置的组件 -->
	<context:component-scan base-package="com.#{company}.service.#{artifactId}.aop" />
	<!-- 激活自动代理功能 -->
	<!-- 启用@AspectJ 支持 -->
	<aop:aspectj-autoproxy />
	
	<!--开启authenticationFilter及取消web.xml的DelegatingFilterProxy的备注即可集成权限控制-->
	<!--
	<bean id="authenticationFilter" class="com.tower.service.web.impl.AuthenticationFilter" />
	-->
	
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
	
	<!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
	<dubbo:application name="#{company}-#{artifactId}-web"/>
	
	<!-- 协议 -->
    <dubbo:protocol name="dubbo" />
    
    <!-- 使用zookeeper发现服务地址 -->
    <!-- 多注册中心配置，竖号分隔表示同时连接多个不同注册中心，同一注册中心的多个集群地址用逗号分隔 -->
    <dubbo:registry id="#{artifactId}Registry" protocol="zookeeper" address="${#{artifactId}.service.registry.address}"/>
    
    <!-- 服务消费者定义 -->
    <dubbo:consumer timeout="${#{artifactId}.web.timeout}" init="true" check="false"/>
    
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
    <!--服务查询定义信息请在该备注以下添加-->
	<context:component-scan base-package="com.#{company}.service.#{artifactId}" />
	
	<!--服务查询定义信息请在该备注以下添加-->
    <!-- 所有服务应用时必须定义registry属性 -->
    <!--
	<dubbo:reference id="xxxService" interface="com.#{company}.service.#{artifactId}.IXxxxService" registry="#{artifactId}Registry"/>
	-->
	
</beans>
