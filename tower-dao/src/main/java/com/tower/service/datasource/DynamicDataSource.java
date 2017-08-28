package com.tower.service.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.configuration.Configuration;

import com.tower.service.concurrent.AsynBizExecutor;
import com.tower.service.config.PrefixPriorityConfig;
import com.tower.service.config.dict.ConfigFileTypeDict;
import com.tower.service.config.utils.ConfigReloadEvent;
import com.tower.service.config.utils.ConfigReloadObserver;
import com.tower.service.config.utils.MailSender;
import com.tower.service.log.LogUtils;
import com.tower.service.util.StringUtil;

/**
 * 支持xml、properties文件格式<br>
 * 配置文件目录：通过设置系统属性‘global.config.dir’设置，其默认值：/config <br>
 * 数据库配置文件名，通过设置系统属性‘database.config’设置，其默认值：database<br>
 * 默认配置文件格式：xml<br>
 * 即默认配置文件为：database.xml <br>
 * 
 * @author alexzhu
 *
 */
public class DynamicDataSource extends PrefixPriorityConfig implements
		IDataSource {

	private IDataSource delegate;
	private String hostName;

	private String datasourceImpl = "com.tower.service.datasource.DBCPDataSource";

	public String getDatasourceImpl() {
		return datasourceImpl;
	}

	public void setDatasourceImpl(String datasourceImpl) {
		this.datasourceImpl = datasourceImpl;
	}

	public DynamicDataSource() {
	}

	/**
	 * 配置文件配置项key格式 <br>
	 * prefix.driver<br>
	 * prefix.url<br>
	 * prefix.username <br>
	 * prefix.password<br>
	 * prefix.initialSize <br>
	 * prefix.maxActive <br>
	 * prefix.maxWait <br>
	 * prefix.minIdle<br>
	 * prefix.maxIdle <br>
	 * prefix.poolPreparedStatements <br>
	 * prefix.defaultReadOnly<br>
	 * prefix.logAbandoned <br>
	 * prefix.removeAbandoned <br>
	 * prefix.removeAbandonedTimeout<br>
	 * prefix.testOnBorrow <br>
	 * prefix.testWhileIdle <br>
	 * prefix.validationQuery<br>
	 * prefix.minEvictableIdleTimeMillis <br>
	 * prefix.timeBetweenEvictionRunsMillis
	 * 
	 */
	protected void build(Configuration config) {

		long start = System.currentTimeMillis();

		String prefix_ = this.getPrefix();
		logger.info("start datasource:" + prefix_);
		String driverClassName_ = config.getString(prefix_ + "driver");
		String url_ = config.getString(prefix_ + "url");
		String username_ = config.getString(prefix_ + "username");
		String password_ = config.getString(prefix_ + "password.encrypted");
		if(StringUtil.isEmpty(password_)){
			password_ = config.getString(prefix_ + "password");
		}
		Integer initialSize_ = config.getInt(prefix_ + "initialSize");
		Integer maxActive_ = config.getInt(prefix_ + "maxActive");
		Integer maxIdle_ = config.getInt(prefix_ + "maxIdle");
		Integer minIdle_ = config.getInt(prefix_ + "minIdle");
		Long maxWait_ = config.getLong(prefix_ + "maxWait");
		Integer removeAbandonedTimeout_ = config.getInt(prefix_
				+ "removeAbandonedTimeout");
		Long minEvictableIdleTimeMillis_ = config.getLong(prefix_
				+ "minEvictableIdleTimeMillis");
		Long timeBetweenEvictionRunsMillis_ = config.getLong(prefix_
				+ "timeBetweenEvictionRunsMillis");
		Boolean poolPreparedStatements_ = config.getBoolean(prefix_
				+ "poolPreparedStatements");
		Boolean defaultReadOnly_ = config.getBoolean(prefix_
				+ "defaultReadOnly");
		Boolean logAbandoned_ = config.getBoolean(prefix_ + "logAbandoned");
		Boolean removeAbandoned_ = config.getBoolean(prefix_
				+ "removeAbandoned");
		Boolean testOnBorrow_ = config.getBoolean(prefix_ + "testOnBorrow");
		Boolean testWhileIdle_ = config.getBoolean(prefix_ + "testWhileIdle");
		String validationQuery_ = config.getString(prefix_ + "validationQuery");

		IDataSource basicDataSource_ = create();
		basicDataSource_.setDriverClassName(driverClassName_);
		basicDataSource_.setUrl(url_);
		basicDataSource_.setUsername(username_);
		basicDataSource_.setPassword(password_);
		basicDataSource_.setInitialSize(initialSize_);
		basicDataSource_.setMaxActive(maxActive_);
		basicDataSource_.setMaxIdle(maxIdle_);
		basicDataSource_.setMinIdle(minIdle_);
		basicDataSource_.setMaxWait(maxWait_);
		basicDataSource_.setPoolPreparedStatements(poolPreparedStatements_);
		basicDataSource_.setDefaultReadOnly(defaultReadOnly_);
		basicDataSource_.setLogAbandoned(logAbandoned_);
		basicDataSource_.setRemoveAbandoned(removeAbandoned_);
		basicDataSource_.setRemoveAbandonedTimeout(removeAbandonedTimeout_);
		basicDataSource_.setTestOnBorrow(testOnBorrow_);
		basicDataSource_.setValidationQuery(validationQuery_);
		basicDataSource_
				.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis_);
		basicDataSource_.setTestWhileIdle(testWhileIdle_);
		basicDataSource_
				.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis_);
		try {
			this.verify(prefix_, basicDataSource_);
			if (delegate == null) {
				delegate = basicDataSource_;
			} else {
				final DataSource oldDataSource = this.delegate;
				this.delegate = basicDataSource_;
				LogUtils.timeused(logger, prefix_ + "reloaded", start);
				new AsynBizExecutor(this.getClass().getName()) {
					@Override
					public void execute() {
						try {
							long start = System.currentTimeMillis();
							Thread.sleep(2000);
							((IDataSource) oldDataSource).close();
						} catch (Exception ex) {
						}
					}
				};
			}
		} catch (Exception ex) {
			LogUtils.error(logger, ex);
			this.notify(prefix_, ex.getMessage(), configToString(config));
		}
	}

	private void verify(String prefix_, IDataSource basicDataSource_) {
		long start = System.currentTimeMillis();
		Connection conn = null;
		try {
			conn = basicDataSource_.getConnection();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		LogUtils.timeused(logger, prefix_ + "verify", start);
	}

	private void notify(String prefix_, String message, String newCfg) {
		long start = System.currentTimeMillis();
		StringBuilder reloadMsgBuffer = new StringBuilder();
		String currentTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(new Date());
		reloadMsgBuffer.append("Hi All,");
		reloadMsgBuffer.append("<br>");
		reloadMsgBuffer.append("在 ");
		reloadMsgBuffer.append(currentTime);
		reloadMsgBuffer.append("时刻，机器 " + hostName
				+ "数据库配置作了变更，但是新配置文件校验失败 ！<br>");
		reloadMsgBuffer.append("具体的失败原因如下：<br>");
		reloadMsgBuffer.append("数据源：" + this.getPrefix() + "<br>");
		reloadMsgBuffer.append("异常：" + message + "<br>");
		reloadMsgBuffer.append("该数据源配置变更如下：" + "<br>");
		reloadMsgBuffer.append(this.getConfig() + " => " + newCfg + "<br>");
		reloadMsgBuffer.append("Best Regards,<br>");
		reloadMsgBuffer.append("Anjuke API Team");

		if (StringUtil.isEmpty(dbReloadEventReceiver)) {
			return;
		}

		Map params = new HashMap();
		params.put("subject", hostName
				+ " database configure reload verify failure");
		params.put("body", reloadMsgBuffer.toString());
		LogUtils.trace(logger, "db reload mail content:" + params);
		ConfigReloadEvent event = new ConfigReloadEvent(params);
		ConfigReloadObserver observer = new ConfigReloadObserver(
				dbReloadEventReceiver);
		observer.setSender(new MailSender());
		event.addObserver(observer);
		event.fireEvent();
		LogUtils.timeused(logger, prefix_ + "notify", start);
	}

	public boolean getTestWhileIdle() {
		return this.delegate.getTestWhileIdle();
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.delegate.setTestWhileIdle(testWhileIdle);
	}

	private String dbReloadEventReceiver;

	public void setDbReloadEventReceiver(String dbReloadEventReceiver) {
		this.dbReloadEventReceiver = dbReloadEventReceiver;
	}

	public String getDriverClassName() {
		return this.delegate.getDriverClassName();
	}

	public void setDriverClassName(String driverClassName) {
		this.delegate.setDriverClassName(driverClassName);
	}

	public String getUrl() {
		return this.delegate.getUrl();
	}

	public void setUrl(String url) {
		this.delegate.setUrl(url);
	}

	public String getUsername() {
		return this.delegate.getUsername();
	}

	public void setUsername(String username) {
		this.delegate.setUsername(username);
	}

	public String getPassword() {
		return this.delegate.getPassword();
	}

	public void setPassword(String password) {
		this.delegate.setPassword(password);
	}

	/**
	 * 默认值为0
	 * 
	 * @return
	 */
	public int getInitialSize() {
		return this.delegate.getInitialSize();
	}

	public void setInitialSize(int initialSize) {
		this.delegate.setInitialSize(initialSize);
	}

	/**
	 * 默认值：8
	 * 
	 * @return
	 */
	public int getMaxActive() {
		return this.delegate.getMaxActive();
	}

	public void setMaxActive(int maxActive) {
		this.delegate.setMaxActive(maxActive);
	}

	/**
	 * 默认值：8
	 * 
	 * @return
	 */
	public int getMaxIdle() {
		return this.delegate.getMaxIdle();
	}

	public void setMaxIdle(int maxIdle) {
		this.delegate.setMaxIdle(maxIdle);
	}

	/**
	 * 默认值：0
	 * 
	 * @return
	 */
	public int getMinIdle() {
		return this.delegate.getMinIdle();
	}

	public void setMinIdle(int minIdle) {
		this.delegate.setMinIdle(minIdle);
	}

	/**
	 * 默认值：－1l
	 * 
	 * @return
	 */
	public long getMaxWait() {
		return this.delegate.getMaxWait();
	}

	public void setMaxWait(long maxWait) {
		this.delegate.setMaxWait(maxWait);
	}

	/**
	 * 默认值：false
	 * 
	 * @return
	 */
	public boolean isPoolPreparedStatements() {
		return this.delegate.isPoolPreparedStatements();
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.delegate.setPoolPreparedStatements(poolPreparedStatements);
	}

	/**
	 * 默认值：false
	 * 
	 * @return
	 */
	public boolean getDefaultReadOnly() {
		return this.delegate.getDefaultReadOnly();
	}

	/**
	 * 默认值：false
	 * 
	 * @return
	 */
	public int getRemoveAbandonedTimeout() {
		return this.delegate.getRemoveAbandonedTimeout();
	}

	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.delegate.setRemoveAbandonedTimeout(removeAbandonedTimeout);
	}

	public void setDefaultReadOnly(boolean defaultReadOnly) {
		this.delegate.setDefaultReadOnly(defaultReadOnly);
	}

	/**
	 * 默认值：false
	 * 
	 * @return
	 */
	public boolean getLogAbandoned() {
		return this.delegate.getLogAbandoned();
	}

	public void setLogAbandoned(boolean logAbandoned) {
		this.delegate.setLogAbandoned(logAbandoned);
	}

	/**
	 * 默认值：false
	 * 
	 * @return
	 */
	public boolean getRemoveAbandoned() {
		return this.delegate.getRemoveAbandoned();
	}

	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.delegate.setRemoveAbandoned(removeAbandoned);
	}

	/**
	 * 默认值：false
	 * 
	 * @return
	 */
	public boolean getTestOnBorrow() {
		return this.delegate.getTestOnBorrow();
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.delegate.setTestOnBorrow(testOnBorrow);
	}

	/**
	 * 默认值：null
	 * 
	 * @return
	 */
	public String getValidationQuery() {
		return this.delegate.getValidationQuery();
	}

	public void setValidationQuery(String validationQuery) {
		this.delegate.setValidationQuery(validationQuery);
	}

	/**
	 * 默认值：1000L
	 * 
	 * @return
	 */
	public long getMinEvictableIdleTimeMillis() {
		return this.delegate.getMinEvictableIdleTimeMillis();
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.delegate.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
	}

	/**
	 * 默认值：－1L
	 * 
	 * @return
	 */
	public long getTimeBetweenEvictionRunsMillis() {
		return this.delegate.getTimeBetweenEvictionRunsMillis();
	}

	public void setTimeBetweenEvictionRunsMillis(
			long timeBetweenEvictionRunsMillis) {
		this.delegate
				.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
	}

	@Override
	public int getMaxOpenPreparedStatements() {
		return this.delegate.getMaxOpenPreparedStatements();
	}

	@Override
	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		this.delegate.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
	}

	public IDataSource getDelegate() {
		return delegate;
	}

	public void setDelegate(IDataSource delegate) {
		this.delegate = delegate;
	}

	public Connection getConnection() throws SQLException {
		return delegate.getConnection();
	}

	public PrintWriter getLogWriter() throws SQLException {
		return delegate.getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		delegate.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		delegate.setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return delegate.getLoginTimeout();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return delegate.unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return delegate.isWrapperFor(iface);
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return delegate.getConnection(username, password);
	}

	public void close() throws SQLException {

		IDataSource basicDataSource = (IDataSource) delegate;
		basicDataSource.close();
	}

	public java.util.logging.Logger getParentLogger()
			throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("not implements");
	}

	@Override
	@PostConstruct
	public void init() {
		this.setFileName(System.getProperty(DB_CONFIG_FILE,
				DEFAULT_DB_CONFIG_NAME));
		this.setType(ConfigFileTypeDict.PROPERTIES);
		super.init();
		this.build(this.getConfig());
	}

	public static void main(String[] args) {
		System.setProperty("app.home.dir",
				"/Users/alexzhu/soa/soafw/soafw-common-dao");
		DynamicDataSource datasource = new DynamicDataSource();
		datasource.setPrefix("tsl_db");
		datasource.setType("properties");
		datasource.init();
		while (true) {
			try {
				Thread.sleep(1000);
				datasource.getString("tsl_db");
			} catch (InterruptedException e) {
				datasource.logger.error(e);
			}
		}
	}

	private IDataSource create() {
		try {
			return (IDataSource) Class.forName(datasourceImpl).newInstance();
		} catch (Exception ex) {
			logger.error("create", ex);
			throw new RuntimeException(ex);
		}
	}
}
