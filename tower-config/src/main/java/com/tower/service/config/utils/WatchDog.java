package com.tower.service.config.utils;

public interface WatchDog {
	void doOnChange();
	void setDelay(long time);
    void start();
}
