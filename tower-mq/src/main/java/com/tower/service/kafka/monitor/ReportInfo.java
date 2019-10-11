package com.tower.service.kafka.monitor;

/**
 * @author: qqmxl
 * @Date: 2018/11/13 16:16
 * @Description:
 */
public class ReportInfo {

	public ReportInfo() {
		super();
	}

	private String ip;

	private String pid;

	private String appName;

	private String topic;

	private String group;

	private String error;

	private String sdkInfo;

	private String connectTime;

	private String appPort;

	/**
	 * 初始连接--切换重连 异常重连
	 */
	private String status;


	private ReportInfo(String topic, String group, String error, String status) {
		this.topic = topic;
		this.group = group;
		this.error = error;
		this.status = status;
	}

	public static ReportInfo of(String topic, String group, String error, ConsumerConnectStatus status) {
		return new ReportInfo(topic, group, error, status.name());
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTopic() {
		return topic;
	}

	public String getGroup() {
		return group;
	}

	public String getError() {
		return error;
	}

	public String getSdkInfo() {
		return sdkInfo;
	}

	public void setSdkInfo(String sdkInfo) {
		this.sdkInfo = sdkInfo;
	}

	public String getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(String connectTime) {
		this.connectTime = connectTime;
	}

	public String getStatus() {
		return status;
	}

	public String getAppPort() {
		return appPort;
	}

	public void setAppPort(String appPort) {
		this.appPort = appPort;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
