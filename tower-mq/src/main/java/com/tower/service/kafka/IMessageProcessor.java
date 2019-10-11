package com.tower.service.kafka;

/**
 * kafka消息处理器
 *
 * @author fanglin
 * @version 1.0
 * @date [2017-09-27]
 */
public interface IMessageProcessor {

	/**
	 * 消息处理
	 *
	 * @param messageBean 消息实体Bean
	 */
	void messageProcess(MessageBean messageBean);
}
