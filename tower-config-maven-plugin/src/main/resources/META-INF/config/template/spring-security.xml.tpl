<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/security"
	xmlns:beans="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
           http://www.springframework.org/schema/security 
           http://www.springframework.org/schema/security/spring-security-3.2.xsd">


	<!-- 静态资源请求不做安全验证 -->
	<http security="none" pattern="/static/**"></http>
	<http security="none" pattern="/plug-in/**"></http>

	<!-- 登录页面，匿名访问 -->
	<http security="none" pattern="/WEB-INF/jsp/login.jsp"></http>
	<http security="none" pattern="/"></http>
	<http security="none" pattern="/login"></http>
	<http security="none" pattern="/timeout"></http>
	<http security="none" pattern="/accessDenied"></http>
	<!-- <http security="none" pattern="/ticketValidate"></http> -->

	<!-- http安全配置 -->   <!-- 尝试访问没有权限的页面时跳转的页面 access-denied-page="/accessDenied" -->
	<http access-denied-page="/accessDenied" use-expressions="true"
		entry-point-ref="ajaxAwareAuthenticationEntryPoint">

		<session-management invalid-session-url="/timeout">
			<!-- 防止第二次登录 如果想让第一次登录失效第二次登录启用则不要配置error-if-maximum-exceeded="true" -->
			<concurrency-control max-sessions="1"
				error-if-maximum-exceeded="true" />
		</session-management>

		<intercept-url pattern="/**" access="hasRole('ROLE_USER')" />

		<!-- 登录页面 -->
		<form-login login-page="/login" login-processing-url="/loginCheck"
			password-parameter="password" username-parameter="userName"
			authentication-failure-url="/login?login_error=1"
			always-use-default-target="true" default-target-url="/index" />

		<logout logout-success-url="/login" logout-url="/logout" />

		<custom-filter ref="filterSecurityInterceptor" before="FILTER_SECURITY_INTERCEPTOR" />
	</http>

	<!-- 一个自定义的filter，必须包含authenticationManager,accessDecisionManager,securityMetadataSource三个属性， 
		我们的所有控制将在这三个类中实现，解释详见具体配置 -->
	<beans:bean id="filterSecurityInterceptor" class="com.tower.service.web.security.FilterSecurityInterceptor">
		<beans:property name="authenticationManager" ref="authenticationManager" />
		<beans:property name="accessDecisionManager" ref="accessDecisionManagerBean" />
		<beans:property name="securityMetadataSource" ref="securityMetadataSource" />
	</beans:bean>

	<authentication-manager alias="authenticationManager">
		<authentication-provider user-service-ref="loginController">
			<password-encoder ref="md5Encoder"></password-encoder>
		</authentication-provider>
	</authentication-manager>

	<beans:bean id="md5Encoder" class="com.tower.service.web.security.MD5Encoder"></beans:bean>
	<!-- 访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源 -->
	<beans:bean id="accessDecisionManagerBean"
		class="com.tower.service.web.security.AccessDecisionManager">
	</beans:bean>

	<!-- 资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问 -->
	<beans:bean id="securityMetadataSource"
		class="com.tower.service.web.security.InvocationSecurityMetadataSource">

	</beans:bean>

	<beans:bean
		class="com.tower.service.web.security.AjaxAwareAuthenticationEntryPoint"
		id="ajaxAwareAuthenticationEntryPoint">
		<beans:constructor-arg name="loginUrl" value="/login"></beans:constructor-arg>
	</beans:bean>


</beans:beans>