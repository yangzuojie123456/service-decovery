package com.miapc.ipudong.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by wangwei on 15/5/6.
 */
@Component("cacheManager")
@Scope("singleton")
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.cache.memecache")
public class MemcachedCacheManager implements CacheManager {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(MemcachedCacheManager.class);
	
	/** The cache map. */
	private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
	
	/** The expire map. */
	private Map<String, Integer> expire = new HashMap<String, Integer>(); // 缓存的时间
	
	/** The memcache host. */
	@Value("${spring.cache.memecache.host}")
	private String memcacheHost;
	
	/** The memcache port. */
	@Value("${spring.cache.memecache.port}")
	private String memcachePort;
	
	/** The memcached client. */
	private MemcachedClient memcachedClient; // xmemcached的客户端

	/**
	 * Instantiates a new Memcached cache manager.
	 */
	public MemcachedCacheManager() {
		logger.info("MemcachedCacheManager init");
	}

	/**
	 * Gets the memcached client.
	 *
	 * @return the memcached client
	 */
	private MemcachedClient getMemcachedClient() {
		if (memcachedClient == null) {
			try {
				memcachedClient = new XMemcachedClientBuilder(memcacheHost + ":" + memcachePort).build();
				memcachedClient.setEnableHeartBeat(true);
				memcachedClient.setConnectTimeout(3000);
				memcachedClient.setOpTimeout(3000);
				memcachedClient.setOptimizeGet(true);
			} catch (IOException e) {
				logger.error("Memcache Client init error!   " + e.getMessage());
			}
		}
		return memcachedClient;
	}

	/**
	 * Load caches.
	 *
	 * @return the collection<? extends cache>
	 */
	protected Collection<? extends Cache> loadCaches() {
		Collection<Cache> values = cacheMap.values();
		return values;
	}

	/* (non-Javadoc)
	 * @see org.springframework.cache.CacheManager#getCache(java.lang.String)
	 */
	@Override
	public Cache getCache(String name) {
		Cache cache = cacheMap.get(name);
		if (cache == null) {
			Integer expire = this.expire.get(name);
			if (expire == null) {
				expire = 86400;
				this.expire.put(name, expire);
			}
			cache = new MemcachedCache(name, expire.intValue(), getMemcachedClient());
			cacheMap.put(name, cache);
		}
		return cache;
	}

	/**
	 * Gets the mem cache.
	 *
	 * @param name the name
	 * @return the mem cache
	 */
	public MemcachedCache getMemCache(String name) {
		return (MemcachedCache) getCache(name);
	}

	/* (non-Javadoc)
	 * @see org.springframework.cache.CacheManager#getCacheNames()
	 */
	@Override
	public Collection<String> getCacheNames() {
		List<String> nameList = new ArrayList<String>();
		for (Cache cache : cacheMap.values()) {
			nameList.add(cache.getName());
		}
		return nameList;
	}

	/**
	 * Sets the memcached client.
	 *
	 * @param memcachedClient the new memcached client
	 */
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	/**
	 * Sets expire.
	 *
	 * @param expire the expire
	 */
	public void setExpire(Map<String, Integer> expire) {
		this.expire = expire;
	}

	/**
	 * Gets expire.
	 *
	 * @return the expire
	 */
	public Map<String, Integer> getExpire() {
		return expire;
	}

	/**
	 * Clear all.
	 */
	public void clearAll() {
		for (Cache cache : loadCaches()) {
			cache.clear();
		}
	}
}
