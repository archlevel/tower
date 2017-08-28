package com.tower.service.web.security.tool;

public abstract interface UrlMatcher {
	public abstract Object compile(String paramString);

	public abstract boolean pathMatchesUrl(Object paramObject,
			String paramString);

	public abstract String getUniversalMatchPattern();

	public abstract boolean requiresLowerCaseUrl();
}