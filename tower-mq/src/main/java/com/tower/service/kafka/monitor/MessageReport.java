package com.tower.service.kafka.monitor;

import com.google.common.collect.Maps;
import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.client.KafkaProducerClient;
import com.mgzf.sdk.mq.kafka.producer.MessageBuilder;
import com.mgzf.sdk.util.DateUtil;
import com.mgzf.sdk.util.MogoSpringContextUtil;
import com.mgzf.sdk.util.ProjectInfoUtils;
import com.mgzf.sdk.util.json.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.Date;
import java.util.Map;

/**
 * @author: qqmxl
 * @Date: 2018/11/13 10:44
 * @Description:
 */

public class MessageReport {

	private static Logger logger = LoggerFactory.getLogger(MessageReport.class);

	private static final String TOPIC = "OTHER-APP-REPORT";

	private static final String TOPIC_T = "MOGO-APP-REPORT";

	private static final String JAR_NAME = "mgzf-sdk-";

	private static final String JOB_ID = "job.id";

	private static Map<String, Integer> topics = Maps.newConcurrentMap();

	static {
		topics.put(TOPIC_T, 1);
	}

	public static void sendProducer(ReportInfo info) {
		if (topics.get(info.getTopic()) == null) {
			topics.put(info.getTopic(), 1);
			send(info);
		}
	}

	/**
	 * 集群切换-需要重新上报
	 */
	public static void clean() {
		topics.clear();
		topics.put(TOPIC_T, 1);
	}

	public static void send(ReportInfo info) {
		try {
			if (ConsumerConnectStatus.INIT.name().equalsIgnoreCase(info.getStatus())) {
				info.setSdkInfo(getSdkVersion());
			}
			String jobId = System.getProperty(JOB_ID);
			info.setAppName(StringUtils.isEmpty(jobId)?ProjectInfoUtils.getProjectName():jobId);
			if (StringUtils.isEmpty(info.getAppName()) || info.getAppName().indexOf(JAR_NAME)>=0) {
				ApplicationContext appName = MogoSpringContextUtil.getApplicationContext();
				if (appName != null && StringUtils.isNotEmpty(appName.getApplicationName())) {
					info.setAppName(appName.getApplicationName());
				} else {
					info.setAppName("unknown");
				}
			}
			info.setConnectTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			info.setIp(SdkContext.getAllIp());
			info.setPid(SdkContext.getPid());

			KafkaProducerClient.getInstance().sendAsyn(MessageBuilder.withPayload(TOPIC, JsonUtil.object2JSON(info)));
		} catch (Exception e) {
			logger.warn("===send report msg fail......", e);
		}
	}

	public static String getSdkVersion() {
		StringBuilder version = new StringBuilder();
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath*:conf/git.properties");
			if (resources == null || resources.length == 0) {
				return null;
			}
			for (Resource resource : resources) {
				String jarUrl = resource.getURL().toString();
				version(version, jarUrl);
			}
		} catch (Exception e) {
			logger.warn("getSkdVersion 日志异常" + e);
		}
		return version.toString();
	}

	private static void version(StringBuilder version, String jarUrl) {
		if (StringUtils.isNotBlank(jarUrl)) {
			jarUrl = jarUrl.substring(0, jarUrl.indexOf("/conf/git.properties"));
			if (jarUrl.indexOf(JAR_NAME) >= 0) {
				int beginIndex = jarUrl.lastIndexOf(JAR_NAME);
				if (beginIndex > -1) {
					String jarPath = jarUrl.substring(beginIndex);
					int lastIndex = jarPath.indexOf(".jar");
					if (lastIndex > -1) {
						version.append(jarPath.substring(0, lastIndex + 4)).append(";");
					}
				}
			}
		}
	}

}
