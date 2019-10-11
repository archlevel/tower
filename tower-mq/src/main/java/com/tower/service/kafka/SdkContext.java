package com.tower.service.kafka;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.mq.kafka.consumer.AbstractMessageListenerContainer;
import com.mgzf.sdk.mq.kafka.producer.KafkaMogoProducer;
import org.springframework.util.StringUtils;
import scala.runtime.BoxesRunTime;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

/**
 * kafka 配置文件和发送者消费者缓存
 *
 * @author moxianglin
 */
public class SdkContext {

	private static Logger logger = LoggerFactory.getLogger(SdkContext.class);

	public static final Map<String, KafkaMogoProducer<String, String>> PRODUCER_MAP = Maps.newConcurrentMap();

	public static final Map<String, AbstractMessageListenerContainer> CONSUMER_LISTENER_MAP = Maps.newConcurrentMap();

	public static final Map<String, String> KAFKA_SERVER_CONFIG = Maps.newConcurrentMap();

	public static final Map<String, String> KAFKA_TOPIC = Maps.newConcurrentMap();

	public static final Map<String, String> KAFKA_GROUP = Maps.newConcurrentMap();

	/**
	 * topic限流
	 */
	public static final Map<String, RateLimiter> TOPIC_RATELIMITER = Maps.newConcurrentMap();

	/**
	 * 切换的topic列表-包括topic特殊标识
	 */
	public static final Map<String, String[]> KAFKA_SWITH_TOPIC = Maps.newConcurrentMap();

	public static final Map<String, String> KAFKA_CONFIG = Maps.newConcurrentMap();

	public static final String MESSAGE_SEND_TIME = "_sendTime";
	public static final String MESSAGE_UUID = "_uuid";
	public static final String REQUEST_UUID = "_requestId";
	public static final String MESSAGE_IP = "_ip";
	public static final String MESSAGE_DATA = "_context";

	public static final String TRUE = "true";

	public static final int THREE = 3;

	public static final int TWO = 2;

	public static final int FOUR = 4;

	public static final int FIVE = 5;

	public static final int EIGHT = 8;

	private static String LOCALIP;

	private static String PID;

	public static void putSdkKafkaConfig(String key, String value) {
		KAFKA_CONFIG.put(key, value);
	}

	public static void putSdkKafkaServerConfig(String key, String value) {
		KAFKA_SERVER_CONFIG.put(key, value);
	}

	public static void putProducer(String kafkaServerGroup, KafkaMogoProducer<String, String> producer) {
		PRODUCER_MAP.put(kafkaServerGroup, producer);
	}

	public static KafkaMogoProducer<String, String> getProducer(String kafkaServerGroup) {
		return PRODUCER_MAP.get(kafkaServerGroup);
	}

	public static String warpConsumerKey(String kafkaGroup, String groupId, String topic) {
		return new StringBuilder().append(kafkaGroup).append("|").append(topic).append("|").append(groupId).toString();
	}

	public static String getPid() {
		if (PID == null) {
			try {
				String name = ManagementFactory.getRuntimeMXBean().getName();
				String pid = name.split("@")[0];
				PID = pid;
			} catch (Exception e) {
				logger.warn("getPid：", e);
			}
		}
		return PID;
	}

	/**
	 * 获取系统ip（网卡）
	 *
	 * @return ips，多个网卡将会有多个ip，以“,”号分割
	 */
	public static String getAllIp() {
		if (LOCALIP == null) {
			StringBuilder sb = new StringBuilder();
			Enumeration<NetworkInterface> netInterfaces;
			try {
				netInterfaces = NetworkInterface.getNetworkInterfaces();
				InetAddress ip;
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = netInterfaces.nextElement();
					Enumeration<InetAddress> addresses = ni.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(':') == -1) {
							sb.append(ip.getHostAddress()).append(",");
						}
					}
				}
				getHostName(sb);
			} catch (Exception e) {
				logger.warn("获取系统ip时出现异常：", e);
				LOCALIP = null;
			}
		}
		return LOCALIP;
	}

	private static void getHostName(StringBuilder sb) throws UnknownHostException {
		String[] ips = sb.toString().split(",");
		if (ips.length > 0) {
			LOCALIP = ips[ips.length - 1];
		} else if (StringUtils.isEmpty(LOCALIP)) {
			LOCALIP = InetAddress.getLocalHost().getHostName();
		}
	}

	public static void unpack(MessageBean msg, boolean isUnpack) {
		if (TRUE.equalsIgnoreCase(SdkContext.KAFKA_CONFIG.get(KafkaConfigConst.MOGO_SDK_KAFKA_CONFIG_IS_PACKAGE_MSG)) || isUnpack) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(msg.getMessage());
				if (jsonObject.containsKey(SdkContext.MESSAGE_UUID)
						&& jsonObject.containsKey(SdkContext.MESSAGE_DATA)
						&& jsonObject.containsKey(SdkContext.REQUEST_UUID)) {//升级SDK是先升级消费者，此处用来适配没有包装的老消息
					msg.setMessage((String) jsonObject.get(SdkContext.MESSAGE_DATA));
					msg.setSendIp((String) jsonObject.get(SdkContext.MESSAGE_IP));
					msg.setUuid((String) jsonObject.get(SdkContext.MESSAGE_UUID));
					msg.setRequestId((String) jsonObject.get(SdkContext.REQUEST_UUID));
					msg.setSendTime((Long) jsonObject.get(SdkContext.MESSAGE_SEND_TIME));
				}
			} catch (Exception e) {
				logger.warn("msg unpack fail.....msg={} err={}", msg, e.getMessage());
			}
		}
	}

	public static String consumerId() {
		return new StringBuilder().append(getAllIp()).append("-").append(BoxesRunTime.boxToLong(System.currentTimeMillis())).append("-").append(getPid()).append("-").append(generateShortUuid()).toString();
	}

	public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z"};


	public static String generateShortUuid() {
		StringBuilder shortBuffer = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < EIGHT; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

}
