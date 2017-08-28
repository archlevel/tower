package com.tower.service.config.utils;

import java.util.concurrent.ThreadPoolExecutor;

import com.tower.service.config.DynamicThreadPool;

public class Executor {
	protected static org.slf4j.Logger _logger = org.slf4j.LoggerFactory
			.getLogger("trace");
	static ThreadPoolExecutor pool = DynamicThreadPool.getInstance().getPool();
	public static void execute(Runnable command) {
		pool.execute(command);
	}

	public static void destory() {
		pool.shutdown();
	}
}
