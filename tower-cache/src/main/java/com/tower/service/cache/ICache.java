package com.tower.service.cache;

import java.util.Date;

public interface ICache {

    public boolean set(String key, Object item);

    /**
     * set
     * @param key
     * @param item
     * @param expiry 单位为秒
     * @return
     */
    public boolean set(String key, Object item, int expiry);

    public boolean set(String key, Object item, Date expiry);

    public boolean add(String key, Object item);

    public boolean add(String key, Object item, int expiry);

    public boolean add(String key, Object item, Date expiry);

    public boolean storeCounter(String key, Long counter);

    public long addOrIncr(String key, long incr);

    public long incr(String key, long incr);

    public boolean replace(String key, Object item);

    public boolean replace(String key, Object item, int expiry);

    public boolean replace(String key, Object item, Date expiry);

    public boolean delete(String key);

    public Object get(String key);

    public Object[] get(String[] keys);

    public boolean flush();
}
