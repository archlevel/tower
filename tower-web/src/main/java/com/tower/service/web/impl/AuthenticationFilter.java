package com.tower.service.web.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tower.service.auth.ResourcesService;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.domain.auth.AuthResourcesDto;
import com.tower.service.domain.auth.AuthResponse;
import com.tower.service.domain.auth.AuthUserDto;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.StringUtil;
import com.tower.service.web.util.J2eeHttpUtil;


public class AuthenticationFilter implements Filter {

    private String ticketValidateUrl;

    private String serverLoginUrl;
    
    private String serverName;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource(name="resourcesService")
    private ResourcesService resourcesService;
    
    @Resource(name=ConfigComponent.WebAppConfig)
    private WebAppConfig config;

    private ObjectMapper mapper = new ObjectMapper();
    private Map<String, List<String>> resourcesMap = new HashMap<String, List<String>>();
    private String app = null;
    private String app_auth_switch;
    private String auth_switch_key_suffix="auth.switch";
    
    /** 
     * 获取链接的后缀名 
     * @return 
     */  
    private static String parseSuffix(String url) {  
        String[] spUrl = url.toString().split("/");
        if (spUrl.length == 1) {
        	return spUrl[0];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < spUrl.length; i++) {
        	sb.append("/").append(spUrl[i]);
		}
        return sb.toString();
    } 
        
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
        app = filterConfig.getInitParameter("appName");
        if(!StringUtil.isEmpty(app)){
            app_auth_switch = app+"."+auth_switch_key_suffix;
        }
        System.setProperty("key_app_auth_switch", app_auth_switch);
        load();
    }
    
    private void load(){
        
        new Thread() {
    		public void run() {
    			int status = 0;
    			while (true) {
    				try {
    			        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    			        //if(status==0 || hour>=9 && hour<=18){
    			        	Map<String, Object> params = new HashMap<>();
    			            params.put("orders", " id desc ");
    			            
    			            AuthResponse<AuthResourcesDto> response = resourcesService
    			                    .selectList(params);
    			            List<AuthResourcesDto> resources = response.getList();
    			            init(resources);
    			            status = 1;
    			            sleep(2000);
    			        //}
    				} catch (InterruptedException e) {
    				}
    			}
    		};
    	}.start();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        boolean auth = config.getBoolean(app_auth_switch, false);
        System.setProperty(app_auth_switch, String.valueOf(auth));
        if (!auth) {//不需要权限控制
            chain.doFilter(request, response);
        } else {//需要权限控制
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String url = req.getRequestURI().replaceAll("//", "/");
            String regex = "[\\w+](\\.css|\\.js|\\.jpg|\\.png|\\.gif|\\.ico)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {//过滤静态资源
                chain.doFilter(request, response);
            } else {
            	url = url.replaceAll("//", "/");//格式化url去除双//
            	/**
            	 * 白名单
            	 */
            	Iterator<String> keys = config.getKeys(app+".white.list");
            	if(keys==null||!keys.hasNext()){
            		keys = config.getKeys("white.list");
            	}
            	while(keys!=null&&keys.hasNext()){
            		String key = keys.next();
            		String tmp = config.getString(key);
            		if(url.toLowerCase().startsWith(tmp.toLowerCase())){
        				chain.doFilter(request, response);
        				return;
        			}
            	}
            	
                String ticket = request.getParameter("ticket");
                
                if (ticket == null || "".equals(ticket.trim())) {
                    Cookie[] cookies = req.getCookies();
                    if (cookies != null && cookies.length > 0) {
                        for (Cookie cookie : cookies) {
                            if ("ticket".equals(cookie.getName())) {
                                ticket = cookie.getValue();
                                break;
                            }
                        }
                    }
                }
                AuthUserDto dto = (AuthUserDto) req.getSession().getAttribute(ticket);
                if (dto != null) {//已经成功登陆
                	if ("admin".equals(dto.getUserName())) {
                		chain.doFilter(request, response);
                		return ;
                	}
                	if (validate(url,dto)) {//已经授权
                        chain.doFilter(request, response);
                        return;
                    } else {
                    	logger.info("{} accc failed url:{}",dto.getUserName(),url);
                        PrintWriter out = null;
                        try {
                            res.setCharacterEncoding("UTF-8");
                            res.setHeader("Cache-Control","no-cache, must-revalidate");
                            res.setContentType("text/html;charset=UTF-8");
                            out = res.getWriter();
                            out.write("无权限访问!");
                        } catch (IOException e) {

                        } finally {
                            if (out != null) {
                                out.flush();
                                out.close();
                                out = null;
                            }
                        }
                    }
                } else {//登陆流程
                    
                    ticketValidateUrl = config.getString("ticketValidateUrl");
                    serverLoginUrl = config.getString("serverLoginUrl"); 
                    serverName = config.getString("serverName");
                    
                    if(!StringUtil.isEmpty(app)){
                        ticketValidateUrl = config.getString(app+"."+"ticketValidateUrl",ticketValidateUrl);
                        serverLoginUrl = config.getString(app+"."+"serverLoginUrl",serverLoginUrl); 
                        serverName = config.getString(app+"."+"serverName",serverName);
                    }
                    
                    if (ticket != null && !"".equals(ticket.trim())) {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Cookie", "JSESSIONID=" + ticket);
                        String resultText = J2eeHttpUtil.postText(ticketValidateUrl + "?ticket=" + ticket,"ticket=" + ticket, headers);
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        try {
                            dto = mapper.readValue(resultText, AuthUserDto.class);
                        } 
                        catch(JsonParseException ex){
                        	res.sendRedirect(serverLoginUrl);
                        	return;
                        }
                        catch (Exception e) {
                            logger.error(e);
                        }
                        if (dto != null) {
                            req.getSession().setAttribute(ticket, dto);
                            Cookie cookie = new Cookie("ticket", ticket);// 创建一个cookie，cookie的名字是lastAccessTime
                            cookie.setPath("/");
                            // 将cookie对象添加到response对象中，这样服务器在输出response对象中的内容时就会把cookie也输出到客户端浏览器
                            res.addCookie(cookie);
                            /*Cookie authsCookie = new Cookie(ticket, resultText);
                            res.addCookie(authsCookie);*/
                            
                            if ("admin".equals(dto.getUserName())) {
                        		chain.doFilter(request, response);
                        		return ;
                        	}
                            if (validate(url,dto)) {//已经授权
                                chain.doFilter(request, response);
                                return;
                            } else {
                            	logger.info("{} accc failed url:{}",dto.getUserName(),url);
                                PrintWriter out = null;
                                try {
                                    res.setCharacterEncoding("UTF-8");
                                    res.setHeader("Cache-Control","no-cache, must-revalidate");
                                    res.setContentType("text/html;charset=UTF-8");
                                    out = res.getWriter();
                                    out.write("无权限访问!");
                                } catch (IOException e) {

                                } finally {
                                    if (out != null) {
                                        out.flush();
                                        out.close();
                                        out = null;
                                    }
                                }
                            }
                        } else {
                            res.sendRedirect(serverLoginUrl);
                            return;
                        }
                    } else {
                        res.sendRedirect(serverLoginUrl);
                        return;
                    }
                }
            }
        }
    }
    
    private void init(List<AuthResourcesDto> resources){
    	Map<String, List<String>> tmp = new HashMap<String, List<String>>();
    	if (resources != null && !resources.isEmpty()) {
            for (AuthResourcesDto resourcesDto : resources) {
            	if (StringUtil.isNotEmpty(resourcesDto.getLinkUrl())) {
            		String linkUrlSuffix = parseSuffix(resourcesDto.getLinkUrl());
            		if(!tmp.containsKey(linkUrlSuffix)){
            			tmp.put(linkUrlSuffix, new ArrayList());
            		}
            		if(!tmp.get(linkUrlSuffix).contains(resourcesDto.getCode())){
            			tmp.get(linkUrlSuffix).add(resourcesDto.getCode());
            		}
            	}
            }
        }
    	resourcesMap=tmp;
    }
    
    private boolean validate(String url,AuthUserDto right){
    	List<String> resourcesCode = resourcesMap.get(url);
        if(resourcesCode==null||resourcesCode.isEmpty()||right ==null){
        	return false;
        }
        Map<String,Integer> authResource = right.getAuths();
        int size = resourcesCode.size();
        for(int i=0;i<size;i++){
        	if(authResource.containsKey(resourcesCode.get(i))){
        		return true;
        	}
        }
    	return false;
    }
    
    @Override
    public void destroy() {

    }
    
    public static void main(String[] args){
    	System.out.println("//margin_manage/ma_audit_list".replaceAll("//", "/"));
    }

}
