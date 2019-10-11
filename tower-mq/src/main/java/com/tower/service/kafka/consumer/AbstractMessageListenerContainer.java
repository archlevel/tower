package com.tower.service.kafka.consumer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.log.MgzfLogType;
import com.mgzf.sdk.log.MgzfMonitorJsonLogger;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.monitor.ConsumerConnectStatus;
import com.mgzf.sdk.mq.kafka.monitor.MessageReport;
import com.mgzf.sdk.mq.kafka.monitor.ReportInfo;
import org.springframework.scheduling.SchedulingAwareRunnable;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author: qqmxl
 * @Date: 2018/11/16 16:14
 * @Description:
 */
public abstract class AbstractMessageListenerContainer implements SchedulingAwareRunnable, MessageListenerContainer, AutoCloseable {

	private Logger logger = LoggerFactory.getLogger(AbstractMessageListenerContainer.class);

	private volatile boolean running = true;

	public int stopSleep = 60;

	public static final int WATI_SLEEP = 2000;

	private ExecutorService executorService;

	private ContainerProperties containerProperties;

	@Override
	public final void start() {
		MessageReport.send(ReportInfo.of(getContainerProperties().getTopic(), getContainerProperties().getGroup(), "应用启动:" + getContainerProperties(), ConsumerConnectStatus.INIT));
		this.doStart();
	}

	@Override
	public final void close() {
		MessageReport.send(ReportInfo.of(getContainerProperties().getTopic(), getContainerProperties().getGroup(), "应用退出:" + getContainerProperties(), ConsumerConnectStatus.CLOSE));
		MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-close", SdkContext.getAllIp(), this.getContainerProperties().getGroup() + "#" + this.getContainerProperties().getTopic(), 0, "pid=" + SdkContext.getPid() + ";" + containerProperties);
		setRunning(false);
		this.doClose();
	}

	@Override
	public final void reconnect(String error) {
		MessageReport.send(ReportInfo.of(getContainerProperties().getTopic(), getContainerProperties().getGroup(), "应用重连error=：" + error + ";参数=" + getContainerProperties(), ConsumerConnectStatus.RECONNECT));
		MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-reconnect", SdkContext.getAllIp(), this.getContainerProperties().getGroup() + "#" + this.getContainerProperties().getTopic(), 0, "pid=" + SdkContext.getPid() + ";" + containerProperties);
		final CountDownLatch latch = new CountDownLatch(1);
		this.doConnect(new Runnable() {

			@Override
			public void run() {
				latch.countDown();
			}
		});
		await(latch, WATI_SLEEP, "====reconnect InterruptedException");
	}

	/**
	 * 重连
	 *
	 * @param callbac
	 */
	protected abstract void doConnect(Runnable callbac);

	/**
	 * 关闭
	 */
	protected abstract void doClose();

	public void switchGroup() {
		containerProperties.setNewKafkaGroup(null);
		containerProperties.setNewTopic(null);
		containerProperties.getMesCount().isCumsumerFinish.set(true);
	}

	public void switchGroup(String newKafkaGroup, String newTopic) {
		containerProperties.getMesCount().isCumsumerFinish.set(true);
		containerProperties.setNewKafkaGroup(newKafkaGroup);
		containerProperties.setNewTopic(newTopic);
	}

	/**
	 * 启动
	 */
	protected abstract void doStart();

	@Override
	public final void stop(String error) {
		MessageReport.send(ReportInfo.of(getContainerProperties().getTopic(), getContainerProperties().getGroup(), "应用停止error=：" + error + ";参数=" + getContainerProperties(), ConsumerConnectStatus.STOP));
		MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "consumer-stop", SdkContext.getAllIp(), this.getContainerProperties().getGroup() + "#" + this.getContainerProperties().getTopic(), 0, "pid=" + SdkContext.getPid() + ";" + containerProperties);
		final CountDownLatch latch = new CountDownLatch(1);
		this.stop(new Runnable() {

			@Override
			public void run() {
				latch.countDown();
			}
		});
		await(latch, 1000, "====stop InterruptedException");
	}

	private void await(CountDownLatch latch, int i, String s) {
		try {
			latch.await(i, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.warn(s + e);
		}
	}

	public void stop(Runnable callback) {
		this.doStop(callback);
	}

	/**
	 * 关闭
	 *
	 * @param var1
	 */
	protected abstract void doStop(Runnable var1);

	protected void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return this.running;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public ContainerProperties getContainerProperties() {
		return containerProperties;
	}

	public void setContainerProperties(ContainerProperties containerProperties) {
		this.containerProperties = containerProperties;
	}

	public String initPool(String s) {
		String name = containerProperties.getKafkaGroup() + "|" + containerProperties.getTopic() + "|" + containerProperties.getGroup();
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
				.setNameFormat(name.toLowerCase(Locale.getDefault()) + s).build();
		executorService = newFixedThreadPool(containerProperties.getThreadNum(), namedThreadFactory);
		MessageCounter mesCount = new MessageCounter();
		//启动监控线程
		Thread task = new Thread(new MonitorConsumer(mesCount, name));
		task.setName("MonitorConsumer_" + name + "_" + System.currentTimeMillis());
		task.setDaemon(true);
		task.start();
		containerProperties.setMesCount(mesCount);
		return name;
	}

}
