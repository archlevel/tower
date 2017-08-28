package com.tower.service.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

/**
 * 过滤器说明： 这个是防止页面提交参数出现XSS攻击的
 * 过滤器，主要针对请求的参数进行检查，如发现有非法字符串
 * 将过滤一遍。
* @ClassName: XssFilter 
* @Description:
* @author alex.zhu
* @date 2015年7月28日 下午2:41:21
 */
public class XssFilter implements Filter {
	private FilterConfig filterConfig = null; 
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		  
		XssHttpServletRequestWrapper wrappedRequest  = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		//trim掉参数空格、过滤xss攻击关键字
		wrappedRequest.addPreprocessors(new TrimParamPreprocessor(), new XssParamPreprocessor());
		chain.doFilter(wrappedRequest, response);  		
		
	}

	@Override
	public void destroy() {
		 this.filterConfig = null; 
		
	}
}
