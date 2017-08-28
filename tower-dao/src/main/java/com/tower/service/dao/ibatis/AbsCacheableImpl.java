package com.tower.service.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.cache.CacheManager;

import redis.clients.jedis.exceptions.JedisDataException;

import com.tower.service.cache.CacheSwitcher;
import com.tower.service.cache.CacheVersion;
import com.tower.service.cache.CacheVersionStack;
import com.tower.service.cache.ICacheable;
import com.tower.service.cache.IModel;
import com.tower.service.cache.dao.ICacheVersionDAO;
import com.tower.service.cache.mem.impl.DynamicMemCache;
import com.tower.service.cache.redis.impl.DynamicRedisCache;
import com.tower.service.config.DynamicConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.exception.DataAccessException;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public abstract class AbsCacheableImpl<T extends IModel> implements
		ICacheable<T> {
	// private int cnt = 0;
	@Resource(name = "CacheVersion")
	private ICacheVersionDAO<CacheVersion> cacheVersionDAO;
	/**
	 * Logger for this class
	 */
	protected final Logger logger = LoggerFactory
			.getLogger("com.tower.service.dao.ibatis.Cache");

	protected DynamicMemCache defaultCache;

	protected DynamicRedisCache redisCache;

	@Resource(name = "cacheManager")
	protected CacheManager cacheManager;

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	@Resource(name = ConfigComponent.CacheConfig)
	protected DynamicConfig cacheConfig;

	public void init() {
		if (logger.isDebugEnabled()) {
			logger.debug("init() - start"); //$NON-NLS-1$
		}

		defaultCache = (DynamicMemCache) this.getCacheManager().getCache(
				DynamicMemCache.DEFAULT_CACHE_NAME);
		redisCache = (DynamicRedisCache) this.getCacheManager().getCache(
				DynamicRedisCache.DEFAULT_CACHE_NAME);

		if (logger.isDebugEnabled()) {
			logger.debug("init() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * 表级缓存keyPrefix TowerTabName+tabNameSuffix@Tn@TV@TabVersion
	 * 
	 * @return
	 */
	public final String getTabCacheKeyPrefix(String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"getTabCacheKeyPrefix(String tabNameSuffix={}) - start", tabNameSuffix); //$NON-NLS-1$
		}

		String returns = getTabVersionKey(tabNameSuffix) + "@TV"
				+ this.getTabVersion(ICacheable.CallFrom_TB,tabNameSuffix);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"getTabCacheKeyPrefix(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, returns); //$NON-NLS-1$
		}
		return returns;
	}

	/**
	 * 表级缓存版本key TowerTabName+tabNameSuffix@Tn
	 * 
	 * @return
	 */
	private String getTabVersionKey(String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"getTabVersionKey(String tabNameSuffix={}) - start", tabNameSuffix); //$NON-NLS-1$
		}

		String returns = this.get$TowerTabName(tabNameSuffix)
				+ "@T"
				+ cacheConfig.getInteger(redisCache.getPrefix()
						+ "tab.cache.tag", 0);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"getTabVersionKey(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, returns); //$NON-NLS-1$
		}
		return returns;
	}

	/**
	 * 主键缓存keyPrefix TowerTabName+tabNameSuffix@Rn@RecVersion
	 * 
	 * @return
	 */
	public String getPKRecCacheKeyPrefix(String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"getPKRecCacheKeyPrefix(String tabNameSuffix={}) - start", tabNameSuffix); //$NON-NLS-1$
		}

		String returns = getRecVersionKey(tabNameSuffix) + "@RV"
				+ this.getRecVersion(ICacheable.CallFrom_PK,tabNameSuffix);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"getPKRecCacheKeyPrefix(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, returns); //$NON-NLS-1$
		}
		return returns;
	}

	/**
	 * 外键缓存keyPrefix TowerTabName+tabNameSuffix@Rn@RecVersion@TabVersion
	 */
	public String getFKRecCacheKeyPrefix(String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"getFKRecCacheKeyPrefix(String tabNameSuffix={}) - start", tabNameSuffix); //$NON-NLS-1$
		}

		String returns = getRecVersionKey(tabNameSuffix) + "@RV"
				+ this.getRecVersion(ICacheable.CallFrom_FK,tabNameSuffix) + "@TV"
				+ getTabVersion(ICacheable.CallFrom_FK,tabNameSuffix);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"getFKRecCacheKeyPrefix(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, returns); //$NON-NLS-1$
		}
		return returns;
	}

	/**
	 * 记录级缓存版本key TowerTabName+tabNameSuffix@Rn
	 * 
	 * @return
	 */
	private String getRecVersionKey(String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"getRecVersionKey(String tabNameSuffix={}) - start", tabNameSuffix); //$NON-NLS-1$
		}

		String returns = this.get$TowerTabName(tabNameSuffix)
				+ "@R"
				+ cacheConfig.getInteger(redisCache.getPrefix()
						+ "rec.cache.tag", 0);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"getRecVersionKey(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, returns); //$NON-NLS-1$
		}
		return returns;
	}

	/**
	 * 表级缓存
	 * 
	 * 更新执行步骤：<br>
	 * 
	 * 改变tabVersion<br>
	 * 
	 * 改变recVersion<br>
	 * 
	 * @param property
	 * @param value
	 */
	protected void synCache(int callFrom, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"synCache(String tabNameSuffix={}) - start", tabNameSuffix); //$NON-NLS-1$
		}

		if (!cacheable()) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"synCache(String tabNameSuffix={}) - end", tabNameSuffix); //$NON-NLS-1$
			}
			return;
		}
		/**
		 * 改变表版本号：表级缓存、外键缓存实效
		 */
		this.incrTabVersion(callFrom, tabNameSuffix);
		/**
		 * 改变记录版本号：主键缓存、外键缓存实效
		 */
		this.incrRecVersion(callFrom,tabNameSuffix);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"synCache(String tabNameSuffix={}) - end", tabNameSuffix); //$NON-NLS-1$
		}
	}

	/**
	 * ［表、外键］缓存版本号升级
	 */
	@Override
	public long incrTabVersion(int callFrom, String tabNameSuffix) {

		if (callFrom == 1) {// 主键操作
			if (!fkCacheable() && !tabCacheable()) {
				return 0l;
			}
		} else if (callFrom == 2 || callFrom == 3) {// 外键或者表级操作
			if (!tabCacheable()) {
				return 0l;
			}
		}
		String key = this.get$TowerTabName(tabNameSuffix);
		try {

			long eft = cacheVersionDAO.incrObjTabVersion(
					this.get$TowerTabName(tabNameSuffix), null);
			
			Map<String,CacheVersion> tabvs = CacheVersionStack.getTabvs();
			if(tabvs!=null){
				tabvs.remove(key);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
						"incrTabVersion(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, eft); //$NON-NLS-1$
			}
			return eft;
		} catch (JedisDataException ex) {
			logger.error("incrTabVersion(String)", ex); //$NON-NLS-1$

			if (logger.isDebugEnabled()) {
				logger.debug(
						"incrTabVersion(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, 0); //$NON-NLS-1$
			}

			return 0;
		}
	}

	/**
	 * 集合级缓存［表级、外键］版本号
	 */
	@Override
	public Long getTabVersion(int callFrom,String tabNameSuffix) {
		
		if (callFrom == 2 && !fkCacheable()) {// 主键操作
			return 0l;
		} else if (callFrom == 3 && !tabCacheable()) {// 外键或者表级操作
			return 0l;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(
					"getTabVersion(String tabNameSuffix={}) - start", tabNameSuffix); //$NON-NLS-1$
		}
		String key = this.get$TowerTabName(tabNameSuffix);
		
		CacheVersion tabv = null;
		Map<String,CacheVersion> tabvs = CacheVersionStack.getTabvs();
		if(tabvs!=null){
			tabv = tabvs.get(key);
		}
		
		if (tabv != null) {
			return tabv.getTabVersion().longValue();
		} else {
			tabv = cacheVersionDAO.queryById(key, null);// redisCache.get(getRecVersionKey(tabNameSuffix));
			if (tabv != null) {
				CacheVersionStack.setTabv(key, tabv);
			}
		}
		String vStr = tabv == null ? "0" : tabv.getTabVersion().toString();
		if (vStr == null) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"getTabVersion(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, 0l); //$NON-NLS-1$
			}
			return 0l;
		} else {
			CacheVersionStack.setTabv(key, tabv);
		}
		Long version = Long.valueOf(vStr);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"getTabVersion(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, version); //$NON-NLS-1$
		}
		return version;
	}

	/**
	 * ［主键、外键］缓存版本号升级
	 */
	@Override
	public long incrRecVersion(int callFrom,String tabNameSuffix) {
		
		if (callFrom == 1 && !pkCacheable()) {// 主键操作
			return 0l;
		}
		
		if (callFrom == 2 && !fkCacheable()) {
			return 0l;
		}
		
		String key = this.get$TowerTabName(tabNameSuffix);
		try {
			long eft = cacheVersionDAO.incrObjRecVersion(
					this.get$TowerTabName(tabNameSuffix), null);
			Map<String,CacheVersion> tabvs = CacheVersionStack.getTabvs();
			if(tabvs!=null){
				tabvs.remove(key);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(
						"incrRecVersion(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, eft); //$NON-NLS-1$
			}
			return eft;
		} catch (JedisDataException ex) {
			logger.error("incrRecVersion(String)", ex); //$NON-NLS-1$

			if (logger.isDebugEnabled()) {
				logger.debug(
						"incrRecVersion(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, 0l); //$NON-NLS-1$
			}
			return 0l;
		}
	}

	/**
	 * 记录［主键、外键］级缓存版本号
	 */
	@Override
	public Long getRecVersion(int callFrom,String tabNameSuffix) {
		
		if (callFrom == 1 && !pkCacheable()) {// 主键操作
			return 0l;
		}
		
		if (callFrom == 2 && !fkCacheable()) {
			return 0l;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(
					"getRecVersion(String tabNameSuffix={}) - start", tabNameSuffix); //$NON-NLS-1$
		}
		
		String key = this.get$TowerTabName(tabNameSuffix);
		CacheVersion tabv = null;
		Map<String,CacheVersion> tabvs = CacheVersionStack.getTabvs();
		if(tabvs!=null){
			tabv = tabvs.get(key);
		}
		
		if (tabv != null) {
			return tabv.getRecVersion().longValue();
		} else {
			tabv = cacheVersionDAO.queryById(key, null);// redisCache.get(getRecVersionKey(tabNameSuffix));
			if (tabv != null) {
				CacheVersionStack.setTabv(key, tabv);
			}
		}
		String vStr = tabv == null ? "0" : tabv.getRecVersion().toString();
		if (vStr == null) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"getRecVersion(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, 0l); //$NON-NLS-1$
			}
			return 0l;
		}

		Long version = Long.valueOf(vStr);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"getRecVersion(String tabNameSuffix={}) - end - return value={}", tabNameSuffix, version); //$NON-NLS-1$
		}
		return version;
	}

	private void synPKCache(int callFrom, int eft, Map<String, Object> params,
			String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"synPKCache(int eft={}, Map<String,Object> params={}, String tabNameSuffix={}) - start", eft, params, tabNameSuffix); //$NON-NLS-1$
		}

		/**
		 * 查询主键ids
		 */
		if (params == null || params.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"synPKCache(int eft={}, Map<String,Object> params={}, String tabNameSuffix={}) - end", eft, params, tabNameSuffix); //$NON-NLS-1$
			}
			return;
		}
		if (eft >= getThresholds()) {
			/**
			 * >主键缓存数量超过threshold_for_delete_pk_by_where＝100值时， 更新recVersion版本号<br>
			 * 当前表主键缓存、外键缓存实效
			 */
			incrRecVersion(callFrom,tabNameSuffix);
		} else {
			List<String> ids = queryIdsByMap(params, tabNameSuffix);
			this.pkCacheEvict(ids, tabNameSuffix);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"synPKCache(int eft={}, Map<String,Object> params={}, String tabNameSuffix={}) - end", eft, params, tabNameSuffix); //$NON-NLS-1$
		}
	}

	/**
	 * 
	 * 
	 * 外键缓存
	 * 
	 * 添加当前外键查询条件到keyGroup
	 * 
	 * 执行步骤:<br>
	 * 
	 * 1. select * from house_types where loupan_id=?;<br>
	 * 
	 * 2. 拼接mc缓存key，{表名}+{recVersion}+{查询条件参数}。ex:house_types@4@{loupan_id=?},
	 * 缓存为expire为1天<br>
	 * 
	 * 3. 将mc缓存key添加到redis的keyGroup［hset结构］中。redis
	 * key:{表名}+{recVersion}+{外键名}+{外键值} [keyGroup:key，value［set］]，同时expire调整到一天<br>
	 * 
	 * 4. 判断是否存在mc缓存key,如果存在直接返回结果，否则查询数据设置mc缓存，返回结果。<br>
	 * 
	 * > 注意：本次redis缓存的key每次都需要调整expire为一天
	 * 
	 * @param property
	 * @param value
	 * @param result
	 */
	protected void addKey2FKGroupCache(String property, Object value,
			final Map<String, Object> attchParams, final List<T> result,
			String tabNameSuffix) {
		if(!fkCacheable()){
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(
					"addKey2FKGroupCache(String property={}, Object value={}, Map<String,Object> attchParams={}, List<T> result={}, String tabNameSuffix={}) - start", property, value, attchParams, result, tabNameSuffix); //$NON-NLS-1$
		}
		/**
		 * 外键缓存keyPrefix TowerTabName+tabNameSuffix@Rn@RecVersion@TabVersion
		 */
		final String fKeyPrefix = getFKRecCacheKeyPrefix(tabNameSuffix);

		final StringBuffer keyGroupKeys = new StringBuffer(fKeyPrefix);
		keyGroupKeys.append("@");
		keyGroupKeys.append(property);
		keyGroupKeys.append("=");
		keyGroupKeys.append(value);

		final int size = result == null ? 0 : result.size();
		if (size > 0) {

			String keyGroupKey = keyGroupKeys.toString();

			if (attchParams != null && !attchParams.isEmpty()) {
				keyGroupKeys.append("@");
				keyGroupKeys.append(attchParams);
			}

			/**
			 * 将mc的缓存key纪录到redis中,每添加一个key到外键keyGroup中，其过期时间顺后延一天
			 */
			redisCache.sadd(keyGroupKey, keyGroupKeys.toString(), ONE_DAY);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"addKey2FKGroupCache(String property={}, Object value={}, Map<String,Object> attchParams={}, List<T> result={}, String tabNameSuffix={}) - end", property, value, attchParams, result, tabNameSuffix); //$NON-NLS-1$
		}
	}

	/**
	 * 外键缓存
	 * 
	 * 更新执行步骤:<br>
	 * 
	 * 1. update house_types where loupan_id=?;<br>
	 * 
	 * 2. 找出house_types对应的主键缓存,删除主键缓存。<br>
	 * >主键缓存数量超过threshold_for_delete_pk_by_where＝100值时，更新recVersion版本号<br>
	 * 
	 * 3. 删除外键缓存。<br>
	 * > 查出redis中对应的外键缓存。(set结构，其中的值对应mc中的key)<br>
	 * > 删除mckey对应的值。<br>
	 * 4. 更新updated版本号，删除cache_tags表缓存<br>
	 * 
	 * @param property
	 * @param value
	 */
	protected void synCache(int callFrom, int eft, final String property,
			final Object value, final Map<String, Object> attchParams,
			String tabNameSuffix) {

		if (!cacheable()) {
			return;
		}
		/**
		 * 外键缓存keyPrefix TowerTabName+tabNameSuffix@Rn@RecVersion@TabVersion
		 */
		final String fKeyPrefix = getFKRecCacheKeyPrefix(tabNameSuffix);

		final StringBuffer keyGroupKeys = new StringBuffer(fKeyPrefix);
		keyGroupKeys.append("@");
		keyGroupKeys.append(property);
		keyGroupKeys.append("=");
		keyGroupKeys.append(value);

		String keyGroupKey = keyGroupKeys.toString();

		Set<String> keyGroup = redisCache.sget(keyGroupKey);

		fkCacheEvict(keyGroup);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(property, value);
		if (attchParams != null) {
			params.putAll(attchParams);
		}

		synPKCache(callFrom, eft, params, tabNameSuffix);

		/**
		 * 表级缓存失效
		 */
		incrTabVersion(callFrom, tabNameSuffix);
		// 删除外键缓存

		if (logger.isDebugEnabled()) {
			logger.debug(
					"synCache(int eft={}, String property={}, Object value={}, Map<String,Object> attchParams={}, String tabNameSuffix={}) - end", eft, property, value, attchParams, tabNameSuffix); //$NON-NLS-1$
		}
	}

	/**
	 * 删除相关外键缓存
	 * 
	 * @param key
	 */
	private void fkCacheEvict(Set<String> keyGroup) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"fkCacheEvict(Set<String> keyGroup={}) - start", keyGroup); //$NON-NLS-1$
		}

		int size = keyGroup == null ? 0 : keyGroup.size();
		if (size > 0) {
			String[] keyGroups = new String[size];
			keyGroup.toArray(keyGroups);
			/**
			 * 删除某一外键对应的所有外键缓存
			 */
			for (int i = 0; i < size; i++) {
				defaultCache.evict(keyGroups[i]);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"fkCacheEvict(Set<String> keyGroup={}) - end", keyGroup); //$NON-NLS-1$
		}
	}

	/**
	 * 清空相关主键缓存
	 * 
	 * @param key
	 */
	protected void pkCacheEvict(List<String> ids, String tabNameSuffix) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"pkCacheEvict(List<String> ids={}, String tabNameSuffix={}) - start", ids, tabNameSuffix); //$NON-NLS-1$
		}

		String pkPrefix = this.getPKRecCacheKeyPrefix(tabNameSuffix);
		int size = ids.size();
		if (size > 0) {

			Long[] idsa = new Long[size];
			ids.toArray(idsa);

			for (int i = 0; i < size; i++) {
				try {
					// 删除主键缓存
					/**
					 * mc pkPrefix: TowerTabName+tabNameSuffix@Rn@RV@id=value
					 */
					String pk = idsa[i].toString();
					String key = pkPrefix + "@id=" + pk;
					defaultCache.evict(key);
				} catch (Exception ex) {
					logger.warn(
							"pkCacheEvict(List<String>, String) - exception ignored", ex); //$NON-NLS-1$
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"pkCacheEvict(List<String> ids={}, String tabNameSuffix={}) - end", ids, tabNameSuffix); //$NON-NLS-1$
		}
	}

	public abstract List<String> queryIdsByMap(Map<String, Object> params,
			String tabNameSuffix);

	protected void suffixValidate(String tabNameSuffix) {
		if (tabNameSuffix == null || tabNameSuffix.trim().length() == 0) {
			return;
		}
		Pattern p = Pattern.compile(TAB_SUFFIX_PATTERN);
		Matcher m = p.matcher(tabNameSuffix.trim());
		if (!m.matches()) {
			throw new DataAccessException(IBatisDAOException.MSG_2_0001, "'"
					+ tabNameSuffix + "'后缀不符合规范");
		}
	}

	/**
	 * 系统是否启用缓存总开关
	 */
	public boolean cacheable() {
		Boolean returnboolean = cacheConfig.getBoolean(redisCache.getPrefix()
				+ CACHE_FLG, false);// 缓存总开关
		return returnboolean; // 缓存开关
	}

	/**
	 * 当前请求是否启用cache
	 * 
	 * @return
	 */
	public boolean enable() {
		return CacheSwitcher.get()&&!SqlmapUtils.hasTransaction();
	}

	/**
	 * 主键缓存开关
	 */
	public boolean pkCacheable() {

		boolean returnboolean = cacheable() // 缓存开关
				&& cacheConfig.getBoolean(
						redisCache.getPrefix() + PK_CACHE_FLG, cacheable());// 主键缓存开关
		return returnboolean; // 主键缓存
	}

	/**
	 * 外键缓存开关
	 */
	public boolean fkCacheable() {

		boolean returnboolean = pkCacheable() // 主键缓存
				&& cacheConfig.getBoolean(
						redisCache.getPrefix() + FK_CACHE_FLG, pkCacheable());// 外键缓存开关
		return returnboolean;// 表级缓存
	}

	/**
	 * 表级缓存开关 缓存开关必须开启，主键缓存、外键缓存必须开启
	 */
	public boolean tabCacheable() {

		boolean returnboolean = fkCacheable() // 外键缓存开关
				&& cacheConfig.getBoolean(
						redisCache.getPrefix() + TB_CACHE_FLG, fkCacheable()); // 表级缓存

		return returnboolean;// 表级缓存
	}

	@Override
	public Integer getThresholds() {

		Integer returnInteger = cacheConfig.getInteger(redisCache.getPrefix()
				+ THRESHOLD_FOR_DEL_PK_BY_WHERE, 100);

		return returnInteger;
	}
}
