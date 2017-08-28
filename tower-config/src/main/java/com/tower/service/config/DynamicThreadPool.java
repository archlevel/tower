package com.tower.service.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tower.service.log.LogUtils;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
;

/**
 * 默认配置<br>
 * 线程池初始化大小：processNum*2<br>
 * 线程池最大大小：processNum*3<br>
 * 线程池排队等待大小：processNum*4<br>
 * 
 * @author alexzhu
 * 
 */
public class DynamicThreadPool {

	protected Logger _logger = LoggerFactory.getLogger("trace");
	private static int processNum = Runtime.getRuntime().availableProcessors();
	/**
	 * int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
	 */
	private static DynamicThreadPool instance = new DynamicThreadPool();
	protected ThreadPoolExecutor pool = null;


	private DynamicThreadPool() {
		build();
	}

	public static DynamicThreadPool getInstance() {
		return instance;
	}

	protected void build() {
		ThreadPoolExecutor _pool = new ThreadPoolExecutor(
				processNum + 1,
				processNum * 2, 
				6000, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(1000),
				new ThreadPoolExecutor.CallerRunsPolicy());
		ThreadPoolExecutor tmpPool = pool;
		pool = _pool;
		if (tmpPool != null) {
			try {
				tmpPool.awaitTermination(2, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
			}
			tmpPool.shutdown();
		}
	}

	public ThreadPoolExecutor getPool() {
		LogUtils.trace(_logger, "tpool anum: " + pool.getActiveCount() + " ,psize:" + pool.getPoolSize() + ",qsize: " + pool.getQueue().size());
		return pool;
	}
}
