package com.tower.service.cache.mem.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.tower.service.cache.CacheOpParamsContext;
import com.tower.service.cache.ICache;
import com.tower.service.cache.annotation.CacheOpParams;
import com.tower.service.config.PrefixPriorityConfig;
import com.tower.service.config.dict.ConfigFileTypeDict;
import com.tower.service.util.DateUtil;

public class DynamicMemCache extends PrefixPriorityConfig
        implements
            Cache,
            ICache {
	/**
	 * Logger for this class
	 */
    public static final String DEFAULT_CACHE_NAME = "defaultCache";

    private MemCachedClient mcInstance;

    private String cacheName = DEFAULT_CACHE_NAME;

    public static final int DEFAULT_INITIAL_CONNECTIONS = 1;
    public static final int DEFAULT_MIN_SPARE_CONNECTIONS = 1;
    public static final int DEFAULT_MAX_SPARE_CONNECTIONS = 10;
    public static final long DEFAULT_MAX_IDLE_TIME = 1000 * 60 * 5;
    public static final long DEFAULT_MAX_BUSY_TIME = 1000 * 30;
    public static final long DEFAULT_MAINT_THREAD_SLEEP = 1000 * 30;
    public static final int DEFAULT_SOCKET_TIMEOUT = 1000 * 30;
    public static final int DEFAULT_SOCKET_CONNECT_TIMEOUT = 1000 * 3;
    public static final boolean DEFAULT_ALIVE_CHECK = false;
    public static final boolean DEFAULT_FAILOVER = false;
    public static final boolean DEFAULT_FAILBACK = true;
    public static final boolean DEFAULT_NAGLE_ALGORITHM = true;
    public static final int DEFAULT_HASHING_ALGORITHM = SockIOPool.CONSISTENT_HASH;
   
    @Override
    public boolean set(String key, Object item) {
		if (logger.isDebugEnabled()) {
			logger.debug("set(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

		boolean returnboolean = set(key, item, null);
		if (logger.isDebugEnabled()) {
			logger.debug("set(String, Object) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public boolean set(String key, Object item, int expiry) {
		if (logger.isDebugEnabled()) {
			logger.debug("set(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).append("int expiry", expiry).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		}

        if (expiry <= 0) {
			boolean returnboolean = this.set(key, item, null);
			if (logger.isDebugEnabled()) {
				logger.debug("set(String, Object, int) - end"); //$NON-NLS-1$
			}
            return returnboolean;
        } else {
			boolean returnboolean = mcInstance.set(key, item, new Date(System.currentTimeMillis() + expiry * 1000));
			if (logger.isDebugEnabled()) {
				logger.debug("set(String, Object, int) - end"); //$NON-NLS-1$
			}
            return returnboolean;
        }
    }

    @Override
    public boolean set(String key, Object item, Date expiry) {
		if (logger.isDebugEnabled()) {
			logger.debug("set(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).append("Date expiry", expiry).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		}

		boolean returnboolean = mcInstance.set(key, item, expiry);
		if (logger.isDebugEnabled()) {
			logger.debug("set(String, Object, Date) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public boolean add(String key, Object item) {
		if (logger.isDebugEnabled()) {
			logger.debug("add(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

		boolean returnboolean = add(key, item, null);
		if (logger.isDebugEnabled()) {
			logger.debug("add(String, Object) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public boolean add(String key, Object item, int expiry) {
		if (logger.isDebugEnabled()) {
			logger.debug("add(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).append("int expiry", expiry).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		}

		boolean returnboolean = add(key, item, new Date(System.currentTimeMillis() + expiry * 1000));
		if (logger.isDebugEnabled()) {
			logger.debug("add(String, Object, int) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public boolean add(String key, Object item, Date expiry) {
		if (logger.isDebugEnabled()) {
			logger.debug("add(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).append("Date expiry", expiry).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		}

		boolean returnboolean = mcInstance.add(key, item, expiry);
		if (logger.isDebugEnabled()) {
			logger.debug("add(String, Object, Date) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public boolean replace(String key, Object item) {
		if (logger.isDebugEnabled()) {
			logger.debug("replace(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

		boolean returnboolean = replace(key, item, null);
		if (logger.isDebugEnabled()) {
			logger.debug("replace(String, Object) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public boolean replace(String key, Object item, int expiry) {
		if (logger.isDebugEnabled()) {
			logger.debug("replace(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).append("int expiry", expiry).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		}

		boolean returnboolean = replace(key, item, new Date(System.currentTimeMillis() + expiry * 1000));
		if (logger.isDebugEnabled()) {
			logger.debug("replace(String, Object, int) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public boolean replace(String key, Object item, Date expiry) {
		if (logger.isDebugEnabled()) {
			logger.debug("replace(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Object item", item).append("Date expiry", expiry).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		}

		boolean returnboolean = mcInstance.replace(key, item, expiry);
		if (logger.isDebugEnabled()) {
			logger.debug("replace(String, Object, Date) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public boolean delete(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		boolean returnboolean = mcInstance.delete(key);
		if (logger.isDebugEnabled()) {
			logger.debug("delete(String) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public Object get(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("get(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		Object returnObject = mcInstance.get(key);
		if (logger.isDebugEnabled()) {
			logger.debug("get(String) - end"); //$NON-NLS-1$
		}
        return returnObject;
    }

    @Override
    public Object[] get(String[] keys) {
		if (logger.isDebugEnabled()) {
			logger.debug("get(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String[] keys", keys).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		Object[] returnObjectArray = mcInstance.getMultiArray(keys);
		if (logger.isDebugEnabled()) {
			logger.debug("get(String[]) - end"); //$NON-NLS-1$
		}
        return returnObjectArray;
    }

    @Override
    public boolean flush() {
		if (logger.isDebugEnabled()) {
			logger.debug("flush() - start"); //$NON-NLS-1$
		}

		boolean returnboolean = mcInstance.flushAll();
		if (logger.isDebugEnabled()) {
			logger.debug("flush() - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    public long addOrIncr(String key, long incr) {
		if (logger.isDebugEnabled()) {
			logger.debug("addOrIncr(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("long incr", incr).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

		long returnlong = mcInstance.addOrIncr(key, incr);
		if (logger.isDebugEnabled()) {
			logger.debug("addOrIncr(String, long) - end"); //$NON-NLS-1$
		}
        return returnlong;
    }

    @Override
    public long incr(String key, long incr) {
		if (logger.isDebugEnabled()) {
			logger.debug("incr(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("long incr", incr).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

		long returnlong = mcInstance.incr(key, incr);
		if (logger.isDebugEnabled()) {
			logger.debug("incr(String, long) - end"); //$NON-NLS-1$
		}
        return returnlong;

    }

    @Override
    public boolean storeCounter(String key, Long counter) {
		if (logger.isDebugEnabled()) {
			logger.debug("storeCounter(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("String key", key).append("Long counter", counter).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

		boolean returnboolean = mcInstance.storeCounter(key, counter);
		if (logger.isDebugEnabled()) {
			logger.debug("storeCounter(String, Long) - end"); //$NON-NLS-1$
		}
        return returnboolean;
    }

    @Override
    @PostConstruct
    public void init() {
		if (logger.isDebugEnabled()) {
			logger.debug("init() - start"); //$NON-NLS-1$
		}

        this.setFileName(System.getProperty(CACHE_MEM_CONFIG_FILE, DEFAULT_CACHE_MEM_CONFIG_NAME));
        this.setType(ConfigFileTypeDict.PROPERTIES);
        super.init();
        this.build(this.getConfig());

		if (logger.isDebugEnabled()) {
			logger.debug("init() - end"); //$NON-NLS-1$
		}
    }
    private String lastPoolName = null;
    public void build(Configuration config) {
		if (logger.isDebugEnabled()) {
			logger.debug("build(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("Configuration config", config).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

        String prefix_ = this.getPrefix();

        try {
        	
            String _sufix = DateUtil.format(new Date(), "yyyyMMddHHmmss");
            String poolName = prefix_ + _sufix;
            SockIOPool pool = SockIOPool.getInstance(poolName);
            if (pool.isInitialized()) {
                throw new IllegalArgumentException(String.format("%s has been created", poolName));
            }

            pool.setInitConn(config.getInt(prefix_ + "memcached.initialConnections",1));
            pool.setMinConn(config.getInt(prefix_ + "memcached.minSpareConnections",1));
            pool.setMaxConn(config.getInt(prefix_ + "memcached.maxSpareConnections",15));
            pool.setMaxIdle(config.getInt(prefix_ + "memcached.maxIdleTime",300000));
            pool.setMaxBusyTime(config.getInt(prefix_ + "memcached.maxBusyTime",30000));
            pool.setMaintSleep(config.getInt(prefix_ + "memcached.maintThreadSleep",30000));
            pool.setSocketTO(config.getInt(prefix_ + "memcached.socketTimeout",3000));
            pool.setSocketConnectTO(this.getInt(prefix_ + "memcached.socketConnectTimeout",3000));
            pool.setAliveCheck(config.getBoolean(prefix_ + "memcached.aliveCheck",false));
            pool.setFailover(config.getBoolean(prefix_ + "memcached.failover",false));
            pool.setFailback(config.getBoolean(prefix_ + "memcached.failback",true));
            pool.setNagle(config.getBoolean(prefix_ + "memcached.nagleAlgorithm",true));
            pool.setHashingAlg(config.getInt(prefix_ + "memcached.hashingAlgorithm",3));
            
            String[] servers = config.getStringArray(prefix_ + "memcached.servers");
            String[] weights = config.getStringArray(prefix_ + "memcached.weights");
            pool.setServers(servers);
            int len = weights == null ? 0 : weights.length;
            if (len > 0) {
                Integer[] weights_ = new Integer[len];
                for (int i = 0; i < len; i++) {
                    weights_[i] = Integer.valueOf(weights[i]);
                }
                pool.setWeights(weights_);
            }

            pool.initialize();
            MemCachedClient _tmpMC = new MemCachedClient(poolName);
            this.mcInstance = _tmpMC;

            if (lastPoolName != null) {
                try {
                    Thread.sleep(2000);
                    SockIOPool.getInstance(lastPoolName).shutDown();
                } catch (Exception ex) {
					logger.warn("build(Configuration) - exception ignored", ex); //$NON-NLS-1$
				}
            }
            
            lastPoolName = poolName;

        } catch (Exception e) {
			logger.warn("build(Configuration) - exception ignored", e); //$NON-NLS-1$

        }

		if (logger.isDebugEnabled()) {
			logger.debug("build(Configuration) - end"); //$NON-NLS-1$
		}
    }

    protected String configToString(Configuration config) {
		if (logger.isDebugEnabled()) {
			logger.debug("configToString(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("Configuration config", config).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

        String prefix_ = getPrefix();

        StringBuffer sb = new StringBuffer();

        sb.append(config.getInt(prefix_ + "memcached.initialConnections")).append("|");
        sb.append(config.getInt(prefix_ + "memcached.minSpareConnections")).append("|");
        sb.append(config.getInt(prefix_ + "memcached.maxSpareConnections")).append("|");
        sb.append(config.getInt(prefix_ + "memcached.maxIdleTime")).append("|");
        sb.append(config.getInt(prefix_ + "memcached.maxBusyTime")).append("|");
        sb.append(config.getInt(prefix_ + "memcached.maintThreadSleep")).append("|");
        sb.append(config.getInt(prefix_ + "memcached.socketTimeout")).append("|");
        sb.append(config.getInt(prefix_ + "memcached.socketConnectTimeout")).append("|");
        sb.append(config.getBoolean(prefix_ + "memcached.aliveCheck")).append("|");
        sb.append(config.getBoolean(prefix_ + "memcached.failover")).append("|");
        sb.append(config.getBoolean(prefix_ + "memcached.failback")).append("|");
        sb.append(config.getBoolean(prefix_ + "memcached.nagleAlgorithm")).append("|");
        sb.append(config.getInt(prefix_ + "memcached.hashingAlgorithm")).append("|");
        sb.append(arrayToString(config.getStringArray(prefix_ + "memcached.servers"))).append("|");
        sb.append(arrayToString(config.getStringArray(prefix_ + "memcached.weights")));

		String returnString = sb.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("configToString(Configuration) - end"); //$NON-NLS-1$
		}
        return returnString;
    }

    private String arrayToString(Object[] objs) {
		if (logger.isDebugEnabled()) {
			logger.debug("arrayToString(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("Object[] objs", objs).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

        StringBuffer sb = new StringBuffer();
        int len = objs == null ? 0 : objs.length;
        for (int i = 0; i < len; i++) {
            sb.append(objs[i].toString());
            if (i < len - 1) {
                sb.append("|");
            }
        }
		String returnString = sb.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("arrayToString(Object[]) - end"); //$NON-NLS-1$
		}
        return returnString;
    }

    // *********************以下方法遵循spring cacheManager规范********************

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        return mcInstance;
    }

    @Override
    public ValueWrapper get(Object key) {
		if (logger.isDebugEnabled()) {
			logger.debug("get(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("Object key", key).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

        Object value = this.mcInstance.get((String) key);
		ValueWrapper returnValueWrapper = (value != null ? new SimpleValueWrapper(value) : null);
		if (logger.isDebugEnabled()) {
			logger.debug("get(Object) - end {}",value); //$NON-NLS-1$
		}
        return returnValueWrapper;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
		if (logger.isDebugEnabled()) {
			logger.debug("get(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("Object key", key).append("Class<T> type", type).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

        Object value = this.mcInstance.get((String) key);
        if (value != null && type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type ["
                    + type.getName() + "]: " + value);
        }
		T returnT = (T) value;
		if (logger.isDebugEnabled()) {
			logger.debug("get(Object, Class<T>) - end"); //$NON-NLS-1$
		}
        return returnT;
    }

    @Override
    public void put(Object key, Object value) {
		if (logger.isDebugEnabled()) {
			logger.debug("put(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("Object key", key).append("Object value", value).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

        CacheOpParams params = CacheOpParamsContext.getOpParams();
        if (params != null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, Long.valueOf(params.time()).intValue());
            this.mcInstance.add((String) key, value, cal.getTime());// ((String) key, value,params.time());
        } else {
            this.mcInstance.add((String) key, value);
        }

		if (logger.isDebugEnabled()) {
			logger.debug("put(Object, Object) - end"); //$NON-NLS-1$
		}
    }

    @Override
    public void evict(Object key) {
		if (logger.isDebugEnabled()) {
			logger.debug("evict(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("Object key", key).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

        this.mcInstance.delete((String) key);

		if (logger.isDebugEnabled()) {
			logger.debug("evict(Object) - end"); //$NON-NLS-1$
		}
    }

	public ValueWrapper putIfAbsent(Object key, Object value) {
		if (logger.isDebugEnabled()) {
			logger.debug("putIfAbsent(" + new ToStringBuilder("", StandardToStringStyle.SIMPLE_STYLE).append("Object key", key).append("Object value", value).toString() + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

		Object value_ = this.mcInstance.get((String) key);
		if(value_==null){
			CacheOpParams params = CacheOpParamsContext.getOpParams();
	        if (params != null) {
	            Calendar cal = Calendar.getInstance();
	            cal.add(Calendar.SECOND, Long.valueOf(params.time()).intValue());
	            this.mcInstance.add((String) key, value, cal.getTime());// ((String) key, value,params.time());
	        } else {
	            this.mcInstance.add((String) key, value);
	        }
			ValueWrapper returnValueWrapper = new SimpleValueWrapper(value);
			if (logger.isDebugEnabled()) {
				logger.debug("putIfAbsent(Object, Object) - end"); //$NON-NLS-1$
			}
	        return returnValueWrapper;
		}
		ValueWrapper returnValueWrapper = (value_ != null ? new SimpleValueWrapper(value_) : null);
		if (logger.isDebugEnabled()) {
			logger.debug("putIfAbsent(Object, Object) - end"); //$NON-NLS-1$
		}
        return returnValueWrapper;
	}
}
