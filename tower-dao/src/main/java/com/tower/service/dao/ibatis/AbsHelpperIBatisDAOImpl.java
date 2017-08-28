package com.tower.service.dao.ibatis;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.tower.service.cache.IModel;
import com.tower.service.cache.mem.impl.DynamicMemCache;
import com.tower.service.cache.redis.impl.DynamicRedisCache;
import com.tower.service.config.DynamicConfig;
import com.tower.service.config.dict.ConfigFileDict;
import com.tower.service.dao.IHelpper;
import com.tower.service.exception.DataAccessException;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public abstract class AbsHelpperIBatisDAOImpl<T extends IHelpper> implements IBatisHelpperDAO<T> {

  /**
   * Logger for this class
   */

  private static DynamicConfig config = new DynamicConfig();

  static {
    config.setFileName(System.getProperty(ConfigFileDict.ACCESS_CONTROL_CONFIG_FILE,
        ConfigFileDict.DEFAULT_ACCESS_CONTROL_CONFIG_NAME));
    config.init();
  }

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected DynamicMemCache defaultCache;

  protected DynamicRedisCache redisCache;

  @PostConstruct
  public void init() {
    if (logger.isDebugEnabled()) {
      logger.debug("init() - start"); //$NON-NLS-1$
    }

    SqlmapUtils.addMapper(getMapperClass(), getMasterSessionFactory());
    SqlmapUtils.addMapper(getMapperClass(), getMapQuerySessionFactory());

    // defaultCache = (DynamicMemCache) this.getCacheManager().getCache(
    // DynamicMemCache.DEFAULT_CACHE_NAME);
    // redisCache = (DynamicRedisCache) this.getCacheManager().getCache(
    // DynamicRedisCache.DEFAULT_CACHE_NAME);

    if (logger.isDebugEnabled()) {
      logger.debug("init() - end"); //$NON-NLS-1$
    }
  }

  /**
   * 获取数据访问层acc.xml配置信息
   * 
   * @return
   */
  protected Configuration getConfig() {
    return config;
  }

  public int countByHelpper(T helpper, String tabNameSuffix) {

    validate(helpper);

    helpper.setTowerTabName(this.get$TowerTabName(tabNameSuffix));

    SqlSessionFactory sessionFactory = getMapQuerySessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IHelpperMapper<T> mapper = session.getMapper(getMapperClass());

      Integer eft = 0;// mapper.countByHelpper(helpper);

      return eft;
    } catch (Exception t) {
      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  public int deleteByHelpper(T helpper, String tabNameSuffix) {
    validate(helpper);

    helpper.setTowerTabName(this.get$TowerTabName(tabNameSuffix));

    SqlSessionFactory sessionFactory = this.getMasterSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IHelpperMapper<T> mapper = session.getMapper(getMapperClass());

      Integer eft = mapper.deleteByHelpper(helpper);

      return eft;
    } catch (Exception t) {
      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  public int updateByHelpper(IModel record, T helpper, String tabNameSuffix) {

    if (record == null) {
      throw new DataAccessException(IBatisDAOException.MSG_1_0004);
    }

    validate(helpper);

    helpper.setTowerTabName(this.get$TowerTabName(tabNameSuffix));

    SqlSessionFactory sessionFactory = this.getMasterSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {
      IHelpperMapper<T> mapper = session.getMapper(getMapperClass());

      Integer eft = mapper.updateByHelpper(record, helpper);

      return eft;
    } catch (Exception t) {
      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  public int updateByHelpper(Map<String, Object> record, T helpper, String tabNameSuffix) {

    validate(record);

    validate(helpper);

    helpper.setTowerTabName(this.get$TowerTabName(tabNameSuffix));

    SqlSessionFactory sessionFactory = this.getMasterSessionFactory();
    SqlSession session = SqlmapUtils.openSession(sessionFactory);
    try {

      IHelpperMapper<T> mapper = session.getMapper(getMapperClass());

      Integer eft = mapper.updateByHelpper(record, helpper);

      return eft;
    } catch (Exception t) {
      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
    } finally {
    	SqlmapUtils.release(session,sessionFactory);
    }
  }

  /**
   * 参数验证
   * 
   * @param params
   */
  protected void validate(Map<String, Object> params) {
    if (logger.isDebugEnabled()) {
      logger.debug("validate(Map<String,Object> params={}) - start", params); //$NON-NLS-1$
    }

    if (params == null || params.isEmpty()) {
      throw new DataAccessException(IBatisDAOException.MSG_1_0004);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validate(Map<String,Object> params={}) - end", params); //$NON-NLS-1$
    }
  }
  
  public final String TAB_SUFFIX_PATTERN = "[0-9a-zA-Z]+(_|-[0-9a-zA-Z]+)*";
  
  protected void suffixValidate(String tabNameSuffix) {
    if (tabNameSuffix == null || tabNameSuffix.trim().length() == 0) {
      return;
    }
    Pattern p = Pattern.compile(TAB_SUFFIX_PATTERN);
    Matcher m = p.matcher(tabNameSuffix.trim());
    if (m.matches()) {
      throw new DataAccessException(IBatisDAOException.MSG_2_0001, "'" + tabNameSuffix + "'后缀不符合规范");
    }
  }
  
  /**
   * 参数验证
   * 
   * @param params
   */
  public void validate(IHelpper params) {
    if (logger.isDebugEnabled()) {
      logger.debug("validate(Map<String,Object> params={}) - start", params); //$NON-NLS-1$
    }

    if (params == null) {
      throw new DataAccessException(IBatisDAOException.MSG_1_0004);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validate(Map<String,Object> params={}) - end", params); //$NON-NLS-1$
    }
  }
}
