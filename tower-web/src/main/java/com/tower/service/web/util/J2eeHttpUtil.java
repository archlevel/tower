package com.tower.service.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.HttpUtil;

public class J2eeHttpUtil extends HttpUtil{

    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        Enumeration<String> rnames = request.getParameterNames();
        Map<String, Object> map = null;
        if (rnames != null) {
            map = new HashMap<String, Object>();
            for (Enumeration<String> e = rnames; e.hasMoreElements();) {
                String thisName = e.nextElement().toString();
                if ("offset".equals(thisName) || "pageSize".equals(thisName)) {
                    continue;
                }
                String thisValue = request.getParameter(thisName);
                if (StringUtils.isNotEmpty(thisValue)) {
                    map.put(thisName, thisValue);
                    logger.debug("name======" + thisName + "-------value=====" + thisValue);
                }
            }
        }
        return map;
    }

    public static String getInputStream(HttpServletRequest request, String charset) {
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(request.getInputStream(), charset));

            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            logger.error(e);
            return "";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获得请求路径
     * 
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        return requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
    }

    public static String getInputStream(HttpServletRequest request) {
        return getInputStream(request, "UTF-8");
    }


    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        String isAjax = request.getHeader("isAjax");
        if ("XMLHttpRequest".equals(header) || "isAjax".equalsIgnoreCase(isAjax)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到HttpServletRequest对象
     * 
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    /** 获取HttpServletResponse */
    public static HttpServletResponse getResponse() {
        return ((ServletWebRequest)(ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
    }

    public static Map<String, Object> getParameterMap() {
        Enumeration<String> rnames = getRequest().getParameterNames();
        Map<String, Object> map = null;
        if (rnames != null) {  
            map = new HashMap<String, Object>();  
            for (Enumeration<String> e = rnames; e.hasMoreElements();) {
                String thisName = e.nextElement().toString();
                if ("offset".equals(thisName) || "pageSize".equals(thisName)) {
                    continue;
                }
                if ("page".equals(thisName) || "rows".equals(thisName)) {
                    continue;
                }
                String thisValue = getRequest().getParameter(thisName);
                if (StringUtils.isNotEmpty(thisValue)) {
                    map.put(thisName, thisValue);
                    logger.debug("name======" + thisName + "-------value=====" + thisValue);
                }
            }
        }
        return map;
    }
    
    public void getSession(){
    	
    }
    private static final Logger logger = LoggerFactory.getLogger(J2eeHttpUtil.class);
}
