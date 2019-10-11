package com.tower.service.kafka.consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.mq.kafka.MessageBean;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.config.ApolloUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * kafka 消费信息保存在kafka本身的t内置topic中
 * private Iterator<ConsumerRecord<String, String>> recordIter;
 *
 * @author mxl
 */
public class MogoNewConsumer implements MogoConsumer {

	private Logger logger = LoggerFactory.getLogger(MogoNewConsumer.class);

	private Map<Integer, KafkaConsumer<String, String>> consumers = Maps.newConcurrentMap();
	private String kafkaServices;
	private String topic;
	private String groupId;
	private int batchSize;
	private int partitions;

	/**
	 * //	public MogoNewConsumer(String kafkaServers, String topic, String groupId, int batchSize) {
	 * //		Properties props = getProperties(batchSize,topic+"."+groupId);
	 * //		KafkaConsumer<String, String> consumer = KafkaConsumerBuilder.builder(props).withBorkerServer(kafkaServers).withGroupId(groupId).build();
	 * //		consumer.subscribe(Collections.singletonList(topic));
	 * //		consumers.add(consumer);
	 * //	}
	 * //	props.setProperty("client.id", topic + "." + groupId + "." + i);
	 *
	 * @param kafkaServers
	 * @param topic
	 * @param groupId
	 * @param batchSize
	 * @param partitions
	 */
	public MogoNewConsumer(String kafkaServers, String topic, String groupId, int batchSize, int partitions) {
		this.kafkaServices = kafkaServers;
		this.topic = topic;
		this.groupId = groupId;
		this.batchSize = batchSize;
		this.partitions = partitions;
		connect();
	}

	/**
	 * //	props.put("client.id", clientId);
	 *
	 * @param batchSize
	 * @param clientId
	 * @return
	 */
	public Properties getProperties(int batchSize, String clientId) {
		Properties props = new Properties();
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 0);
		props.put(ConsumerConfig.EXCLUDE_INTERNAL_TOPICS_CONFIG, "false");
		//注意该值不要改得太大，如果 poll 太多数据，而不能在下次 poll 之前消费完，则会触发一次负载均衡，产生卡顿
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, batchSize);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		return props;
	}


	/**
	 * //		if (recordIter == null || !recordIter.hasNext()) {
	 * //			recordIter = consumers.get(index).poll(SdkContext.getConsumerPollTimeOut()).iterator();
	 * //		}
	 * //		ConsumerRecord<String, String> record = recordIter.next();
	 * //		MessageBean messageBean = new MessageBean();
	 * //		//加上uuid_时间戳_ip{}
	 * //		messageBean.setPartition(record.partition());
	 * //		messageBean.setOffset(record.offset());
	 * //		messageBean.setTopic(record.topic());
	 * //		messageBean.setMessage(record.value());
	 *
	 * @param index
	 * @return
	 */
	@Override
	public MessageBean receive(int index,boolean isUnpack) {
		return null;
	}

	@Override
	public Map<Integer, List<MessageBean>> batch(int index,boolean isUnpack) {
		Map<Integer, List<MessageBean>> integerListMap = Maps.newHashMap();

		while(true){
			try {
				connect(index);
				// 请不要改得太大，服务器会掐掉空闲连接，不要超过 30000
				ConsumerRecords<String, String> records = consumers.get(index).poll(TimeUnit.SECONDS.toMillis(ApolloUtils.CONSUMER_POLL_TIMEOUT.get()));
				List<MessageBean> msgList = null;

				if (records != null && records.count() > 0) {
					MessageBean messageBean = null;
					for (ConsumerRecord<String, String> record : records) {
						messageBean = new MessageBean();
						messageBean.setPartition(record.partition());
						messageBean.setOffset(record.offset());
						messageBean.setTopic(record.topic());
						messageBean.setMessage(record.value());
						SdkContext.unpack(messageBean, isUnpack);
						msgList = integerListMap.get(record.partition());
						if (msgList == null) {
							msgList = Lists.newArrayList();
							msgList.add(messageBean);
							integerListMap.put(record.partition(), msgList);
						} else {
							msgList.add(messageBean);
						}
					}
					break;
				}
			}
			catch(Exception ex){
				this.release(index);
			}
		}

		return integerListMap;
	}

	public boolean connect(){

		for (int i = 0; i < partitions; i++) {
			this.connect(i);
		}
		return true;
	}

	public void release(int index){

		try {
			synchronized (consumers){
				KafkaConsumer<String, String> consumer = consumers.get(index);
				if (consumer != null) {
					consumer.close();
					consumers.remove(index);
					logger.info("成功释放资源");
				}
			}
		}
		catch (Exception ex){
			logger.error("释放资源失败",ex);
		}
	}

	public  boolean connect(int index){

		Properties props = getProperties(batchSize, topic + "." + groupId);
		KafkaConsumer<String, String> consumer = null;
		synchronized(consumers){
			if(!consumers.containsKey(index)){
				consumer = KafkaConsumerBuilder.builder(props).withBorkerServer(this.kafkaServices).withGroupId(groupId).build();
				consumer.assign(Arrays.asList(new TopicPartition(topic, index)));
				consumers.put(index, consumer);
				logger.info("成功建立consumer_"+index);
			}
		}

		return true;
	}

	@Override
	public void commit(int index) {
		consumers.get(index).commitSync();
	}

	@Override
	public void close(int threadIndex) {
		consumers.get(threadIndex).close();
	}

}
