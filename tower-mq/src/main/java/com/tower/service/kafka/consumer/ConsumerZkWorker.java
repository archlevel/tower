package com.tower.service.kafka.consumer;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.log.MgzfLogType;
import com.mgzf.sdk.log.MgzfMonitorJsonLogger;
import com.mgzf.sdk.mq.kafka.MessageBean;
import com.mgzf.sdk.mq.kafka.SdkContext;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

/**
 * kafka 单个消息消费处理
 *
 * @author mxl
 */
public class ConsumerZkWorker implements Runnable {

	private Logger logger = LoggerFactory.getLogger(ConsumerZkWorker.class);

	private MogoConsumer mogoConsumer;

	private int threadIndex;

	private CountDownLatch countDownLatch;

	private ContainerProperties containerProperties;

	public ConsumerZkWorker(MogoConsumer mogoConsumer, int threadIndex, ContainerProperties containerProperties, CountDownLatch countDownLatch) {
		this.mogoConsumer = mogoConsumer;
		this.threadIndex = threadIndex;
		this.containerProperties = containerProperties;
		this.countDownLatch = countDownLatch;
		logger.info("new zk worker");
	}

	/**
	 * //mogoConsumer.close();暂时不调用关闭所有consumer
	 */
	@Override
	public void run() {
		MessageCounter mesCount = containerProperties.getMesCount();
		Method method = containerProperties.getMethod();
		try {
			while (mesCount.isRun.get()) {

				long startTime = System.currentTimeMillis();
				String[] newTopicInfo = SdkContext.KAFKA_SWITH_TOPIC.get(containerProperties.getTopicKey());
				boolean isPackage = false;
				if (newTopicInfo != null && newTopicInfo.length >= SdkContext.FOUR) {
					//控制封包解包
					isPackage = SdkContext.TRUE.equalsIgnoreCase(newTopicInfo[2]);
				}
				MessageBean messageBean = mogoConsumer.receive(threadIndex, isPackage);
				if (messageBean == null) {
					continue;
				}
				mogoConsumer.commit(threadIndex);
				Boolean isError = onMessage(method, messageBean);
				if (isError) {
					mesCount.inrFail();
					MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-fail", SdkContext.getAllIp(), containerProperties.getGroup() + "#" + containerProperties.getTopic(), System.currentTimeMillis() - startTime, "pid=" + SdkContext.getPid() + ";ThreadIndex=" + threadIndex);
				} else {
					mesCount.inrSuc();
					StringBuilder msg = new StringBuilder();
					log(startTime, messageBean, msg);
				}
			}
		} catch (Exception e) {
			logger.error("======Consumer poll msg fail.....", e);
			MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-error", SdkContext.getAllIp(), containerProperties.getGroup() + "#" + containerProperties.getTopic(), 0, "pid=" + SdkContext.getPid() + ";ThreadIndex: " + threadIndex + ";运行异常=" + e.getMessage());
		} finally {
			countDownLatch.countDown();
			mesCount.isRun.set(false);
			mogoConsumer.close(threadIndex);
			MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-quit", SdkContext.getAllIp(), containerProperties.getGroup() + "#" + containerProperties.getTopic(), 0, "pid=" + SdkContext.getPid() + ";ThreadIndex=" + threadIndex);
		}
	}

	private void log(long startTime, MessageBean messageBean, StringBuilder msg) {
		msg.append("uuid=").append(messageBean.getUuid()).
				append(";sendTime=").append(messageBean.getSendTime())
				.append(";ip=").append(messageBean.getSendIp())
				.append(";partition=").append(messageBean.getPartition())
				.append(";offset=").append(messageBean.getOffset())
				.append(";threadIndex=").append(threadIndex)
				.append(";").append(messageBean.getMessage().length() > 100 ? messageBean.getMessage().substring(0, 100) + "..." : messageBean.getMessage());
		MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-suc", SdkContext.getAllIp(), containerProperties.getGroup() + "#" + containerProperties.getTopic(), System.currentTimeMillis() - startTime, msg.toString());
	}

	private boolean onMessage(Method method, MessageBean messageBean) {
		try {
			if (containerProperties.getIMessageProcessor() != null) {
				containerProperties.getIMessageProcessor().messageProcess(messageBean);
			} else {
				method.invoke(containerProperties.getBean(), messageBean);
			}
		} catch (Exception e) {
			logger.error("【业务代码】处理消息过程中出现异常：", e);
			return true;
		}
		return false;
	}

}
