package com.tower.service.json.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tower.service.json.IJson;

public class GsonImpl implements IJson {

    private static IJson instance;

    private static Gson gson = new GsonBuilder().create();

    public static IJson getInstance(){

        if(instance == null){
            synchronized (GsonImpl.class){
                if( instance == null) {
                    instance = new GsonImpl();
                }
            }
        }

        return instance;
    }

    @Override
    public String bean2Json(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return gson.fromJson(jsonStr, objClass);
    }
}
