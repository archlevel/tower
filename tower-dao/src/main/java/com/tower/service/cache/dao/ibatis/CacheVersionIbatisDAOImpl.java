package com.tower.service.cache.dao.ibatis;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.tower.service.cache.CacheVersion;
import com.tower.service.cache.dao.ICacheVersionDAO;
import com.tower.service.cache.dao.ibatis.mapper.CacheVersionMapper;
import com.tower.service.dao.ibatis.AbsStrIDIBatisDAOImpl;
import com.tower.service.dao.ibatis.IBatisDAOException;
import com.tower.service.dao.ibatis.ISMapper;
import com.tower.service.dao.ibatis.SqlmapUtils;
import com.tower.service.exception.DataAccessException;

@Repository("CacheVersion")
public class CacheVersionIbatisDAOImpl extends
		AbsStrIDIBatisDAOImpl<CacheVersion> implements
		ICacheVersionDAO<CacheVersion> {

	@Resource(name = "cacheSessionFactory")
	private SqlSessionFactory masterSessionFactory;

	@Resource(name = "cacheSlaveSessionFactory")
	private SqlSessionFactory slaveSessionFactory;

	@Resource(name = "cacheMapQuerySessionFactory")
	private SqlSessionFactory mapQuerySessionFactory;

	public CacheVersionIbatisDAOImpl() {
	}

	@Override
	public Class<CacheVersionMapper> getMapperClass() {

		return CacheVersionMapper.class;
	}

	@Override
	public Class<CacheVersion> getModelClass() {

		return CacheVersion.class;
	}

	@Override
	public boolean isFk(String property) {

		return CacheVersion.isFk(property);
	}

	public String getTableName() {
		return "soa_cache_version";
	}

	@Override
	public SqlSessionFactory getMasterSessionFactory() {
		return masterSessionFactory;
	}

	@Override
	public SqlSessionFactory getSlaveSessionFactory() {
		if (slaveSessionFactory == null) {
			return getMasterSessionFactory();
		}
		return slaveSessionFactory;
	}

	@Override
	public SqlSessionFactory getMapQuerySessionFactory() {
		if (mapQuerySessionFactory == null) {
			return getSlaveSessionFactory();
		}
		return mapQuerySessionFactory;
	}

	public String insert(CacheVersion model, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"insert(T model={}, String tabNameSuffix={}) - start", model, tabNameSuffix); //$NON-NLS-1$
		}

		validate(model);

		model.setTowerTabName(this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = this.getMasterSessionFactory();
		SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			ISMapper<CacheVersion> mapper = session.getMapper(getMapperClass());

			Long id = mapper.insert(model);
			if (id != null) {
				this.incrTabVersion(CallFrom_PK,tabNameSuffix);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
						"insert(T model={}, String tabNameSuffix={}) - end - return value={}", model, tabNameSuffix, id); //$NON-NLS-1$
			}
			return model.getId();
		} catch (Exception t) {
			logger.error("insert(T, String)", t); //$NON-NLS-1$
			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session, sessionFactory);
		}
	}

	@Cacheable(value = "defaultCache", key = CacheKeyPrefixExpress + "", unless = "#result == null", condition = "#root.target.pkCacheable() and #root.target.enable()")
	@Override
	public CacheVersion queryById(String id, String tabNameSuffix) {
		return queryById(id, false, tabNameSuffix);
	}

	@Cacheable(value = "defaultCache", key = CacheKeyPrefixExpress  + "", unless = "#result == null", condition = "!#master and #root.target.pkCacheable() and #root.target.enable()")
	@Override
	public CacheVersion queryById(String id, Boolean master,
			String tabNameSuffix) {
		validate(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = master ? this
				.getMasterSessionFactory() : getSlaveSessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
	    CacheVersion returnT = null;
		try {
			CacheVersionMapper mapper = session.getMapper(getMapperClass());
			List<CacheVersion> objs = mapper.queryByMap(params);
			if (objs != null && !objs.isEmpty()) {
				returnT = objs.get(0);
			}
			if (returnT == null) {
				returnT = new CacheVersion();
				returnT.setId(id);
				returnT.setRecVersion(new BigInteger("0"));
				returnT.setTabVersion(new BigInteger("0"));
				returnT.setTowerTabName(this.get$TowerTabName(tabNameSuffix));
				mapper.insert(returnT);
			}
			return returnT;
		} catch (Exception t) {
			logger.error("queryById(String, Boolean, String)", t); //$NON-NLS-1$

			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session,sessionFactory);
		}
	}

	@CacheEvict(value = "defaultCache", key = CacheKeyPrefixExpress+ "", condition = "#root.target.pkCacheable()")
	public int incrObjVersion(String id, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"incrObjVersion(String id={},String tabNameSuffix={}) - start", id,tabNameSuffix); //$NON-NLS-1$
		}
		validate(id);
		CacheVersion model = new CacheVersion();
		model.setId(id);
		model.setTowerTabName(this.get$TowerTabName(tabNameSuffix));
		SqlSessionFactory sessionFactory = this.getMasterSessionFactory();
		SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			CacheVersionMapper mapper = session.getMapper(getMapperClass());
			Integer eft = mapper.incrVersion(model);
			if (logger.isDebugEnabled()) {
				logger.debug(
						"incrObjVersion(String id={},String tabNameSuffix={}) - end - return value={}",id, tabNameSuffix, eft); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session, sessionFactory);
		}
	}

	@CacheEvict(value = "defaultCache", key = CacheKeyPrefixExpress+ "", condition = "#root.target.pkCacheable()")
	public int incrObjRecVersion(String id, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"incrObjRecVersion(String id={},String tabNameSuffix={}) - start", id,tabNameSuffix); //$NON-NLS-1$
		}
		validate(id);

		CacheVersion model = new CacheVersion();
		model.setId(id);
		model.setTowerTabName(this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = this.getMasterSessionFactory();
		SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			CacheVersionMapper mapper = session.getMapper(getMapperClass());
			Integer eft = mapper.incrRecVersion(model);
			if (logger.isDebugEnabled()) {
				logger.debug(
						"incrObjRecVersion(String id={},String tabNameSuffix={}) - end - return value={}",id, tabNameSuffix, eft); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session, sessionFactory);
		}
	}

	@CacheEvict(value = "defaultCache", key = CacheKeyPrefixExpress+ "", condition = "#root.target.pkCacheable()")
	public int incrObjTabVersion(String id, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"incrObjTabVersion(String id={},String tabNameSuffix={}) - start", id,tabNameSuffix); //$NON-NLS-1$
		}
		validate(id);

		CacheVersion model = new CacheVersion();
		model.setId(id);
		model.setTowerTabName(this.get$TowerTabName(tabNameSuffix));

		SqlSessionFactory sessionFactory = this.getMasterSessionFactory();
		SqlSession session = SqlmapUtils.openSession(sessionFactory);
		try {
			CacheVersionMapper mapper = session.getMapper(getMapperClass());
			Integer eft = mapper.incrTabVersion(model);
			if (logger.isDebugEnabled()) {
				logger.debug(
						"incrObjTabVersion(String id={},String tabNameSuffix={}) - end - return value={}",id, tabNameSuffix, eft); //$NON-NLS-1$
			}
			return eft;
		} catch (Exception t) {
			throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
		} finally {
			SqlmapUtils.release(session, sessionFactory);
		}
	}

	protected void validate(String id) {
		if (id == null || id.trim().length() == 0) {
			throw new DataAccessException(IBatisDAOException.MSG_1_0004);
		}
	}
}
