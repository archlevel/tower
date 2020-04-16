package com.tower.service.json.impl;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 对象字段值超长时去除字段值超过36字符
 *  @brief： 去除字段值超过36字符
 *  @details: 只在SDK内部使用
 *  @Date ： 2019/9/17
 *  @Author ： zhugongping@mgzf.com
 */
public class SimpleValueFilter implements ValueFilter {
    private int maxLen = 36;
    @Override
    public Object process(Object object, String name, Object value) {
        // 只要字段名中包含mobile，则值输出为一串星号
        if(value != null && value instanceof String && ((String) value).length()>maxLen){
            return ((String) value).substring(0,maxLen)+"...";
        }

        return value;
    }
}