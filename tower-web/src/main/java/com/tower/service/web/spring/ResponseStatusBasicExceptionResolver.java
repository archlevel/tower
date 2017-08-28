package com.tower.service.web.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.tower.service.exception.basic.BasicException;

public class ResponseStatusBasicExceptionResolver extends AbstractHandlerExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		try {
			StringBuilder resultStringBuilder = new StringBuilder();
			/**
			 * 抛出的异常不在自定义异常范围内，也需要对其处理. 返回：code:500,ex.getMessage();
			 */
			if (!(ex instanceof BasicException)) {
				resultStringBuilder.append("{\"code\":500,\"message\":");
				resultStringBuilder.append("\"");
				resultStringBuilder.append(ex.getMessage());
				resultStringBuilder.append("\"}");
			} else {
				BasicException basicException = (BasicException) ex;
				resultStringBuilder = new StringBuilder();
				resultStringBuilder.append("{\"code\":").append(basicException.getCode());
				if (ex.getMessage() != null) {
					resultStringBuilder.append(",\"message\":\"").append(basicException.getMessage()).append("\"}");
				} else {
					resultStringBuilder.append("}");
				}
			}
			String resultMessage = resultStringBuilder.toString();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.getWriter().write(resultMessage);
			// response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultMessage);
			return new ModelAndView();
		} catch (Exception e) {
			logger.warn("Handling of ResponseStatusBasicExceptionResolver resulted in Exception", e);
			return null;
		}
	}
}
