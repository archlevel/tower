<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.springframework.org/schema/beans" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:cache="http://www.springframework.org/schema/cache"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.0.xsd">
	
	<!--框架配置：该scan设置请不要轻易改变-->
	<context:component-scan base-package="com.tower.service.dao.ibatis" />
	<context:component-scan base-package="com.tower.service.cache.dao.ibatis" />
	<context:component-scan base-package="com.#{company}.service.#{artifactId}.dao.ibatis" />
	
	<!--框架配置：该import设置请不要轻易改变-->
	<import resource="classpath*:/META-INF/config/spring/spring-cache.xml"/>
	
	<!--框架配置：该数据源设置请不要轻易改变-->
	<bean id="cache_dbSrc" class="com.tower.service.datasource.DynamicDataSource"
		init-method="init">
		<property name="prefix" value="cache_db" />
	</bean>
	<bean id="cache_db" class="com.tower.service.datasource.DataSourceRouter">
        <property name="defaultTargetDataSource" ref="cache_dbSrc"/>
        <property name="targetDataSources">
            <map>
                <entry key="cache_dbSrc" value-ref="cache_dbSrc"/>
                <!-- 
                <entry key="sqlServerDataSource" value-ref="sqlServerDataSource"/>
                 -->
            </map>
        </property>
    </bean>
	<bean id="cacheSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="configurationProperties">
			<props>
				<prop key="database_name">cache_db</prop>
			</props>
		</property>
 		<property name="dataSource" ref="cache_db" />
 	</bean>
	<bean id="cache_db_slaveSrc" class="com.tower.service.datasource.DynamicDataSource"
		init-method="init">
		<property name="prefix" value="cache_db_slave" />
	</bean>
	<bean id="cache_db_slave" class="com.tower.service.datasource.DataSourceRouter">
        <property name="defaultTargetDataSource" ref="cache_db_slaveSrc"/>
        <property name="targetDataSources">
            <map>
                <entry key="cache_db_slaveSrc" value-ref="cache_db_slaveSrc"/>
                <!-- 
                <entry key="sqlServerDataSource" value-ref="sqlServerDataSource"/>
                 -->
            </map>
        </property>
    </bean>
	<bean id="cacheSlaveSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="configurationProperties">
			<props>
				<prop key="database_name">cache_db_slave</prop>
			</props>
		</property>   
 		<property name="dataSource" ref="cache_db_slave" />
 	</bean>
	<bean id="cache_db_map_querySrc" class="com.tower.service.datasource.DynamicDataSource"
		init-method="init">
		<property name="prefix" value="cache_db_map_query" />
	</bean>
	<bean id="cache_db_map_query" class="com.tower.service.datasource.DataSourceRouter">
        <property name="defaultTargetDataSource" ref="cache_db_map_querySrc"/>
        <property name="targetDataSources">
            <map>
                <entry key="cache_db_map_querySrc" value-ref="cache_db_map_querySrc"/>
                <!-- 
                <entry key="sqlServerDataSource" value-ref="sqlServerDataSource"/>
                 -->
            </map>
        </property>
    </bean>
	<bean id="cacheMapQuerySessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="configurationProperties">
			<props>
				<prop key="database_name">cache_db_map_query</prop>
			</props>
		</property>     
 		<property name="dataSource" ref="cache_db_map_query" />
 	</bean>
 	
	<!--自定义信息请在该备注以下添加-->
	<!--新增数据库是需要添加 master、slave、map_query三个-->
	<bean id="#{artifactId}_dbSrc" class="com.tower.service.datasource.DynamicDataSource"
		init-method="init">
		<property name="prefix" value="#{artifactId}_db" />
	</bean>
	<bean id="#{artifactId}_db" class="com.tower.service.datasource.DataSourceRouter">
        <property name="defaultTargetDataSource" ref="#{artifactId}_dbSrc"/>
        <property name="targetDataSources">
            <map>
                <entry key="#{artifactId}_dbSrc" value-ref="#{artifactId}_dbSrc"/>
                <!-- 
                <entry key="sqlServerDataSource" value-ref="sqlServerDataSource"/>
                 -->
            </map>
        </property>
    </bean>
	<bean id="#{artifactId}SessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="configurationProperties">
			<props>
				<prop key="database_name">#{artifactId}_db</prop>
			</props>
		</property>     
 		<property name="dataSource" ref="#{artifactId}_db" />
 	</bean>
	<bean id="#{artifactId}_db_slaveSrc" class="com.tower.service.datasource.DynamicDataSource"
		init-method="init">
		<property name="prefix" value="#{artifactId}_db_slave" />
	</bean>
	<bean id="#{artifactId}_db_slave" class="com.tower.service.datasource.DataSourceRouter">
        <property name="defaultTargetDataSource" ref="#{artifactId}_db_slaveSrc"/>
        <property name="targetDataSources">
            <map>
                <entry key="#{artifactId}_db_slaveSrc" value-ref="#{artifactId}_db_slaveSrc"/>
                <!-- 
                <entry key="sqlServerDataSource" value-ref="sqlServerDataSource"/>
                 -->
            </map>
        </property>
    </bean>
	<bean id="#{artifactId}SlaveSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="configurationProperties">
			<props>
				<prop key="database_name">#{artifactId}_db_slave</prop>
			</props>
		</property>     
 		<property name="dataSource" ref="#{artifactId}_db_slave" />
 	</bean>
	<bean id="#{artifactId}_db_map_querySrc" class="com.tower.service.datasource.DynamicDataSource"
		init-method="init">
		<property name="prefix" value="#{artifactId}_db_map_query" />
	</bean>
	<bean id="#{artifactId}_db_map_query" class="com.tower.service.datasource.DataSourceRouter">
        <property name="defaultTargetDataSource" ref="#{artifactId}_db_map_querySrc"/>
        <property name="targetDataSources">
            <map>
                <entry key="#{artifactId}_db_map_querySrc" value-ref="#{artifactId}_db_map_querySrc"/>
                <!-- 
                <entry key="sqlServerDataSource" value-ref="sqlServerDataSource"/>
                 -->
            </map>
        </property>
    </bean>
	<bean id="#{artifactId}MapQuerySessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="configurationProperties">
			<props>
				<prop key="database_name">#{artifactId}_db_map_query</prop>
			</props>
		</property>     
 		<property name="dataSource" ref="#{artifactId}_db_map_query" />
 	</bean>
 	
 	<bean id="StatementHandlerPlugin" class="com.tower.service.dao.ibatis.StatementHandlerPlugin">
 		<property name="properties">
            <props>
                <prop key="prefix">#{artifactId}</prop>
            </props>
        </property>
 	</bean>
 	
 	<!-- enable the configuration of transactional behavior based on annotations -->
 	<!--
 	<bean id="transactionManager" class="com.tower.service.datasource.TowerDataSourceTransactionManager">
	    <property name="dataSource" ref="#{artifactId}_db"/>
	</bean>
	
	<tx:annotation-driven transaction-manager="transactionManager"/>
 	-->
	
</beans>