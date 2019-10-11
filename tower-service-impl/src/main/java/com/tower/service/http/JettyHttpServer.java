package com.tower.service.http;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.remoting.http.HttpHandler;
import com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet;
import com.alibaba.dubbo.remoting.http.support.AbstractHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/**
 * @author yhzhu
 */
public class JettyHttpServer extends AbstractHttpServer {
    private static final Logger logger = LoggerFactory.getLogger(JettyHttpServer.class);
    private static final int MAX_JSON_RPC_THREADS = 4;

    private Server server;

    public JettyHttpServer(URL url, final HttpHandler handler) {
        super(url, handler);
        DispatcherServlet.addHttpHandler(url.getPort(), handler);

        int threads = getThreadNum(url);
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setDaemon(true);
        threadPool.setMaxThreads(threads);
        threadPool.setMinThreads(1);

        server = new Server(threadPool);

        // HTTP connector
        ServerConnector connector = new ServerConnector(server);
        if (!url.isAnyHost() && NetUtils.isValidLocalHost(url.getHost())) {
            connector.setHost(url.getHost());
        }
        connector.setPort(url.getPort());
        server.addConnector(connector);

        ServletHandler servletHandler = new ServletHandler();
        ServletHolder servletHolder = servletHandler.addServletWithMapping(DispatcherServlet.class, "/*");
        servletHolder.setInitOrder(2);

        ServletHolder servlet = servletHandler.addServletWithMapping(TimeServlet.class, "/msf/time");
        servlet.setInitOrder(100);

        server.insertHandler(servletHandler);

        try {
            logger.info("Starting: "+url);
            server.start();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to start jetty server on " + url.getAddress() + ", cause: "
                    + e.getMessage(), e);
        }
    }

    private int getThreadNum(URL url) {
        int threads = url.getParameter(Constants.THREADS_KEY, Constants.DEFAULT_THREADS);
        if (threads == 0){
            threads = Runtime.getRuntime().availableProcessors();
            if (threads < MAX_JSON_RPC_THREADS){
                threads = MAX_JSON_RPC_THREADS;
            }
        }

        return threads;
    }

    @Override
    public void close() {
        super.close();
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }
}
