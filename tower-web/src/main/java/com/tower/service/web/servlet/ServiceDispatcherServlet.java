package com.tower.service.web.servlet;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

public class ServiceDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void initStrategies(ApplicationContext context) {
		super.initStrategies(context);
		for (AbstractHandlerMapping handlerMapping : BeanFactoryUtils
				.beansOfTypeIncludingAncestors(context,
						AbstractHandlerMapping.class, true, false).values()) {
			handlerMapping.setRemoveSemicolonContent(false);
		}
	}
}