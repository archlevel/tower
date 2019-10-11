package com.tower.service.json.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tower.service.json.IJson;

import java.io.IOException;

public class JacksonImpl implements IJson {

    private static IJson instance;

    private static ObjectMapper mapper = new ObjectMapper();

    public static IJson getInstance(){

        if(instance == null){
            synchronized (JacksonImpl.class){
                if( instance == null) {
                    instance = new JacksonImpl();
                }
            }
        }

        return instance;
    }

    @Override
    public String bean2Json(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            return mapper.readValue(jsonStr, objClass);
        } catch (IOException e) {
            return null;
        }
    }
}
