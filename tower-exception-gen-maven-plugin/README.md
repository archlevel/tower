## Usage:
### 1.  父项目中 maven plugin
```
    <properties>
        <exception.enum.class></exception.enum.class>
        <exception.config></exception.config>
        <exception.level>2</exception.level>
    </properties>
	<plugin>
        <groupId>com.company.service.common</groupId>
        <artifactId>exception-generator</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <configuration>
            <!-- 异常枚举类生成位置 -->
            <exceptionEnumClass>${exception.enum.class}</exceptionEnumClass>
            <!-- -->
            <dbpwd>${exception.dbpwd}</dbpwd>
            <dburl>${exception.dburl}</dburl>
            <dbuser>${exception.dbuser}</dbuser>
            <tableName>${exception.tableName}</tableName>
            <!-- 加载方式  DB FILE URL CLOSE (不生成) -->
            <loadType>${exception.loadType}</loadType>
            <exceptionLevel>${exception.level}</exceptionLevel>
            <!-- 服务代码 ，用来加载异常 请到soa_provider 表中注册 -->
            <spId>${SPID}</spId>
        </configuration>
    </plugin>
```
### 2. 添加 maven properties  若本模块不需要生成异常枚举，不配置以下参数即可
	<properties>
        <exception.enum.class>com.company.service.exception.ExceptionEnum</exception.enum.class>
        <exception.level>2</exception.level>
        <spId>00</spId>
    </properties>
### 3. gen Exception Code
	run maven command : mvn exception-generator:gen
	
### 表结构

CREATE TABLE `soa_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_name` varchar(100) DEFAULT NULL,
  `start_port` int(11) DEFAULT '8000',
  `stop_port` int(11) DEFAULT '9000',
  `service_port` int(11) DEFAULT '20880',
  `sp_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`sp_name`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

CREATE TABLE `soa_exception` (
  `id` int(11) NOT NULL auto_increment,
  `code` int(11) default '0',
  `type` tinyint(4) default '0',
  `message` varchar(500) default NULL,
  `spid` tinyint(4) default '0',
  `level` tinyint(4) default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `index2` (`code`,`type`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
	    