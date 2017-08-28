package com.tower.service.dao.ibatis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.tower.service.annotation.JField;
import com.tower.service.cache.IModel;
import com.tower.service.cache.annotation.CacheOpParams;
import com.tower.service.config.DynamicConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.dao.MapPage;
import com.tower.service.exception.DataAccessException;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.BeanUtil;

/**
 * 该接口为非主键、外键操作接口
 * 
 * 
 * 表级缓存<br>
 * 
 * 应用场景:查询既不是主键查询，也不是外键查询的查询，使用该缓存。<br>
 * 
 * 查询执行步骤:<br>
 * 
 * 1. select * from loupan_basic where status=?;<br>
 * 
 * 2. 拼接mc缓存key, {表名}+{tabVersion}+{查询条件参数}<br>
 * 
 * 3. 是否存在该缓存,如果存在直接返回结果，否则查询数据设置mc缓存，返回结果。<br>
 * 
 * 更新执行步骤：<br>
 * 
 * 改变tabVersion<br>
 * 
 * 改变recVersion<br>
 * 
 * 外键缓存优化<br>
 * 
 * 功能:对于有些外键查询实现一次查询，分开缓存。由于分开缓存，所以只能满足一些特殊查询。<br>
 * 条件:<br>
 * 1. 外键用in的查询<br>
 * 2. 查询结果不排序,也就是查询语句中无order。<br>
 * 3. 查询无limit和offset限制。<br>
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public abstract class AbsIBatisDAOImpl<T extends IModel> extends
		AbsCacheableImpl<T> implements IBatisDAO<T> {
	
	/**
	 * Logger for this class
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Logger for this class
	 */
	@Resource(name = ConfigComponent.AccConfig)
	protected DynamicConfig accConfig;
	
	@PostConstruct
	public void init() {
		if (logger.isDebugEnabled()) {
			logger.debug("init() - start"); //$NON-NLS-1$
		}

		SqlmapUtils.addMapper(getMapperClass(), getMasterSessionFactory());
		SqlmapUtils.addMapper(getMapperClass(), getSlaveSessionFactory());
		SqlmapUtils.addMapper(getMapperClass(), getMapQuerySessionFactory());
		super.init();

		if (logger.isDebugEnabled()) {
			logger.debug("init() - end"); //$NON-NLS-1$
		}
	}

	@CacheEvict(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", condition = "#root.target.tabCacheable()")
	@Override
	public Integer deleteByMap(Map<String, Object> params, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteByMap(Map<String,Object>, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		nonePK$FKCheck(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMasterSessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			Integer eft = mapper.deleteByMap(params);
			if (eft > 0) {
				this.synCache(CallFrom_TB,tabNameSuffix);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("deleteByMap(Map<String,Object>, String) - end"); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			logger.error("deleteByMap(Map<String,Object>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheEvict(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#cond))", condition = "#root.target.tabCacheable()")
	@Override
	public Integer updateByMap(Map<String, Object> newValue,
			Map<String, Object> cond, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateByMap(Map<String,Object>, Map<String,Object>, String) - start"); //$NON-NLS-1$
		}

		validate(cond);

		if (newValue == null || newValue.isEmpty()) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0008);
		}

		nonePK$FKCheck(cond);

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
				this.synCache(CallFrom_TB,tabNameSuffix);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("updateByMap(Map<String,Object>, Map<String,Object>, String) - end"); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			logger.error(
					"updateByMap(Map<String,Object>, Map<String,Object>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "#root.target.tabCacheable()")
	@Override
	public List<T> queryByMap(Map<String, Object> params, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryByMap(Map<String,Object>, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		nonePK$FKCheck(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = (IMapper<T>) session
					.getMapper(getMapperClass());
			List<T> returnList = mapper.queryByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("queryByMap(Map<String,Object>, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error("queryByMap(Map<String,Object>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "#root.target.tabCacheable()")
	@Override
	public List<T> queryByMap(Map<String, Object> params, String orders,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryByMap(Map<String,Object>, String, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		nonePK$FKCheck(params);

		params.put("orders", this.convert(this.getModelClass(), orders));

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = (IMapper<T>) session
					.getMapper(getMapperClass());
			List<T> returnList = mapper.queryByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("queryByMap(Map<String,Object>, String, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error("queryByMap(Map<String,Object>, String, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "#root.target.tabCacheable()")
	@Override
	public List<String> queryIdsByMap(Map<String, Object> params,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryIdsByMap(Map<String,Object>, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = (IMapper<T>) session
					.getMapper(getMapperClass());
			List<String> returnList = mapper.queryIdsByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("queryIdsByMap(Map<String,Object>, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error("queryIdsByMap(Map<String,Object>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "#root.target.tabCacheable()")
	@Override
	public List<String> queryIdsByMap(Map<String, Object> params,
			String orders, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryIdsByMap(Map<String,Object>, String, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		params.put("orders", this.convert(this.getModelClass(), orders));

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = (IMapper<T>) session
					.getMapper(getMapperClass());
			List<String> returnList = mapper.queryIdsByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("queryIdsByMap(Map<String,Object>, String, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error("queryIdsByMap(Map<String,Object>, String, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "!#master and #root.target.tabCacheable()")
	@Override
	public List queryIdsByMap(Map<String, Object> params, Boolean master,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryIdsByMap(Map<String,Object>, Boolean, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = master ? this.getMasterSessionFactory()
	            : getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = (IMapper<T>) session
					.getMapper(getMapperClass());
			List returnList = mapper.queryByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("queryIdsByMap(Map<String,Object>, Boolean, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error(
					"queryIdsByMap(Map<String,Object>, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "!#master and #root.target.tabCacheable()")
	@Override
	public List queryIdsByMap(Map<String, Object> params, String orders,
			Boolean master, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryIdsByMap(Map<String,Object>, String, Boolean, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		params.put("orders", this.convert(this.getModelClass(), orders));

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = master ? this.getMasterSessionFactory()
	            : getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = (IMapper<T>) session
					.getMapper(getMapperClass());
			List returnList = mapper.queryByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("queryIdsByMap(Map<String,Object>, String, Boolean, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error(
					"queryIdsByMap(Map<String,Object>, String, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "!#master and #root.target.tabCacheable()")
	@Override
	public List<T> queryByMap(Map<String, Object> params, Boolean master,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryByMap(Map<String,Object>, Boolean, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		nonePK$FKCheck(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = master ? this.getMasterSessionFactory()
	            : getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = (IMapper<T>) session
					.getMapper(getMapperClass());
			List<T> returnList = mapper.queryByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("queryByMap(Map<String,Object>, Boolean, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error("queryByMap(Map<String,Object>, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "!#master and #root.target.tabCacheable()")
	@Override
	public List<T> queryByMap(Map<String, Object> params, String orders,
			Boolean master, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryByMap(Map<String,Object>, String, Boolean, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		nonePK$FKCheck(params);

		params.put("orders", this.convert(this.getModelClass(), orders));

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = master ? this.getMasterSessionFactory()
	            : getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = (IMapper<T>) session
					.getMapper(getMapperClass());
			List<T> returnList = mapper.queryByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("queryByMap(Map<String,Object>, String, Boolean, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error(
					"queryByMap(Map<String,Object>, String, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat('cnt:').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "#root.target.tabCacheable()")
	@Override
	public Integer countByMap(Map<String, Object> params, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("countByMap(Map<String,Object>, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		nonePK$FKCheck(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			Integer returnInteger = mapper.countByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("countByMap(Map<String,Object>, String) - end"); //$NON-NLS-1$
			}
			return returnInteger;
		} catch (Exception t) {
			logger.error("countByMap(Map<String,Object>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat('cnt:').concat(#root.target.serializable(#params))", unless = "#result == null", condition = "!#master and #root.target.tabCacheable()")
	@Override
	public Integer countByMap(Map<String, Object> params, Boolean master,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("countByMap(Map<String,Object>, Boolean, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		nonePK$FKCheck(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
		
		SqlSessionFactory sessionFactory = master ? getMasterSessionFactory()
				: getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			Integer returnInteger = mapper.countByMap(params);

			if (logger.isDebugEnabled()) {
				logger.debug("countByMap(Map<String,Object>, Boolean, String) - end"); //$NON-NLS-1$
			}
			return returnInteger;
		} catch (Exception t) {
			logger.error("countByMap(Map<String,Object>, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat('page:').concat(#root.target.serializable(#params)).concat('@').concat(#page).concat('@').concat(#size)", unless = "#result == null", condition = "#root.target.tabCacheable()")
	@Override
	public List<T> pageQuery(Map<String, Object> params, int page, int size,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("pageQuery(Map<String,Object>, int, int, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
		SqlSessionFactory sessionFactory = getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			MapPage<Map<String, Object>> cmd = new MapPage<Map<String, Object>>();
			cmd.setPageSize(size);
			cmd.setPageIndex(getPageIndex(page));
			cmd.setStart(getPageStart(page, size));
			cmd.setEnd(getPageEnd(page, size));
			cmd.setParams(params);

			List<T> returnList = mapper.pageQuery(cmd);

			if (logger.isDebugEnabled()) {
				logger.debug("pageQuery(Map<String,Object>, int, int, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error("pageQuery(Map<String,Object>, int, int, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat('page:').concat(#root.target.serializable(#params)).concat('@').concat(#page).concat('@').concat(#size)", unless = "#result == null", condition = "!#master and #root.target.tabCacheable()")
	public List<T> pageQuery(Map<String, Object> params, int page, int size,
			Boolean master, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("pageQuery(Map<String,Object>, int, int, Boolean, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = master ? this.getMasterSessionFactory()
	            : getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			MapPage<Map<String, Object>> cmd = new MapPage<Map<String, Object>>();
			cmd.setPageSize(size);
			cmd.setPageIndex(getPageIndex(page));
			cmd.setStart(getPageStart(page, size));
			cmd.setEnd(getPageEnd(page, size));
			cmd.setParams(params);
			List<T> returnList = mapper.pageQuery(cmd);

			if (logger.isDebugEnabled()) {
				logger.debug("pageQuery(Map<String,Object>, int, int, Boolean, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error(
					"pageQuery(Map<String,Object>, int, int, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat('page:').concat(#root.target.serializable(#params)).concat('@').concat(#page).concat('@').concat(#size).concat('@').concat(#orders)", unless = "#result == null", condition = "#root.target.tabCacheable()")
	@Override
	public List<T> pageQuery(Map<String, Object> params, int page, int size,
			String orders, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("pageQuery(Map<String,Object>, int, int, String, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			MapPage<Map<String, Object>> cmd = new MapPage<Map<String, Object>>();
			cmd.setPageSize(size);
			cmd.setPageIndex(getPageIndex(page));
			cmd.setStart(getPageStart(page, size));
			cmd.setEnd(getPageEnd(page, size));
			cmd.setParams(params);
			cmd.setOrders(this.convert(this.getModelClass(), orders));
			List<T> returnList = mapper.pageQuery(cmd);

			if (logger.isDebugEnabled()) {
				logger.debug("pageQuery(Map<String,Object>, int, int, String, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error(
					"pageQuery(Map<String,Object>, int, int, String, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheOpParams(time = ONE_DAY)
	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
			+ ".concat('@').concat('page:').concat(#root.target.serializable(#params)).concat('@').concat(#page).concat('@').concat(#size).concat('@').concat(#orders)", unless = "#result == null", condition = "!#master and #root.target.tabCacheable()")
	@Override
	public List<T> pageQuery(Map<String, Object> params, int page, int size,
			String orders, Boolean master, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("pageQuery(Map<String,Object>, int, int, String, Boolean, String) - start"); //$NON-NLS-1$
		}

		validate(params);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = master ? this.getMasterSessionFactory()
	            : getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			MapPage<Map<String, Object>> cmd = new MapPage<Map<String, Object>>();
			cmd.setPageSize(size);
			cmd.setPageIndex(getPageIndex(page));
			cmd.setStart(getPageStart(page, size));
			cmd.setEnd(getPageEnd(page, size));
			cmd.setParams(params);
			cmd.setOrders(this.convert(this.getModelClass(), orders));
			List<T> returnList = mapper.pageQuery(cmd);

			if (logger.isDebugEnabled()) {
				logger.debug("pageQuery(Map<String,Object>, int, int, String, Boolean, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error(
					"pageQuery(Map<String,Object>, int, int, String, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	/**
	 * 把属性名称映射到数据库字段
	 * 
	 * @param model
	 * @param orders
	 * @return
	 */
	protected String convert(Class model, String orders) {
		if (logger.isDebugEnabled()) {
			logger.debug("convert(Class, String) - start"); //$NON-NLS-1$
		}

		if (orders == null || orders.trim().isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("convert(Class, String) - end"); //$NON-NLS-1$
			}
			return null;
		}

		String[] order = orders.trim().split(",");

		int len = order == null ? 0 : order.length;
		StringBuffer orders_ = new StringBuffer();
		for (int i = 0; i < len; i++) {
			String[] tmp = order[i].split(" ");
			int tlen = tmp == null ? 0 : tmp.length;

			if (tmp != null && tlen > 0) {
				orders_.append(BeanUtil.getJField(model, tmp[0], JField.class));
			}

			if (tmp != null && tlen > 1) {
				orders_.append(" ").append(tmp[1]);
			}
			if (i < len - 1) {
				orders_.append(",");
			}
		}
		String returnString = orders_.toString();

		if (returnString.trim().length() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("convert(Class, String) - end"); //$NON-NLS-1$
			}
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("convert(Class, String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	public void preInsert(T model) {
		if (logger.isDebugEnabled()) {
			logger.debug("preInsert(T) - start"); //$NON-NLS-1$
		}

		model.validate();

		if (logger.isDebugEnabled()) {
			logger.debug("preInsert(T) - end"); //$NON-NLS-1$
		}
	}

	protected int getPageIndex(int page) {
		if (logger.isDebugEnabled()) {
			logger.debug("getPageIndex(int) - start"); //$NON-NLS-1$
		}

		int pageIndex = page;
		if (pageIndex < 1) {
			page = 1;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getPageIndex(int) - end"); //$NON-NLS-1$
		}
		return pageIndex;
	}

	protected int getPageStart(int page, int size) {
		if (logger.isDebugEnabled()) {
			logger.debug("getPageStart(int, int) - start"); //$NON-NLS-1$
		}

		if (page < 1) {
			page = 1;
		}
		int returnint = (page - 1) * size;

		if (logger.isDebugEnabled()) {
			logger.debug("getPageStart(int, int) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	protected int getPageEnd(int page, int size) {
		if (logger.isDebugEnabled()) {
			logger.debug("getPageEnd(int, int) - start"); //$NON-NLS-1$
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getPageEnd(int, int) - end"); //$NON-NLS-1$
		}
		return size;
	}

	private void nonePK$FKCheck(Map<String, Object> params) {
		if (logger.isDebugEnabled()) {
	      logger.debug("nonePK$FKCheck(Map<String,Object>) - start"); //$NON-NLS-1$
	    }
	    boolean pk = false;// 包含主键字段
	    boolean fk = false;// 包含外键字段
	    int fkCnt = 0;
	    boolean nofk = false;// 包含非主外键之外的字段
	    Iterator<String> keys = params.keySet().iterator();
	    while (keys.hasNext()) {
	      String key = keys.next();
	      if ("id".equals(key)) {
	        pk = true;
	      } else {
	        boolean tmp = isFk(key);
	        if (tmp) {
	          fkCnt++;
	        } else {
	          nofk = true;
	        }
	      }
	    }

	    if (!nofk && pk && fkCnt == 0) {// 只含有主键
	      throw new DataAccessException(IBatisDAOException.MSG_1_0011);
	    } else if (!nofk && !pk && fkCnt == 1) {// 只含有单外键
	      throw new DataAccessException(IBatisDAOException.MSG_1_0011);
	    }

	    if (logger.isDebugEnabled()) {
	      logger.debug("nonePK$FKCheck(Map<String,Object>) - end"); //$NON-NLS-1$
	    }
	}

	/**
	 * 参数验证
	 * 
	 * @param params
	 */
	protected void validate(Map<String, Object> params) {
		if (logger.isDebugEnabled()) {
			logger.debug("validate(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		if (params == null || params.isEmpty()) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0004);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("validate(Map<String,Object>) - end"); //$NON-NLS-1$
		}
	}
	
	public void validate(IModel model) {
		if (logger.isDebugEnabled()) {
			logger.debug("validate(IModel) - start"); //$NON-NLS-1$
		}

		if (model == null) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0004);
		}
		model.validate();

		if (logger.isDebugEnabled()) {
			logger.debug("validate(IModel) - end"); //$NON-NLS-1$
		}
	}

	protected void validateCols(List<String> cols) {
		if (logger.isDebugEnabled()) {
			logger.debug("validateCols(List<String>) - start"); //$NON-NLS-1$
		}

		if (cols == null||cols.size()==0) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0012);
		}
		int len = cols == null ? 0 : cols.size();
		Field[] fields = getModelClass().getDeclaredFields();
		int flen = fields == null ? 0 : fields.length;
		for (int i = 0; i < len; i++) {
			String col = cols.get(i);
			boolean passed = false;
			for (int j = 0; j < flen; j++) {
				Field field = fields[j];
				field.setAccessible(true);
				if (col.equals(field.getName())) {
					passed = true;
					continue;
				}
			}
			if (!passed) {
				logger.info("批量插入对象属性" + col + "在"
						+ getModelClass().getSimpleName() + "找不到！");
				throw new DataAccessException(IBatisDAOException.MSG_1_0012);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validateCols(List<String>) - end"); //$NON-NLS-1$
		}
	}

	protected List<String> convert(List<String> properties) {
		if (logger.isDebugEnabled()) {
			logger.debug("convert(List<String>) - start"); //$NON-NLS-1$
		}

		List<String> cols = new ArrayList<String>();
		for (int i = 0; i < properties.size(); i++) {
			cols.add(BeanUtil.getJField(this.getModelClass(),
					properties.get(i), JField.class));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("convert(List<String>) - end"); //$NON-NLS-1$
		}
		return cols;
	}
	
//	@CacheOpParams(time = ONE_DAY)
//	@Cacheable(value = "defaultCache", key = TabCacheKeyPrefixExpress
//			+ ".concat('@').concat('batch:').concat(#root.target.serializable(#datas))", unless = "#result == null", condition = "!#master and #root.target.tabCacheable()")
	@Override
	public List<T> batchQuery(List<Map<String, Object>> datas,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("batchQuery(List<Map<String,Object>>, String) - start"); //$NON-NLS-1$
		}

		validate(datas);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fields", merge(datas));
		params.put("list", datas);
		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMasterSessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IBatchMapper<T> mapper = session.getMapper(getMapperClass());
			List<T> returnList = mapper.batchQuery(params);
			if (logger.isDebugEnabled()) {
				logger.debug("batchQuery(List<Map<String,Object>>, String) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception t) {
			logger.error("batchQuery(List<Map<String,Object>>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tower.service.dao.IBatchDAO#batchUpdate(java.util.Map,
	 * java.util.List, java.lang.String)
	 */
	@Override
	public Integer batchUpdate(Map<String, Object> new_,
			List<Map<String, Object>> datas, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("batchUpdate(Map<String,Object>, List<Map<String,Object>>, String) - start"); //$NON-NLS-1$
		}

		validate(datas);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fields", merge(datas));
		params.put("list", datas);
		params.put("newObj", new_);
		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = getMasterSessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IBatchMapper<T> mapper = session.getMapper(getMapperClass());
			int eft = mapper.batchUpdate(params);
			if (eft > 0) {
				this.synCache(CallFrom_TB,tabNameSuffix);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("batchUpdate(Map<String,Object>, List<Map<String,Object>>, String) - end"); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			logger.error(
					"batchUpdate(Map<String,Object>, List<Map<String,Object>>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tower.service.dao.IBatchDAO#batchDelete(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public Integer batchDelete(List<Map<String, Object>> datas,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("batchDelete(List<Map<String,Object>>, String) - start"); //$NON-NLS-1$
		}

		validate(datas);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fields", merge(datas));
		params.put("list", datas);
		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
		
		SqlSessionFactory sessionFactory = getMasterSessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IBatchMapper<T> mapper = session.getMapper(getMapperClass());
			int eft = mapper.batchDelete(params);
			if (eft > 0) {
				this.synCache(CallFrom_TB,tabNameSuffix);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("batchDelete(List<Map<String,Object>>, String) - end"); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			logger.error("batchDelete(List<Map<String,Object>>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	protected void validate(List<Map<String, Object>> datas) {
		if (logger.isDebugEnabled()) {
			logger.debug("validate(List<Map<String,Object>>) - start"); //$NON-NLS-1$
		}

		if (datas == null) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0005);
		}
		int size = datas.size();
		for(int i=0;i<size;i++){
			validate(datas.get(i));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validate(List<Map<String,Object>>) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public String getTableName() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTableName() - start"); //$NON-NLS-1$
		}

		throw new RuntimeException(this.getClass().getSimpleName()
				+ ".getTableName（）必须实现");
	}

	@Override
	public String get$TowerTabName(String tabNameSuffix) {

		suffixValidate(tabNameSuffix);
		StringBuilder tableName = new StringBuilder(this.getTableName());
		if (tabNameSuffix != null && tabNameSuffix.trim().length() > 0) {
			tableName.append("_");
			tableName.append(tabNameSuffix.trim());
		}
		String returnString = tableName.toString();

		return returnString;
	}

	@Override
	public int getVersion() {
		return 1;
	}
	
	public String serializable(Map<String,Object> params){
		return new TreeMap<String,Object>(params).toString();
	}
	/**
	 * 合并list 中的非空字段［其对应value曾经有非空的字段］列表
	 * @param list
	 * @return
	 */
	protected Map<String,Object> merge(List<Map<String,Object>> list){
    	Map<String,Object> map = new HashMap<String,Object>();
    	int size = list.size();
    	for(int i=0;i<size;i++){
    		Map<String,Object> tmp = list.get(i);
    		int tsize = tmp.size();
    		if(tsize>0){
    			String[] tarray = new String[tsize];
        		tmp.keySet().toArray(tarray);
        		for(int j=0;j<tsize;j++){
        			String tmpKey = tarray[j];
        			if(tmp.get(tmpKey)!=null && !map.containsKey(tmpKey)){
        				map.put(tmpKey, 0);
        			}
        			else if(tmp.get(tmpKey)!=null && map.containsKey(tmpKey)){
        				map.put(tmpKey, ((Integer)map.get(tmpKey))+1);
        			}
        		}
    		}
    	}
    	return map;
    }
	
	protected void validate(String[] cols){
		if (cols == null || cols.length==0) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0012);
		}
	}
}
