package com.tower.service.cache.dao.ibatis.mapper;

import com.tower.service.cache.CacheVersion;
import com.tower.service.dao.ibatis.ISMapper;

public interface CacheVersionMapper extends ISMapper<CacheVersion> {

  int incrVersion(CacheVersion objName);

  int incrRecVersion(CacheVersion objName);

  int incrTabVersion(CacheVersion objName);
}
