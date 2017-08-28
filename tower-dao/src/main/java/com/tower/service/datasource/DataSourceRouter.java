package com.tower.service.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter extends AbstractRoutingDataSource {
	
	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();
	/**
	 * 切换到指定的数据源
	 * @param dataSource
	 */
	public static void switch2(String dataSource) {
		dataSourceKey.set(dataSource);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceKey.get();
	}
}
