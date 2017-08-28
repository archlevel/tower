package com.tower.service.log;

import com.tower.service.util.Request;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class IDConverter extends ClassicConverter {

	@Override
	public String convert(ILoggingEvent event) {
		return Request.getId();
	}

}
