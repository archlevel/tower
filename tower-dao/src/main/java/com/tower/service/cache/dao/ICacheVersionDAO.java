package com.tower.service.cache.dao;

import com.tower.service.cache.CacheVersion;
import com.tower.service.cache.ICacheVersion;
import com.tower.service.dao.IDAO;
import com.tower.service.dao.IFKDAO;
import com.tower.service.dao.ISDAO;


public interface ICacheVersionDAO<T extends CacheVersion> extends ISDAO<T>, IFKDAO<T>, IDAO<T>,ICacheVersion<T> {

  
}
