package com.tower.service.web.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {

	public String getCookieValue(HttpServletRequest servletRequest, String name) {
		Cookie[] cookies = servletRequest.getCookies();
		if ((cookies != null) && (cookies.length > 0)) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
