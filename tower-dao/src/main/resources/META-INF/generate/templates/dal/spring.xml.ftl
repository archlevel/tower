<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">
	
	<bean id="${masterDataSource}_db" class="com.tower.service.datasource.DynamicDataSource">
		<property name="prefix" value="${upperDataSource}_db" />
	</bean>

	<bean id="${upperDataSource}_db_slave" class="com.tower.service.datasource.DynamicDataSource">
		<property name="prefix" value="${upperDataSource}_db_slave" />
	</bean>

	<bean id="${upperDataSource}_db_map_query" class="com.tower.service.datasource.DynamicDataSource">
		<property name="prefix" value="${upperDataSource}_db_map_query" />
	</bean>
</beans>

