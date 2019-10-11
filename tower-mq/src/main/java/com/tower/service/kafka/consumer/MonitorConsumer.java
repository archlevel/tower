package com.tower.service.kafka.consumer;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.log.MgzfLogType;
import com.mgzf.sdk.log.MgzfMonitorJsonLogger;
import com.mgzf.sdk.mq.kafka.KafkaConfigConst;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.config.ApolloUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * kafka 消费者监控线程  --集群切换或topic切换都通过CONSUMERKAFKAGROUPCHANGE标志位来控制是否重启连接
 *
 * @author mxl
 */
public class MonitorConsumer implements Runnable {

	private Logger logger = LoggerFactory.getLogger(MonitorConsumer.class);

	private MessageCounter messageCounter;

	private String topicGroup;

	private long lastSucSize = 0;

	private long lastFailSize = 0;

	private long lastConsumerTime = System.currentTimeMillis();

	private static final String LOG_TYPE = "consumer-monitor-switch-warn";

	public AtomicBoolean isRun = new AtomicBoolean(true);

	public MonitorConsumer(MessageCounter messageCounter, String topicGroup) {
		this.messageCounter = messageCounter;
		this.topicGroup = topicGroup;
	}

	@Override
	public void run() {
		//需要换一个标志位，可能消费没有完全，监控先停了
		while (isRun.get()) {
			//每分钟监控消息消费的情况  总条数 每分钟消费速率 每分钟增量 失败数量 统计多长时间没有消费消息了，监控消费集群地址是否有变更
			try {
				TimeUnit.SECONDS.sleep(ApolloUtils.SDK_MONITOR_INTERVAL_TIME.get());
				long curSize = messageCounter.mesSucCount.get();
				long incSize = curSize - lastSucSize;
				lastSucSize = curSize;
				long curFailSize = messageCounter.mesFailCount.get();
				long incFailSize = curFailSize - lastFailSize;
				lastFailSize = curFailSize;
				long pretotal = incSize + incFailSize;
				pretotal = pretotal == 0 ? -1 : pretotal;
				if (pretotal == -1) {
					isRun.set(messageCounter.isRun.get());
					MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-monitor-minute", SdkContext.getAllIp(), topicGroup, 0,
							"检测到consumer-" + ((System.currentTimeMillis() - lastConsumerTime) / 1000) + "s未消费消息;历史累计消费总数=" + messageCounter.mesSucCount);
					change();
				} else {
					lastConsumerTime = System.currentTimeMillis();
					StringBuilder msg = new StringBuilder();
					msg.append("pid=").append(SdkContext.getPid())
							.append(";成功总数=").append(messageCounter.mesSucCount)
							.append(";失败总数=").append(messageCounter.mesFailCount)
							.append(";增量成功=").append(incSize)
							.append(";增量失败=").append(incFailSize)
							.append(";成功率=").append((pretotal == -1 ? -1 : incSize) / pretotal * 100).append("%");
					MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-monitor-minute", SdkContext.getAllIp(), topicGroup, 0,
							msg.toString());
				}
			} catch (Exception e) {
				logger.warn("=========MonitorConsumer fail.......", e);
			}
		}
		MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, LOG_TYPE, SdkContext.getAllIp(), topicGroup, 0,
				"MonitorConsumer正常退出;消息总数=" + messageCounter.mesSucCount + "   消息失败=" + messageCounter.mesFailCount);
	}

	private void change() {
		//如果发生集群切换或者单个topic迁移，设置此标志位为true.
		if (messageCounter.isCumsumerFinish.get()) {
			messageCounter.isRun.set(false);
			String[] kafkaGroupTopic = topicGroup.split("\\|");
			if (kafkaGroupTopic.length == SdkContext.THREE) {
				isRun.set(false);
				String consumerKey = SdkContext.warpConsumerKey(kafkaGroupTopic[0], kafkaGroupTopic[2], kafkaGroupTopic[1]);
				AbstractMessageListenerContainer container = SdkContext.CONSUMER_LISTENER_MAP.get(consumerKey);
				if (container != null) {
					String newKafkaGroup = container.getContainerProperties().getNewKafkaGroup();
					String newConsumerKey = null;
					if (!StringUtils.isEmpty(newKafkaGroup)) {
						SdkContext.CONSUMER_LISTENER_MAP.remove(consumerKey);
						container.getContainerProperties().setKafkaGroup(container.getContainerProperties().getNewKafkaGroup());
						container.getContainerProperties().setTopic(container.getContainerProperties().getNewTopic());
						//这里是为了consumer重连时，能获取到最新的topic信息-不然重连会报错
						SdkContext.KAFKA_TOPIC.put(container.getContainerProperties().getTopicKey(), container.getContainerProperties().getTopic());
						newConsumerKey = SdkContext.warpConsumerKey(container.getContainerProperties().getKafkaGroup(), container.getContainerProperties().getGroup(), container.getContainerProperties().getTopic());
						SdkContext.CONSUMER_LISTENER_MAP.put(newConsumerKey, container);
					}
					String msg = "监控到Consumer集群地址或topic发生切换:旧=" + topicGroup + "新=" + newConsumerKey + " ips=" + SdkContext.KAFKA_SERVER_CONFIG.get(KafkaConfigConst.MOGO_SDK_KAFKA_ZOOKEEPER_PREFIX + kafkaGroupTopic[0]);
					MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-monitor-switch", SdkContext.getAllIp(), topicGroup, 0,
							msg);
					container.reconnect(msg);
				} else {
					MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, LOG_TYPE, SdkContext.getAllIp(), topicGroup, 0,
							"监控到Consumer集群地址或topic发生切换,;AbstractMessageListenerContainer没有找到;key=" + topicGroup);
				}
			} else {
				MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, LOG_TYPE, SdkContext.getAllIp(), topicGroup, 0,
						"监控到Consumer集群地址或topic发生切换,但当前监控的key不正确;key=" + topicGroup);
			}
		}
	}
}
