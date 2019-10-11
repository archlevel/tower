package com.tower.service.kafka;


/**
 * kafka消费者客户端配置常量
 *
 * @author fanglin
 * @version 1.0
 * @date [2017-09-27]
 */
public class ConsumerConfigConst {

	/**
	 * Zookeeper quorum设置。如果有多个使用逗号分割
	 */
	public static final String ZOOKEEPER_CONNECT = "zookeeper.connect";

	public static final String KAFKA_BROKER_CONNECT = "bootstrap.servers";

	/**
	 * 决定该Consumer归属的唯一组ID
	 */
	public static final String GROUP_ID = "group.id";
	public static final String AUTO_COMMIT_ENABLE = "auto.commit.enable";
	/**
	 * true时，Consumer会在消费消息后将offset同步到zookeeper
	 */
	public static final String ZOOKEEPER_CONNECTION_TIMEOUT_MS = "zookeeper.connection.timeout.ms";
	/**
	 * 连接zk的超时时间
	 */
	public static final String ZOOKEEPER_SESSION_TIMEOUT_MS = "zookeeper.session.timeout.ms";
	/**
	 * ZooKeeper集群中leader和follower之间的同步实际
	 */
	public static final String ZOOKEEPER_SYNC_TIME_MS = "zookeeper.sync.time.ms";
	/**
	 * 自动提交的时间间隔
	 */
	public static final String AUTO_COMMIT_INTERVAL_MS = "auto.commit.interval.ms";
	/**
	 * 当zookeeper中没有初始的offset时，或者超出offset上限时的处理方式 。
	 * smallest ：重置为最小值
	 * largest:重置为最大值
	 * anything else：抛出异常给consumer
	 */
	public static final String AUTO_OFFSET_RESET = "auto.offset.reset";

	public static final String EXCLUDE_INTERNAL_TOPICS = "exclude.internal.topics";
}
