package com.tower.service.config;

import org.apache.commons.configuration.Configuration;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;


public class DynamicZookeeper extends PrefixPriorityConfig{
	
	private CuratorFramework client;
	private String connString;
	private Integer sessionTimeoutMs;
	private Integer connectionTimeoutMs;
	private Integer lockAcquireTimeout;
	private Integer maxRetryTimes = 3;
	private Integer retryIntervalInMs = 10;

	public String getConnString() {
		return connString;
	}

	public Integer getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public Integer getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public Integer getLockAcquireTimeout() {
		return lockAcquireTimeout;
	}

	public Integer getMaxRetryTimes() {
		return maxRetryTimes;
	}

	public Integer getRetryIntervalInMs() {
		return retryIntervalInMs;
	}

	public synchronized CuratorFramework getClient() {
		if (client != null
				&& client.getState() != CuratorFrameworkState.STARTED) {
			client.start();
		}
		return client;
	}

	public DynamicZookeeper() {
		super();
		this.setPrefix("zookeeper");
		super.init();
		this.build(getConfig());
	}

	@Override
	protected synchronized void build(Configuration config) {

		connString = config.getString("connectionString");//10.20.3.9:2181,10.20.6.107:2181,10.20.6.39:2181
		connectionTimeoutMs = getInteger("connectionTimeoutMs", 50);
		sessionTimeoutMs = getInteger("sessionTimeoutMs", 2 * 1000);
		lockAcquireTimeout = getInteger("lockAcquireTimeout", 1);
		maxRetryTimes = getInteger("maxRetryTimes", 3);
		retryIntervalInMs = getInteger("retryIntervalInMs", 10);

		CuratorFramework _client = CuratorFrameworkFactory.newClient(
				connString, sessionTimeoutMs, connectionTimeoutMs,
				new ExponentialBackoffRetry(retryIntervalInMs,
						maxRetryTimes));

		if (_client != null
				&& _client.getState() != CuratorFrameworkState.STARTED) {
			long start_ = System.currentTimeMillis();
			_client.start();
		}
		client = _client;
	}
}
