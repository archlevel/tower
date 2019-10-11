package com.tower.service.kafka.zk;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.mq.kafka.KafkaConfigConst;
import com.mgzf.sdk.mq.kafka.SdkContext;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * zk操作
 *
 * @author moxianglin
 */
public class CuratorService {

	private static Logger logger = LoggerFactory.getLogger(CuratorService.class);

	private static final String CONSUMER_DIR = "/consumers";
	private static final String OFFSET_DIR = "offsets";

	public synchronized static CuratorFramework getInstance(String groupCode) {
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		builder.connectString(SdkContext.KAFKA_SERVER_CONFIG.get(KafkaConfigConst.MOGO_SDK_KAFKA_ZOOKEEPER_PREFIX + groupCode));
		builder.sessionTimeoutMs(60000);
		builder.connectionTimeoutMs(30000);
		builder.retryPolicy(new RetryOneTime(3000));
		CuratorFramework curator = builder.build();
		curator.start();
		return curator;
	}

	public static void writeData(String group, String topic, int partition, long offset, CuratorFramework curator) {
		try {
			String pathAll = CONSUMER_DIR + "/" + group + "/" + OFFSET_DIR + "/" + topic + "/" + partition;
			Stat exists = curator.checkExists().forPath(pathAll);
			if (null == exists) {
				String path = CONSUMER_DIR + "/" + group;
				exists = curator.checkExists().forPath(path);
				if (null == exists) {
					curator.create().withMode(CreateMode.PERSISTENT).forPath(path);
				}
				path = CONSUMER_DIR + "/" + group + "/" + OFFSET_DIR;
				exists = curator.checkExists().forPath(path);
				if (null == exists) {
					curator.create().withMode(CreateMode.PERSISTENT).forPath(path);
				}
				path = CONSUMER_DIR + "/" + group + "/" + OFFSET_DIR + "/" + topic;
				exists = curator.checkExists().forPath(path);
				if (null == exists) {
					curator.create().withMode(CreateMode.PERSISTENT).forPath(path);
				}
				curator.create().withMode(CreateMode.PERSISTENT).forPath(pathAll);
			}
			curator.setData().forPath(pathAll, (offset + "").getBytes());

		} catch (Exception e) {
			logger.warn("", e);
		}
	}


}