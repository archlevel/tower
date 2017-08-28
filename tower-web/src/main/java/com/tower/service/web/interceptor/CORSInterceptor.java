package com.tower.service.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class CORSInterceptor extends HandlerInterceptorAdapter{
	
	private final static Logger logger = LoggerFactory.getLogger(CORSInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

        logger.info("拦截请求，设置跨域");
        response.addHeader( "Access-Control-Allow-Origin", "*");
        response.addHeader( "Access-Control-Allow-Methods", "POST,GET");
        response.addHeader( "Access-Control-Max-Age", "1000");
		return true;
	}
}
