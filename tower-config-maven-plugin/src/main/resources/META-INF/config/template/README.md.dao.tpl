### dao 实现

+ 实现标准

	+ 配置必须动态加载
	+ 应用配置与资源配置分离
	
	+ 动态数据源［当数据库配置变化时，会加载最新配置］
		+ 实现类 com.tower.service.datasource.DynamicDataSource
			+ 可以通过设置datasourceImpl属性来改变数据库连接池的实现，默认:com.tower.service.datasource.DBCPDataSource[DBCP实现的封装]
			+ 其他实现:com.tower.service.datasource.StatsDataSource［DruidDataSource实现的封装］
	+ 带慢事务监控的事务管理器
		+ TowerDataSourceTransactionManager 默认 1000ms为慢事务
+ ... 

+ 配置
	
	+ 应用配置
		+ 保存在#{artifactId}-dao/src/main/resource/META-INF/config/spring
		+ spring-dao.xml
		+ 通过配置DataSourceRouter实例的targetDataSources属性列表
	+ 事务配置
		+ 找到spring-dao.xml,取消transactionManager的注释
	+ 资源&开关配置
		+ 资源文件默认保存在/config目录,当没有找到时，程序会从tsl-dao/src/main/resource/META-INF/config/local中读取配置信息
		+ database.properties
		+ acc.xml
			+ sql 防火墙配置
				+ doSqlFireWall 默认开启 true
				+ noneBaseStatementAllow 默认 true
				+ callAllow 默认 false
				+ selelctAllow 默认 true
				+ selectIntoAllow 默认 flase
				+ selectIntoOutfileAllow 默认 false
				+ selectWhereAlwayTrueCheck 默认 true
				+ selectHavingAlwayTrueCheck 默认 true
				+ selectUnionCheck 默认 true
				+ selectMinusCheck 默认 true
				+ selectExceptCheck 默认 true
				+ selectIntersectCheck 默认 true
				+ createTableAllow 默认 false
				+ dropTableAllow 默认 false
				+ alterTableAllow 默认 false
				+ renameTableAllow 默认 false
				+ hintAllow 默认 true
				+ lockTableAllow 默认 false
				+ startTransactionAllow 默认 true
				+ conditionAndAlwayTrueAllow 默认 false
				+ conditionAndAlwayFalseAllow 默认 false
				+ conditionDoubleConstAllow 默认 false
				+ conditionLikeTrueAllow 默认 false
				+ selectAllColumnAllow 默认 false
				+ deleteAllow 默认 false
				+ deleteWhereAlwayTrueCheck 默认 true
				+ deleteWhereNoneCheck 默认 true
				+ updateAllow 默认 true
				+ UpdateWhereAlayTrueCheck 默认 true
				+ UpdateWhereNoneCheck 默认 true
				+ insertAllow 默认 true
				+ mergeAllow 默认 true
				+ intersectAllow 默认 true
				+ replaceAllow 默认 true
				+ setAllow 默认 true
				+ commitAllow 默认 true
				+ rollbackAllow 默认 true
				+ useAllow 默认 true
				+ multiStatementAllow 默认 true
				+ truncateAllow 默认 false
				+ commentAllow 默认 true
				+ strictSyntaxCheck 默认 true
				+ constArithmeticAllow 默认 true
				+ limitZeroAllow 默认 false
				+ describeAllow 默认 false
				+ showAllow 默认 false
				+ schemaCheck 默认 true
				+ tableCheck 默认 true
				+ functionCheck 默认 true
				+ objectCheck 默认 true
				+ variantCheck 默认 true
				+ mustParameterized 默认 false
				+ doPrivilegedAllow 默认 true
				+ wrapAllow 默认 true
				+ metadataAllow 默认 true
				+ conditionOpXorAllow 默认 false
				+ caseConditionConstAllow 默认 true
				+ completeInsertValuesCheck 默认 true
				+ insertValuesCheckSize 默认 3
			+ slowTTime 慢事务执行时长监控 默认 1000ms
	+ 分库
		+ 通过DataSourceRouter在serviceImpl相关业务中实现
	+ 分表
		+ 通过数据访问层tabNameSuffix接口参数支持
	+ 读写分离
		+ 通过设置datasource.properties xxxx_db(读/写)、xxxx_db_slave（读）、xxxx_db_map_query（读）地址支持

+ dao生成器的使用
	
	+ 找到#{artifactId}-dao/src/main/resource/META-INF/config/local/database.properties
		+ 找到并修改相应的url、username、password信息，保存退出
	+ 找到#{artifactId}-dao/src/test/java/com/#{company}/service/#{artifactId}/dao/DaoGen.java
		+ 目前支持mysql、sql server两种形式；
		+ 找到'表名'并且替换成真实的表名eg:Code，保存退出并且直接运行该类，生成该表数据访问层代码
		+ 生成出来的代码基本上可以满足90%以上的需求，详见如下列表；
			+ ICodeDAO.java
			+ CodeIbatisDAOImpl.java
			+ CodeMapper.java
			+ Code.java
			+ CodeMapper.xml
		
+ dao操作扩展实现
	
	+ 1,在相应的对象数据访问层接口定义接口 aa
	+ 2,在mapper接口上定义相关接口 aa
	+ 3,在mapper文件上定义sql实现 id 为 aa [注意：parameterType必须为"java.util.Map"，表名必须采用‘${TowerTabName}’替换]
	+ 4,在数据访问层定义实现，实现代码如下
		
		```
		/**
	 	 * 
	 	 * @param params 操作支持的参数
	 	 * @param tabNameSuffix 分表访问支持
	     */
	     
	  pubic void aa(Map param,String tabNameSuffix){
	    
	    SqlSessionFactory sessionFactory = getSlaveSessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
	    try{
    	    IMapper<EccOrderinfo> mapper = session.getMapper(getMapperClass());
    	    Map params = new HashMap();
    	    params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));//分表访问支持
    	    List<EccOrderinfo> returnList = mapper.aa(params);
	    } catch (Exception t) {
	      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
	    } finally {
	      session.commit();
	      session.close();
	    }
	 }
		
		```