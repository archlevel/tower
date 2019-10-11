package com.tower.service.kafka.client;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.log.Request;
import com.mgzf.sdk.mq.kafka.KafkaConfigConst;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.config.ApolloUtils;
import com.mgzf.sdk.mq.kafka.exception.KafkaException;
import com.mgzf.sdk.mq.kafka.exception.RateLimiterException;
import com.mgzf.sdk.mq.kafka.producer.*;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.utils.AppInfoParser;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * kafka生产者客户端重构
 *
 * @author mxl
 */
@Component
public class KafkaProducerClient implements AutoCloseable {
	private Logger logger = LoggerFactory.getLogger(KafkaProducerClient.class);

	private static KafkaProducerClient kafkaProducerClient = null;

	private ReentrantLock reentrantLock = new ReentrantLock();

	private static final String TRUE = "true";

	/**
	 * 实例化永远 给 kafkaProducerClient
	 */
	public KafkaProducerClient() {
		kafkaProducerClient = this;
	}

	public static KafkaProducerClient getInstance() {
		if (kafkaProducerClient == null) {
			kafkaProducerClient = new KafkaProducerClient();
		}
		return kafkaProducerClient;
	}

	private KafkaMogoProducer<String, String> getProducerInstance(String kafkaServerGroup) {
		KafkaMogoProducer<String, String> producer = SdkContext.getProducer(kafkaServerGroup);
		if (producer == null) {
			String kafkaServersIps = null;
			Long beginTime = System.currentTimeMillis();
			try {
				reentrantLock.lock();
				producer = SdkContext.getProducer(kafkaServerGroup);
				if (producer == null) {
					kafkaServersIps = SdkContext.KAFKA_SERVER_CONFIG.get(KafkaConfigConst.MOGO_SDK_KAFKA_SERVER_PREFIX + kafkaServerGroup);
					Assert.notNull(kafkaServersIps, "Apollo配置中心【" + ApolloUtils.getCunEnv() + "】，配置参数mogo.sdk.kafka.server." + kafkaServerGroup + "未配置，请进行配置.");
					producer = KafkaProducerBuilder.builder().withServer(kafkaServersIps).build();
					SdkContext.putProducer(kafkaServerGroup, producer);
				}
			} finally {
				reentrantLock.unlock();
				logger.info("|Monitor|Producer|{}|{}|{}|{}|kafkaServerGroup={}|kafkaServersIps={}|times(ms)={}", AppInfoParser.getVersion(), SdkContext.getAllIp(), SdkContext.getPid(), "创建producer连接", kafkaServerGroup, kafkaServersIps, System.currentTimeMillis() - beginTime);
			}
		}
		return producer;
	}

	/**
	 * 发消息后关闭连接
	 *
	 * @param kafkaServerGroup
	 * @param topicKey
	 * @param partition
	 * @param message
	 * @param isSync
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public Map<Integer, Long> pingSendMessage(String kafkaServerGroup, String topicKey, int partition, Object message, boolean isSync) {
		try {
			return sendMessage(kafkaServerGroup, topicKey, partition, message, isSync);
		} finally {
			producerClose(kafkaServerGroup);
		}
	}

	/**
	 * 生产者发送消息
	 *
	 * @param topicKey  topic名称对应的key
	 * @param partition partition，如partition = -1，则消息随机分发到不同的partition中；如partition >= 0,则消息发送到指定partition中。
	 * @param message   具体消息，最终以string的方式发送到kafka中，如果传输Bean，则需要在Bean中实现toString()方法
	 * @param isSync    是否同步发送，true=同步；false=异步
	 * @return 发送在各个partition中的消息数，Map<Integer,Long> key=partition value=在该partition中的消息数量
	 * @deprecated
	 */
	@Deprecated
	public Map<Integer, Long> sendMessage(String kafakServerGroup, String topicKey, int partition, Object message, boolean isSync) {
		Assert.notNull(message, "消息发送内容为空-topicKey=" + topicKey);
		List<Object> data = new ArrayList<Object>();
		data.add(message);
		return sendMessages(kafakServerGroup, topicKey, partition, data, isSync);
	}

	/**
	 * 生产者发送消息
	 *
	 * @param topicKey topic名称对应的key
	 * @param message  具体消息，最终以string的方式发送到kafka中，如果传输Bean，则需要在Bean中实现toString()方法
	 * @param isSync   是否同步发送，true=同步；false=异步
	 * @return 发送在各个partition中的消息数，Map<Integer,Long> key=partition value=在该partition中的消息数量
	 * @deprecated
	 */
	@Deprecated
	public Map<Integer, Long> sendMessage(String kafakServerGroup, String topicKey, Object message, boolean isSync) {
		return sendMessage(kafakServerGroup, topicKey, -1, message, isSync);
	}

	/**
	 * 生产者发送消息
	 *
	 * @param topicKey topic名称对应的key
	 * @param data     具体消息集合，最终以string的方式发送到kafka中，如果传输Bean，则需要在Bean中实现toString()方法
	 * @return 发送在各个partition中的消息数，Map<Integer,Long> key=partition value=在该partition中的消息数量
	 * @deprecated
	 */
	@Deprecated
	public Map<Integer, Long> sendMessages(String kafakServerGroup, String topicKey, List<?> data, boolean isSync) {
		Assert.isTrue(!data.isEmpty(), "消息发送内容为空-topicKey=" + topicKey);
		return sendMessages(kafakServerGroup, topicKey, -1, data, isSync);
	}

	public Long send(MessageBuilder messageBuilder) {
		change(messageBuilder);
		Map<Integer, Long> totalMap = sendMessages(messageBuilder, true);
		Long sendTotalSize = 0L;
		for (Map.Entry<Integer, Long> entry : totalMap.entrySet()) {
			sendTotalSize = sendTotalSize + entry.getValue();
		}
		return sendTotalSize;
	}

	private void change(MessageBuilder messageBuilder) {
		if (messageBuilder.getValue() != null) {
			List<Object> data = new ArrayList<Object>();
			data.add(messageBuilder.getValue());
			messageBuilder.setvList(data);
		}
	}

	/**
	 * 异步发送，在极端情况下可以会丢消息--不影响主线程-性能较高
	 *
	 * @param messageBuilder
	 */
	public void sendAsyn(MessageBuilder messageBuilder) {
		change(messageBuilder);
		sendMessages(messageBuilder, false);
	}

	/**
	 * 限流
	 * RateLimiter rateLimiter = SdkContext.TOPIC_RATELIMITER.get(topic);
	 * 		if (rateLimiter != null) {
	 * 			//zuse
	 * 			rateLimiter.acquire();
	 * //			if (rateLimiter.tryAcquire())
	 * //			Assert.notNull(null, "Topic已经超速被限流:topic=" + topic + " ;当前速率t/s=" + rateLimiter.getRate());
	 *                }
	 * @param messageBuilder
	 * @param isSync
	 * @return
	 */
	private Map<Integer, Long> sendMessages(MessageBuilder messageBuilder, boolean isSync) {
		ApolloUtils.checkboxGroup(messageBuilder.getKafkaGroup());
		String topic = SdkContext.KAFKA_TOPIC.get(messageBuilder.getTopicName());
		Map<Integer, Long> dataCount = Maps.newHashMap();
		KafkaMogoProducer<String, String> producer = null;
		boolean isPackage = false;
		boolean topicSendMsgClose = false;
		String limit=null;
		String[] newTopicInfo = SdkContext.KAFKA_SWITH_TOPIC.get(messageBuilder.getTopicName());
		if (newTopicInfo != null && newTopicInfo.length >= SdkContext.FOUR) {
			topic = newTopicInfo[1];
			messageBuilder.setKafkaGroup(newTopicInfo[0]);
			//控制封包解包
			isPackage = TRUE.equalsIgnoreCase(newTopicInfo[2]);
			//是否发送
			topicSendMsgClose = TRUE.equals(newTopicInfo[3]);
			limit=newTopicInfo.length >= SdkContext.FIVE?newTopicInfo[4]:null;
		}
		Assert.notNull(topic, ApolloUtils.getCunEnv() + "，配置中心mogo.sdk.kafka.config.topic." + messageBuilder.getKafkaGroup() + "未配置" + messageBuilder.getTopicName() + ":映射.");
		if (saveFile(messageBuilder, topic, dataCount, topicSendMsgClose)) {
			return dataCount;
		}
		if (limit!=null) {
			limit(messageBuilder, topic, limit);
		}
		Integer partition = 0;
		try {
			producer = this.getProducerInstance(messageBuilder.getKafkaGroup());
			MessageBuilder newMessageBuilder = null;
			for (Object obj : messageBuilder.getvList()) {
				String message = obj.toString();
				newMessageBuilder = MessageBuilder.withPayload(messageBuilder.getKafkaGroup(), topic, message, messageBuilder.getUuid()).partition(messageBuilder.getPartition()).key(messageBuilder.getKey());
				newMessageBuilder.setValue(packageMsg(newMessageBuilder, message, isPackage));
				partition = isSync ? sync(producer, newMessageBuilder) : asyn(producer, newMessageBuilder);
				// 总记录数
				Long oldCount = dataCount.get(partition);
				oldCount = oldCount == null ? 0L : oldCount;
				dataCount.put(partition, oldCount + 1);
			}
		} catch (Exception e) {
			logger.error("sdk sendMessage error for messageBuilder=" + messageBuilder + " cause by,", e);
			FailMsgWriteFile.writer(messageBuilder.getKafkaGroup(), topic, messageBuilder.getvList());
			producerClose(messageBuilder.getKafkaGroup());
			throw new KafkaException(e);
		}
		return dataCount;
	}

	private void limit(MessageBuilder messageBuilder, String topic, String limit) {
		try{
			boolean isLimited = checkLimited(topic, Integer.parseInt(limit), messageBuilder.getvList());
			if (!isLimited){
				RateLimiterWriter.writer(messageBuilder.getKafkaGroup(), topic, messageBuilder.getvList());
				throw new RateLimiterException("RateLimiter happened on "+ messageBuilder.getKafkaGroup() +": "+ topic+", message size: "+messageBuilder.getvList().size());
			}
		}
		catch(NumberFormatException e){
			logger.warn("kafka-sdk fond Topic ["+topic+"] limited is not a number ["+limit+"]");
		}
	}

	private boolean checkLimited(String topic, int limited, List valueList) {
		if (!CollectionUtils.isEmpty(valueList) && limited > 0) {
			RateLimiter rateLimiter = SdkContext.TOPIC_RATELIMITER.computeIfAbsent(topic, t -> initialRateLimiter(limited));
			if (!rateLimiter.tryAcquire(valueList.size(), 1, TimeUnit.NANOSECONDS)){
				return false;
			}
		}
		return true;
	}

	private RateLimiter initialRateLimiter(int limited) {
		return RateLimiter.create(limited);
	}

	private Integer sync(KafkaMogoProducer<String, String> producer, MessageBuilder newMessageBuilder) throws InterruptedException, ExecutionException, TimeoutException {
		Future<RecordMetadata> result = producer.send(newMessageBuilder);
		producer.flush(); //立即发送
		RecordMetadata recordMetadata = result.get(20, TimeUnit.SECONDS);
		return recordMetadata.partition();
	}

	private Integer asyn(KafkaMogoProducer<String, String> producer, MessageBuilder newMessageBuilder) {
		producer.sendAsyn(newMessageBuilder);
		return 0;
	}

	private boolean saveFile(MessageBuilder messageBuilder, String topic, Map<Integer, Long> dataCount, boolean topicSendMsgClose) {
		Integer partition = messageBuilder.getPartition();
		if (TRUE.equalsIgnoreCase(SdkContext.KAFKA_CONFIG.get(KafkaConfigConst.MOGO_SDK_KAFKA_CONFIG_IS_DISK)) || topicSendMsgClose) {
			logger.info("消息写入磁盘...topicKey={}", topic);
			LocalFileWrite.writer(messageBuilder.getKafkaGroup(), topic, messageBuilder.getvList());
			dataCount.put(partition == null ? 0 : partition, Long.valueOf(messageBuilder.getvList().size()));
			return true;
		}
		return false;
	}

	/**
	 * 生产者发送消息
	 *
	 * @param topicKey  topic名称对应的key
	 * @param partition partition，如partition = -1，则消息随机分发到不同的partition中；如partition >= 0,则消息发送到指定partition中。
	 * @param data      具体消息集合，最终以string的方式发送到kafka中，如果传输Bean，则需要在Bean中实现toString()方法
	 * @return 发送在各个partition中的消息数，Map<Integer,Long> key=partition value=在该partition中的消息数量
	 * //String kafkaGroupIpList = Utils.getProducerServers(kafkaGroup);
	 * //Assert.notNull(kafkaGroupIpList, "Apollo配置中心mogo.sdk.kafka.server."+kafkaGroup+"未配置，请核对kafka服务组后再进行消息发送.");
	 * RecordMetadata recordMetadata = result.get(5, TimeUnit.SECONDS);
	 * @deprecated
	 */
	@Deprecated
	public Map<Integer, Long> sendMessages(String kafkaGroup, String topicKey, int partition, List<?> data, boolean isSync) {
		return sendMessages(MessageBuilder.withBatchPayload(kafkaGroup, topicKey, data).partition(partition < 0 ? null : partition), isSync);
	}

	/**
	 * 对应用消息进行封装{_sendTime，_uuid，_requestId，_ip，_context}，其中_context存应用消息内容
	 * @param messageBuilder
	 * @param message
	 * @param isPackage
	 * @return
	 */
	private String packageMsg(MessageBuilder messageBuilder, String message, boolean isPackage) {
		if (TRUE.equalsIgnoreCase(SdkContext.KAFKA_CONFIG.get(KafkaConfigConst.MOGO_SDK_KAFKA_CONFIG_IS_PACKAGE_MSG)) || isPackage) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(SdkContext.MESSAGE_SEND_TIME, System.currentTimeMillis());
			jsonObject.put(SdkContext.MESSAGE_UUID, StringUtils.isEmpty(messageBuilder.getUuid()) ? "" : messageBuilder.getUuid());
			String requestId = Request.getRequestId();
			jsonObject.put(SdkContext.REQUEST_UUID, StringUtils.isEmpty(requestId) ? "" : requestId);
			jsonObject.put(SdkContext.MESSAGE_IP, SdkContext.getAllIp());
			jsonObject.put(SdkContext.MESSAGE_DATA, message);
			message = jsonObject.toString();
		}
		return message;
	}

	public void producerClose(String kafkaServerGroup) {
		try {
			KafkaMogoProducer producer = SdkContext.PRODUCER_MAP.remove(kafkaServerGroup);
			if (producer != null) {
				logger.info("kafkaServerGroup producer has been closed");
				producer.close();
			}
		} catch (Exception e) {
			logger.error("Close producer failed", e);
		}
	}

	@PreDestroy
	public void destroy() {
		logger.info("================>>close kafkaProduct......");
		for (Map.Entry<String, KafkaMogoProducer<String, String>> producer : SdkContext.PRODUCER_MAP.entrySet()) {
			producerClose(producer.getKey());
		}
	}

	@Override
	public void close() {
		logger.debug("================>>此方式和sonar规则使用冲突，关闭采用spring方式管理销毁......");
	}

}
