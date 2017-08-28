package com.tower.service.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.tower.service.cache.IModel;
import com.tower.service.cache.annotation.CacheOpParams;
import com.tower.service.dao.IFKDAO;
import com.tower.service.exception.DataAccessException;

/**
 * 外键缓存(fk缓存策略)<br>
 * 
 * 应用场景:根据外键查询<br>
 * 
 * 查询执行步骤:<br>
 * 
 * 1. select * from house_types where loupan_id=?;<br>
 * 
 * 2. 拼接mc缓存key，{表名}+{recVersion}+{tabVersion}+{查询条件参数}。ex:house_types@4@{ loupan_id=?}<br>
 * 
 * 3. 将mc缓存key纪录到redis中。redis key:{表名}+{recVersion}+{tabVersion}+{外键名}+{外键值}<br>
 * 
 * 4. 判断是否存在mc缓存key,如果存在直接返回结果，否则查询数据设置mc缓存，返回结果。<br>
 * 
 * 更新执行步骤:<br>
 * 
 * 1. update house_types where loupan_id=?;<br>
 * 2. 找出house_types对应的主键缓存,删除主键缓存。<br>
 * >主键缓存数量超过threshold_for_delete_pk_by_where＝100值时，更新recVersion版本号<br>
 * 
 * 3. 删除外键缓存。<br>
 * > 查出redis中对应的外键缓存。(set结构，其中的值对应mc中的key)<br>
 * > 删除mckey对应的值。<br>
 * 4. 更新updated版本号，删除cache_tags表缓存<br>
 * 
 * @todo job缓存开关［查询建议屏蔽缓存，更新需要同步缓存］
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public abstract class AbsFKIBatisDAOImpl<T extends IModel> extends AbsIBatisDAOImpl<T> implements
    IFKDAO<T> {
 
  @CacheEvict(value = "defaultCache", key = FkCacheKeyPrefixExpress + "", condition = "#root.target.fkCacheable()")
  @Override
  public Integer deleteByFK(String property, Object fkValue, String tabNameSuffix) {
    if (logger.isDebugEnabled()) {
      logger
          .debug(
              "deleteByFK(String property={}, Object fkValue={}, String tabNameSuffix={}) - start", property, fkValue, tabNameSuffix); //$NON-NLS-1$
    }

    validate(property, fkValue);

    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put(property, fkValue);
    cond.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

    SqlSessionFactory sessionFactory = getMasterSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IMapper<T> mapper = session.getMapper(getMapperClass());
      Integer eft = mapper.deleteByMap(cond);
      if (eft > 0) {
        synCache(CallFrom_FK,eft, property, fkValue, null, tabNameSuffix);
      }

      if (logger.isDebugEnabled()) {
        logger
            .debug(
                "deleteByFK(String property={}, Object fkValue={}, String tabNameSuffix={}) - end - return value={}", property, fkValue, tabNameSuffix, eft); //$NON-NLS-1$
      }
      return eft;
    } catch (Exception t) {
      logger.error("deleteByFK(String, Integer, String)", t); //$NON-NLS-1$

      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  @CacheEvict(value = "defaultCache", key = FkCacheKeyPrefixExpress
      + ".concat('@').concat(#root.target.serializable(#attchParams))", condition = "#root.target.fkCacheable()")
  @Override
  public Integer deleteByFK(String property, Object fkValue, Map<String, Object> attchParams,
      String tabNameSuffix) {
    if (logger.isDebugEnabled()) {
      logger
          .debug(
              "deleteByFK(String property={}, Object fkValue={}, Map<String,Object> attchParams={}, String tabNameSuffix={}) - start", property, fkValue, attchParams, tabNameSuffix); //$NON-NLS-1$
    }

    validate(property, fkValue);

    validate(attchParams);

    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put(property, fkValue);
    cond.putAll(attchParams);
    cond.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

    SqlSessionFactory sessionFactory = getMasterSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IMapper<T> mapper = session.getMapper(getMapperClass());
      Integer eft = mapper.deleteByMap(cond);
      if (eft > 0) {
        synCache(CallFrom_FK,eft, property, fkValue, attchParams, tabNameSuffix);
      }

      if (logger.isDebugEnabled()) {
        logger
            .debug(
                "deleteByFK(String property={}, Object fkValue={}, Map<String,Object> attchParams={}, String tabNameSuffix={}) - end - return value={}", property, fkValue, attchParams, tabNameSuffix, eft); //$NON-NLS-1$
      }
      return eft;
    } catch (Exception t) {
      logger.error("deleteByFK(String, Integer, Map<String,Object>, String)", t); //$NON-NLS-1$

      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  @CacheOpParams(time = ONE_DAY)
  @Cacheable(value = "defaultCache", key = FkCacheKeyPrefixExpress + "", unless = "#result == null", condition = "#root.target.fkCacheable()")
  @Override
  public List<T> queryByFK(String property, Object fkValue, String tabNameSuffix) {
    if (logger.isDebugEnabled()) {
      logger
          .debug(
              "queryByFK(String property={}, Object fkValue={}, String tabNameSuffix={}) - start", property, fkValue, tabNameSuffix); //$NON-NLS-1$
    }

    validate(property, fkValue);

    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put(property, fkValue);
    cond.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
    SqlSessionFactory sessionFactory = getSlaveSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IMapper<T> mapper = (IMapper<T>) session.getMapper(getMapperClass());
      List<T> result = mapper.queryByMap(cond);
      addKey2FKGroupCache(property, fkValue, null, result, tabNameSuffix);

      if (logger.isDebugEnabled()) {
        logger
            .debug(
                "queryByFK(String property={}, Object fkValue={}, String tabNameSuffix={}) - end - return value={}", property, fkValue, tabNameSuffix, result); //$NON-NLS-1$
      }
      return result;
    } catch (Exception t) {
      logger.error("queryByFK(String, Integer, String)", t); //$NON-NLS-1$

      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }

  }

  @CacheOpParams(time = ONE_DAY)
  @Cacheable(value = "defaultCache", key = FkCacheKeyPrefixExpress
      + ".concat('@').concat(#root.target.serializable(#attchParams))", unless = "#result == null", condition = "#root.target.fkCacheable()")
  @Override
  public List<T> queryByFK(String property, Object fkValue, Map<String, Object> attchParams,
      String tabNameSuffix) {
    if (logger.isDebugEnabled()) {
      logger
          .debug(
              "queryByFK(String property={}, Object fkValue={}, Map<String,Object> attchParams={}, String tabNameSuffix={}) - start", property, fkValue, attchParams, tabNameSuffix); //$NON-NLS-1$
    }

    validate(property, fkValue);

    validate(attchParams);

    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put(property, fkValue);
    cond.putAll(attchParams);
    cond.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
    SqlSessionFactory sessionFactory = getSlaveSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IMapper<T> mapper = (IMapper<T>) session.getMapper(getMapperClass());
      List<T> result = mapper.queryByMap(cond);
      addKey2FKGroupCache(property, fkValue, attchParams, result, tabNameSuffix);

      if (logger.isDebugEnabled()) {
        logger
            .debug(
                "queryByFK(String property={}, Object fkValue={}, Map<String,Object> attchParams={}, String tabNameSuffix={}) - end - return value={}", property, fkValue, attchParams, tabNameSuffix, result); //$NON-NLS-1$
      }
      return result;
    } catch (Exception t) {
      logger.error("queryByFK(String, Integer, Map<String,Object>, String)", t); //$NON-NLS-1$

      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  @CacheOpParams(time = ONE_DAY)
  @Cacheable(value = "defaultCache", key = FkCacheKeyPrefixExpress + "", unless = "#result == null", condition = "!#master and #root.target.fkCacheable()")
  @Override
  public List<T> queryByFK(String property, Object fkValue, Boolean master, String tabNameSuffix) {
    if (logger.isDebugEnabled()) {
      logger
          .debug(
              "queryByFK(String property={}, Object fkValue={}, Boolean master={}, String tabNameSuffix={}) - start", property, fkValue, master, tabNameSuffix); //$NON-NLS-1$
    }

    validate(property, fkValue);

    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put(property, fkValue);
    cond.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
    SqlSessionFactory sessionFactory = master ? this.getMasterSessionFactory()
            : getSlaveSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IMapper<T> mapper = (IMapper<T>) session.getMapper(getMapperClass());
      List<T> result = mapper.queryByMap(cond);
      addKey2FKGroupCache(property, fkValue, null, result, tabNameSuffix);

      if (logger.isDebugEnabled()) {
        logger
            .debug(
                "queryByFK(String property={}, Object fkValue={}, Boolean master={}, String tabNameSuffix={}) - end - return value={}", property, fkValue, master, tabNameSuffix, result); //$NON-NLS-1$
      }
      return result;
    } catch (Exception t) {
      logger.error("queryByFK(String, Integer, Boolean, String)", t); //$NON-NLS-1$

      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  @CacheOpParams(time = ONE_DAY)
  @Cacheable(value = "defaultCache", key = FkCacheKeyPrefixExpress
      + ".concat('@').concat(#root.target.serializable(#attchParams))", unless = "#result == null", condition = "!#master and #root.target.fkCacheable()")
  @Override
  public List<T> queryByFK(String property, Object fkValue, Map<String, Object> attchParams,
      Boolean master, String tabNameSuffix) {
    if (logger.isDebugEnabled()) {
      logger
          .debug(
              "queryByFK(String property={}, Object fkValue={}, Map<String,Object> attchParams={}, Boolean master={}, String tabNameSuffix={}) - start", property, fkValue, attchParams, master, tabNameSuffix); //$NON-NLS-1$
    }

    validate(property, fkValue);
    validate(attchParams);
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put(property, fkValue);
    cond.putAll(attchParams);
    cond.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
    SqlSessionFactory sessionFactory = master ? this.getMasterSessionFactory()
            : getSlaveSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IMapper<T> mapper = (IMapper<T>) session.getMapper(getMapperClass());
      List<T> result = mapper.queryByMap(cond);
      addKey2FKGroupCache(property, fkValue, attchParams, result, tabNameSuffix);

      if (logger.isDebugEnabled()) {
        logger
            .debug(
                "queryByFK(String property={}, Object fkValue={}, Map<String,Object> attchParams={}, Boolean master={}, String tabNameSuffix={}) - end - return value={}", property, fkValue, attchParams, master, tabNameSuffix, result); //$NON-NLS-1$
      }
      return result;
    } catch (Exception t) {
      logger.error("queryByFK(String, Integer, Map<String,Object>, Boolean, String)", t); //$NON-NLS-1$

      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  @CacheEvict(value = "defaultCache", key = FkCacheKeyPrefixExpress + "", condition = "#root.target.fkCacheable()")
  @Override
  public Integer updateByFK(String property, Object fkValue, Map<String, Object> newValue,
      String tabNameSuffix) {
    if (logger.isDebugEnabled()) {
      logger
          .debug(
              "updateByFK(String property={}, Object fkValue={}, Map<String,Object> newValue={}, String tabNameSuffix={}) - start", property, fkValue, newValue, tabNameSuffix); //$NON-NLS-1$
    }

    if (newValue == null || newValue.isEmpty()) {
      throw new DataAccessException(IBatisDAOException.MSG_1_0008);
    }

    validate(property, fkValue);

    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put(property, fkValue);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("newObj", newValue);
    params.put("params", cond);
    params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

    SqlSessionFactory sessionFactory = getMasterSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IMapper<T> mapper = session.getMapper(getMapperClass());
      Integer eft = mapper.cmplxUpdate(params);
      if (eft > 0) {
        synCache(CallFrom_FK,eft, property, fkValue, null, tabNameSuffix);
      }

      if (logger.isDebugEnabled()) {
        logger
            .debug(
                "updateByFK(String property={}, Object fkValue={}, Map<String,Object> newValue={}, String tabNameSuffix={}) - end - return value={}", property, fkValue, newValue, tabNameSuffix, eft); //$NON-NLS-1$
      }
      return eft;
    } catch (Exception t) {
      logger.error("updateByFK(String, Integer, Map<String,Object>, String)", t); //$NON-NLS-1$

      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  @CacheEvict(value = "defaultCache", key = FkCacheKeyPrefixExpress
      + ".concat('@').concat(#root.target.serializable(#attchParams))", condition = "#root.target.fkCacheable()")
  @Override
  public Integer updateByFK(String property, Object fkValue, Map<String, Object> attchParams,
      Map<String, Object> newValue, String tabNameSuffix) {
    if (logger.isDebugEnabled()) {
      logger
          .debug(
              "updateByFK(String property={}, Object fkValue={}, Map<String,Object> attchParams={}, Map<String,Object> newValue={}, String tabNameSuffix={}) - start", property, fkValue, attchParams, newValue, tabNameSuffix); //$NON-NLS-1$
    }

    if (newValue == null || newValue.isEmpty()) {
      throw new DataAccessException(IBatisDAOException.MSG_1_0008);
    }

    validate(property, fkValue);

    validate(attchParams);

    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put(property, fkValue);
    cond.putAll(attchParams);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("newObj", newValue);
    params.put("params", cond);
    params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

    SqlSessionFactory sessionFactory = getMasterSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IMapper<T> mapper = session.getMapper(getMapperClass());
      Integer eft = mapper.cmplxUpdate(params);
      if (eft > 0) {
        synCache(CallFrom_FK,eft, property, fkValue, attchParams, tabNameSuffix);
      }

      if (logger.isDebugEnabled()) {
        logger
            .debug(
                "updateByFK(String property={}, Object fkValue={}, Map<String,Object> attchParams={}, Map<String,Object> newValue={}, String tabNameSuffix={}) - end - return value={}", property, fkValue, attchParams, newValue, tabNameSuffix, eft); //$NON-NLS-1$
      }
      return eft;
    } catch (Exception t) {
      logger
          .error("updateByFK(String, Integer, Map<String,Object>, Map<String,Object>, String)", t); //$NON-NLS-1$

      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  /**
   * 参数验证
   * 
   * @param property
   * @param fkValue
   */
  protected void validate(String property, Object fkValue) {
    if (logger.isDebugEnabled()) {
      logger.debug("validate(String property={}, Object fkValue={}) - start", property, fkValue); //$NON-NLS-1$
    }

    if (fkValue == null) {
      throw new DataAccessException(IBatisDAOException.MSG_1_0010);
    }

    if (!isFk(property)) {
      throw new DataAccessException(IBatisDAOException.MSG_1_0009, property);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validate(String property={}, Object fkValue={}) - end", property, fkValue); //$NON-NLS-1$
    }
  }
  
  // ##################################################################################################

}
