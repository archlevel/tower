package com.tower.service.http;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.http.HttpBinder;
import com.alibaba.dubbo.remoting.http.HttpHandler;
import com.alibaba.dubbo.remoting.http.HttpServer;

/**
 * @author yhzhu
 */
public class JettyHttpBinder implements HttpBinder {

    @Override
    public HttpServer bind(URL url, HttpHandler handler) {
        return new JettyHttpServer(url, handler);
    }

}
