package com.tower.service.web.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartResolver;

import com.alibaba.dubbo.container.spring.SpringContainer;

public class XssHttpServletRequestWrapper extends
		HttpServletRequestWrapper {

	private List<IParamPreprocessor> preprocessors = new ArrayList<>();

	public XssHttpServletRequestWrapper(HttpServletRequest request) {

		super(request);

		// 这里做了一个很关键的判断
		// 如果from 表单提交时使用的contentType 为 multipart/form-data（也就是说文件上传+表单提交混合类型）
		// 这样过滤时request无法根据key获取到结果，因为这个request里面包含的是一个流。因此需要将request转换为spring的MultipartHttpServletRequest.
		// 有了这个request就可以根据key获取值了
		MultipartResolver cmr = (MultipartResolver) SpringContainer.getContext().getBean("multipartResolver");
		if (cmr != null && cmr.isMultipart(request)) {
			try {
				request = cmr.resolveMultipart(request);
			} catch (MultipartException e) {
				// 如果转换异常，那就什么都不做了，按照原来的request执行
			}
		}

		super.setRequest(request);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}

		String[] values2 = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			for (IParamPreprocessor processor : preprocessors) {
				value = processor.preprocessParameter(parameter, value);
			}
			values2[i] = value;
		}

		return values2;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}

		for (IParamPreprocessor processor : preprocessors) {
			value = processor.preprocessParameter(parameter, value);
		}
		return value;
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}

		for (IParamPreprocessor processor : preprocessors) {
			value = processor.preprocessHeader(name, value);
		}
		return value;
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		Enumeration<String> enums = super.getHeaders(name);
		if (enums == null) {
			return null;
		}

		Vector<String> result = new Vector<String>();

		while (enums.hasMoreElements()) {
			String value = enums.nextElement();
			if (value == null) {
				continue;
			}
			for (IParamPreprocessor processor : preprocessors) {
				value = processor.preprocessHeader(name, value);
			}
			result.addElement(value);
		}

		return result.elements();

	}

	public void addPreprocessors(IParamPreprocessor... preprocessors) {
		this.preprocessors.addAll(Arrays.asList(preprocessors));
	}
}
