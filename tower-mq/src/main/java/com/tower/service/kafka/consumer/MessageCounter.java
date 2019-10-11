package com.tower.service.kafka.consumer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * kafka 消息计数器
 *
 * @author mxl
 */
public class MessageCounter {

	public AtomicLong mesSucCount = new AtomicLong(0);

	public AtomicLong mesFailCount = new AtomicLong(0);

	public void inrSuc() {
		mesSucCount.addAndGet(1);
	}

	public void inrSuc(int step) {
		mesSucCount.addAndGet(step);
	}

	public void inrFail() {
		mesFailCount.addAndGet(1);
	}

	public void inrFail(int step) {
		mesFailCount.addAndGet(step);
	}

	public AtomicBoolean isRun = new AtomicBoolean(true);

	public AtomicBoolean isCumsumerFinish = new AtomicBoolean(false);

}
