package com.tower.service.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tower.service.cache.CacheSwitcher;
import com.tower.service.util.Request;
import com.tower.service.web.util.HttpRequestUtil;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		String rid = request.getHeader("X-Request-ID");
		
		String cached = request.getHeader("X-Cached");
		
		String ip = HttpRequestUtil.getIpAddr(request);
		Request.setRIP(ip);
		
		LOGGER.debug("rid: {}", rid);
		
		Request.setId(rid);
		
		LOGGER.debug("{}", Request.getId());
		if("true".equalsIgnoreCase(cached)||"false".equalsIgnoreCase(cached)){
			CacheSwitcher.set(Boolean.valueOf(cached));
		}
		else{
			CacheSwitcher.set(true);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		Request.unset();
		CacheSwitcher.unset();
	}
}
