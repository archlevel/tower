package com.tower.service.http;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author yhzhu
 */
public class TimeServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (RpcJsonConstants.INIT_SERVICE_SIZE.get() == RpcJsonConstants.PUBLISH_SERVICE_SIZE.get()) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String time = now.format(formatter);
			try {
				resp.getWriter().println(time);
				resp.setContentType("text/html;charset=utf-8");
				resp.setStatus(HttpServletResponse.SC_OK);
			} catch (Exception e) {
				if (resp != null) {
					resp.getWriter().println(e.getMessage());
					resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				}
				LOGGER.error(e.getMessage(), e);
			}
		} else {
			resp.getWriter().println("服务正在启动中.....");
			resp.setContentType("text/html;charset=utf-8");
			resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
