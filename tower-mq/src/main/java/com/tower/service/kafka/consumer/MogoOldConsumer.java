package com.tower.service.kafka.consumer;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.mq.kafka.ConsumerConfigConst;
import com.mgzf.sdk.mq.kafka.MessageBean;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.util.Const;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * kafka 消费信息保存在zk中的consumer创建
 *
 * @author mxl
 */
public class MogoOldConsumer implements MogoConsumer {

	private Logger logger = LoggerFactory.getLogger(MogoOldConsumer.class);

	private ConsumerConnector connector;
	private List<KafkaStream<byte[], byte[]>> iterStreams;
	private String zkServers;
	private String topic;
	private String groupId;
	private int threadNum;
	private String id;
	private AtomicBoolean inited = new AtomicBoolean(false);

	public MogoOldConsumer(String zkServers, String topic, String groupId, int threadNum) {
		this.zkServers = zkServers;
		this.topic = topic;
		this.groupId = groupId;
		this.threadNum = threadNum;
		this.id = SdkContext.consumerId();
	}

	/**
	 *
	 * @param index 线程index
	 * @param isUnpack 是否拆包
	 * @return
	 */
	@Override
	public MessageBean receive(int index,boolean isUnpack) {

		MessageBean messageBean = new MessageBean();

		while(true){
			try {
				connect();
				ConsumerIterator<byte[], byte[]> iter = iterStreams.get(index).iterator();
				if (!iter.hasNext()) {
					return null;
				}
				MessageAndMetadata<byte[], byte[]> record = iter.next();
				messageBean.setPartition(record.partition());
				messageBean.setOffset(record.offset());
				messageBean.setTopic(record.topic());
				messageBean.setMessage(new String(record.message()));
				SdkContext.unpack(messageBean, isUnpack);
				break;
			}
			catch(Exception ex){
				this.release();
				logger.error("获取消息出错",ex);
			}
		}


		return messageBean;
	}

	@Override
	public Map<Integer, List<MessageBean>> batch(int index,boolean isUnpack) {
		return null;
	}

	public synchronized boolean connect(){
		if(!inited.get()){
			long start = System.currentTimeMillis();
			Properties props = new Properties();
			props.put(ConsumerConfigConst.AUTO_COMMIT_ENABLE, "true");
			props.put(ConsumerConfigConst.AUTO_COMMIT_INTERVAL_MS, "1000");
			props.put(ConsumerConfigConst.ZOOKEEPER_CONNECTION_TIMEOUT_MS, "30000");
			props.put(ConsumerConfigConst.ZOOKEEPER_SESSION_TIMEOUT_MS, "20000");
			props.put("rebalance.max.retries", "7");
			props.put("rebalance.backoff.ms", "3000");
			props.put(ConsumerConfigConst.ZOOKEEPER_SYNC_TIME_MS, "2000");
			props.put(ConsumerConfigConst.AUTO_OFFSET_RESET, "smallest");
			props.put("consumer.id", this.id);
			ConsumerConnector tmpConnector = KafkaConsumerBuilder.builder(props).withZkServer(zkServers).withGroupId(groupId).buildConsumer();
			this.connector = tmpConnector;
			Map<String, Integer> topicCountMap = new HashMap<String, Integer>(Const.DS_INITIAL_SIZE_MAP);
			topicCountMap.put(topic, threadNum);
			List<KafkaStream<byte[], byte[]>> tmpIterStreams = connector.createMessageStreams(topicCountMap).get(topic);
			this.iterStreams = tmpIterStreams;
			inited.set(true);
			logger.info("成功建立连接 timeused: "+(System.currentTimeMillis()-start));
		}
		return true;
	}

	public synchronized void release(){
		this.close(this.threadNum);
		inited.set(false);

	}

	@Override
	public void close(int threadIndex) {
		logger.info("开始释放资源");
		if (connector != null) {
			connector.shutdown();
			connector = null;
			logger.info("释放资源完毕");
		}
	}

	@Override
	public void commit(int index) {
		logger.debug("=====自動提交");
	}

	public static void main(String[] args){
		MogoOldConsumer consumer = new MogoOldConsumer("192.168.60.103:2182,192.168.60.103:2183,192.168.60.103:2184","A4","group_other_Key1",1);

		MessageBean bean = consumer.receive(0,false	);

		System.out.println(bean);
	}

}
