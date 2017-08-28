http://blog.csdn.net/m13321169565/article/details/7729422

Spring+Mybatis+Tomcat下多数据源与 atomikos 分布式事务配置

配置两个数据源

将不同数据库的mapper和xml文件分开放在两个文件夹中如 main 和 pay

Main数据库配置如下：

<bean id="mainDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://127.0.0.1:3306/main?characterEncoding=utf8&allowMultiQueries=true"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</bean>


<bean id="mainSqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="mainDataSource"/>
    <property name="configLocation" value="classpath:mybatis.xml"/>
    <property name="mapperLocations" value="classpath*:mapper/main/*.xml"/>
</bean>

<!-- scan for mappers and let them be autowired -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="mainSqlSessionFactory"/>
    <!-- Mapper接口所在包名，Spring会自动查找其下的Mapper -->
    <property name="basePackage" value="com.loftor.mapper.main"/>
</bean>


Pay数据库配置如下：

<bean id="payDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://127.0.0.1:3306/main?characterEncoding=utf8&allowMultiQueries=true"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</bean>

<bean id="paySqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="payDataSource"/>
    <property name="configLocation" value="classpath:mybatis.xml"/>
    <property name="mapperLocations" value="classpath*:mapper/pay/*.xml"/>
</bean>

<!-- scan for mappers and let them be autowired -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="paySqlSessionFactory"/>
    <!-- Mapper接口所在包名，Spring会自动查找其下的Mapper -->
    <property name="basePackage" value="com.loftor.mapper.pay"/>
</bean>

这样配置之后，代码中在不需要事务时可以像平时一样操作数据库都没有任何的问题。但是如果需要使用事务时，使用注解方式@Transactional，我尝试以下的方法：

<bean id="mainTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="mainDataSource"/>
</bean>

<bean id="payTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="payDataSource"/>
</bean>

<tx:annotation-driven transaction-manager="mainTransactionManager"/>
<tx:annotation-driven transaction-manager="payTransactionManager"/>

这样配置后数据一个结果就是main数据库的数据能正常插入，但是pay数据库中的数据没法插入，也就是说只有main数据的事务被提交，而pay数据库的事务没有被提交，如果将

<tx:annotation-driven transaction-manager="mainTransactionManager"/>
<tx:annotation-driven transaction-manager="payTransactionManager"/>
顺序换一下，则pay数据库有数据，main则没有，所以TransactionManager应该是只有一个会生效。

经过一番baidu google后，得到的信息是多数据库只能用jta分布式事务管理，而且还不支持tomcat，但是辛亏还有第三方的开源解决方案:JOTM和Atomikos等。看到这两个名字后，很自然一般人都会选择JOTM，为什么?因为它名字短……抱着试试看的态度去JOTM的官网看了一下，这真的是个古董，最后一次更新都离现在有4年多了……那就看看Atomikos吧，它分为免费版和收费版，有公司负责维护，最新版目前是3.9.3。嗯好的就它了，通过下载官方的使用示例和网上前辈们的资料，折腾如下：

1.下载相关jar包

我们需要用到的jar有
transactions-jdbc
transactions-jta
transactions-api
transactions
atomikos-utils

<dependency>  
    <groupId>com.atomikos</groupId>  
    <artifactId>transactions-jdbc</artifactId>  
    <version>3.7.0</version>  
</dependency>  
<dependency>  
    <groupId>javax.transaction</groupId>  
    <artifactId>jta</artifactId>  
    <version>1.1</version>  
</dependency>

官方下载地址在 http://www.atomikos.com/Main/InstallingTransactionsEssentials
还需要一个jta.jar包，我使用的是1.1版本
为了方便大家
我方的下载地址在 http://url.cn/UW1wOH
2.配置数据源

我使用是mysql数据库，需要注意的是数据库引擎应该使用innodb
配置数据库有两种方式，一种是使用XA，另一种当然是不使用，可以baidu之，推荐使用，一下对应的配置

<!-- 一般方式 -->
<!--<bean id="mainDataSource" class="com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean" init-method="init" destroy-method="close">-->
    <!--<property name="uniqueResourceName" value="db_main"/>-->
    <!--<property name="driverClassName" value="com.mysql.jdbc.Driver"/>-->
    <!--<property name="url" value="jdbc:mysql://127.0.0.1:3306/main?characterEncoding=utf8&allowMultiQueries=true"/>-->
    <!--<property name="user" value="root"/>-->
    <!--<property name="password" value="123456"/>-->
    <!--<property name="poolSize" value="5"/>-->
<!--</bean>-->

<!-- XA方式 -->
<bean id="mainDataSource" class="com.atomikos.jdbc.AtomikosDataSourceBean" init-method="init" destroy-method="close">
    <property name="uniqueResourceName" value="db_main"/>
    <property name="xaDataSourceClassName" value="com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"/>
    <property name="xaProperties">
        <props>
            <prop key="url">jdbc:mysql://127.0.0.1:3306/main?characterEncoding=utf8&allowMultiQueries=true</prop>
            <prop key="user">root</prop>
            <prop key="password">123456</prop>
        </props>
    </property>
    <property name="minPoolSize" value="10" />
    <property name="maxPoolSize" value="100" />
    <property name="borrowConnectionTimeout" value="30" />
    <property name="testQuery" value="select 1" />
    <property name="maintenanceInterval" value="60" />
</bean>

oracle可以使用

<bean id="oracleDataSource" class="com.atomikos.jdbc.AtomikosDataSourceBean" init-method="init" destroy-method="close">
    <property name="uniqueResourceName" value="oracleDataSource"/>
    <property name="xaDataSourceClassName" value="com.sybase.jdbc3.jdbc.SybXADataSource"/>
    <property name="xaProperties">
        <props>
            <prop key="serverName">192.168.1.10</prop>
                        <prop key="portNumber">2638</prop>
                        <prop key="databaseName">test</prop>
            <prop key="user">test</prop>
            <prop key="password">test</prop>
        </props>
    </property>
    <property name="minPoolSize" value="10" />
    <property name="maxPoolSize" value="100" />
    <property name="borrowConnectionTimeout" value="30" />
    <property name="testQuery" value="select 1" />
    <property name="maintenanceInterval" value="60" />
</bean>

其中uniqueResourceName项需要配置唯一值不能重复
多个数据源类似的配置

3.sqlsessionFactory和其他的配置一致没有区别

<bean id="mainSqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="mainDataSource"/>
    <property name="configLocation" value="classpath:mybatis.xml"/>
    <property name="mapperLocations" value="classpath*:mapper/main/*.xml"/>
</bean>

<!-- scan for mappers and let them be autowired -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="mainSqlSessionFactory"/>
    <!-- Mapper接口所在包名，Spring会自动查找其下的Mapper -->
    <property name="basePackage" value="com.loftor.mapper.main"/>
</bean>

4.事务管理器配置

<!-- 分布式事务 -->
<bean id="atomikosTransactionManager" class="com.atomikos.icatch.jta.UserTransactionManager" init-method="init" destroy-method="close">
    <property name="forceShutdown" value="true"/>
</bean>

<bean id="atomikosUserTransaction" class="com.atomikos.icatch.jta.UserTransactionImp">
    <property name="transactionTimeout" value="300"/>
</bean>


<bean id="transactionManager" class="org.springframework.transaction.jta.JtaTransactionManager">
    <property name="transactionManager" ref="atomikosTransactionManager"/>
    <property name="userTransaction" ref="atomikosUserTransaction"/>
</bean>

<tx:annotation-driven transaction-manager="transactionManager"/>


这步是关键，<tx:annotation-driven transaction-manager="transactionManager"/> 也可以写为<tx:annotation-driven/> 不需要配置transaction-manager 因为spring默认取的就是transactionManager。

到这里应该已经可以使用了。大功告成了。但是如果要对atomikos配置的话，只要增加一个jta.properties放在和log4j.properties同样的目录就行，配置的内容可以在官方文档找到，如：

com.atomikos.icatch.service=com.atomikos.icatch.standalone.UserTransactionServiceFactory
com.atomikos.icatch.log_base_name = jdbc
com.atomikos.icatch.tm_unique_name = com.atomikos.spring.jdbc.tm
com.atomikos.icatch.serializable_logging=false


1、采用数据映射器（MapperFactoryBean）的方式，不用写mybatis映射文件，采用注解方式提供相应的sql语句和输入参数。
  （1）Spring配置文件：

   <!-- 引入jdbc配置文件 -->      

 <context:property-placeholder location="jdbc.properties"/>          

 <!--创建jdbc数据源 -->        

<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">         

 <property name="driverClassName" value="${driver}"/>        

  <property name="url" value="${url}"/>         

 <property name="username" value="${username}"/>         

 <property name="password" value="${password}"/>          

<property name="initialSize" value="${initialSize}"/>          

<property name="maxActive" value="${maxActive}"/>       

   <property name="maxIdle" value="${maxIdle}"/>          

<property name="minIdle" value="${minIdle}"/>       

 </bean>        

  <!-- 创建SqlSessionFactory，同时指定数据源-->       

 <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">        

 <property name="dataSource" ref="dataSource" />         

</bean>         

  <!--创建数据映射器，数据映射器必须为接口-->  

      <bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">        

 <property name="mapperInterface" value="com.xxt.ibatis.dbcp.dao.UserMapper" />       

  <property name="sqlSessionFactory" ref="sqlSessionFactory" />        

 </bean>         

 <bean id="userDaoImpl2" class="com.xxt.ibatis.dbcp.dao.impl.UserDaoImpl2">       

 <property name="userMapper" ref="userMapper"/>  

 </bean>  

 

数据映射器UserMapper,代码如下:

public interface UserMapper {     
    @Select("SELECT * FROM user WHERE id = #{userId}")        
  User getUser(@Param("userId") long id);   
 }  
 
 
dao接口类UserDao，代码如下：
Java代码
public interface UserDao {   
    public User getUserById(User user);  
 }  
 
dao实现类UserDaoImpl2,，代码如下:
Java代码
public class UserDaoImpl2 implements UserDao {   
     private UserMapper userMapper;      
     public void setUserMapper(UserMapper userMapper) {       
      this.userMapper = userMapper;      
   }         
   public User getUserById(User user) {      
     return userMapper.getUser(user.getId());      
   }  
  }  
 
 
2、采用接口org.apache.ibatis.session.SqlSession的实现类org.mybatis.spring.SqlSessionTemplate。
    mybatis中, sessionFactory可由SqlSessionFactoryBuilder.来创建。
MyBatis-Spring 中，使用了SqlSessionFactoryBean来替代。
SqlSessionFactoryBean有一个必须属性dataSource，
另外其还有一个通用属性configLocation（用来指定mybatis的xml配置文件路径）。
   （1）Spring配置文件：
<!-- 创建SqlSessionFactory，同时指定数据源-->  
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">     
 <property name="dataSource" ref="dataSource" />     
 <!-- 指定sqlMapConfig总配置文件，订制的environment在spring容器中不在生效-->   
 <property  name="configLocation"  value="classpath:sqlMapConfig.xml"/>   
 <!--指定实体类映射文件，可以指定同时指定某一包以及子包下面的所有配置文件，mapperLocations和configLocation有一个即可，当需要为实体类指定别名时，可指定configLocation属性，再在mybatis总配置文件中采用mapper引入实体类映射文件 -->  
  <!- - <property  name="mapperLocations"  value="classpath*:com/xxt/ibatis/dbcp/**/*.xml"/>  -->
 <bean>
  (2)mybatis总配置文件sqlMapConfig.xml：
<configuration>    
 <typeAliases>     
  <typeAlias type="com.xxt.ibatis.dbcp.domain.User" alias="User" />   
 </typeAliases>     
<mappers>      
  <mapper resource="com/xxt/ibatis/dbcp/domain/user.map.xml" />      
 </mappers>  
 </configuration> 
(3)实体类映射文件user.map.xml:
<mapper namespace="com.xxt.ibatis.dbcp.domain.User">       
 <resultMap type="User" id="userMap">         
  <id property="id" column="id" />        
   <result property="name" column="name" />        
   <result property="password" column="password" />      
     <result property="createTime" column="createtime" />     
   </resultMap>     
   <select id="getUser" parameterType="User" resultMap="userMap">     
     select * from user where id = #{id}        
</select>  
 <mapper/> 
 (4)dao层接口实现类UserDaoImpl：
Java代码
public class UserDaoImpl implements  UserDao  {  
    public SqlSessionTemplate sqlSession;    
      public User getUserById(User user) {     
     return (User)sqlSession.selectOne("com.xxt.ibatis.dbcp.domain.User.getUser", user); 
     }  
    public void setSqlSession(SqlSessionTemplate sqlSession) {       
    this.sqlSession = sqlSession;      }  
  }  
3、采用抽象类org.mybatis.spring.support.SqlSessionDaoSupport提供SqlSession。
   (1)spring配置文件:
Java代码
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">      
 <property name="dataSource" ref="dataSource" />     
 <property  name="configLocation"  value="classpath:sqlMapConfig.xml"/>     
 <!-- <property  name="mapperLocations"  value="classpath*:com/xxt/ibatis/dbcp/domain/user.map.xml"/   >  -->   
</bean>    
  <bean id="sqlSession"     class="org.mybatis.spring.SqlSessionTemplate">         
 <constructor-arg index="0" ref="sqlSessionFactory" />   
 </bean>    
 <bean id="userDaoImpl3" class="com.xxt.ibatis.dbcp.dao.impl.UserDaoImpl3">     
 <!--注入SqlSessionTemplate实例 -->      
<property name="sqlSessionTemplate" ref="sqlSession" />     
  <!--也可直接注入SqlSessionFactory实例，二者都指定时，SqlSessionFactory失效 -->     
 <!-- <property name="sqlSessionFactory" ref="sqlSessionFactory" />    -->  
 </bean>  
 
 (2) dao层接口实现类UserDaoImpl3：
   
Java代码
public class UserDaoImpl3 extends SqlSessionDaoSupport implements UserDao {  
   public User getUserById(User user) {     
   return (User) getSqlSession().selectOne("com.xxt.ibatis.dbcp.domain.User.getUser", user);     
} 
  }  

参考资料

### [JTA原理文档](JTA.MD)

http://loftor.com/archives/spring-mybatis-tomcat-jta.html

http://zxlaiye.iteye.com/blog/1441299
