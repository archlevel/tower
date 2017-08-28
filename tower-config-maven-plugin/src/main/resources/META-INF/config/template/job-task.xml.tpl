<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:util="http://www.springframework.org/schema/util"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd  
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">
 
	<!-- 执行任务  -->
	<bean id="job#{moduleSuffix}Impl" class="com.#{company}.service.#{artifactId}.job.JobImpl">
		<constructor-arg><value>#{artifactId}.job#{moduleSuffix}</value></constructor-arg> 
		<property name="jobDetail" ref="jobDetailImpl" />
		<property name="trigger" ref="triggerImpl" />
		<property name="scheduler" ref="schedulerImpl" />
	</bean> 

	<bean id="jobDetailImpl" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">  
		<property name="targetObject">  
			<ref bean="job#{moduleSuffix}Impl"/>  
		</property>  
		<property name="targetMethod">  <!-- 要执行的方法名称，框架生成，请不要随意修改！ -->  
			<value>start</value>  
		</property>  
	</bean> 
	
    <!-- 任务触发器  -->
    <bean id="triggerImpl" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">   
        <property name="jobDetail" ref="jobDetailImpl" />   
        <!--  每一小时运行一次(待定)  -->
        <property name="cronExpression" value="${#{artifactId}.job#{moduleSuffix}.CronExpression}" />   
    </bean>
	
	<!--任务执行器还可以为：SimpleAsyncTaskExecutor、 SyncTaskExecutor、ConcurrentTaskExecutor、SimpleThreadPoolTaskExecutor、ThreadPoolTaskExecutor、TimerTaskExecutor、WorkManagerTaskExecutor-->
	<bean id="syncTaskExecutor" class="org.springframework.core.task.SyncTaskExecutor" />
	
	<!-- 任务调度器 --> 
	<bean id="schedulerImpl" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
		<property name="taskExecutor" ref="syncTaskExecutor" />
		<property name="triggers">
			<list>
				<!--  触发器列表  -->
				<ref bean="triggerImpl" />
			</list>
		</property>
	</bean>  
	
</beans>   
