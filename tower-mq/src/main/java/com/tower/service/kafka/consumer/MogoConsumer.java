package com.tower.service.kafka.consumer;

import com.mgzf.sdk.mq.kafka.MessageBean;

import java.util.List;
import java.util.Map;

/**
 * kafka 消费接口
 *
 * @author mxl
 */
public interface MogoConsumer {

	/**
	 * 接收消息
	 * @param index
	 * @param isUnpack
	 * @return
	 */
	MessageBean receive(int index, boolean isUnpack);

	/**
	 * 批量接收消息
	 * @param index
	 * @param isUnpack
	 * @return
	 */
	Map<Integer,List<MessageBean>> batch(int index, boolean isUnpack);

	/**
	 * 关闭
	 * @param index
	 */
	void close(int index);

	/**
	 * 消息提交
	 * @param index
	 */
	void commit(int index);

}
