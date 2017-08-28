package com.tower.service.web.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.tower.service.web.security.tool.AntUrlPathMatcher;
import com.tower.service.web.security.tool.UrlMatcher;


/**
 * 
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 
 */
public class InvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public InvocationSecurityMetadataSource() {
		loadResourceDefine();
	}

	private void loadResourceDefine() {
		if (resourceMap == null) {  
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			
			/*ResourcesService resourcesService = (ResourcesService)SpringUtil.getBean("resourcesService");
			List<Resources> list = resourcesService.selectByExample(null);
			if (list != null && !list.isEmpty()) {
				for (Resources resources : list) {
					if (StringUtil.isNotEmpty(resources.getLinkUrl())) {
						List<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();
						ConfigAttribute ca = new SecurityConfig(resources.getCode());
						cas.add(ca);
						resourceMap.put("/"+resources.getLinkUrl(), cas);
					}
				}
			}*/
		}
	}

	// According to a URL, Find out permission configuration of this URL.
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// guess object is a URL.
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (urlMatcher.pathMatchesUrl(resURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
	 
}