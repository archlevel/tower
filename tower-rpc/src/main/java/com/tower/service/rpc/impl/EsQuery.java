package com.tower.service.rpc.impl;

import io.searchbox.core.search.sort.Sort;

import java.util.List;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public interface EsQuery<T> {  
    /** 
     * 组建es query 
     * @return 
     */  
    QueryBuilder buildQuery();  
    /** 
     * 组建es filter 
     * @return 
     */  
    FilterBuilder buildFilter();  
    /** 
     * 获取搜索结果的from 
     * @return 
     */  
    int getFrom();  
    /** 
     * 获取搜索结果的size 
     * @return 
     */  
    int getSize();  
    /** 
     * 获取排序方式 
     * @return 
     */  
    Sort getSort();  
    /** 
     * 获取ES 检索结果的类型 
     * @return 
     */  
    <T> Class<T> getResultClass();  
      
    List<T> prepareData();  
} 