<?xml version="1.0" encoding="UTF-8"?>
<configuration>
	<conversionRule conversionWord="id"
		converterClass="com.tower.service.log.IDConverter" />
		
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- %.-1level 只显示信息级别的首字母,%-5level 左对齐显示信息级别全称 -->
            <!-- 如需自定义关键字，用 %mdc{键名} 表示,程序中用MDC.put("键名","键值")设置，可动态设置 [%logger:%line]-->
            <Pattern>[%id %date{yyyy-MM-dd HH:mm:ss}] [%-5level] [%logger{0}] - %msg%n</Pattern>
        </encoder>
    </appender>
    <appender name="detail" class="ch.qos.logback.core.rolling.RollingFileAppender">
    	<!--框架设置：该file信息请不要修改-->
        <file>/data1/logs/service/#{artifactId}/sdlog.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        	<!--框架设置：该FileNamePattern信息请不要修改-->
            <FileNamePattern>/data1/logs/service/#{artifactId}/sdlog.log-%d{yyyy-MM-dd}</FileNamePattern>
        </rollingPolicy>
        <encoder>
            <!-- %-40(%-35logger{35}:%-4line) -->
            <Pattern>[%id %date{yyyy-MM-dd HH:mm:ss}] [%-5level] [%logger{0}] - %msg%n</Pattern>
        </encoder>
    </appender>
    <appender name="error" class="ch.qos.logback.core.rolling.RollingFileAppender">
    	<!--框架设置：该file信息请不要修改-->
        <file>/data1/logs/service/#{artifactId}/selog.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        	<!--框架设置：该FileNamePattern信息请不要修改-->
            <FileNamePattern>/data1/logs/service/#{artifactId}/selog.log-%d{yyyy-MM-dd}</FileNamePattern>
        </rollingPolicy>
        <encoder>
            <!-- %-40(%-35logger{35}:%-4line) -->
            <Pattern>[%id %date{yyyy-MM-dd HH:mm:ss}] [%-5level] [%logger{0}] - %msg%n</Pattern>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
			<level>ERROR</level>
			<onMatch>ACCEPT</onMatch>
			<onMismatch>DENY</onMismatch>
		</filter>
    </appender>
    <logger name="org.springframework" level="INFO"/>
    <logger name="java.sql.Connection" level="INFO"/>
    <logger name="java.sql.ResultSet" level="INFO"/>
    <logger name="org.apache.zookeeper" level="INFO"/>
    <logger name="com.tower.service" level="INFO"/>
    <logger name="com.#{company}.service" level="INFO"/>
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="detail"/>
        <appender-ref ref="error"/>
    </root>
</configuration>
