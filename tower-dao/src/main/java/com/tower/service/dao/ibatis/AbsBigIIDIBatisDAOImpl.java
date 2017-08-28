package com.tower.service.dao.ibatis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.tower.service.cache.IModel;
import com.tower.service.dao.IBBatchDAO;
import com.tower.service.exception.DataAccessException;

/**
 * 主键缓存(pk)<br>
 * 应用场景:根据主键查询时使用。<br>
 * 查询执行步骤:<br>
 * 1. select * from loupan_basic where loupan_id=?;<br>
 * 2. 是否存在缓存，mc缓存key拼接规则{表名}+{recVersion}+{主键ID},
 * 如果存在直接返回结果，否则查询数据库，设置缓存并返回结果。例如：loupan_basic表主键mc缓存key:loupan_basic##2##1<br>
 * 更新执行步骤:<br>
 * 1. update loupan_basic set loupan_name=? where loupan_id=?;<br>
 * 2. 删除外键缓存。(如何删除外键缓存，下文有说明)<br>
 * 3. 删除对应的主键缓存<br>
 * 4. 更新updated版本号，删除cache_tags表缓存<br>
 * 
 * @author alexzhu
 *
 * @param <T>
 */

public abstract class AbsBigIIDIBatisDAOImpl<T extends IModel> extends
		AbsFKIBatisDAOImpl<T> implements IBigIBatisDAO<T>, IBBatchDAO<T> {
	/**
	 * Logger for this class
	 */

	@Override
	public BigInteger[] batchInsert(List<Map<String, Object>> datas,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("batchInsert(List<Map<String,Object>>, String) - start"); //$NON-NLS-1$
		}
		validate(datas);
		Map<String,Object> cols = this.merge(datas);
		BigInteger[] returnBigIntegerArray = batchInsert(new ArrayList(cols.keySet()), datas,
				tabNameSuffix);
		if (logger.isDebugEnabled()) {
			logger.debug("batchInsert(List<Map<String,Object>>, String) - end"); //$NON-NLS-1$
		}
		return returnBigIntegerArray;
	}
	
	@Override
	public BigInteger[] batchInsert(String[] cols,List<Map<String,Object>> datas, String tabNameSuffix){
		validate(cols);
		return batchInsert(Arrays.asList(cols), datas, tabNameSuffix);
	}
	
	@Override
	public BigInteger[] batchInsert(List<String> cols,
			List<Map<String, Object>> datas, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug("batchInsert(List<String>, List<Map<String,Object>>, String) - start"); //$NON-NLS-1$
		}

		validate(datas);
		Map<String, Object> params = new HashMap<String, Object>();
		if (cols == null || cols.size() == 0) {
			Map<String, Object> firstData = datas.get(0);
			cols = new ArrayList<String>(firstData.keySet());
		}
		validateCols(cols);
		params.put("batchInsertProps", cols);
		params.put("batchInsertCols", convert(cols));
		params.put("list", datas);
		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
		SqlSessionFactory sessionFactory = getMasterSessionFactory();
		SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IBatchMapper<T> mapper = session.getMapper(getMapperClass());
			Integer eft = mapper.batchInsert(params);
			if (eft > 0) {
				BigInteger lastId = (BigInteger) params.get("id");
				this.incrTabVersion(CallFrom_PK,tabNameSuffix);
				BigInteger[] ids = new BigInteger[eft];
				for (int i = 0; i < eft; i++) {
					ids[i] = lastId.add(new BigInteger(String.valueOf(i)));
				}

				if (logger.isDebugEnabled()) {
					logger.debug("batchInsert(List<String>, List<Map<String,Object>>, String) - end"); //$NON-NLS-1$
				}
				return ids;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("batchInsert(List<String>, List<Map<String,Object>>, String) - end"); //$NON-NLS-1$
			}
			return null;
		} catch (Exception t) {
			logger.error(
					"batchInsert(List<String>, List<Map<String,Object>>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session, sessionFactory);
		}
	}

	@Cacheable(value = "defaultCache", key = PkCacheKeyPrefixExpress + "", unless = "#result == null", condition = "#root.target.pkCacheable()")
	@Override
	public T queryById(BigInteger id, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"queryById(BigInteger id={}, String tabNameSuffix={}) - start", id, tabNameSuffix); //$NON-NLS-1$
		}

		T returnT = queryById(id, false, tabNameSuffix);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"queryById(BigInteger id={}, String tabNameSuffix={}) - end - return value={}", id, tabNameSuffix, returnT); //$NON-NLS-1$
		}
		return returnT;

	}

	@Cacheable(value = "defaultCache", key = PkCacheKeyPrefixExpress + "", unless = "#result == null", condition = "!#master and #root.target.pkCacheable()")
	@Override
	public T queryById(BigInteger id, Boolean master, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"queryById(BigInteger id={}, Boolean master={}, String tabNameSuffix={}) - start", id, master, tabNameSuffix); //$NON-NLS-1$
		}

		validate(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
		SqlSessionFactory sessionFactory = master ? this
				.getMasterSessionFactory() : getSlaveSessionFactory();
		SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IBigIMapper<T> mapper = session.getMapper(getMapperClass());
			List<T> objs = mapper.queryByMap(params);
			if (objs == null || objs.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug(
							"queryById(BigInteger id={}, Boolean master={}, String tabNameSuffix={}) - end - return value={}", id, master, tabNameSuffix, null); //$NON-NLS-1$
				}
				return null;
			}
			T returnT = objs.get(0);

			if (logger.isDebugEnabled()) {
				logger.debug(
						"queryById(BigInteger id={}, Boolean master={}, String tabNameSuffix={}) - end - return value={}", id, master, tabNameSuffix, returnT); //$NON-NLS-1$
			}
			return returnT;
		} catch (Exception t) {
			logger.error("queryById(BigInteger, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session, sessionFactory);
		}
	}

	@CacheEvict(value = "defaultCache", key = PkCacheKeyPrefixExpress + "", condition = "#root.target.pkCacheable()")
	@Override
	public Integer deleteById(BigInteger id, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"deleteById(BigInteger id={}, String tabNameSuffix={}) - start", id, tabNameSuffix); //$NON-NLS-1$
		}

		validate(id);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
		SqlSessionFactory sessionFactory = getMasterSessionFactory();
		SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			Integer eft = mapper.deleteByMap(params);
			if (eft > 0) {
				this.incrTabVersion(CallFrom_PK,tabNameSuffix);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
						"deleteById(BigInteger id={}, String tabNameSuffix={}) - end - return value={}", id, tabNameSuffix, eft); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			logger.error("deleteById(BigInteger, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheEvict(value = "defaultCache", key = PkCacheKeyPrefixExpress + "", condition = "#root.target.pkCacheable()")
	@Override
	public Integer updateById(BigInteger id, Map<String, Object> newValue,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"updateById(BigInteger id={}, Map<String,Object> newValue={}, String tabNameSuffix={}) - start", id, newValue, tabNameSuffix); //$NON-NLS-1$
		}

		validate(id);

		if (newValue == null || newValue.isEmpty()) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0007);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(newValue);
		params.put("id", id);
		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));
		SqlSessionFactory sessionFactory = getMasterSessionFactory();
		SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			IMapper<T> mapper = session.getMapper(getMapperClass());
			Integer eft = mapper.updateById(params);
			if (eft > 0) {
				this.incrTabVersion(CallFrom_PK,tabNameSuffix);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
						"updateById(BigInteger id={}, Map<String,Object> newValue={}, String tabNameSuffix={}) - end - return value={}", id, newValue, tabNameSuffix, eft); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			logger.error("updateById(BigInteger, Map<String,Object>, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	protected void validate(BigInteger id) {
		if (logger.isDebugEnabled()) {
			logger.debug("validate(BigInteger id={}) - start", id); //$NON-NLS-1$
		}

		if (id == null) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0005);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validate(BigInteger id={}) - end", id); //$NON-NLS-1$
		}
	}

}
