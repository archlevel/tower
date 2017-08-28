package com.tower.service.dao.ibatis;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;

import com.tower.service.config.DynamicConfig;
import com.tower.service.dao.util.SqlFireWall;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.reflection.MetaObject;
import com.tower.service.reflection.factory.DefaultObjectFactory;
import com.tower.service.util.NetUtils;
import com.tower.service.util.Request;
import com.tower.service.util.SPUtil;

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class StatementHandlerPlugin implements Interceptor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(StatementHandlerPlugin.class);

	private static String ip;
	private static String pid = "unknow";
	private static SqlFireWall fireWall = null;
	static {
		pid = ManagementFactory.getRuntimeMXBean().getName();
		ip = NetUtils.getLocalAddress().getHostAddress();
		pid=pid.replaceAll("localhost", ip);
		fireWall = SqlFireWall.getInstance();
	}

	protected DynamicConfig accConfig = new DynamicConfig("acc","xml");
	public StatementHandlerPlugin(){
		accConfig.init();
		fireWall.init();
	}
	public Object intercept(Invocation invocation) throws Throwable {

		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();

		MetaObject metaStatementHandler = DefaultObjectFactory
				.getMetaObject(statementHandler);

		Configuration configuration = (Configuration) metaStatementHandler
				.getValue("delegate.configuration");

		BoundSql boundSql = (BoundSql) metaStatementHandler
				.getValue("delegate.boundSql");

		metaStatementHandler.setValue("delegate.boundSql.sql",
				buildSql(boundSql.getSql(), configuration));
		if (logger.isDebugEnabled()) {
			logger.debug(
					"intercept(Invocation invocation={}) - end - return value={}", boundSql.getSql()); //$NON-NLS-1$
		}
		// 将执行权交给下一个拦截器
		Object returnObject = invocation.proceed();
		return returnObject;
	}

	private String buildSql(String sql, Configuration configuration) {
		String tempSql = sql;
		if (tempSql.indexOf(" /*from_api:") == -1) {
			String db = null;
			Environment env = null;
			if (configuration != null) {
				env = configuration.getEnvironment();
				db = configuration.getVariables().getProperty("database_name");
			}
			
			/**
			 * 删除sql \/*...*\/的多行注释，和以 -- 开始的单行注释
			 */
			Pattern p = Pattern
					.compile("(?ms)('(?:''|[^'])*')|--.*?$|/\\*.*?\\*/");
			sql = p.matcher(sql).replaceAll("$1");//\/*...*\/的多行注释，和以 -- 开始的单行注释 防止sql注入
			StringBuilder sb = new StringBuilder(sql);
			sb.append(" /*from_api:");
			sb.append(Request.getId());
			sb.append(pid);
			sb.append(" spid=");
			sb.append(SPUtil.getSpid());
			sb.append(" dbname=");
			sb.append(db);
			sb.append("*/");
			sql = sb.toString().replaceAll("\n", " ").replaceAll("\t", " ")
					.replaceAll("[\\s]+", " ");//格式化sql语句
			String tmp = sql.toLowerCase().trim();
			
//			Boolean deletable = accConfig.getBoolean(properties==null?"deletable":properties.get("prefix")+".deletable", false);
//			if(!deletable && tmp.indexOf("delete ") == 0){//屏蔽删除操作
//				throw new DataAccessException(IBatisDAOException.MSG_1_0014,
//						sql);
//			}
//			if (tmp.indexOf("update ") == 0
//					&& tmp.indexOf(" where ") == -1) {//屏蔽不带where 条件的更新操作
//				throw new DataAccessException(IBatisDAOException.MSG_1_0007,
//						sql);
//			}
			
			tempSql = tmp;
		}
		fireWall.check(tempSql);
		return tempSql;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
	private Properties properties;
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
