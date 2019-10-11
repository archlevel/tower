package com.tower.service.kafka.consumer;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.log.MgzfLogType;
import com.mgzf.sdk.log.MgzfMonitorJsonLogger;
import com.mgzf.sdk.mq.kafka.KafkaConfigConst;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.config.ApolloUtils;
import com.mgzf.sdk.mq.kafka.zk.CuratorService;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.util.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : qqmxl
 * @Date: 2018/11/16 16:23
 * @Description:
 */

public class PartitionKafkaMessageListenerContainer extends AbstractMessageListenerContainer {

	private Logger logger = LoggerFactory.getLogger(PartitionKafkaMessageListenerContainer.class);

	private MogoConsumer mogoConsumer;

	private CuratorFramework curatorFramework;

	public PartitionKafkaMessageListenerContainer() {
		logger.debug("=========");
	}

	public PartitionKafkaMessageListenerContainer(ContainerProperties properties) {
		init(properties);
	}

	private void init(ContainerProperties properties) {
		long beginTime = System.currentTimeMillis();
		this.setContainerProperties(properties);
		String brokerServersIps = SdkContext.KAFKA_SERVER_CONFIG.get(KafkaConfigConst.MOGO_SDK_KAFKA_SERVER_PREFIX + properties.getKafkaGroup());
		Assert.notNull(brokerServersIps, "Apollo配置中心【" + ApolloUtils.getCunEnv() + "】，配置参数" + KafkaConfigConst.MOGO_SDK_KAFKA_SERVER_PREFIX + properties.getKafkaGroup() + "未配置，请进行配置.");
		StringBuilder mesg = new StringBuilder();
		mesg.append("创建consumer连kafka-broker;").append("kafkaServerGroup=").append(properties.getKafkaGroup()).append("kafkaServersIps=").append(brokerServersIps).append(";").append(properties);
		this.mogoConsumer = new MogoNewConsumer(brokerServersIps, properties.getTopic(), properties.getGroup(), properties.getBatchSize(), properties.getPartitions());
		this.curatorFramework = CuratorService.getInstance(properties.getKafkaGroup());
		MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-init-partition", SdkContext.getAllIp(), properties.getGroup() + "#" + properties.getTopic(), System.currentTimeMillis() - beginTime, mesg.toString());
	}

	/**
	 * 启动线程消费
	 */
	@Override
	protected void doStart() {
		boolean isInit = false;
		while (this.isRunning()) {
			try {
				if (isInit) {
					init();
				}
				initPool("-partitionConsumer-pool-%d");
				int threadNum = this.getContainerProperties().getThreadNum();
				CountDownLatch countDownLatch = new CountDownLatch(threadNum);
				for (int i = 0; i < threadNum; i++) {
					this.getExecutorService().submit(new MuiPartitionConsumerWorker(mogoConsumer, i, this.getContainerProperties(), countDownLatch, curatorFramework));
				}
				countDownLatch.await();
				isInit = true;
				MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-init", SdkContext.getAllIp(), this.getContainerProperties().getGroup() + "#" + this.getContainerProperties().getTopic(), 0, "pid=" + SdkContext.getPid() + ";开始重连;参数=" + getContainerProperties());
			} catch (IllegalArgumentException e1) {
				isInit = true;
				logger.error("============", e1);
				MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-error", SdkContext.getAllIp(), this.getContainerProperties().getGroup() + "#" + this.getContainerProperties().getTopic(), 0, "pid=" + SdkContext.getPid() + "err=" + e1.getMessage());
			} catch (Exception e) {
				isInit = true;
				MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-error", SdkContext.getAllIp(), this.getContainerProperties().getGroup() + "#" + this.getContainerProperties().getTopic(), 0, "pid=" + SdkContext.getPid() + "err=" + e.getMessage());
			}
			finally {
				this.shutdown();
			}
		}
	}

	private void init() {
		String kafkaGroup = this.getContainerProperties().getKafkaGroup();
		String groupId = SdkContext.KAFKA_GROUP.get(this.getContainerProperties().getGroupKey());
		Assert.notNull(groupId, ApolloUtils.getCunEnv() + "，配置中心mogo.sdk.kafka.config.consumer.group." + kafkaGroup + "未配置group消费组" + this.getContainerProperties().getGroupKey() + ":映射.");
		this.getContainerProperties().setGroup(groupId);
		init(this.getContainerProperties());
	}

	@Override
	protected void doStop(Runnable var1) {
		try {
			TimeUnit.SECONDS.sleep(this.stopSleep);
		} catch (Exception e) {
			logger.warn("=====doStop sleep ..." + e);
		}
	}

	@Override
	protected void doConnect(Runnable var1) {
		shutdown();
	}

	@Override
	public boolean isLongLived() {
		return true;
	}

	@Override
	public void run() {
		start();
	}

	@Override
	protected void doClose() {
		shutdown();
	}

	private void shutdown() {
		try {
			MessageCounter messageCounter = getContainerProperties().getMesCount();
			if (messageCounter != null) {
				messageCounter.isRun.set(false);
			}
			ExecutorService executorService = this.getExecutorService();
			if (executorService != null) {
				executorService.shutdown();
			}
			if (curatorFramework != null) {
				curatorFramework.close();
			}
		} catch (Exception e) {
			logger.warn("===shutdown consumer fail.....", e);
		}
	}

}
