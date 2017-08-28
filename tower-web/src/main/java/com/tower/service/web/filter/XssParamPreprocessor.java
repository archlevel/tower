package com.tower.service.web.filter;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class XssParamPreprocessor implements IParamPreprocessor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(XssParamPreprocessor.class);

	@Override
	public String preprocessParameter(String paramName, String value) {
		return cleanXSS(value);
	}

	@Override
	public String preprocessHeader(String header, String value) {
		return cleanXSS(value);
	}

	private String cleanXSS(String value) {
		String oldValue = value;
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		if (value.indexOf("script") != -1) {
			if (value.indexOf("text/javascript") == -1) {
				value = value.replaceAll("script", "");
				value = value.replaceAll(
						"[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			}
		}

		if (!oldValue.equals(value)) {
			LOGGER.info("\r\n->>在用户请求的参数中发现有非法字符串,可能存在XSS攻击的可能\r\n.->>因此由:"
					+ oldValue + "\r\n->>替换为：\r\n" + value);
		}

		return value;
	}

}
