package com.tower.service.json.impl;

import com.alibaba.fastjson.JSON;
import com.tower.service.json.IJson;

public class FastJsonImpl implements IJson {

    private static IJson instance;

    private FastJsonImpl(){
    }
    public static IJson getInstance(){

        if(instance == null){
            synchronized (FastJsonImpl.class){
                if( instance == null) {
                    instance = new FastJsonImpl();
                }
            }
        }

        return instance;
    }
    @Override
    public String bean2Json(Object obj) {
        String toJSONString = JSON.toJSONString(obj);
        return toJSONString;
    }

    @Override
    public <T> T json2Bean(String jsonStr, Class<T> objClass) {
        T object = JSON.parseObject(jsonStr, objClass);
        return object;
    }
}
