package com.tower.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @描述:httpclient 工具类
 * @author feng.peifeng
 * @date:2015年4月2日
 * @版权 上海跨境通国际有限公司
 */
public class HttpUtil {
	private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
    public static final String  APPLICATION_JSON = "application/json";
    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }
 
    public static String doGet(String url, Map<String, String> params){
        return doGet(url, params,CHARSET);
    }
    public static String doPost(String url, Map<String, String> params){
        return doPost(url, params,CHARSET);
    }
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doGet(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
     
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doPost(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,CHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数 json 格式
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doPostJson(String url ,String requestJsonParams, String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            StringEntity se = new StringEntity(requestJsonParams);
            se.setContentType(APPLICATION_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                //throw new RuntimeException("HttpClient,error status code :" + statusCode);
                System.out.println("请求返回码statusCode:"+statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static int CONNECTTIMEOUT = 5000;

    public static int READTIMEOUT = 10000;


    public static String CHARSET_UTF8 = "UTF-8";



    /**
     * 对url进行编码
     */
    public static String encodeURL(String url) {
        try {
            return URLEncoder.encode(url, CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对url进行解码
     * 
     * @param url
     * @return
     */
    public static String decodeURL(String url) {
        try {
            return URLDecoder.decode(url, CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断URL地址是否存在
     * 
     * @param url
     * @return
     */
    public static boolean isURLExist(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            int state = connection.getResponseCode();
            if (state == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断URL地址是否存在
     * 
     * @param url
     * @return
     */
    public static boolean isURLExist(HttpURLConnection connection) {
        try {
            int state = connection.getResponseCode();
            if (state == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将请求参数还原为key=value的形式,for struts2
     * 
     * @param params
     * @return
     */
    public static String getParamString(Map<?, ?> params) {
        StringBuffer queryString = new StringBuffer(256);
        Iterator<?> it = params.keySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            String key = (String) it.next();
            String[] param = (String[]) params.get(key);
            for (int i = 0; i < param.length; i++) {
                if (count == 0) {
                    count++;
                } else {
                    queryString.append("&");
                }
                queryString.append(key);
                queryString.append("=");
                try {
                    queryString.append(URLEncoder.encode((String) param[i], "UTF-8"));
                } catch (UnsupportedEncodingException e) {}
            }
        }
        return queryString.toString();
    }



    /**
     * 抓取网页内容,自动识别编码
     * 
     * @param urlString
     * @return
     */
    public static String readWebPageContent(String urlLink) {
        HttpURLConnection conn = null;
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL(urlLink);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1500);
            conn.setReadTimeout(5000);

            URLConnection c = url.openConnection();
            c.connect();

            String contentType = c.getContentType();
            String characterEncoding = null;
            int index = contentType.indexOf("charset=");
            if (index == -1) {
                characterEncoding = CHARSET_UTF8;
            } else {
                characterEncoding = contentType.substring(index + 8, contentType.length());
            }
            InputStreamReader isr = new InputStreamReader(conn.getInputStream(), characterEncoding);
            BufferedReader br = new BufferedReader(isr);
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp).append("\n");
            }
            br.close();
            isr.close();
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String postXml(String url, String xml) {
        return postXml(url, xml, CHARSET_UTF8);
    }

    public static String postXml(String url, String xml, Map<String, String> headers) {
        return postXml(url, xml, CHARSET_UTF8, headers);
    }

    public static String postXml(String url, String xml, String encode) {
        return send(url, xml, encode, null, "POST", "text/xml;charset=UTF-8");
    }

    public static String postXml(String url, String xml, String encode, Map<String, String> headers) {
        return send(url, xml, encode, headers, "POST", "text/xml;charset=UTF-8");
    }

    public static String post(String url, String text, Map<String, String> headers) {
        return post(url, text, CHARSET_UTF8, headers);
    }

    public static String post(String url, String text, String encode, Map<String, String> headers) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setConnectTimeout(CONNECTTIMEOUT);
            connection.setReadTimeout(READTIMEOUT);

            for (Map.Entry<String, String> map : headers.entrySet()) {
                connection.setRequestProperty(map.getKey(), map.getValue());
            }

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");

            connection.getOutputStream().write(text.getBytes(encode));
            connection.getOutputStream().flush();
            connection.getOutputStream().close();

            StringBuilder sb = new StringBuilder();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    public static String post(String url, String text) {
        return post(url, text, CHARSET_UTF8);
    }

    public static String post(String url, String text, String encode) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setConnectTimeout(CONNECTTIMEOUT);
            connection.setReadTimeout(READTIMEOUT);

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");

            connection.getOutputStream().write(text.getBytes(encode));
            connection.getOutputStream().flush();
            connection.getOutputStream().close();

            StringBuilder sb = new StringBuilder();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    public static String postText(String url, String text) {
        return postText(url, text, CHARSET_UTF8);
    }

    public static String postText(String url, String text, Map<String, String> headers) {
        return send(url, text, CHARSET_UTF8, headers, "POST", "text/plain;charset=UTF-8");
    }

    public static String postText(String url, String text, String encode,
            Map<String, String> headers) {
        return send(url, text, encode, headers, "POST", "text/plain;charset=UTF-8");
    }

    public static String postText(String url, String text, String encode) {
        return send(url, text, encode, null, "POST", "text/plain;charset=UTF-8");
    }

    public static String send(String url, String text, String encode, Map<String, String> headers,
            String method, String property) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setConnectTimeout(CONNECTTIMEOUT);
            connection.setReadTimeout(READTIMEOUT);
            connection.setRequestProperty("Content-type", property);
            if (headers != null) {
                for (Map.Entry<String, String> map : headers.entrySet()) {
                    connection.setRequestProperty(map.getKey(), map.getValue());
                }
            }
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method);

            connection.getOutputStream().write(text.getBytes(encode));
            connection.getOutputStream().flush();
            connection.getOutputStream().close();

            StringBuilder sb = new StringBuilder();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    public static InputStream getInputStream(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            int state = connection.getResponseCode();
            if (state == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
     
}