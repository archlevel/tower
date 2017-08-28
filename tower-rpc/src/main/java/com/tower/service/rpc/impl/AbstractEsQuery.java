package com.tower.service.rpc.impl;

import io.searchbox.core.search.sort.Sort;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public abstract class AbstractEsQuery implements EsQuery {  
	  
    Integer pageIndex = 0;  
    Integer pageSize = 10;  
    
  public QueryBuilder composeQuery(BoolQueryBuilder query, FilterBuilder filter) {  
      if(filter == null) return query;  
      query = (query == null ? new BoolQueryBuilder().must(QueryBuilders.matchAllQuery()) : query);  
      return null;//new FilteredQueryBuilder(query, filter);  
  }  
  
//  public BoolFilterBuilder mustFilter(BoolFilterBuilder boolFilter, FilterBuilder filter) {  
//      boolFilter = (boolFilter == null ? new BoolFilterBuilder() : boolFilter);  
//      boolFilter.must(filter);  
//      return boolFilter;  
//  }  
  
/*  public BoolQueryBuilder mustQuery(BoolQueryBuilder boolQuery, QueryBuilder query) { 
      boolQuery = (boolQuery == null ? new BoolQueryBuilder() : boolQuery); 
      boolQuery.must(query); 
      return boolQuery; 
  }*/  
    
  public BoolQueryBuilder mustQuery(BoolQueryBuilder boolQuery, QueryBuilder... querys) {  
      boolQuery = (boolQuery == null ? new BoolQueryBuilder() : boolQuery);  
      for(QueryBuilder query:querys){  
          boolQuery.must(query);  
      }  
      return boolQuery;  
  }  
    
  public int getFrom(){  
      return pageIndex*pageSize;  
  }  
  
  public int getSize(){  
      return pageSize;  
  }  
  
  public Integer getPageIndex() {  
      return pageIndex;  
  }  
  
  public Integer getPageSize() {  
      return pageSize;  
  }  
    
  @Override  
  public Sort getSort() {  
      return null;  
  }  
  
} 