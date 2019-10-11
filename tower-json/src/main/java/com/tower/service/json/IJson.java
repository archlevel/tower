package com.tower.service.json;

public interface IJson {

    String bean2Json(Object obj);

    <T> T json2Bean(String jsonStr,Class<T> objClass);
}
