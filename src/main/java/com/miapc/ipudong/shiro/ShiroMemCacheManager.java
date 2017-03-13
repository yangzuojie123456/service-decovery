package com.miapc.ipudong.shiro;

import com.miapc.ipudong.cache.MemcachedCacheManager;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangwei on 2016/10/31.
 */
@Component
public class ShiroMemCacheManager extends AbstractCacheManager {
    private MemcachedCacheManager cacheManager;

    /**
     * Instantiates a new Shiro mem cache manager.
     *
     * @param cacheManager the cache manager
     */
    public ShiroMemCacheManager(MemcachedCacheManager cacheManager) {
        this.cacheManager=cacheManager;
    }

    @Override
    protected Cache createCache(String name) throws CacheException {
        return new ShrioMemCache(name, cacheManager);
    }
}
