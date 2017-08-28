package com.tower.service.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Request {

	private static final ThreadLocal<Map<String, String>> REQUESTS = new ThreadLocal<Map<String, String>>();
	private static final ThreadLocal<Long> startTimes = new ThreadLocal<Long>();

	public static String genId() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	private static void init() {
		Map<String, String> request = REQUESTS.get();
		if (request == null) {
			REQUESTS.set(new HashMap<String, String>());
		}
	}

	public static void unset() {
		REQUESTS.remove();
	}

	private static final String XID = "XID";
	private static final String RIP = "RIP";
	private static final String XCACHED = "XCACHED";

	public static void setId(String rid) {
		String uuid = "";
		if (null == rid) {
			uuid = genId();
		} else {
			uuid = rid;
		}
		init();
		REQUESTS.get().put(XID, uuid + " ");
	}

	public static String getId() {
		init();
		String uuid = REQUESTS.get().get(XID);
		if (uuid == null || uuid.trim().length() == 0) {
			setId(null);
		}
		return REQUESTS.get().get(XID);
	}

	public static void setRIP(String remoteIp) {
		init();
		REQUESTS.get().put(RIP, remoteIp);
	}

	public static String getRIP() {
		init();
		return REQUESTS.get().get(RIP);
	}

	public static void setXCached(String remoteIp) {
		init();
		REQUESTS.get().put(XCACHED, remoteIp);
	}

	public static String getXCached() {
		init();
		return REQUESTS.get().get(XCACHED);
	}

	public static void start() {
		startTimes.set(System.currentTimeMillis());
	}

	public static Long getStartTime() {
		return startTimes.get();
	}

	public static void deleteStartTime() {
		startTimes.remove();
	}
}
