package com.tower.service.kafka.producer;

import com.mgzf.sdk.log.Logger;
import com.mgzf.sdk.log.LoggerFactory;
import com.mgzf.sdk.log.MgzfLogType;
import com.mgzf.sdk.log.MgzfMonitorJsonLogger;
import com.mgzf.sdk.mq.kafka.SdkContext;
import com.mgzf.sdk.mq.kafka.monitor.ConsumerConnectStatus;
import com.mgzf.sdk.mq.kafka.monitor.MessageReport;
import com.mgzf.sdk.mq.kafka.monitor.ReportInfo;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 抽象kafka基类
 *
 * @param <K>
 * @param <V>
 * @author qqmxl
 * @Date: 2018/9/7 15:36
 * @Description:
 */
public class KafkaMogoProducer<K, V> extends KafkaProducer<K, V> {

	private Logger logger = LoggerFactory.getLogger(KafkaMogoProducer.class);

	public KafkaMogoProducer(Properties properties) {
		super(properties);
	}


	/**
	 * 异步发送消息-
	 *
	 * @param messageBuilder
	 */
	public void sendAsyn(MessageBuilder messageBuilder) {
		report(false, messageBuilder);
		call(messageBuilder);
	}

	public Future<RecordMetadata> send(MessageBuilder messageBuilder) {
		report(true, messageBuilder);
		return call(messageBuilder);
	}

	private Future<RecordMetadata> call(final MessageBuilder messageBuilder) {
		final long beginTime = System.currentTimeMillis();
		return super.send((ProducerRecord<K, V>) new ProducerRecord<>(messageBuilder.getTopicName(), messageBuilder.getPartition(), messageBuilder.getKey(), messageBuilder.getValue()), new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception e) {
				if (e != null) {
					logger.warn("======>send kafka msg fail.....msgId=" + messageBuilder.getUuid() + "topic=" + messageBuilder.getTopicName() + "message=" + messageBuilder.getValue(), e);
					FailMsgWriteFile.writer(messageBuilder.getKafkaGroup(), messageBuilder.getTopicName(), messageBuilder.getValue().toString());
				} else {
					StringBuilder msg = new StringBuilder();
					String message = messageBuilder.getValue().toString();
					msg.append("uuid=").append(messageBuilder.getUuid())
							.append(";ip=").append(SdkContext.getAllIp())
							.append(";pid=").append(SdkContext.getPid())
							.append(";partition=").append(metadata.partition())
							.append(";offset=").append(metadata.offset())
							.append(";").append(message.length() > 100 ? message.substring(0, 100) + "..." : message);
					MgzfMonitorJsonLogger.log(MgzfLogType.KAFKA_MESG, "producer-suc", SdkContext.getAllIp(), messageBuilder.getTopicName(), System.currentTimeMillis() - beginTime, msg.toString());
				}
			}
		});
	}

	private void report(boolean isSyn, MessageBuilder messageBuilder) {
		MessageReport.sendProducer(ReportInfo.of(messageBuilder.getTopicName(), "", "应用启动:同步调用=" + isSyn, ConsumerConnectStatus.INIT));
	}
}
