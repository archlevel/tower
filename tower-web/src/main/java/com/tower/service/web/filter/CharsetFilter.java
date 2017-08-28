package com.tower.service.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CharsetFilter implements Filter {

	public static final String DEFAULT_CHARSET_VALUE = "UTF-8";
	private Pattern inputCharsetPattern;
	private String defaultCharset;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.inputCharsetPattern = Pattern.compile("_charset_=([\\w-]+)", 2);
		if (this.defaultCharset == null)
			this.defaultCharset = DEFAULT_CHARSET_VALUE;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		try {
			String queryString = httpServletRequest.getQueryString();
			if (queryString != null) {
				Matcher matcher = this.inputCharsetPattern.matcher(queryString);

				if (matcher.find()) {
					MatchResult matchResult = matcher.toMatchResult();
					String charset = matchResult.group(1);
					httpServletRequest.setCharacterEncoding(charset);
					httpServletRequest.getParameterNames();
					// log.debug("Set INPUT charset to " + charset);
				}
			} else {
				httpServletRequest.setCharacterEncoding(this.defaultCharset);
			}
		} catch (UnsupportedEncodingException e) {
			try {
				httpServletRequest.setCharacterEncoding(this.defaultCharset);
			} catch (UnsupportedEncodingException ee) {
				// log.error("Failed to set INPUT charset to " + this.defaultCharset, e);
			}
		}
		chain.doFilter(httpServletRequest, response);
	}

	@Override
	public void destroy() {
	}

}
