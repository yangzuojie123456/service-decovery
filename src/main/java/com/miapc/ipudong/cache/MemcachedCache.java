package com.miapc.ipudong.cache;


import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by wangwei on 15/5/6.
 */
public class MemcachedCache implements Cache {

	/** The name. */
    private final String name;
    
    /** The mem cache. */
    private final MemCache memCache;

    /**
     * Instantiates a new memcached cache.
     *
     * @param name            the name
     * @param expire          the expire
     * @param memcachedClient the memcached client
     */
    public MemcachedCache(String name, int expire, MemcachedClient memcachedClient) {
        this.name = name;
        this.memCache = new MemCache(name, expire, memcachedClient);
    }

    /* (non-Javadoc)
     * @see org.springframework.cache.Cache#clear()
     */
    @Override
    public void clear() {
        memCache.clear();
    }

    /* (non-Javadoc)
     * @see org.springframework.cache.Cache#evict(java.lang.Object)
     */
    @Override
    public void evict(Object key) {
        memCache.delete(key.toString());
    }

    /* (non-Javadoc)
     * @see org.springframework.cache.Cache#get(java.lang.Object)
     */
    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = null;
        Object value = memCache.get(key.toString());
        if (value != null) {
            wrapper = new SimpleValueWrapper(value);
        }
        return wrapper;
    }

    /**
     * Gets the value.
     *
     * @param key the key
     * @return the value
     */
    public Object getValue(Object key) {
        ValueWrapper wrapper =get(key.toString());
        if (wrapper != null) {
            return wrapper.get();
        }
        return wrapper;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.cache.Cache#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /* (non-Javadoc)
     * @see org.springframework.cache.Cache#getNativeCache()
     */
    @Override
    public MemCache getNativeCache() {
        return this.memCache;
    }

    /* (non-Javadoc)
     * @see org.springframework.cache.Cache#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public void put(Object key, Object value) {
        memCache.put(key.toString(), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        ValueWrapper result = get(key.toString());
        if(result!=null){
            return result;
        }else {
            put(key,value);
        }
        return new SimpleValueWrapper(value);
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param key the key
     * @param type the type
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> type) {
        Object cacheValue = this.memCache.get(key.toString());
        Object value = (cacheValue != null ? cacheValue : null);
        if (type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }

    /**
     * Get keys set.
     *
     * @return the set
     */
    public Set<String> getKeys(){
        return this.memCache.getKeySet();
    }

    /**
     * Size.
     *
     * @return the int
     */
    public int size(){
        return memCache.size();
    }

    /**
     * Gets the values.
     *
     * @return the values
     */
    public Object[] getValues() {
        return memCache.getValues();
    }

    /**
     * Gets the memCache.
     *
     * @return the memCache
     */
    public MemCache getMemCache() {
		return memCache;
	}

	@Override
	public <T> T get(Object arg0, Callable<T> arg1) {
		// TODO 自动生成的方法存根
		return null;
	}

}