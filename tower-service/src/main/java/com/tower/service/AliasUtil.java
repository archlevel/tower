package com.tower.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.tower.service.util.StringUtil;

public class AliasUtil {

	public static String KEYMAP_KEY = "@@@$$$$$@@@@";

	public static Map getAlias(String keys, ProceedingJoinPoint pjp,
			boolean both) {
		if (pjp.getTarget() == null) {
			return null;
		}
		MethodSignature sign = (MethodSignature) pjp.getSignature();
		String methodName = sign.getName();
		String returnType = sign.getReturnType().getSimpleName();
		Class[] classes = sign.getParameterTypes();
		String[] paramNames = sign.getParameterNames();
		Object[] args = pjp.getArgs();
		Map params = getAlias(keys, sign, paramNames, classes, args, both);
		String className = pjp.getTarget().getClass().getSimpleName();
		if (both) {
			params.put("simple", returnType + ":" + methodName + "@"
					+ className + params.get("simple"));
		}
		params.put("full", returnType + ":" + methodName + "@" + className
				+ params.get("detail"));
		params.put("className", className);
		params.put("methodName", methodName);
		return params;
	}

	private static Map getAlias(String keys, MethodSignature sign,
			String[] paramNames, Class[] classes, Object[] args, boolean both) {
		Method method = sign.getMethod();
		Annotation[][] ans = method.getParameterAnnotations();
		StringBuilder detail = new StringBuilder("(");
		StringBuilder simple = new StringBuilder("(");
		String name = "";
		if (!StringUtil.isEmpty(keys)) {
			String[] kv = keys.split("=");
			if (kv != null || kv.length > 1) {
				if ("params".equals(kv[0])) {
					name = kv[1];
				}
			}
		}
		Map keyMap = new HashMap();
		for (int i = 0; i < classes.length; i++) {
			String value = getAliasAccordingType(ans[i], classes[i], args[i]);
			String paramName = null;
			if(paramNames==null){
				paramName = "param"+i;
				keyMap.put(paramName, value);
			}
			else{
				paramName =paramNames[i];
			}
			keyMap.put(paramName, value);
			if (i > 0) {
				if (both)
					simple.append(",");
				detail.append(",");
			}
			if (both) {
				simple.append(classes[i].getSimpleName());
				simple.append(" ");
				simple.append(paramName);
			}
			detail.append(paramName + "=" + value);
		}

		Map params = new HashMap();
		if (keyMap.size() > 0)
			params.put(KEYMAP_KEY, keyMap);
		params.put("detail", detail.append(")").toString());
		if (both)
			params.put("simple", simple.append(")").toString());
		return params;
	}

	private static String getAliasAccordingType(Annotation[] anns,
			Class class1, Object object) {
		if (object == null)
			return String.valueOf("null");
		return object.toString();
	}

	private static String getValue(Object object) {
		String value = String.valueOf(object);
		if (value.length() > 70) {
			value = "...";
		}
		return value;
	}
}
