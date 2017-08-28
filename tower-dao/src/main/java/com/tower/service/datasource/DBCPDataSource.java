package com.tower.service.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;

public class DBCPDataSource extends BasicDataSource implements IDataSource {
	private BasicDataSource datasource = new BasicDataSource();

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public int getInitialSize() {
		return datasource.getInitialSize();
	}

	@Override
	public void setInitialSize(int initialSize) {
		datasource.setInitialSize(initialSize);
	}

	@Override
	public int getMaxActive() {
		return datasource.getMaxActive();
	}

	@Override
	public void setMaxActive(int maxActive) {
		datasource.setMaxActive(maxActive);
	}

	@Override
	public int getMinIdle() {
		return datasource.getMinIdle();
	}

	@Override
	public void setMinIdle(int minIdle) {
		datasource.setMinIdle(minIdle);
	}

	@Override
	public int getMaxIdle() {
		return datasource.getMaxIdle();
	}

	@Override
	public void setMaxIdle(int maxIdle) {
		datasource.setMaxIdle(maxIdle);
	}

	@Override
	public long getMaxWait() {
		return datasource.getMaxWait();
	}

	@Override
	public void setMaxWait(long maxWait) {
		datasource.setMaxWait(maxWait);
	}

	@Override
	public boolean isPoolPreparedStatements() {
		return datasource.isPoolPreparedStatements();
	}

	@Override
	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.datasource.setPoolPreparedStatements(poolPreparedStatements);
	}

	@Override
	public boolean getDefaultReadOnly() {
		return datasource.getDefaultReadOnly();
	}

	@Override
	public void setDefaultReadOnly(boolean defaultReadOnly) {
		this.datasource.setDefaultReadOnly(defaultReadOnly);
	}

	@Override
	public boolean getLogAbandoned() {
		return datasource.getLogAbandoned();
	}

	@Override
	public void setLogAbandoned(boolean logAbandoned) {
		datasource.setLogAbandoned(logAbandoned);
	}

	@Override
	public boolean getRemoveAbandoned() {
		return datasource.getRemoveAbandoned();
	}

	@Override
	public void setRemoveAbandoned(boolean removeAbandoned) {
		datasource.setRemoveAbandoned(removeAbandoned);
	}

	@Override
	public int getRemoveAbandonedTimeout() {
		return datasource.getRemoveAbandonedTimeout();
	}

	@Override
	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
	}

	@Override
	public boolean getTestOnBorrow() {
		return datasource.getTestOnBorrow();
	}

	@Override
	public void setTestOnBorrow(boolean testOnBorrow) {
		datasource.setTestOnBorrow(testOnBorrow);
	}

	@Override
	public boolean getTestWhileIdle() {
		return datasource.getTestWhileIdle();
	}

	@Override
	public void setTestWhileIdle(boolean testWhileIdle) {
		datasource.setTestWhileIdle(testWhileIdle);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return datasource.getConnection(username, password);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return datasource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		datasource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		datasource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return datasource.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return datasource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return datasource.isWrapperFor(iface);
	}

	@Override
	public String getDriverClassName() {
		return datasource.getDriverClassName();
	}

	@Override
	public void setDriverClassName(String driverClassName) {
		datasource.setDriverClassName(driverClassName);
	}

	@Override
	public String getUrl() {
		return datasource.getUrl();
	}

	@Override
	public void setUrl(String url) {
		this.datasource.setUrl(url);
	}

	@Override
	public String getUsername() {
		return datasource.getUsername();
	}

	@Override
	public void setUsername(String username) {
		datasource.setUsername(username);
	}

	@Override
	public String getPassword() {
		return datasource.getPassword();
	}

	@Override
	public void setPassword(String password) {
		datasource.setPassword(password);
	}

	@Override
	public String getValidationQuery() {
		return datasource.getValidationQuery();
	}

	@Override
	public void setValidationQuery(String validationQuery) {
		datasource.setValidationQuery(validationQuery);
	}

	@Override
	public long getMinEvictableIdleTimeMillis() {
		return datasource.getMinEvictableIdleTimeMillis();
	}

	@Override
	public long getTimeBetweenEvictionRunsMillis() {
		return datasource.getTimeBetweenEvictionRunsMillis();
	}

	@Override
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
	}

	@Override
	public void setTimeBetweenEvictionRunsMillis(
			long timeBetweenEvictionRunsMillis) {
		datasource
				.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
	}

	@Override
	public int getMaxOpenPreparedStatements() {
		return datasource.getMaxOpenPreparedStatements();
	}

	@Override
	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		datasource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
	}

	@Override
	public void close() throws SQLException {
		datasource.close();
	}
}
