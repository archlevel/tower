package com.tower.service.dao.ibatis;

import java.util.List;
import java.util.Map;

public interface IBatchMapper<T> {
  
  List<T> batchQuery(Map<String, Object> params);

  int batchInsert(Map<String, Object> params);

  int batchUpdate(Map<String, Object> params);

  int batchDelete(Map<String, Object> params);

}
