package com.tower.service.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

public interface IDataSource extends DataSource {

	public String getDriverClassName();

	public void setDriverClassName(String driverClassName);

	public String getUrl();

	public void setUrl(String url);

	public String getUsername();

	public void setUsername(String username);

	public String getPassword();

	public void setPassword(String password);

	public int getInitialSize();

	public void setInitialSize(int initialSize);

	public int getMaxActive();

	public void setMaxActive(int maxActive);

	public int getMinIdle();

	public void setMinIdle(int minIdle);

	public int getMaxIdle();

	public void setMaxIdle(int maxIdle);

	public long getMaxWait();

	public void setMaxWait(long maxWait);

	public boolean isPoolPreparedStatements();

	public void setPoolPreparedStatements(boolean poolPreparedStatements);

	public boolean getDefaultReadOnly();

	public void setDefaultReadOnly(boolean defaultReadOnly);

	public boolean getLogAbandoned();

	public void setLogAbandoned(boolean logAbandoned);

	public boolean getRemoveAbandoned();

	public void setRemoveAbandoned(boolean removeAbandoned);

	public int getRemoveAbandonedTimeout();

	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout);

	public boolean getTestOnBorrow();

	public void setTestOnBorrow(boolean testOnBorrow);

	public boolean getTestWhileIdle();

	public void setTestWhileIdle(boolean testWhileIdle);

	public String getValidationQuery();

	public void setValidationQuery(String validationQuery);

	public int getMaxOpenPreparedStatements();

	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements);

	public long getMinEvictableIdleTimeMillis();

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis);

	public long getTimeBetweenEvictionRunsMillis();

	public void setTimeBetweenEvictionRunsMillis(
			long timeBetweenEvictionRunsMillis);

	public void close() throws SQLException;
}
