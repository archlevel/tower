package com.tower.service.protocol;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.remoting.http.HttpBinder;
import com.alibaba.dubbo.remoting.http.HttpHandler;
import com.alibaba.dubbo.remoting.http.HttpServer;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProxyProtocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcClientException;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.spring.JsonProxyFactoryBean;
import com.tower.service.http.RpcJsonConstants;
import org.springframework.remoting.RemoteAccessException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yhzhu
 */
public class JsonRpcProtocol extends AbstractProxyProtocol {

	public static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
	public static final String ACCESS_CONTROL_ALLOW_METHODS_HEADER = "Access-Control-Allow-Methods";
	public static final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER = "Access-Control-Allow-Headers";
	public static final String OPTIONS = "OPTIONS";
	public static final String GET = "GET";
	public static final String POST = "POST";

	public static final int DEFAULT_PORT = 18080;
	public static final String JSONRPC_PROTOCOL_PORT = "jsonrpc.protocol.port";

	private final Map<String, HttpServer> serverMap = new ConcurrentHashMap<>();

	private final Map<String, JsonRpcServer> skeletonMap = new ConcurrentHashMap<>();

	private HttpBinder httpBinder;

	public JsonRpcProtocol() {
		super(RuntimeException.class, JsonRpcClientException.class);
	}

	public void setHttpBinder(HttpBinder httpBinder) {
		this.httpBinder = httpBinder;
	}

	@Override
	public int getDefaultPort() {
		if (RpcJsonConstants.JSONRPC_PROTOCOL_PORT.get() == 0) {
			String port = System.getProperty(JSONRPC_PROTOCOL_PORT);
			RpcJsonConstants.JSONRPC_PROTOCOL_PORT.set(StringUtils.isEmpty(port) || Integer.parseInt(port) <= 0 ? NetUtils.getAvailablePort(DEFAULT_PORT) : Integer.parseInt(port));
		}
		return RpcJsonConstants.JSONRPC_PROTOCOL_PORT.get();
	}

	static class InternalHandler implements HttpHandler {

		private final Map<String, JsonRpcServer> skeletonMap;
		private boolean cors;

		public InternalHandler(Map<String, JsonRpcServer> skeletonMap, boolean cors) {
			this.cors = cors;
			this.skeletonMap = skeletonMap;
		}

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response)
				throws ServletException {
			String uri = request.getRequestURI();
			JsonRpcServer skeleton = skeletonMap.get(uri);
			if (cors) {
				response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
				response.setHeader(ACCESS_CONTROL_ALLOW_METHODS_HEADER, "GET, POST");
				response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS_HEADER, "*");
			}
			if (request.getMethod().equalsIgnoreCase(OPTIONS)) {
				response.setStatus(200);
			} else if (request.getMethod().equalsIgnoreCase(POST) || request.getMethod().equalsIgnoreCase(GET)) {

				RpcContext.getContext().setRemoteAddress(request.getRemoteAddr(), request.getRemotePort());
				try {
					if (skeleton != null) {
						skeleton.handle(request, response);
					}
				} catch (Exception e) {
					throw new ServletException(e);
				}
			} else {
				response.setStatus(500);
			}
		}

	}

	@Override
	protected <T> Runnable doExport(T impl, Class<T> type, URL url) throws RpcException {
		url = url.setPort(RpcJsonConstants.JSONRPC_PROTOCOL_PORT.get());
		String addr = url.getIp() + ":" + url.getPort();
		HttpServer server = serverMap.get(addr);
		if (server == null) {
			server = httpBinder.bind(url, new InternalHandler(skeletonMap, url.getParameter("cors", false)));
			serverMap.put(addr, server);
		}
		final String path = url.getAbsolutePath();
		JsonRpcServer skeleton = new JsonRpcServer(new ObjectMapper(), impl, type);
		skeletonMap.put(path, skeleton);
		return new Runnable() {
			@Override
			public void run() {
				skeletonMap.remove(path);
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T doRefer(final Class<T> serviceType, URL url) throws RpcException {
		JsonProxyFactoryBean jsonProxyFactoryBean = new JsonProxyFactoryBean();
		jsonProxyFactoryBean.setServiceUrl(url.setProtocol("http").toIdentityString());
		jsonProxyFactoryBean.setServiceInterface(serviceType);

		jsonProxyFactoryBean.afterPropertiesSet();
		return (T) jsonProxyFactoryBean.getObject();
	}

	@Override
	protected int getErrorCode(Throwable e) {
		if (e instanceof RemoteAccessException) {
			e = e.getCause();
		}
		if (e != null) {
			Class<?> cls = e.getClass();
			if (SocketTimeoutException.class.equals(cls)) {
				return RpcException.TIMEOUT_EXCEPTION;
			} else if (IOException.class.isAssignableFrom(cls)) {
				return RpcException.NETWORK_EXCEPTION;
			} else if (ClassNotFoundException.class.isAssignableFrom(cls)) {
				return RpcException.SERIALIZATION_EXCEPTION;
			}
		}
		return super.getErrorCode(e);
	}

	@Override
	public void destroy() {
		super.destroy();
		for (String key : new ArrayList<>(serverMap.keySet())) {
			HttpServer server = serverMap.remove(key);
			if (server != null) {
				try {
					if (logger.isInfoEnabled()) {
						logger.info("Close jsonrpc server " + server.getUrl());
					}
					server.close();
				} catch (Exception t) {
					logger.warn(t.getMessage(), t);
				}
			}
		}
	}

}