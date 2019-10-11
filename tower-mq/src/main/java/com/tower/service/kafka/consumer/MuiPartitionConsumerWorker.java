package com.tower.service.kafka.consumer;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.log.MgzfLogType;
import com.mgzf.sdk.log.MgzfMonitorJsonLogger;
import com.mgzf.sdk.mq.kafka.MessageBean;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.zk.CuratorService;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * kafka 消费者监听批量消息处理
 *
 * @author mxl
 */
public class MuiPartitionConsumerWorker implements Runnable {

	private Logger logger = LoggerFactory.getLogger(MuiPartitionConsumerWorker.class);

	private int threadIndex;

	private ContainerProperties containerProperties;

	private MogoConsumer mogoConsumer;

	private CountDownLatch countDownLatch;

	private CuratorFramework curatorFramework;

	public MuiPartitionConsumerWorker(MogoConsumer mogoConsumer, int threadIndex, ContainerProperties containerProperties, CountDownLatch countDownLatch, CuratorFramework curatorFramework) {
		this.containerProperties = containerProperties;
		this.threadIndex = threadIndex;
		this.mogoConsumer = mogoConsumer;
		this.countDownLatch = countDownLatch;
		this.curatorFramework = curatorFramework;
	}

	@Override
	public void run() {
		MessageCounter mesCount = containerProperties.getMesCount();
		try {
			while (mesCount.isRun.get()) {
				long startTime = System.currentTimeMillis();
				boolean isError = false;
				long minOffset = 0;
				int size = 0;
				Map<Integer, List<MessageBean>> messageBeanList = null;
				String[] newTopicInfo = SdkContext.KAFKA_SWITH_TOPIC.get(containerProperties.getTopicKey());
				boolean isPackage = false;
				if (newTopicInfo != null && newTopicInfo.length >= SdkContext.FOUR) {
					//控制封包解包
					isPackage = SdkContext.TRUE.equalsIgnoreCase(newTopicInfo[2]);
				}
				messageBeanList = mogoConsumer.batch(threadIndex, isPackage);
				if (messageBeanList == null || messageBeanList.size() <= 0) {
					continue;
				}
				mogoConsumer.commit(threadIndex);
				List<MessageBean> mesgList = messageBeanList.get(threadIndex);
				isError = onMessage(isError, mesgList);
				minOffset = mesgList.get(0).getOffset();
				size = mesgList.size();
				String offset = minOffset + "-" + (minOffset + size - 1);
				StringBuilder msg = new StringBuilder();
				if (isError) {
					mesCount.inrFail(size);
					msg.append("pid=").append(SdkContext.getPid()).append(";threadIndex=").append(threadIndex).append(";offset=").append(offset);
					MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-fail", SdkContext.getAllIp(), containerProperties.getGroup() + "#" + containerProperties.getTopic(), System.currentTimeMillis() - startTime, msg.toString());
				} else {
					mesCount.inrSuc(size);
					MessageBean messageBean = mesgList.get(0);
					msg.append("uuid=").append(messageBean.getUuid()).
							append(";sendTime=").append(messageBean.getSendTime())
							.append(";ip=").append(messageBean.getSendIp())
							.append(";partition=").append(messageBean.getPartition())
							.append(";offset=").append(offset)
							.append(";threadIndex=").append(threadIndex)
							.append(";").append(messageBean.getMessage().length() > 100 ? messageBean.getMessage().substring(0, 100) + "..." : messageBean.getMessage());
					MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-suc", SdkContext.getAllIp(), containerProperties.getGroup() + "#" + containerProperties.getTopic(), System.currentTimeMillis() - startTime, msg.toString());
				}
			}
		} catch (Exception e) {
			MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-error", SdkContext.getAllIp(), containerProperties.getGroup() + "#" + containerProperties.getTopic(), 0, "pid=" + SdkContext.getPid() + ";err=" + e.getMessage());
		} finally {
			countDownLatch.countDown();
			mesCount.isRun.set(false);
			mogoConsumer.close(threadIndex);
			MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-quit", SdkContext.getAllIp(), containerProperties.getGroup() + "#" + containerProperties.getTopic(), 0, "pid=" + SdkContext.getPid() + ";ThreadIndex=" + threadIndex);
		}
	}

	private boolean onMessage(boolean isError, List<MessageBean> mesgList) {
		try {
			CuratorService.writeData(containerProperties.getGroup(), containerProperties.getTopic(), threadIndex, (mesgList.get(0).getOffset() + mesgList.size()-1), curatorFramework);
			containerProperties.getIMessageBatchProcessor().messageBatchProcess(mesgList);
		} catch (Exception e) {
			isError = true;
			logger.warn("【写zk异常】不影响业务-只用于监控：", e);
		}
		return isError;
	}
}
