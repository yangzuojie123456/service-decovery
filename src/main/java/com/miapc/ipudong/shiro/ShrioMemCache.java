package com.miapc.ipudong.shiro;

import com.google.common.collect.Lists;
import com.miapc.ipudong.cache.MemcachedCache;
import com.miapc.ipudong.cache.MemcachedCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * Created by wangwei on 2016/10/31.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
@Slf4j
public class ShrioMemCache<K, V> implements Cache<K, V> {
    private String prefix = "shiro_Mem:";
    private MemcachedCacheManager cacheManager;
    private MemcachedCache cache;
    private Logger log= LoggerFactory.getLogger(ShrioMemCache.class);
    /**
     * Instantiates a new Shrio mem cache.
     *
     * @param prefix       the prefix
     * @param cacheManager the cache manager
     */
    public ShrioMemCache(String prefix, MemcachedCacheManager cacheManager) {
        this.prefix = prefix;
        this.cacheManager = cacheManager;

        cache = cacheManager.getMemCache(prefix);
    }

    @Override
    public V get(K key) throws CacheException {
        if (log.isDebugEnabled()) {
            log.debug("Key: {}", key);
        }
        if (key == null) {
            return null;
        }
        return (V) getCache().getValue(key);
    }

    /**
     * Gets cache.
     *
     * @return the cache
     */
    public MemcachedCache getCache() {
        if (cache == null) {
            cache = cacheManager.getMemCache(prefix);
        }
        return cache;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        if (log.isDebugEnabled()) {
            log.debug("Key: {}, value: {}", key, value);
        }

        if (key == null || value == null) {
            return null;
        }
        getCache().put(key, value);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        if (log.isDebugEnabled()) {
            log.debug("Key: {}", key);
        }

        if (key == null) {
            return null;
        }
        V value = get(key);
        getCache().evict(key);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        getCache().clear();
    }

    @Override
    public int size() {
        return getCache().size();
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) getCache().getKeys();
    }

    @Override
    public Collection<V> values() {
        return (Collection<V>) Arrays.asList(getCache().getValues());
    }

    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets prefix.
     *
     * @param prefix the prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
