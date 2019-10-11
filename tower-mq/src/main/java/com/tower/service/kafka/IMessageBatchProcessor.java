package com.tower.service.kafka;

import java.util.List;

/**
 * @author : qqmxl
 * @Date: 2018/9/10 19:02
 * @Description:
 */
public interface IMessageBatchProcessor {

	/**
	 * 消息批量处理
	 * @param messageBeanList
	 * @return
	 */
	public boolean messageBatchProcess(List<MessageBean> messageBeanList);

}
