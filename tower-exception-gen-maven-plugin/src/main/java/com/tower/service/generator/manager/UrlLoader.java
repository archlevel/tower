package com.tower.service.generator.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.maven.plugin.MojoExecutionException;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tower.service.generator.ExceptionMessage;

/**
 * 通过URL接口方式获取配置信息
 * URL 返回结构格式
 *
 * <pre>
 *     Example:
 *     [
 *      {"code":1,"message":"参数不合法","type":1},
 *      {"code":2,"message":"状态不符","type":2},
 *     ]
 * </pre>
 */
public class UrlLoader extends AbstractLoader {
    private String url;
    public UrlLoader(String url){
        this.url=url;
    }

    @Override
    public List<ExceptionMessage> getExceptionMessages() throws MojoExecutionException {
        String config=getConfigUseUrl(url);
        List<ExceptionMessage> exceptionMessages = Lists.newArrayList();
        if(Strings.isNullOrEmpty(config)){
            return ImmutableList.of();
        }
        JSONArray  jsonArray = JSONArray.fromObject(config);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject json = jsonArray.getJSONObject(i);
            exceptionMessages.add(new ExceptionMessage(
                    json.getInt("code"),
                    json.getString("message"),
                    json.getInt("type"),json.getInt("spid"),
                    json.getInt("level")));
        }
        return exceptionMessages;
    }
    private String getConfigUseUrl(String _uu) throws MojoExecutionException{
        String message=null;
        try {
            URL url=new URL(_uu);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.connect();
            InputStream inStream = conn.getInputStream();
            message = stream2String(inStream);
            inStream.close();
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage());
        }
        return message;
    }

    private String stream2String(InputStream inputStream){
        BufferedReader br =null;
        String message="";
        try {
            if(inputStream!=null) {
                br = new BufferedReader(new InputStreamReader(inputStream));
                String s;
                while ((s = br.readLine()) != null) {
                   message+=s;
                }
            }
        } catch (IOException e) {

        }finally{
            try {
                if(br!=null) {
                    br.close();
                }
            } catch (IOException e) {
                //ignore exception
            }
        }
        return message;
    }
}
