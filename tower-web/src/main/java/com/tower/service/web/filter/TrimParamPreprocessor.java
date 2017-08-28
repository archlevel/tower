package com.tower.service.web.filter;

public class TrimParamPreprocessor implements IParamPreprocessor{

	@Override
	public String preprocessParameter(String paramName, String value) {
		return value.trim();
	}

	@Override
	public String preprocessHeader(String header, String value) {
		return value.trim();
	}
}